package module.seleciona_contador;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

public class SelecionaContadorGUI extends JFrame {

//	private final SelecionaContadorController controller;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextField txtPesquisa;
	private JTable table;
	private JButton btnSelecionar;
	private JButton btnCadastrar;
	private JButton btnPesquisar;
	private JPanel panel;

	/**
	 * Create the panel.
	 */
	public SelecionaContadorGUI() {
		initComponents();

	}

	public void initComponents() {
//		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 465, 264);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		table = new JTable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
         	public boolean editCellAt(int row, int column, java.util.EventObject e) {
         		return false;
         }};
        table.setSurrendersFocusOnKeystroke(true);
 		table.setShowHorizontalLines(false);
 		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
 		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 40, 430, 134);
		getContentPane().add(scrollPane);
		
//		scrollPane.setViewportView(table);
		

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 176, 430, 25);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		btnSelecionar = new JButton("Utilizar");
		btnSelecionar.setBounds(0, 0, 89, 23);
		panel_1.add(btnSelecionar);

		btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(89, 0, 89, 23);
		panel_1.add(btnCadastrar);

		JLabel lblNewLabel = new JLabel("* Tecle Enter | Dois Cliques para seleciona o item.");
		lblNewLabel.setBounds(10, 207, 430, 14);
		contentPane.add(lblNewLabel);

		panel = new JPanel();
		panel.setBounds(10, 0, 429, 39);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel("Procurar");
		label.setBounds(10, 15, 53, 14);
		panel.add(label);

		txtPesquisa = new JTextField();
		txtPesquisa.setBounds(66, 12, 235, 20);
		panel.add(txtPesquisa);
		txtPesquisa.setColumns(10);

		btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setBounds(311, 11, 89, 23);
		panel.add(btnPesquisar);

	}

	public JTextField getTxtPesquisa() {
		return txtPesquisa;
	}

	public void setTxtPesquisa(JTextField txtPesquisa) {
		this.txtPesquisa = txtPesquisa;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JButton getBtnSelecionar() {
		return btnSelecionar;
	}

	public void setBtnSelecionar(JButton btnSelecionar) {
		this.btnSelecionar = btnSelecionar;
	}

	public JButton getBtnCadastrar() {
		return btnCadastrar;
	}

	public void setBtnCadastrar(JButton btnCadastrar) {
		this.btnCadastrar = btnCadastrar;
	}

	public JButton getBtnPesquisar() {
		return btnPesquisar;
	}

	public void setBtnPesquisar(JButton btnPesquisar) {
		this.btnPesquisar = btnPesquisar;
	}
	
}
