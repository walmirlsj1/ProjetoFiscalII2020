package module.fiscal;

import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.mail.EmailException;

//import org.apache.log4j.Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.security.InvalidKeyException;

import java.awt.event.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.text.ParseException;
import javax.swing.*;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;


import global.Status;
import global.util.pilha.LinkedQueueque;
import global.util.file.FileListTransferHandler;
import global.util.fiscal.GeradorFiscal;
import global.util.mail.CommonsMail;
import global.service.ServiceCriptografia;
import global.service.config.Config;

import module.usuario.Usuario;
import module.versao.VersaoDAO;

public class FiscalController {
    private final static Logger logger = LoggerFactory.getLogger(FiscalController.class.getName());

    private static final String chave_criptografia = "$#@!RaGnaRoK1!@#$";
    private ServiceCriptografia servicoCripto;


    private final FiscalGUI view;
    private final FiscalHelper helper;
    private final Usuario usuarioLogado;

    private JList<LivroFiscal> listFiles;

    private JTextField txtPesquisa;

    private FiscalDAO fiscalDAO;

    private JList<File> panel_drag_drop;


    private LinkedQueueque<Fiscal> tarefaEnviarEmail;

    private final Runnable threadEmail;

    private boolean running;

    private MaskFormatter maskData;

    private int mes;
    private int ano;

    private final DefaultListModel<File> defaultListModelFile;

    private int rowSelected;
    private Fiscal fiscalSelecionado;


    public FiscalController(Usuario usuarioLogado) {

        this.usuarioLogado = usuarioLogado;

        defaultListModelFile = new DefaultListModel<>();

        this.view = new FiscalGUI();
        this.helper = new FiscalHelper(view);
        init_controller();
//        this.view.setVisible(true);
        try {
            servicoCripto = new ServiceCriptografia(chave_criptografia);
        } catch (InvalidKeyException e) {
            logger.trace("Falha ao criar servico de criptografia");
        }

        tarefaEnviarEmail = new LinkedQueueque<>();

        running = false;


//
        // modelo dos filosofos
        threadEmail = () -> {
            while (running) {
                if (tarefaEnviarEmail.isEmpty()) {
                    running = false;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        logger.debug("InterruptedException");
                    }
                    helper.mostrarProgress(false);
                    return;
                } else {
                    progressProcessando();
                    progressInderminate();
//                    helper.atualizaProgress(threadEmailContador, tarefaEnviarEmail.size());
                    executarTarefaPendente();

                    progressClose();
                }
            }

        };
    }

    @SuppressWarnings("serial")
    public void init_controller() {
        this.carregaCmbVersao();
        this.carregaCmbMesFiscal();
        this.carregaCmbAnoFiscal();

        try {
            maskData = new MaskFormatter("##.###.###/####-##");
            maskData.setValidCharacters("1234567890");
            maskData.setValueContainsLiteralCharacters(false);
            maskData.install(view.getTxtCNPJ());
        } catch (ParseException e1) {
            logger.warn("Falha ao aplicar mascara CNPJ " + view.getTxtCNPJ().getText());
        }


        txtPesquisa = view.getTxtPesquisa();


        panel_drag_drop = view.getPanel_drag_drop();
        panel_drag_drop.setDragEnabled(true);
        panel_drag_drop.setTransferHandler(new FileListTransferHandler(panel_drag_drop));

        listFiles = (JList<LivroFiscal>) view.getListFiles();
        listFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listFiles.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    e.consume();
//                    if(listFiles.getModel().getSize()==0) return;
                    int index = listFiles.locationToIndex(e.getPoint());

                    LivroFiscal livro = listFiles.getModel().getElementAt(index);

                    fiscalSelecionado.getListaLivroFiscal().remove(livro);

                    logger.warn("DELETE livro: " + livro.getId() + " " + livro.getFileName() + " " + livro.getModificado() + " Fiscal: " + fiscalSelecionado.getId());

                    getFiscalDAO().update(fiscalSelecionado);

                    preencheListaArquivos();
                }
            }

        });
        JTable table = view.getTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    evt.consume();
                    carregaForm(null);
                    helper.focusInTabCad(1);
                }
            }

        });


        view.getBtnSalvarArquivos().addActionListener(evt ->
                salvarArquivosNaPastaCliente()
        );

        txtPesquisa.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                pesquisaPor();
            }
        });

        view.getCmbMesFiscal().addActionListener(evt -> {
            mes = view.getCmbMesFiscal().getSelectedIndex() + 1;
            helper.limparCampos();
            atualizaTabela();
        });

        view.getCmbAnoFiscal().addActionListener(evt -> {
            ano = Integer.parseInt(Objects.requireNonNull(view.getCmbAnoFiscal().getSelectedItem()).toString());
            helper.limparCampos();
            atualizaTabela();
        });

        view.getBtnGerarFiscal().addActionListener(evt -> new Thread(() -> {
            view.getBtnGerarFiscal().setEnabled(false);

            progressProcessando();

            GeradorFiscal.gerarFiscal(helper.getProgressBar(), mes, ano);

            progressAtualizando();
            progressInderminate();

            try {
                Thread.sleep(3000);
                atualizaTabela();
                progressInderminate();
            } catch (InterruptedException e) {
                logger.debug("InterruptedException");
            }

            view.getBtnGerarFiscal().setEnabled(true);
            progressClose();

        }).start());

        view.getBtnEnviarPendentes().addActionListener(evt -> {
            List<Fiscal> lstFiscal = helper.getListFromModel();
            if (!lstFiscal.isEmpty()) {
                //@FIXME pode ser melhor, se fosse em ordem conforme a lista, "alfabetica/cnpj/id/etc"

                lstFiscal.stream()
                        .filter(f -> (f.getStatus() == Status.NAO_GEROU_DOC_FISCAL || f.getStatus() == Status.AGUARDANDO_ENVIO || f.getStatus() == Status.FALHA_ENVIO))
                        .forEach(this::verificaAntesDeEnviar);
            }
        });

        try {
            view.getBtnAnydesk().setIcon(new ImageIcon(getClass().getResource("/imagens/anydesk_icon18x18.png")));
        } catch (Exception e) {
            logger.warn("btnAnyDesk - Não foi possivel localizar a imagem.");
        }

        view.getBtnAnydesk().addActionListener(evt -> {
            try {
                boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

                ProcessBuilder processBuilder = new ProcessBuilder();

                if (isWindows) {
                    processBuilder.command("cmd.exe", "/c", "anydesk " + view.getTxtIdRemoto().getText());
                } else {
                    processBuilder.command("sh", "-c", "anydesk " + view.getTxtIdRemoto().getText());
                }
                processBuilder.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        view.getBtnCarregarArquivos().addActionListener(evt -> {
            //@FIXME UPDATE HERE livroFiscal

            progressProcessando();
            progressInderminate();

            preencheListaArquivos();

            progressClose();
        });

        view.getBtnEnviarEmail().addActionListener(evt -> enviaEmail(fiscalSelecionado));

        view.getBtnResetaStatus().addActionListener(evt -> atualizaStatusFiscal(Status.PENDENTE));

        view.getBtnAguardaEnvio().addActionListener(evt -> atualizaStatusFiscal(Status.AGUARDANDO_ENVIO));

        view.getBtnConcluirManual().addActionListener(evt -> atualizaStatusFiscal(Status.CONCLUIDO_MANUAL));

        view.getBtnClienteEnviou().addActionListener(evt -> atualizaStatusFiscal(Status.CLIENTE_ENVIOU));

        view.getBtnClienteNoEmitiu().addActionListener(evt -> atualizaStatusFiscal(Status.NAO_GEROU_DOC_FISCAL));

        view.getBtnFirstRegTable().addActionListener(evt -> moveRegTable(0));

        view.getBtnBackRegTable().addActionListener(evt -> moveRegTable(1));

        view.getBtnNextRegTable().addActionListener(evt -> moveRegTable(2));

        view.getBtnEndRegTable().addActionListener(evt -> moveRegTable(3));

        view.getBtnConfigSMTP().addActionListener(evt -> logger.warn("Não implementado! - btnConfigSMTP"));

        mes = view.getCmbMesFiscal().getSelectedIndex() + 1;
        ano = Integer.parseInt(Objects.requireNonNull(view.getCmbAnoFiscal().getSelectedItem()).toString());
        this.atualizaTabela();
    }

    private void verificaAntesDeEnviar(Fiscal f) {
        carregaForm(f);
        helper.focusInTabCad(1);

        if (JOptionPane.showConfirmDialog(null,
                "Confera os dados! \n Antes de enviar, \n enviar? \n Status: " + f.getStatus(), "Atenção!!!",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            enviaEmail(f);
        }
    }

    protected void moveRegTable(int action) {
        int index = view.getTable().getSelectedRow();
        switch (action) {
            case 0:
                index = 0;
                break;
            case 1:
                index--;// = (index + view.getTable().getRowCount() - 1) %
                // view.getTable().getRowCount();
                break;
            case 2:
                index++;// = (index + 1) % view.getTable().getRowCount();
                break;
            case 3:
                index = view.getTable().getRowCount() - 1;
                break;
            default:
                break;
        }
        panel_drag_drop.setModel(defaultListModelFile);
        if (index > -1 && index < view.getTable().getRowCount()) {
            selecionaRow(index);
            carregaForm(null);
        }
    }

    private void selecionaRow(int index) {
        view.getTable().setRowSelectionInterval(index, index);
        view.getTable().scrollRectToVisible(view.getTable().getCellRect(index, 0, true));
    }

    private void salvarArquivosNaPastaCliente() {
        if (fiscalSelecionado == null) return;
        progressProcessando();

        DefaultListModel<File> model = (DefaultListModel<File>) panel_drag_drop.getModel();
        progressSetMaximum(model.getSize());

//        LivroFiscalDAO livroDAO = new LivroFiscalDAO();
        LivroFiscal livro;

        File source;

        ByteArrayOutputStream bos;
        FileInputStream fis;

//        livroDAO.abrindo();

        for (int i = 0; i < model.getSize(); i++) {

            livro = new LivroFiscal();
            livro.setFiscal(fiscalSelecionado);

            source = Paths.get(model.getElementAt(i).toString()).toFile();

            try {
                fis = new FileInputStream(source);
                bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];

                for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                    bos.write(buf, 0, readNum); //no doubt here is 0
                }

                livro.setFileName(source.getName());

//                livro.setFile(BlobProxy.generateProxy(servicoCripto.criptografa(bos.toByteArray())));

                livro.setFile(servicoCripto.criptografa(bos.toByteArray()));

                fiscalSelecionado.getListaLivroFiscal().add(livro);
            } catch (IOException e) {
                logger.trace("Falha na leitura do arquivo " + source.getAbsolutePath());
            } catch (Exception e) {
                logger.trace("Falha ao gravar na base " + source.getAbsolutePath());
            }

            progressUpdate(i);
        }

        panel_drag_drop.setModel(defaultListModelFile);
        atualizaStatusFiscal(Status.AGUARDANDO_ENVIO);
        progressClose();
        preencheListaArquivos();
    }

    public void preencheListaArquivos() {
        if (fiscalSelecionado == null) {
            listFiles.setModel(new DefaultListModel<>());
            return;
        }

        DefaultListModel<LivroFiscal> listModel = new DefaultListModel<>();
//        listModel.addAll(fiscalSelecionado.getListaLivroFiscal());
        List<LivroFiscal> lista = fiscalSelecionado.getListaLivroFiscal();
        if (!lista.isEmpty()) {
            lista.forEach(l -> listModel.addElement(l));
        }

        listFiles.setModel(listModel);
    }

    public void enviaEmail(Fiscal fiscal) {

        if (tarefaEnviarEmail == null) {
            tarefaEnviarEmail = new LinkedQueueque<>();
        }
        if (!tarefaEnviarEmail.buscar(fiscal)) {
            tarefaEnviarEmail.add(fiscal);
        } else {
            logger.warn("Está email ja foi adicionado na lista de processamento!");
        }

        if (!this.running) {
            this.running = true;
            new Thread(threadEmail).start();
        }
    }

    public String obterMesString(int mes) {
        String[] meses = {"JAN", "FEV", "MAR", "ABR", "MAI", "JUN", "JUL", "AGO", "SET", "OUT", "NOV", "DEZ"};
        return meses[mes];
    }

    private void carregaCmbMesFiscal() {
        ArrayList<String> lstMes = new ArrayList<>(Arrays.asList("Janeiro", "Fevereiro", "Março", "Abril", "Maio",
                "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"));

        helper.preencherCmbMesFiscal(lstMes);
    }

    private void carregaCmbAnoFiscal() {
        ArrayList<String> lstAno = new ArrayList<>(Arrays.asList("2019", "2020", "2021", "2022", "2023"));

        helper.preencherCmbAnoFiscal(lstAno);

    }

    public void carregaCmbVersao() {
        VersaoDAO versaoDAO = new VersaoDAO();
        helper.preencherCmbVersao(versaoDAO.selectAll());
    }

    public void atualizaTabela() {
        progressAtualizando();

        helper.preencherTabela(getFiscalDAO().selectFilterMesAno(mes, ano));

        if (fiscalSelecionado != null) {

            Optional<Fiscal> fiscal1 = helper.getListFromModel().stream().filter(o1 -> o1.getCliente().getId().equals(fiscalSelecionado.getCliente().getId())).findFirst();
            if (fiscal1.isPresent()) {
                fiscalSelecionado = fiscal1.get();
                atualizaSelectedRowIndexToView(helper.getIndexFromModel(fiscalSelecionado));
                fiscalSelecionado = helper.getFiscalFromModel(rowSelected);
            }
        }
        progressClose();
    }

    public void pesquisaPor() {
        TableRowSorter sorter = ((TableRowSorter) view.getTable().getRowSorter());

        String text = Pattern.quote(txtPesquisa.getText());
        String regex = String.format("(?i)(%s)", text);

        sorter.setRowFilter(RowFilter.regexFilter(regex));
    }

    private void atualizaSelectedRowIndexToModel(int rowSelected2) {
        rowSelected = view.getTable().getRowSorter().convertRowIndexToModel(rowSelected2 > -1 ? rowSelected2 : 0);
        selecionaRow(rowSelected2);
    }

    private void atualizaSelectedRowIndexToView(int rowSelected2) {
        rowSelected = rowSelected2;
        selecionaRow(view.getTable().getRowSorter().convertRowIndexToView(rowSelected2 > -1 ? rowSelected2 : 0));
    }

    public void carregaForm(Fiscal fiscal) {

        // @FIXME TA FEIO EM
        if (fiscal != null) {
            fiscalSelecionado = fiscal;
            atualizaSelectedRowIndexToView(helper.getIndexFromModel(fiscalSelecionado));
        } else {
            atualizaSelectedRowIndexToModel(view.getTable().getSelectedRow());
            fiscalSelecionado = helper.getFiscalFromModel(rowSelected);
        }

        helper.preencherCampos(fiscalSelecionado);

        preencheListaArquivos();

        helper.focusInTabCad(1);
    }

    private void regLogFileEmail(String log) {
        String pathFile = "";
        try {
            pathFile = Config.getConfiguracao().getString("path_log_email");
        } catch (ConfigurationException | IOException e1) {
            // TODO Auto-generated catch block
            logger.warn("Falha ao gravar log diretorio: " + pathFile);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathFile, true))) {
            writer.write(log);
            writer.newLine();
            writer.flush();
//                writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void progressSetMaximum(int value) {
        helper.getProgressBar().setMaximum(value);
    }

    private void progressInderminate() {
        helper.getProgressBar().setIndeterminate(true);
    }

    private void progressUpdate(int value) {
        helper.getProgressBar().setValue(value);
    }

    private void progressProcessando() {
        helper.mostrarProgress(true);
        helper.atualizaStatusProgress("Processando...");
    }

    private void progressAtualizando() {
        helper.mostrarProgress(true);
        helper.atualizaStatusProgress("Atualizando tabela");
        helper.getProgressBar().setIndeterminate(true);
    }

    private void progressClose() {
        helper.getProgressBar().setIndeterminate(false);
        helper.mostrarProgress(false);
    }

    private void executarTarefaPendente() {

        List<LivroFiscal> listaLivroFiscal;
        Fiscal fiscal;

        String log;

        fiscal = tarefaEnviarEmail.remove();
        log = "";

        if (fiscal.getStatus() == Status.PENDENTE
                || fiscal.getStatus() == Status.CONCLUIDO
                || fiscal.getStatus() == Status.CLIENTE_ENVIOU
                || fiscal.getStatus() == Status.CONCLUIDO_MANUAL)
            return;

        //|| fiscal.getStatus() == Status.NAO_GEROU_DOC_FISCAL esse deve emitir enviar uma msg tbm
        String cnpj = null;
        try {
            cnpj = maskData.valueToString(fiscal.getCliente().getCnpj());
        } catch (ParseException e1) {
            logger.warn("Erro ao aplicar mascara: " + fiscal.getCliente().getCnpj());
        }

        CommonsMail email = gerarEmail(fiscal);

        if (fiscal.getStatus() == Status.AGUARDANDO_ENVIO) {
            byte[] zipTemp = null;
            listaLivroFiscal = fiscal.getListaLivroFiscal();

            if (!listaLivroFiscal.isEmpty()) {

                HashMap<String, byte[]> arquivos = new HashMap<>();

                listaLivroFiscal.forEach(l -> {
                    byte[] file = servicoCripto.decriptografa(l.getFile());
                    arquivos.put(l.getFileName(), file);
                });

                try {
                    zipTemp = zipMemory(arquivos);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                email.addAnexo(fiscal.getCliente().getNome() + "-" + fiscal.getMes() + "-" + fiscal.getAno() + ".zip", zipTemp);

                email.setMsg("Segue em anexo, os arquivos fiscais, \n\n\n att. " + usuarioLogado.getNome()); // username
                email.setTitulo("Arquivos Fiscais - " + obterMesString(mes - 1) + "/" + ano + " - "
                        + fiscal.getCliente().getNome() + " - " + cnpj);

                log += "Arquivos Fiscais - " + obterMesString(mes - 1) + "/" + ano + " - "
                        + fiscal.getCliente().getNome() + " - " + cnpj + " enviado arquivo para: "
                        + fiscal.getCliente().getContador().getEmail() + " ";

                try {
                    email.enviaEmailComAnexo();
                    fiscal.setDataEnvio(new Date());
                    atualizaStatusFiscal(fiscal, Status.CONCLUIDO);
                } catch (EmailException e) {
                    e.printStackTrace();
                    atualizaStatusFiscal(fiscal, Status.FALHA_ENVIO);
                }
            } else {
                atualizaStatusFiscal(fiscal, Status.FALHA_ENVIO);
                logger.warn("Lista vazia de arquivos" + cnpj);
            }
        } else {
            email.setMsg("Informando que o cliente: " + fiscal.getCliente().getNome()
                    + "\nInscrito no CNPJ: " + cnpj
                    + "\nNão gerou documentos fiscais durante o mês " + obterMesString(mes - 1) + "/" + ano
                    + ",\n\n\n att. " + usuarioLogado.getNome());

            email.setTitulo("Não gerou documentos fiscais - " + obterMesString(mes - 1) + "/" + ano + " - "
                    + fiscal.getCliente().getNome() + " - " + cnpj);

            log += "Não gerou documentos fiscais - " + obterMesString(mes - 1) + "/" + ano + " - "
                    + fiscal.getCliente().getNome() + " - " + cnpj + " enviado arquivo para: "
                    + fiscal.getCliente().getContador().getEmail() + " ";

            try {
                email.enviaEmailSimples();
                fiscal.setDataEnvio(new Date());
                atualizaStatusFiscal(fiscal, Status.CONCLUIDO_NAO_GEROU_DOC_FISCAL);
            } catch (EmailException e) {
                e.printStackTrace();
                atualizaStatusFiscal(fiscal, Status.NAO_GEROU_DOC_FISCAL);
            }
        }


        log += fiscal.getStatus().name();

        regLogFileEmail(log);

    }

    private CommonsMail gerarEmail(Fiscal fiscal) {
        CommonsMail email = new CommonsMail();
        email.setDestinatario_email(fiscal.getCliente().getContador().getEmail());
        email.setDestinatario_nome(fiscal.getCliente().getContador().getNome());

        return email;
    }

    /**
     * NAO DEVE PERMANECER AQUI!!!
     * zipMemoryFriendGUJ
     * https://respostas.guj.com.br/16368-compactar-arquivo-em-java
     */
    private static byte[] zipMemory(Map<String, byte[]> files) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (ZipOutputStream zipfile = new ZipOutputStream(bos)) {
            for (Map.Entry<String, byte[]> file : files.entrySet()) {
                final ZipEntry zipEntry = new ZipEntry(file.getKey());
                zipfile.putNextEntry(zipEntry);
                zipfile.write(file.getValue(), 0, file.getValue().length);
            }
        }
        return bos.toByteArray();
    }


//    /**
//     * https://www.guj.com.br/t/converter-conteudo-de-campo-blob-para-string-resolvido/101184/5
//     *
//     * @return String
//     */
//    private String streamToString(InputStream in) throws IOException {
//        StringBuilder ret = new StringBuilder();
//        int lidos;
//        byte[] b = new byte[2048];
//        String temp;
//        while ((lidos = in.read(b)) != -1) {
//            temp = new String(b, 0, lidos);
//            ret.append(temp);
//        }
//        return ret.toString();
//    }

    private void atualizaStatusFiscal(Fiscal fiscal, Status status) {
        fiscal.setStatus(status);

        getFiscalDAO().update(fiscal);

        int rowFiscal = view.getTable().getRowSorter().convertRowIndexToModel(helper.getIndexFromModel(fiscal));

        helper.updateFiscalTableModel(rowFiscal, fiscal);
    }

    private void atualizaStatusFiscal(Status status) {
        if (fiscalSelecionado == null) return;

        fiscalSelecionado.setStatus(status);
        getFiscalDAO().update(fiscalSelecionado);
        helper.updateFiscalTableModel(rowSelected, fiscalSelecionado);
    }

    private FiscalDAO getFiscalDAO() {
        if (fiscalDAO == null)
            fiscalDAO = new FiscalDAO(); // ClienteDAO clienteDAO = new ClienteDAO();
        return fiscalDAO;
    }

    public FiscalGUI getView() {
        return view;
    }

}
