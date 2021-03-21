package module.main;

import java.awt.GraphicsConfiguration;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

import javax.swing.JToolBar;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class MainWinGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JMenuItem mntCadContador;
	private JMenuItem mntCadClientes;
	private JMenuItem mntCadTipo;
	private JMenuItem mntCadStatus;
	
	private JMenu mntFiscal;
	private JMenuItem mntFiscalGer;
	private JMenu mntUsers;
	private JMenuItem mntUsersGer;
	private JMenuItem mntUsersGer2;
	private JMenu mntSair;

	/**
	 * Create the application.
	 */
	public MainWinGUI() {
		initialize();

		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.SOUTH);
	}

	/**
	 * Initialize the contents of the
	 */
	private void initialize() {
//		setDefaultLookAndFeelDecorated(false);

		final GraphicsConfiguration config = this.getGraphicsConfiguration();

		final int left = Toolkit.getDefaultToolkit().getScreenInsets(config).left;
		final int right = Toolkit.getDefaultToolkit().getScreenInsets(config).right;
		final int top = Toolkit.getDefaultToolkit().getScreenInsets(config).top;
		final int bottom = Toolkit.getDefaultToolkit().getScreenInsets(config).bottom;

		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final int width = screenSize.width - left - right;
		final int height = screenSize.height - top - bottom;

//        this.setResizable(false);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		this.setSize(width, height);
		setLocationRelativeTo(null);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mntCad = new JMenu("Cadastro");
		menuBar.add(mntCad);

		mntCadContador = new JMenuItem("Contador");
		mntCad.add(mntCadContador);

		mntCadClientes = new JMenuItem("Clientes");
		mntCad.add(mntCadClientes);

		mntCadTipo = new JMenuItem("Versão");
		mntCad.add(mntCadTipo);

		mntCadStatus = new JMenuItem("Status");
		mntCad.add(mntCadStatus);

		JMenu mntFiscal = new JMenu("Fiscal");
		menuBar.add(mntFiscal);

		mntFiscalGer = new JMenuItem("Gerenciar");
		mntFiscal.add(mntFiscalGer);

		mntUsers = new JMenu("Usuarios");
		menuBar.add(mntUsers);

		mntUsersGer = new JMenuItem("Gerenciar");
		mntUsers.add(mntUsersGer);

		mntUsersGer2 = new JMenuItem("teste");
		mntUsers.add(mntUsersGer2);

		mntSair = new JMenu("Sair");
		menuBar.add(mntSair);
	}

	public JMenuItem getMntCadClientes() {
		return mntCadClientes;
	}

	public void setMntCadClientes(JMenuItem mntCadClientes) {
		this.mntCadClientes = mntCadClientes;
	}

	public JMenuItem getMntCadContador() {
		return mntCadContador;
	}

	public void setMntCadContador(JMenuItem mntCadContador) {
		this.mntCadContador = mntCadContador;
	}

	public JMenuItem getMntCadTipo() {
		return mntCadTipo;
	}

	public void setMntCadTipo(JMenuItem mntCadTipo) {
		this.mntCadTipo = mntCadTipo;
	}

	public JMenuItem getMntCadStatus() {
		return mntCadStatus;
	}

	public void setMntCadStatus(JMenuItem mntCadStatus) {
		this.mntCadStatus = mntCadStatus;
	}

	public JMenu getMntFiscal() {
		return mntFiscal;
	}

	public void setMntFiscal(JMenu mntFiscal) {
		this.mntFiscal = mntFiscal;
	}

	public JMenuItem getMntFiscalGer() {
		return mntFiscalGer;
	}

	public void setMntFiscalGer(JMenuItem mntFiscalGer) {
		this.mntFiscalGer = mntFiscalGer;
	}

	public JMenu getMntUsers() {
		return mntUsers;
	}

	public void setMntUsers(JMenu mntUsers) {
		this.mntUsers = mntUsers;
	}

	public JMenuItem getMntUsersGer() {
		return mntUsersGer;
	}

	public void setMntUsersGer(JMenuItem mntUsersGer) {
		this.mntUsersGer = mntUsersGer;
	}

	public JMenuItem getMntUsersGer2() {
		return mntUsersGer2;
	}

	public void setMntUsersGer2(JMenuItem mntUsersGer2) {
		this.mntUsersGer2 = mntUsersGer2;
	}

	public JMenu getMntSair() {
		return mntSair;
	}

	public void setMntSair(JMenu mntSair) {
		this.mntSair = mntSair;
	}

//	public Usuario getUsuario() {
//		return usuario;
//	}

}
