package module.cliente;

import module.versao.Versao;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;


import javax.swing.LayoutStyle.ComponentPlacement;

public class ClienteGUI extends JFrame {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7984720303972360445L;

	private JPanel contentPane;
	
	private JTabbedPane tabbedPane;

	private int id;
	private int contadorId;
	
	private JPanel panel_lista;
	private JPanel panel_detalhe;

	
	private JTable table;
	private JTextField txtNome;
	private JFormattedTextField txtCNPJ;
	private JFormattedTextField txtTelefone;
	private JTextField txtSerial;
	private JTextField txtContador;
	private JComboBox<Versao> cmbVersao;
	private JTextField txtEmail;
	private JTable tblFiscal;
	private JTextField txtPesquisa;
	private JTextField txtIdRemoto;
	private JCheckBox chbEnviaDadosContador;
	private JCheckBox chbAtivo;
	private JButton btnProcuraContadorDetalhes;
	private JButton btnNovo;
	private JButton btnEditar;
	private JButton btnCancelar;
	private JButton btnExcluir;
	
	/**
	 * Create the frame.
	 */
	public ClienteGUI() {
		initComponents();
		
	}
	
	public void initComponents() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 808, 631);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		
		btnEditar = new JButton("Editar");
		btnEditar.setBounds(63, 0, 74, 44);
		panel_2.add(btnEditar);
		
		btnNovo = new JButton("Novo");
		btnNovo.setBounds(0, 0, 66, 44);
		panel_2.add(btnNovo);
		
		txtPesquisa = new JTextField();
		
		txtPesquisa.setColumns(10);
		txtPesquisa.setBounds(600, 11, 159, 20);
		panel_2.add(txtPesquisa);
		
		JLabel label_2 = new JLabel("Pesquisar");
		label_2.setBounds(548, 15, 66, 14);
		panel_2.add(label_2);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 769, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 532, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(85, Short.MAX_VALUE))
		);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(136, 0, 80, 44);
		panel_2.add(btnCancelar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(216, 0, 89, 44);
		panel_2.add(btnExcluir);
		
		panel_lista = new JPanel();
		tabbedPane.addTab("Listagem", null, panel_lista, null);
		panel_lista.setLayout(null);
		
		table = new JTable() {
					
					private static final long serialVersionUID = -1402267673119957797L;

					public boolean editCellAt(int row, int column, java.util.EventObject e) {
		         		return false;
		         }};
		
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 777, 504);
		panel_lista.add(scrollPane);
		
		panel_detalhe = new JPanel();
		tabbedPane.addTab("Detalhes", null, panel_detalhe, null);
		panel_detalhe.setLayout(null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(UIManager.getColor("CheckBox.darkShadow")));
		panel_4.setBounds(10, 53, 749, 122);
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
		chbEnviaDadosContador.setBounds(516, 36, 165, 23);
		panel_4.add(chbEnviaDadosContador);

		chbAtivo = new JCheckBox("Ativo");
		chbAtivo.setBounds(516, 90, 165, 23);
		panel_4.add(chbAtivo);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 199, 749, 294);
		panel_detalhe.add(scrollPane_1);
		
		tblFiscal = new JTable() {
			public boolean editCellAt(int row, int column, java.util.EventObject e) {
				return false;
			}};
		scrollPane_1.setViewportView(tblFiscal);
		
		JLabel lblNewLabel_7 = new JLabel("Informações Fiscais Enviadas");
		lblNewLabel_7.setBounds(10, 186, 278, 14);
		panel_detalhe.add(lblNewLabel_7);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(UIManager.getColor("CheckBox.darkShadow")));
		panel_5.setBounds(10, 11, 749, 37);
		panel_detalhe.add(panel_5);
		contentPane.setLayout(gl_contentPane);
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public JTable getTblFiscal() {
		return tblFiscal;
	}

	public void setTblFiscal(JTable tblFiscal) {
		this.tblFiscal = tblFiscal;
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

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
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
	public JCheckBox getChbAtivo() {
		return chbAtivo;
	}

	public void setChbAtivo(JCheckBox chbAtivo) {
		this.chbAtivo = chbAtivo;
	}

	public int getContadorId() {
		return contadorId;
	}

	public void setContadorId(int contadorId) {
		this.contadorId = contadorId;
	}

	public JButton getBtnNovo() {
		return btnNovo;
	}

	public void setBtnNovo(JButton btnNovo) {
		this.btnNovo = btnNovo;
	}

	public JButton getBtnEditar() {
		return btnEditar;
	}

	public void setBtnEditar(JButton btnEditar) {
		this.btnEditar = btnEditar;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public JButton getBtnExcluir() {
		return btnExcluir;
	}

	public void setBtnExcluir(JButton btnExcluir) {
		this.btnExcluir = btnExcluir;
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
	
	public void mensagemView(String title, String msg, String nivel) {
		JOptionPane.showMessageDialog(new JFrame(), msg, title,
		        JOptionPane.ERROR_MESSAGE);
	}

	public static void main(String[] args) {
		ClienteGUI clienteGui = new ClienteGUI();
		clienteGui.setVisible(true);
	}
}
