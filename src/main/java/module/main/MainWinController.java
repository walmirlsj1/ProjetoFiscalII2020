package module.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import module.usuario.Usuario;
import module.cliente.ClienteController;
import module.contador.ContadorController;
import module.fiscal.FiscalController;
import module.usuario.UsuarioController;
import module.versao.VersaoController;

public class MainWinController {
	private final MainWinGUI view;
	private Usuario usuario;

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

//	view = new LoginGUI();
//	helper = new LoginHelper(view);
//	initComponents();
//	view.setVisible(true);
//private void initComponents()
	public MainWinController(Usuario usuario) {
		this.usuario = usuario;

		this.view = new MainWinGUI();

		initialize();
		view.setVisible(true);
	}

	private void initialize() {
		// TODO Auto-generated method stub
		mntCadContador = view.getMntCadContador();
		mntCadClientes = view.getMntCadClientes();
		mntCadTipo = view.getMntCadTipo();
		mntCadStatus = view.getMntCadStatus();

		mntFiscal = view.getMntFiscal();
		mntFiscalGer = view.getMntFiscalGer();
		mntUsers = view.getMntUsers();
		mntUsersGer = view.getMntUsersGer();
		mntUsersGer2 = view.getMntUsersGer2();
		mntSair = view.getMntSair();

		inicializaEventos();
	}

	private void inicializaEventos(){
		mntCadContador.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				navegarContador();
			}
		});
		mntCadClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				navegarCliente();
			}
		});
		mntCadTipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				navegarVersao();
			}
		});

		mntFiscalGer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				navegarFiscal();
			}
		});
		mntUsersGer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				navegarUsuario();
			}
		});
//		mntUsersGer2.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				navegarTeste();
//			}
//		});

		mntSair.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
		});
//		mntSair.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				System.exit(0);
//			}
//		});
	}

	private void montarJanela(JFrame view) {
		JDialog frame = new JDialog();
		frame.setContentPane(view.getContentPane());
		frame.pack();
		frame.setAutoRequestFocus(true);
		frame.setModal(true);
		frame.setSize(view.getSize());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void navegarCliente() {
		ClienteController clienteView = new ClienteController(usuario);
		montarJanela(clienteView.getView());
	}

	public void navegarContador() {
		ContadorController contadorView = new ContadorController(usuario);
		montarJanela(contadorView.getView());
	}

	public void navegarFiscal() {
		FiscalController fiscalView = new FiscalController(usuario);
		montarJanela(fiscalView.getView());
	}

	public void navegarUsuario() {
		UsuarioController usuarioView = new UsuarioController(usuario);
		montarJanela(usuarioView.getView());
	}

	public void navegarVersao() {
		VersaoController versaoView = new VersaoController(usuario);
		montarJanela(versaoView.getView());
	}

	public void sair() {
		System.exit(0);
	}

}
