package module.login;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;


public class LoginGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5210657298303844265L;

//	private final LoginController controller;
	private JPanel contentPane;

	private JPasswordField txtSenha;
	private JTextField txtLogin;

	private JButton btnEntrar;
	private JButton btnSair;
	private JLabel imgFundo;
	
	/**
	 * Create the frame.
	 */
	public LoginGUI() {

		initComponents();
//		controller = new LoginController(this);

	}

	private void initComponents() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setBounds(100, 100, 420, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(30, 144, 255));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		btnEntrar = new JButton("Entrar");
		btnEntrar.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		btnEntrar.setMnemonic('E');
		btnEntrar.setBounds(211, 212, 89, 23);
		panel.add(btnEntrar);

		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(105, 134, 38, 14);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Senha");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(105, 173, 38, 14);
		panel.add(lblNewLabel_1);

		txtLogin = new JTextField();
		
		txtLogin.setBounds(147, 131, 153, 20);
		txtLogin.setColumns(10);
		panel.add(txtLogin);

		txtSenha = new JPasswordField();
		
		txtSenha.setBounds(147, 170, 153, 20);
		panel.add(txtSenha);

		btnSair = new JButton("Sair");
		btnSair.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		btnSair.setMnemonic('S');
		btnSair.setBounds(107, 212, 94, 23);
		panel.add(btnSair);

		imgFundo = new JLabel("");
		imgFundo.setBounds(0, 0, 420, 300);
		panel.add(imgFundo);

	}

	public JButton getBtnEntrar() {
		return btnEntrar;
	}

	public void setBtnEntrar(JButton btnEntrar) {
		this.btnEntrar = btnEntrar;
	}

	public JPasswordField getTxtSenha() {
		return txtSenha;
	}

	public void setTxtSenha(JPasswordField txtSenha) {
		this.txtSenha = txtSenha;
	}

	public JTextField getTxtLogin() {
		return txtLogin;
	}

	public void setTxtLogin(JTextField txtLogin) {
		this.txtLogin = txtLogin;
	}

	public void exibeMensagem(String msg) {
		JOptionPane.showMessageDialog(null, msg);
	}

	public JButton getBtnSair() {
		return btnSair;
	}

	public void setBtnSair(JButton btnSair) {
		this.btnSair = btnSair;
	}

	public JLabel getImgFundo() {
		return imgFundo;
	}

	public void setImgFundo(JLabel imgFundo) {
		this.imgFundo = imgFundo;
	}
	
}
