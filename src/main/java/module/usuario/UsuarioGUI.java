package module.usuario;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

public class UsuarioGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	private JTextField txtNome;
	private JTextField txtLogin;
	private JPasswordField txtPasswd;
	private JTextField txtTelefone;
	private JTextField txtModificado;
	private JTextField txtEmail;

	/**
	 * Create the frame.
	 */
	public UsuarioGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
		);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Listagem", null, panel, null);
		panel.setLayout(null);
		
		table = new JTable();

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 0, 419, 179);
		panel.add(scrollPane);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBounds(0, 179, 419, 44);
		panel.add(panel_3);
		
		JButton button = new JButton("Editar");
		button.setBounds(0, 0, 66, 44);
		panel_3.add(button);
		
		JButton button_1 = new JButton("Salvar");
		button_1.setBounds(63, 0, 66, 44);
		panel_3.add(button_1);
		
		textField = new JTextField();
		textField.setBounds(303, 11, 106, 20);
		panel_3.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Pesquisar");
		lblNewLabel.setBounds(253, 14, 46, 14);
		panel_3.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Detalhes", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setBounds(10, 11, 38, 14);
		panel_1.add(lblNewLabel_1);
		
		txtNome = new JTextField();
		txtNome.setBounds(46, 8, 278, 20);
		panel_1.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Login");
		lblNewLabel_2.setBounds(10, 36, 30, 14);
		panel_1.add(lblNewLabel_2);
		
		txtLogin = new JTextField();
		txtLogin.setText("99.999.999/9999-99");
		txtLogin.setBounds(46, 33, 115, 20);
		panel_1.add(txtLogin);
		txtLogin.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Senha");
		lblNewLabel_3.setBounds(171, 36, 38, 14);
		panel_1.add(lblNewLabel_3);
		
		txtPasswd = new JPasswordField();
		txtPasswd.setText("0000000");
		txtPasswd.setBounds(209, 33, 76, 20);
		panel_1.add(txtPasswd);
		txtPasswd.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Telefone");
		lblNewLabel_4.setBounds(10, 61, 46, 14);
		panel_1.add(lblNewLabel_4);
		
		txtTelefone = new JTextField();
		txtTelefone.setText("(67) 9 9999-9999");
		txtTelefone.setBounds(56, 58, 115, 20);
		panel_1.add(txtTelefone);
		txtTelefone.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 179, 419, 44);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnEditarDetalhes = new JButton("Editar");
		btnEditarDetalhes.setBounds(0, 0, 66, 44);
		panel_2.add(btnEditarDetalhes);
		
		JButton btnSalvarDetalhes = new JButton("Salvar");
		btnSalvarDetalhes.setBounds(63, 0, 66, 44);
		panel_2.add(btnSalvarDetalhes);
		
		JLabel lblNewLabel_6 = new JLabel("Modificado em");
		lblNewLabel_6.setBounds(181, 61, 76, 14);
		panel_1.add(lblNewLabel_6);
		
		txtModificado = new JTextField();
		txtModificado.setText("99/99/9999");
		txtModificado.setBounds(255, 58, 69, 20);
		panel_1.add(txtModificado);
		txtModificado.setColumns(10);
		
		JButton btnVersao = new JButton("Alterar Senha");
		btnVersao.setBounds(286, 32, 38, 23);
		panel_1.add(btnVersao);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(46, 84, 278, 20);
		panel_1.add(txtEmail);
		
		JLabel label = new JLabel("E-mail");
		label.setBounds(10, 87, 46, 14);
		panel_1.add(label);
		contentPane.setLayout(gl_contentPane);
	}
}
