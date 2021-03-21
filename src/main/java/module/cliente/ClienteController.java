package module.cliente;

//import org.apache.log4j.Logger;
import module.login.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;

import global.StatusView;
import module.contador.Contador;
import module.usuario.Usuario;
import module.seleciona_contador.SelecionaContadorController;
import module.versao.VersaoDAO;


public class ClienteController {

    private final static Logger logger = LoggerFactory.getLogger(ClienteController.class.getName());

    private final ClienteGUI view;
    private final ClienteHelper helper;
    private Cliente clienteSelecionado;
    private ClienteDAO clienteDAO;
    private int rowSelected;

    private final Usuario usuarioLogado;

    private StatusView statusView;

    private JButton btnEditar;
    private JButton btnNovo;
    private JButton btnCancelar;
    private JButton btnExcluir;

    private JTextField txtPesquisa;

    public ClienteController(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        this.view = new ClienteGUI();

        this.helper = new ClienteHelper(view);
        init_controller();
        setStatusView(StatusView.INICIAL);
    }

    private void montarJanela(JFrame view) {
        final JDialog frame = new JDialog(view);

        frame.setContentPane(view.getContentPane());
        frame.pack();
        frame.setAutoRequestFocus(true);
        frame.setModal(true);
        frame.setSize(view.getSize());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void init_controller() {
        MaskFormatter maskData = null;
        try {
            maskData = new MaskFormatter("##.###.###/####-##");
            maskData.install(view.getTxtCNPJ());
        } catch (ParseException e1) {
            logger.warn("Falha ao aplicar mascara CNPJ");
        }

        btnCancelar = view.getBtnCancelar();
        btnEditar = view.getBtnEditar();
        btnNovo = view.getBtnNovo();
        btnExcluir = view.getBtnExcluir();

        txtPesquisa = view.getTxtPesquisa();

        JTable table = view.getTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    evt.consume();
                    carregaForm();
                }
            }
        });

        btnEditar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if ((statusView == StatusView.INICIAL || statusView == StatusView.VISUALIZAR)) {
                    helper.focusInTabCad(1);
                    setStatusView(StatusView.MODIFICAR);
                } else if (statusView == StatusView.CRIAR || statusView == StatusView.MODIFICAR || statusView == StatusView.SALVAR) {
                    setStatusView(StatusView.SALVAR);
                } else {
                    view.mensagemView("Cadastro Cliente", "Selecione um registro para ser editado! ", "Warning");
                }
            }
        });

        btnNovo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                setStatusView(StatusView.CRIAR);
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setStatusView(StatusView.CANCELAR);
            }
        });

        btnExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                setStatusView(StatusView.EXCLUIR);
            }
        });

        view.getBtnProcuraContadorDetalhes().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                selecionaContador();
            }
        });

        txtPesquisa.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                // if (e.getKeyCode() == KeyEvent.VK_ENTER)
                pesquisaPor();
            }
        });

        this.atualizaTabela();
        this.carregaCmbVersao();
    }

    private void salvarDados() {
        if (helper.validarCampos()) {

            helper.obterDados(clienteSelecionado);

            if (clienteSelecionado.getId() == null) {
                // user updatedAt
                getClienteDAO().insert(clienteSelecionado);
                helper.insertClienteTableModel(clienteSelecionado);
                LoggerFactory.getLogger(ClienteController.class.getName()).info("UsuarioId: " + usuarioLogado.getId() + " cadastrou - cliente " + clienteSelecionado.getId());
            } else {
                getClienteDAO().update(clienteSelecionado);
                helper.updateClienteTableModel(rowSelected, clienteSelecionado);
                LoggerFactory.getLogger(ClienteController.class.getName()).info("UsuarioId: " + usuarioLogado.getId() + " alterou - cliente " + clienteSelecionado.getId());
            }

            setStatusView(StatusView.VISUALIZAR);
        } else {
            view.mensagemView("Cadastro Cliente", "Falha ao validar os campos, por favor preencha corretamente! ",
                    "Warning");
            LoggerFactory.getLogger(ClienteController.class.getName()).info("UsuarioId: " + usuarioLogado.getId() + " falha na validacao dos campos cadastro cliente");
        }

    }

    private void excluirDados() {
        if (helper.getActiveTabCad() == 0) {
            carregaForm();
        }
        if (clienteSelecionado.getId() == null) {
            view.mensagemView("Atenção", "Selecione um item da tabela para excluir!", "");
            return;
        }
        int resp = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o item selecionado? \n" + clienteSelecionado.getNome(),
                "Excluir item", JOptionPane.YES_NO_OPTION);

        if (resp == JOptionPane.YES_OPTION) {


            getClienteDAO().delete(clienteSelecionado);
            helper.removeClienteTableModel(rowSelected);

            LoggerFactory.getLogger(ClienteController.class.getName()).info("UsuarioId: " + usuarioLogado.getId() + " removeu - cliente " + clienteSelecionado.getId());

            setStatusView(StatusView.INICIAL);

            helper.limparCampos();
            helper.focusInTabCad(0);
            JOptionPane.showMessageDialog(null, "Item excluido!");
        } else {
            setStatusView(StatusView.VISUALIZAR);
        }

    }

    public ClienteDAO getClienteDAO() {
        if (clienteDAO == null)
            clienteDAO = new ClienteDAO(); // ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO;
    }

    private void selecionaContador() {
        SelecionaContadorController janela = new SelecionaContadorController(this);
        montarJanela(janela.getView());
    }

    public void carregaCmbVersao() {
        helper.preencherCmbVersao(new VersaoDAO().selectAll());
    }

    public void atualizaTabela() {
        helper.preencherTabela(getClienteDAO().selectAll());
    }

    public void pesquisaPor() {
        TableRowSorter<ClienteTableModel> sorter = (TableRowSorter) view.getTable().getRowSorter();

        String text = Pattern.quote(txtPesquisa.getText());
        String regex = String.format("(?i)(%s)", text);

        sorter.setRowFilter(RowFilter.regexFilter(regex));
    }

    public void carregaForm() {
        rowSelected = view.getTable().getSelectedRow();
        rowSelected = view.getTable().getRowSorter().convertRowIndexToModel(rowSelected > -1 ? rowSelected : 0);

        clienteSelecionado = helper.getClienteFromModel(rowSelected);
        try {
            helper.preencherCampos(clienteSelecionado);
            helper.focusInTabCad(1);
        } catch (Exception e) {
            logger.warn("ID não encontrado!");
        }
    }

    public ClienteGUI getView() {
        return view;
    }

    public void setContador(Contador contador) {
        helper.setContador(contador);
    }

    public void setStatusView(StatusView statusView) {
        this.statusView = statusView;

        LoggerFactory.getLogger(ClienteController.class.getName()).info("statusView - " + this.statusView);

        switch (statusView) {
            case CANCELAR: // Cancelar
                helper.limparCampos();
                if (clienteSelecionado != null && clienteSelecionado.getId() != null) {
                    carregaForm();
                    setStatusView(StatusView.VISUALIZAR);
                } else {
                    setStatusView(StatusView.INICIAL);
                }
                break;
            case INICIAL: // Start
//			helper.bloquearBotoes(true);
                helper.bloquearCampos(true);
                btnEditar.setText("Editar");
                btnCancelar.setEnabled(false);
                btnNovo.setEnabled(true);
                helper.focusInTabCad(0);
                view.getTabbedPane().setEnabledAt(0, true);
                break;
            case VISUALIZAR: // Visualizacao
                helper.bloquearCampos(true);
                btnEditar.setText("Editar");
                btnCancelar.setEnabled(false);
                btnNovo.setEnabled(true);
                view.getTabbedPane().setEnabledAt(0, true);
                helper.focusInTabCad(1);
                break;
            case CRIAR: // Novo
                clienteSelecionado = new Cliente();
                helper.limparCampos();
                btnNovo.setEnabled(false);
                btnCancelar.setEnabled(true);
                helper.bloquearCampos(false);
                btnEditar.setText("Salvar");
                view.getTabbedPane().setEnabledAt(0, false);
                helper.focusInTabCad(1);
                break;
            case MODIFICAR: // Edicao
                carregaForm();
                btnNovo.setEnabled(false);
                helper.bloquearCampos(false);
                btnCancelar.setEnabled(true);
                btnEditar.setText("Salvar");
                view.getTabbedPane().setEnabledAt(0, false);
                helper.focusInTabCad(1);
                break;
            case SALVAR:
                salvarDados();
                break;
            case EXCLUIR:
                excluirDados();
                break;
            default:
                break;
        }

    }

}
