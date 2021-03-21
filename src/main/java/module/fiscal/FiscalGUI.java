package module.fiscal;

import module.versao.Versao;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;

import javax.swing.LayoutStyle.ComponentPlacement;
import java.io.File;

import javax.swing.JList;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import java.awt.SystemColor;

/**
 * @author ghost
 */
public class FiscalGUI extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = -7984720303972360445L;

//	private final FiscalController controller;

    private JPanel contentPane;

    private JTabbedPane tabbedPane;

    private int id;
    private int fiscal_id;
    private int contadorId;

    private JPanel panel_lista;
    private JPanel panel_detalhe;

    private JComboBox<String> cmbMesFiscal;
    private JComboBox<String> cmbAnoFiscal;

    private JTable table;
    private JTextField txtNome;
    private JFormattedTextField txtCNPJ;
    private JFormattedTextField txtTelefone;
    private JTextField txtSerial;
    private JTextField txtContador;
    private JComboBox<Versao> cmbVersao;
    private JTextField txtEmail;
    private JTextField txtPesquisa;
    private JTextField txtIdRemoto;
    private JCheckBox chbEnviaDadosContador;
    private JButton btnProcuraContadorDetalhes;
    private JButton btnFirstRegTable;
    private JButton btnBackRegTable;
    private JButton btnNextRegTable;
    private JButton btnEndRegTable;
    private JButton btnAguardaEnvio;
    private JButton btnConcluirManual;
    private JButton btnClienteEnviou;
    private JButton btnClienteNoEmitiu;

    private JList<?> listFiles;

    private JButton btnGerarFiscal;
    private JButton btnEnviarPendentes;
    private JButton btnCarregarArquivos;

    private JButton btnEnviarEmail;
    private JButton btnConfigSMTP;

    private JList<File> panel_drag_drop;
    private JList<File> panel_drag_drop_1;
    private JButton btnSalvarArquivos;
    private ImageIcon tIcon;
    private JButton btnAnydesk;
    private JButton btnResetaStatus;

    private JLabel lblQtdFalha, lblQtdNaoGerou, lblQtdAguardando, lblQtdConcluido,
            lblQtdPendente, lblQtdTotal;

    private JLabel lblProgress;
    private JProgressBar progressBar;

    private JLabel lblFiscalStatusName, lblFiscalStatusEnviadoEm, lblFiscalStatusUsername;

    /**
     * Create the frame.
     */
    public FiscalGUI() {
        initComponents();
        JToolBar toolBar = new JToolBar();
        getContentPane().add(toolBar, BorderLayout.SOUTH);
    }

    public void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 808, 649);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.control);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBorder(new LineBorder(Color.DARK_GRAY));

        JPanel panel_2 = new JPanel();
        panel_2.setLayout(null);

        btnBackRegTable = new JButton("<");
        btnBackRegTable.setBounds(52, 0, 51, 44);
        panel_2.add(btnBackRegTable);

        btnFirstRegTable = new JButton("<<");
        btnFirstRegTable.setBounds(0, 0, 51, 44);
        panel_2.add(btnFirstRegTable);

        txtPesquisa = new JTextField();

        txtPesquisa.setColumns(10);
        txtPesquisa.setBounds(600, 11, 159, 20);
        panel_2.add(txtPesquisa);

        JLabel label_2 = new JLabel("Pesquisar");
        label_2.setBounds(548, 15, 66, 14);
        panel_2.add(label_2);

        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(Color.DARK_GRAY));

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane
                .setHorizontalGroup(
                        gl_contentPane.createParallelGroup(Alignment.LEADING)
                                .addGroup(gl_contentPane.createSequentialGroup()
                                        .addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 769,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap())
                                .addComponent(panel, GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE)
                                .addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE));
        gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                        .addComponent(panel, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 501, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        panel.setLayout(null);

        JLabel lblNewLabel_8 = new JLabel("Mes");
        lblNewLabel_8.setBounds(543, 14, 34, 14);
        panel.add(lblNewLabel_8);

        cmbMesFiscal = new JComboBox();
        cmbMesFiscal.setBounds(574, 11, 89, 20);
        panel.add(cmbMesFiscal);

        cmbAnoFiscal = new JComboBox();
        cmbAnoFiscal.setBounds(697, 11, 75, 20);
        panel.add(cmbAnoFiscal);

        JLabel lblNewLabel_9 = new JLabel("Ano");
        lblNewLabel_9.setBounds(673, 14, 24, 14);
        panel.add(lblNewLabel_9);

        btnGerarFiscal = new JButton("Gerar Fiscal >>");
        btnGerarFiscal.setBounds(411, 10, 122, 23);
        panel.add(btnGerarFiscal);

        btnEnviarPendentes = new JButton("Enviar Pendentes");
        btnEnviarPendentes.setBounds(267, 10, 134, 23);
        panel.add(btnEnviarPendentes);

        JPanel panel_6 = new JPanel();
        panel_6.setBackground(SystemColor.controlHighlight);
        panel_6.setBorder(new LineBorder(Color.DARK_GRAY));
        panel_6.setBounds(0, 0, 122, 42);
        panel.add(panel_6);
        panel_6.setLayout(null);

        JLabel lblNewLabel_10 = new JLabel("FISCAL");
        lblNewLabel_10.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel_10.setBounds(20, 11, 92, 20);
        panel_6.add(lblNewLabel_10);

        btnConfigSMTP = new JButton("Configuração");
        btnConfigSMTP.setBounds(132, 10, 101, 23);
        panel.add(btnConfigSMTP);

        btnNextRegTable = new JButton(">");
        btnNextRegTable.setBounds(104, 0, 51, 44);
        panel_2.add(btnNextRegTable);

        btnEndRegTable = new JButton(">>");
        btnEndRegTable.setBounds(156, 0, 51, 44);
        panel_2.add(btnEndRegTable);

        progressBar = new JProgressBar();
        progressBar.setBounds(414, 17, 117, 14);
        panel_2.add(progressBar);

        lblProgress = new JLabel("Processando");
        lblProgress.setBounds(414, 0, 134, 14);
        panel_2.add(lblProgress);

        lblQtdPendente = new JLabel("Pendente:");
        lblQtdPendente.setBounds(214, 0, 95, 14);
        panel_2.add(lblQtdPendente);

        lblQtdConcluido = new JLabel("Concluido:");
        lblQtdConcluido.setBounds(309, 0, 95, 14);
        panel_2.add(lblQtdConcluido);

        lblQtdAguardando = new JLabel("Aguardando:");
        lblQtdAguardando.setBounds(214, 15, 95, 14);
        panel_2.add(lblQtdAguardando);

        lblQtdNaoGerou = new JLabel("Não gerou:");
        lblQtdNaoGerou.setBounds(309, 30, 93, 14);
        panel_2.add(lblQtdNaoGerou);

        lblQtdFalha = new JLabel("Falha:");
        lblQtdFalha.setBounds(214, 30, 95, 14);
        panel_2.add(lblQtdFalha);

        lblQtdTotal = new JLabel("Total:");
        lblQtdTotal.setBounds(309, 15, 93, 14);
        panel_2.add(lblQtdTotal);

        panel_lista = new JPanel();
        tabbedPane.addTab("Listagem", null, panel_lista, null);
        panel_lista.setLayout(null);

        table = new JTable() {

            private static final long serialVersionUID = -1402267673119957797L;

            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }

            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                JComponent jc = (JComponent) c;

                // Alternate row color
                /**
                 * FIXME TODO TUDO
                 */
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? getBackground() : Color.decode("#F2F2F2"));
                    /**
                     * view.getTable().getColumnModel().getColumn(0).setPreferredWidth(47); //id
                     * view.getTable().getColumnModel().getColumn(1).setPreferredWidth(122);//serial
                     * view.getTable().getColumnModel().getColumn(2).setPreferredWidth(216);//razaosocial
                     * view.getTable().getColumnModel().getColumn(3).setPreferredWidth(131);//contador
                     * view.getTable().getColumnModel().getColumn(4).setPreferredWidth(51);//enviardados
                     * view.getTable().getColumnModel().getColumn(5).setPreferredWidth(106);//status
                     * view.getTable().getColumnModel().getColumn(6).setPreferredWidth(86);//data
                     */
                    int modelRow = convertRowIndexToModel(row);
                    String column1 = (String) getModel().getValueAt(modelRow, 5);
//					Pendente
//					Aguardando Envio
//					Falha no Envio
//					Concluido
                    switch (column1.charAt(0)) {
                        case 'C':// Concluido
                            c.setBackground(Color.GREEN);
                            break;
                        case 'A': // Aguardando Envio
                            c.setBackground(Color.YELLOW);
                            break;
                        case 'F': // Falha no Envio
                            c.setBackground(Color.RED);
                            break;
                        case 'N': // Falha no Envio
                            c.setBackground(Color.GRAY);
                            break;
                        case 'P':// Pendente
                            break;
                        default:
                            break;
                    }

                }
                return c;
            }
        };

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 0, 777, 472);
        panel_lista.add(scrollPane);

        panel_detalhe = new JPanel();
        tabbedPane.addTab("Detalhes", null, panel_detalhe, null);
        panel_detalhe.setLayout(null);

        JPanel panel_4 = new JPanel();
        panel_4.setBackground(SystemColor.controlHighlight);
        panel_4.setBorder(new LineBorder(Color.DARK_GRAY));
        panel_4.setBounds(10, 11, 749, 100);
        panel_detalhe.add(panel_4);
        panel_4.setLayout(null);

        txtEmail = new JTextField();
        txtEmail.setToolTipText("email@servidor.com");
        txtEmail.setBounds(428, 65, 268, 20);
        panel_4.add(txtEmail);
        txtEmail.setColumns(10);

        JLabel label = new JLabel("E-mail");
        label.setBounds(382, 68, 46, 14);
        panel_4.add(label);

        JLabel lblNewLabel_4 = new JLabel("Serial");
        lblNewLabel_4.setBounds(10, 68, 46, 14);
        panel_4.add(lblNewLabel_4);

        txtSerial = new JTextField();
        txtSerial.setToolTipText("XXX0000000000X0");
        txtSerial.setBounds(46, 65, 156, 20);
        panel_4.add(txtSerial);
        txtSerial.setColumns(10);

        JLabel lblNewLabel_5 = new JLabel("Contador");
        lblNewLabel_5.setBounds(10, 40, 57, 14);
        panel_4.add(lblNewLabel_5);

        txtContador = new JTextField();
        txtContador.setBounds(66, 37, 218, 20);
        panel_4.add(txtContador);
        txtContador.setColumns(10);

        btnProcuraContadorDetalhes = new JButton("Procurar Contador");
        btnProcuraContadorDetalhes.setBounds(286, 36, 38, 23);
        panel_4.add(btnProcuraContadorDetalhes);

        JLabel lblNewLabel_1 = new JLabel("Nome");
        lblNewLabel_1.setBounds(10, 14, 38, 14);
        panel_4.add(lblNewLabel_1);

        txtNome = new JTextField();
        txtNome.setBounds(46, 11, 278, 20);
        panel_4.add(txtNome);
        txtNome.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("CNPJ");
        lblNewLabel_2.setBounds(334, 14, 48, 14);
        panel_4.add(lblNewLabel_2);

        txtCNPJ = new JFormattedTextField();
        txtCNPJ.setToolTipText("99.999.999/9999-99");
        txtCNPJ.setBounds(382, 11, 128, 20);
        panel_4.add(txtCNPJ);
        txtCNPJ.setColumns(10);

        JLabel lblNewLabel_3 = new JLabel("Telefone");
        lblNewLabel_3.setBounds(332, 40, 59, 14);
        panel_4.add(lblNewLabel_3);

        txtTelefone = new JFormattedTextField();
        txtTelefone.setToolTipText("(67) 9 9999-9999");
        txtTelefone.setBounds(392, 37, 118, 20);
        panel_4.add(txtTelefone);
        txtTelefone.setColumns(10);

        JLabel lblNewLabel_6 = new JLabel("Versão");
        lblNewLabel_6.setBounds(214, 68, 46, 14);
        panel_4.add(lblNewLabel_6);

        cmbVersao = new JComboBox<Versao>();
        cmbVersao.setBounds(256, 65, 118, 20);
        panel_4.add(cmbVersao);

        JLabel lblNewLabel = new JLabel("ID Remoto");
        lblNewLabel.setBounds(522, 14, 59, 14);
        panel_4.add(lblNewLabel);

        txtIdRemoto = new JTextField();
        txtIdRemoto.setBounds(584, 11, 103, 20);
        panel_4.add(txtIdRemoto);
        txtIdRemoto.setColumns(10);

        chbEnviaDadosContador = new JCheckBox("Enviar Dados Contador");
        chbEnviaDadosContador.setBackground(SystemColor.controlHighlight);
        chbEnviaDadosContador.setBounds(516, 36, 165, 23);
        panel_4.add(chbEnviaDadosContador);

        btnAnydesk = new JButton("");
        btnAnydesk.setIcon(null);
        btnAnydesk.setBounds(692, 12, 18, 18);
        panel_4.add(btnAnydesk);

        JPanel panel_5 = new JPanel();
        panel_5.setBounds(10, 122, 749, 298);
        panel_detalhe.add(panel_5);
        panel_5.setLayout(null);

        JPanel panel_9 = new JPanel();
        panel_9.setBackground(SystemColor.controlHighlight);
        panel_9.setBorder(new LineBorder(Color.DARK_GRAY));
        panel_9.setBounds(0, 13, 375, 69);
        panel_5.add(panel_9);
        panel_9.setLayout(null);

        JLabel lblNewLabel_11 = new JLabel("Status:");
        lblNewLabel_11.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_11.setBounds(23, 11, 46, 14);
        panel_9.add(lblNewLabel_11);

        JLabel lblNewLabel_12 = new JLabel("Enviado em:");
        lblNewLabel_12.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_12.setBounds(10, 36, 59, 14);
        panel_9.add(lblNewLabel_12);

        JLabel lblNewLabel_13 = new JLabel("Usuário:");
        lblNewLabel_13.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_13.setBounds(200, 36, 46, 14);
        panel_9.add(lblNewLabel_13);

        lblFiscalStatusName = new JLabel("{status_name}");
        lblFiscalStatusName.setBounds(79, 11, 149, 14);
        panel_9.add(lblFiscalStatusName);

        lblFiscalStatusEnviadoEm = new JLabel("00/00/00 00:00:00");
        lblFiscalStatusEnviadoEm.setBounds(79, 36, 96, 14);
        panel_9.add(lblFiscalStatusEnviadoEm);

        lblFiscalStatusUsername = new JLabel("{username}");
        lblFiscalStatusUsername.setBounds(256, 36, 96, 14);
        panel_9.add(lblFiscalStatusUsername);

        JLabel lblNewLabel_7 = new JLabel("Fiscal informações");
        lblNewLabel_7.setBounds(0, 0, 278, 14);
        panel_5.add(lblNewLabel_7);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(385, 13, 186, 280);
        panel_5.add(panel_1);
        panel_1.setBorder(new LineBorder(Color.DARK_GRAY));
        panel_1.setLayout(null);

        listFiles = new JList();
        listFiles.setBackground(SystemColor.controlHighlight);
        listFiles.setBounds(0, 0, 186, 240);
        panel_1.add(listFiles);
        listFiles.setBorder(new LineBorder(Color.DARK_GRAY));

        btnCarregarArquivos = new JButton("Carregar Arquivos");
        btnCarregarArquivos.setBounds(10, 246, 166, 23);
        panel_1.add(btnCarregarArquivos);

        JLabel lblArquivosDaPasta = new JLabel("Arquivos da Pasta");
        lblArquivosDaPasta.setBounds(385, 0, 168, 14);
        panel_5.add(lblArquivosDaPasta);

        JLabel lblOpes = new JLabel("Opções");
        lblOpes.setBounds(581, 0, 140, 14);
        panel_5.add(lblOpes);

        JPanel panel_3 = new JPanel();
        panel_3.setBackground(SystemColor.controlHighlight);
        panel_3.setBounds(581, 13, 168, 280);
        panel_5.add(panel_3);
        panel_3.setBorder(new LineBorder(Color.DARK_GRAY));
        panel_3.setLayout(null);

        btnEnviarEmail = new JButton("Enviar");
        btnEnviarEmail.setBounds(10, 11, 148, 23);
        panel_3.add(btnEnviarEmail);

        JButton btnNaoFuncional = new JButton("finalizar");
        btnNaoFuncional.setBounds(10, 34, 148, 23);
        panel_3.add(btnNaoFuncional);

        JButton btnAtivar = new JButton("Ativar");
        btnAtivar.setBounds(10, 56, 148, 23);
        panel_3.add(btnAtivar);

        JPanel panel_8 = new JPanel();
        panel_8.setBackground(SystemColor.controlHighlight);
        panel_8.setBounds(10, 133, 148, 135);
        panel_3.add(panel_8);
        panel_8.setLayout(null);

        btnAguardaEnvio = new JButton("Aguardando Envio");
        btnAguardaEnvio.setBounds(0, 12, 146, 23);
        panel_8.add(btnAguardaEnvio);

        btnConcluirManual = new JButton("Concluir Manual");
        btnConcluirManual.setBounds(0, 35, 146, 23);
        panel_8.add(btnConcluirManual);

        btnClienteEnviou = new JButton("Cliente Enviou");
        btnClienteEnviou.setBounds(0, 58, 146, 23);
        panel_8.add(btnClienteEnviou);

        btnClienteNoEmitiu = new JButton("Não gerou Fiscal");
        btnClienteNoEmitiu.setBounds(0, 81, 146, 23);
        panel_8.add(btnClienteNoEmitiu);

        btnResetaStatus = new JButton("Pendente");
        btnResetaStatus.setBounds(0, 112, 146, 23);
        panel_8.add(btnResetaStatus);

        JLabel lblAlterarStatus = new JLabel("Alterar Status");
        lblAlterarStatus.setBounds(10, 118, 122, 15);
        panel_3.add(lblAlterarStatus);

        panel_drag_drop = new JList<File>();

        tIcon = new ImageIcon();
        try {
            tIcon = new ImageIcon(getClass().getResource("/imagens/drag_drop2.png"));
//			panel_drag_drop.setIcon(tIcon);
        } catch (Exception e) {
            tIcon = null;
        }
        if (tIcon == null) {
            try {
                tIcon = new ImageIcon(getClass().getResource("/imagens/drag_drop2.png"));
//				panel_drag_drop.setIcon(tIcon);
            } catch (Exception e) {

            }
        }
        panel_drag_drop_1 = new JList<File>() {
            /**
             *
             */
            private static final long serialVersionUID = 1L;

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(tIcon.getImage(), 0, 0, 375, 169, panel_5);
            }
        };
        panel_drag_drop_1.setBackground(SystemColor.controlHighlight);
        panel_drag_drop_1.setBorder(new LineBorder(Color.DARK_GRAY));
        panel_drag_drop_1.setBounds(0, 96, 375, 169);
        panel_5.add(panel_drag_drop_1);

        JLabel lblOutros = new JLabel("Solte os arquivos aqui");
        lblOutros.setBounds(0, 82, 278, 14);
        panel_5.add(lblOutros);

        btnSalvarArquivos = new JButton("Salvar Arquivos");
        btnSalvarArquivos.setBounds(98, 270, 166, 23);
        panel_5.add(btnSalvarArquivos);

        JPanel panel_7 = new JPanel();
        panel_7.setBounds(10, 422, 749, 38);
        panel_detalhe.add(panel_7);
        panel_7.setLayout(null);
        contentPane.setLayout(gl_contentPane);
    }

    public JComboBox<String> getCmbMesFiscal() {
        return cmbMesFiscal;
    }

    public void setCmbMesFiscal(JComboBox<String> cmbMesFiscal) {
        this.cmbMesFiscal = cmbMesFiscal;
    }

    public JComboBox<String> getCmbAnoFiscal() {
        return cmbAnoFiscal;
    }

    public void setCmbAnoFiscal(JComboBox<String> cmbAnoFiscal) {
        this.cmbAnoFiscal = cmbAnoFiscal;
    }

//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}

    public JTextField getTxtNome() {
        return txtNome;
    }

    public void setTxtNome(JTextField txtNome) {
        this.txtNome = txtNome;
    }

    public JFormattedTextField getTxtCNPJ() {

        return txtCNPJ;
    }

    public void setTxtCNPJ(JFormattedTextField txtCNPJ) {
        this.txtCNPJ = txtCNPJ;
    }

    public JFormattedTextField getTxtTelefone() {
        return txtTelefone;
    }

    public void setTxtTelefone(JFormattedTextField txtTelefone) {
        this.txtTelefone = txtTelefone;
    }

    public JTextField getTxtSerial() {
        return txtSerial;
    }

    public void setTxtSerial(JTextField txtSerial) {
        this.txtSerial = txtSerial;
    }

    public JTextField getTxtContador() {
        return txtContador;
    }

    public void setTxtContador(JTextField txtContador) {
        this.txtContador = txtContador;
    }

    public JComboBox<Versao> getCmbVersao() {
        return cmbVersao;
    }

    public void setCmbVersao(JComboBox<Versao> cmbVersao) {
        this.cmbVersao = cmbVersao;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public void setTxtEmail(JTextField txtEmail) {
        this.txtEmail = txtEmail;
    }

    public JTextField getTxtPesquisa() {
        return txtPesquisa;
    }

    public void setTxtPesquisa(JTextField txtPesquisa) {
        this.txtPesquisa = txtPesquisa;
    }

    public JButton getBtnProcuraContadorDetalhes() {
        return btnProcuraContadorDetalhes;
    }

    public void setBtnProcuraContadorDetalhes(JButton btnProcuraContadorDetalhes) {
        this.btnProcuraContadorDetalhes = btnProcuraContadorDetalhes;
    }

    public JList<?> getListFiles() {
        return listFiles;
    }

    public void setListFiles(JList<?> listFiles) {
        this.listFiles = listFiles;
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public int getFiscal_id() {
        return fiscal_id;
    }

    public void setFiscal_id(int fiscal_id) {
        this.fiscal_id = fiscal_id;
    }

    public JTextField getTxtIdRemoto() {
        return txtIdRemoto;
    }

    public void setTxtIdRemoto(JTextField txtIdRemoto) {
        this.txtIdRemoto = txtIdRemoto;
    }

    public JCheckBox getChbEnviaDadosContador() {
        return chbEnviaDadosContador;
    }

    public void setChbEnviaDadosContador(JCheckBox chbEnviaDadosContador) {
        this.chbEnviaDadosContador = chbEnviaDadosContador;
    }

    public int getContadorId() {
        return contadorId;
    }

    public void setContadorId(int contadorId) {
        this.contadorId = contadorId;
    }

    public JButton getBtnFirstRegTable() {
        return btnFirstRegTable;
    }

    public JButton getBtnNextRegTable() {
        return btnNextRegTable;
    }

    public void setBtnNextRegTable(JButton btnNextRegTable) {
        this.btnNextRegTable = btnNextRegTable;
    }

    public JButton getBtnEndRegTable() {
        return btnEndRegTable;
    }

    public void setBtnEndRegTable(JButton btnEndRegTable) {
        this.btnEndRegTable = btnEndRegTable;
    }

    public void setBtnFirstRegTable(JButton btnFirstRegTable) {
        this.btnFirstRegTable = btnFirstRegTable;
    }

    public JButton getBtnBackRegTable() {
        return btnBackRegTable;
    }

    public void setBtnBackRegTable(JButton btnBackRegTable) {
        this.btnBackRegTable = btnBackRegTable;
    }

    public JButton getBtnCancelar() {
        return btnNextRegTable;
    }

    public void setBtnCancelar(JButton btnCancelar) {
        this.btnNextRegTable = btnCancelar;
    }

    public JButton getBtnExcluir() {
        return btnEndRegTable;
    }

    public void setBtnExcluir(JButton btnExcluir) {
        this.btnEndRegTable = btnExcluir;
    }

    public JButton getBtnGerarFiscal() {
        return btnGerarFiscal;
    }

    public void setBtnGerarFiscal(JButton btnGerarFiscal) {
        this.btnGerarFiscal = btnGerarFiscal;
    }

    public JButton getBtnEnviarPendentes() {
        return btnEnviarPendentes;
    }

    public void setBtnEnviarPendentes(JButton btnEnviarPendentes) {
        this.btnEnviarPendentes = btnEnviarPendentes;
    }

    public JButton getBtnCarregarArquivos() {
        return btnCarregarArquivos;
    }

    public void setBtnCarregarArquivos(JButton btnCarregarArquivos) {
        this.btnCarregarArquivos = btnCarregarArquivos;
    }

    public JButton getBtnSalvarArquivos() {
        return btnSalvarArquivos;
    }

    public void setBtnSalvarArquivos(JButton btnSalvarArquivos) {
        this.btnSalvarArquivos = btnSalvarArquivos;
    }

    public JPanel getPanel_lista() {
        return panel_lista;
    }

    public void setPanel_lista(JPanel panel_lista) {
        this.panel_lista = panel_lista;
    }

    public JPanel getPanel_detalhe() {
        return panel_detalhe;
    }

    public void setPanel_detalhe(JPanel panel_detalhe) {
        this.panel_detalhe = panel_detalhe;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public void setTabbedPane(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
    }

//	public FiscalController getController() {
//		return controller;
//	}

    public JButton getBtnAguardaEnvio() {
        return btnAguardaEnvio;
    }

    public void setBtnAguardaEnvio(JButton btnAguardaEnvio) {
        this.btnAguardaEnvio = btnAguardaEnvio;
    }

    public JButton getBtnConcluirManual() {
        return btnConcluirManual;
    }

    public void setBtnConcluirManual(JButton btnConcluirManual) {
        this.btnConcluirManual = btnConcluirManual;
    }

    public JButton getBtnEnviarEmail() {
        return btnEnviarEmail;
    }

    public void setBtnEnviarEmail(JButton btnEnviarEmail) {
        this.btnEnviarEmail = btnEnviarEmail;
    }

    public JButton getBtnConfigSMTP() {
        return btnConfigSMTP;
    }

    public void setBtnConfigSMTP(JButton btnConfigSMTP) {
        this.btnConfigSMTP = btnConfigSMTP;
    }

    public JList<File> getPanel_drag_drop() {
        return panel_drag_drop_1;
    }

    public void setPanel_drag_drop(JList<File> panel_drag_drop) {
        this.panel_drag_drop_1 = panel_drag_drop;
    }

    public JButton getBtnAnydesk() {
        return btnAnydesk;
    }

    public void setBtnAnydesk(JButton btnAnydesk) {
        this.btnAnydesk = btnAnydesk;
    }

    public JButton getBtnClienteEnviou() {
        return btnClienteEnviou;
    }

    public void setBtnClienteEnviou(JButton btnClienteEnviou) {
        this.btnClienteEnviou = btnClienteEnviou;
    }

    public JButton getBtnClienteNoEmitiu() {
        return btnClienteNoEmitiu;
    }

    public void setBtnClienteNoEmitiu(JButton btnClienteNoEmitiu) {
        this.btnClienteNoEmitiu = btnClienteNoEmitiu;
    }

    public JButton getBtnResetaStatus() {
        return btnResetaStatus;
    }

    public void setBtnResetaStatus(JButton btnResetaStatus) {
        this.btnResetaStatus = btnResetaStatus;
    }

    public JLabel getLblQtdFalha() {
        return lblQtdFalha;
    }

    public void setLblQtdFalha(JLabel lblQtdFalha) {
        this.lblQtdFalha = lblQtdFalha;
    }

    public JLabel getLblQtdNaoGerou() {
        return lblQtdNaoGerou;
    }

    public void setLblQtdNaoGerou(JLabel lblQtdNaoGerou) {
        this.lblQtdNaoGerou = lblQtdNaoGerou;
    }

    public JLabel getLblQtdAguardando() {
        return lblQtdAguardando;
    }

    public void setLblQtdAguardando(JLabel lblQtdAguardando) {
        this.lblQtdAguardando = lblQtdAguardando;
    }

    public JLabel getLblQtdConcluido() {
        return lblQtdConcluido;
    }

    public void setLblQtdConcluido(JLabel lblQtdConcluido) {
        this.lblQtdConcluido = lblQtdConcluido;
    }

    public JLabel getLblQtdPendente() {
        return lblQtdPendente;
    }

    public void setLblQtdPendente(JLabel lblQtdPendente) {
        this.lblQtdPendente = lblQtdPendente;
    }

    public JLabel getLblQtdTotal() {
        return lblQtdTotal;
    }

    public void setLblQtdTotal(JLabel lblQtdTotal) {
        this.lblQtdTotal = lblQtdTotal;
    }

    public JLabel getLblFiscalStatusName() {
        return lblFiscalStatusName;
    }

    public void setLblFiscalStatusName(JLabel lblFiscalStatusName) {
        this.lblFiscalStatusName = lblFiscalStatusName;
    }

    public JLabel getLblFiscalStatusEnviadoEm() {
        return lblFiscalStatusEnviadoEm;
    }

    public void setLblFiscalStatusEnviadoEm(JLabel lblFiscalStatusEnviadoEm) {
        this.lblFiscalStatusEnviadoEm = lblFiscalStatusEnviadoEm;
    }

    public JLabel getLblFiscalStatusUsername() {
        return lblFiscalStatusUsername;
    }

    public void setLblFiscalStatusUsername(JLabel lblFiscalStatusUsername) {
        this.lblFiscalStatusUsername = lblFiscalStatusUsername;
    }

    public JLabel getLblProgress() {
        return lblProgress;
    }

    public void setLblProgress(JLabel lblProgress) {
        this.lblProgress = lblProgress;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    /**
     * @param title
     * @param msg
     * @param nivel ex. JOptionPane.ERROR_MESSAGE
     */
    public void mensagemView(String title, String msg, int nivel) {
        if (nivel != JOptionPane.ERROR_MESSAGE) {
            nivel = JOptionPane.ERROR_MESSAGE;
        }
        JOptionPane.showMessageDialog(new JFrame(), msg, title, nivel);
    }
}
