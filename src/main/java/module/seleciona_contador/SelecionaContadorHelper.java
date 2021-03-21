package module.seleciona_contador;

import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import module.contador.Contador;

public class SelecionaContadorHelper {
	private final SelecionaContadorGUI view;
	
	public SelecionaContadorHelper(SelecionaContadorGUI view) {
		this.view = view;
	}
	
	public void preencherTabela(List<Contador> lstContador) {
		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.setNumRows(0);
		
		tableModel.addColumn("ID");
		tableModel.addColumn("Nome");
		tableModel.addColumn("Cnpj");
		tableModel.addColumn("Telefone");
		tableModel.addColumn("Email");
		
		for(Contador contador : lstContador) {
			tableModel.addRow(new Object[] {
					contador.getId(),
					contador.getNome(),
					contador.getCnpj(),
					contador.getTelefone(),
					contador.getEmail()
			});
		}
		view.getTable().setModel(tableModel);
	}

	public int tablePegaID(MouseEvent evt) {
		JTable table = view.getTable();
		int row = table.rowAtPoint(evt.getPoint());
        int col = table.columnAtPoint(evt.getPoint());
        if (row >= 0 && col >= 0) {
        	return (Integer) table.getModel().getValueAt(row, 0);
        }
        return -1;
	}
	
	public void limpaTela() {
		new Throwable("Não implementado!");
	}
}
