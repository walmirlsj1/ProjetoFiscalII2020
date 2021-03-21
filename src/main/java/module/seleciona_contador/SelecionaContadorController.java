package module.seleciona_contador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.table.TableRowSorter;

import module.cliente.ClienteTableModel;
import module.contador.Contador;
import module.cliente.ClienteController;
import module.contador.ContadorDAO;
import module.contador.ContadorGUI;

public class SelecionaContadorController {
	
	private final SelecionaContadorGUI view;
	private final SelecionaContadorHelper helper;
	
	private final ClienteController viewCliente;

	private JButton btnCadastrar;
	private JButton btnSelecionar;
	private JTable table;
	
	public SelecionaContadorController(ClienteController viewCliente) {
		this.viewCliente = viewCliente;
		this.view = new SelecionaContadorGUI();
		this.helper = new SelecionaContadorHelper(view);
		this.configInit();
	}

	public void atualizaTabela() {
		ContadorDAO contadorDAO = new ContadorDAO();
		helper.preencherTabela(contadorDAO.selectAll());
	}

	public void configInit() {
		table = view.getTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					evt.consume();
					tablePegaID(evt);
				}
			}
		});
		btnSelecionar = view.getBtnSelecionar();
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				
				int row_index =(int) table.getSelectedRow();
				int col_index =(int) table.getSelectedColumn();
				
				if (col_index < 0 || row_index < 0) return;
				
				int id = (Integer) table.getValueAt(row_index, col_index);
				selecionaContador(id);
			}
		});
		btnCadastrar = view.getBtnCadastrar();
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContadorGUI contadorView = new ContadorGUI();
				
				contadorView.getTabbedPane().setEnabledAt(0, false);;
				contadorView.getTabbedPane().setSelectedIndex(1);
				montarJanela(contadorView);
				atualizaTabela();
			}
		});

		view.getBtnPesquisar().addActionListener(e->{
			pesquisar();
		});

		view.getTxtPesquisa().addActionListener(e->{
			pesquisar();
		});
		atualizaTabela();
	}

	private void pesquisar(){
		view.getTable().setAutoCreateRowSorter(true);
		TableRowSorter<ClienteTableModel> sorter = (TableRowSorter) view.getTable().getRowSorter();

		String text = Pattern.quote(view.getTxtPesquisa().getText());
		String regex = String.format("(?i)(%s)", text);

		sorter.setRowFilter(RowFilter.regexFilter(regex));
	}
	
	public void tablePegaID(MouseEvent evt) {
		
		selecionaContador(helper.tablePegaID(evt));
		
	}
	
	private void selecionaContador(int id) {
		ContadorDAO contadorDAO = new ContadorDAO();
		viewCliente.setContador(contadorDAO.selectPerID(id));
		view.dispose();
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

	public SelecionaContadorGUI getView() {
		return view;
	}
	
}
