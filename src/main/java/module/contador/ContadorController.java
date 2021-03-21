package module.contador;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import java.text.ParseException;
import java.util.regex.Pattern;

import global.StatusView;
import module.usuario.Usuario;

public class ContadorController {

    private final ContadorGUI view;
    private final ContadorHelper helper;
    private Contador contadorSelecionado;
    private ContadorDAO contadorDAO;
    private int rowSelected;

    private final Usuario usuarioLogado;

    private StatusView statusView;

    private JButton btnEditar;
    private JButton btnNovo;
    private JButton btnCancelar;
    private JButton btnExcluir;

    private JTextField txtPesquisa;

    public ContadorController(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        this.view = new ContadorGUI();

        this.helper = new ContadorHelper(view);
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
//			"99.999.999/9999-99"(67) 9 9999-9999
            maskData.install(view.getTxtCNPJ());
        } catch (ParseException e1) {
            System.out.println("Falha ao aplicar mascara CNPJ");
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

        txtPesquisa.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                // if (e.getKeyCode() == KeyEvent.VK_ENTER)
                pesquisaPor();
            }
        });

        this.atualizaTabela();
    }

    private void salvarDados() {
        if (helper.validarCampos()) {

            helper.obterDados(contadorSelecionado);

            if (contadorSelecionado.getId() == null) {
                getContadorDAO().insert(contadorSelecionado);
                helper.insertContadorTableModel(contadorSelecionado);
            } else {
                getContadorDAO().update(contadorSelecionado);
                helper.updateContadorTableModel(rowSelected, contadorSelecionado);
            }
            setStatusView(StatusView.VISUALIZAR);
        } else {
            view.mensagemView("Cadastro Cliente", "Falha ao validar os campos, por favor preencha corretamente! ",
                    "Warning");
        }

    }

    private void excluirDados() {
        if (helper.getActiveTabCad() == 0) {
            carregaForm();
        }
        if (contadorSelecionado.getId() == null) {
            view.mensagemView("Atenção", "Selecione um item da tabela para excluir!", "");
            return;
        }
        int resp = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o item selecionado? \n" + contadorSelecionado.getNome(),
                "Excluir item", JOptionPane.YES_NO_OPTION);

        if (resp == JOptionPane.YES_OPTION) {

            getContadorDAO().delete(contadorSelecionado);
            helper.removeContadorTableModel(rowSelected);


            setStatusView(StatusView.INICIAL);

            helper.limparCampos();
            helper.focusInTabCad(0);
            JOptionPane.showMessageDialog(null, "Item excluido!");
        } else {
            setStatusView(StatusView.VISUALIZAR);
        }

    }

    public ContadorDAO getContadorDAO() {
        if (contadorDAO == null)
            contadorDAO = new ContadorDAO(); // ContadorDAO contadorDAO = new ContadorDAO();
        return contadorDAO;
    }

    public void atualizaTabela() {
        helper.preencherTabela(getContadorDAO().selectAll());
    }

    public void pesquisaPor() {
        TableRowSorter<ContadorTableModel> sorter = (TableRowSorter) view.getTable().getRowSorter();

        String text = Pattern.quote(txtPesquisa.getText());
        String regex = String.format("(?i)(%s)", text);

        sorter.setRowFilter(RowFilter.regexFilter(regex));
    }

    public void carregaForm() {
        rowSelected = view.getTable().getSelectedRow();
        rowSelected = view.getTable().getRowSorter().convertRowIndexToModel(rowSelected > -1 ? rowSelected : 0);

        contadorSelecionado = helper.getContadorFromModel(rowSelected);
        try {
            helper.preencherCampos(contadorSelecionado);
            helper.focusInTabCad(1);
        } catch (Exception e) {
            System.out.println("ID não encontrado!");
        }
    }

    public ContadorGUI getView() {
        return view;
    }

    public void setStatusView(StatusView statusView) {
        this.statusView = statusView;

        switch (statusView) {
            case CANCELAR: // Cancelar
                helper.limparCampos();
                if (contadorSelecionado != null && contadorSelecionado.getId() != null) {
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
                contadorSelecionado = new Contador();
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
