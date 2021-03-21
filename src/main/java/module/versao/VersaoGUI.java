package module.versao;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import javax.swing.UIManager;

public class VersaoGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 782528448030883957L;

	private int id;
	private JPanel contentPane;
	private JTable table;
	private JTextField txtVersao;
	
	private JButton btnExcluir;
	private JButton btnNovo;
	private JButton btnEditar;
	
	
	/**
	 * Create the frame.
	 */
	public VersaoGUI() {
		initComponents();
	}

	public void initComponents() {
//		pack();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 374, 201);
//		setSize(464, 201);

		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		table = new JTable();

		JScrollPane scrollPane = new JScrollPane(table);
		
		scrollPane.setBounds(0, 0, 185, 162);
		contentPane.add(scrollPane);

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, UIManager.getColor("Button.light"),
				UIManager.getColor("Button.shadow")));
		panel.setBounds(186, 0, 172, 162);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Versão");
		lblNewLabel.setBounds(10, 11, 46, 14);
		panel.add(lblNewLabel);

		txtVersao = new JTextField();
		txtVersao.setBounds(10, 26, 150, 20);
		panel.add(txtVersao);
		txtVersao.setColumns(10);

		btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(10, 124, 150, 23);
		panel.add(btnExcluir);

		btnNovo = new JButton("Novo");
		btnNovo.setBounds(10, 57, 62, 23);
		panel.add(btnNovo);

		btnEditar = new JButton("Editar");
		btnEditar.setBounds(74, 57, 86, 23);
		panel.add(btnEditar);

		JButton btnAtivar = new JButton("Ativar");
		btnAtivar.setBounds(10, 91, 150, 23);
		panel.add(btnAtivar);

	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public JTable getTable() {
		return table;
	}

	public void setTable(JTable table) {
		this.table = table;
	}

	public JTextField getTxtVersao() {
		return txtVersao;
	}

	public void setTxtVersao(JTextField txtVersao) {
		this.txtVersao = txtVersao;
	}

	public JButton getBtnExcluir() {
		return btnExcluir;
	}

	public void setBtnExcluir(JButton btnExcluir) {
		this.btnExcluir = btnExcluir;
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
	
}
