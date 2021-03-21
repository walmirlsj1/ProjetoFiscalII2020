package module.versao;

import module.usuario.Usuario;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VersaoHelper {

	private final VersaoGUI view;
	private final Usuario usuario;
	
	public VersaoHelper(VersaoGUI view, Usuario usuario) {
		this.view = view;
		this.usuario = usuario;
	}
	
	public void preencherCampos(Versao versao) {
//		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		view.setId(Integer.valueOf(versao.getId()));
		view.getTxtVersao().setText(versao.getVersao());
	}
	
	public int getId() {
		return view.getId();
	}
	
	public Versao pegaCampos() {
		String versao = view.getTxtVersao().getText();
		
		if(!versao.isEmpty())
			return new Versao(this.getId(), versao, usuario);
		
		return null;
	}
	
	
	public void preencherTabela(List<Versao> versoes) {
		//(DefaultTableModel) view.getTable().getModel()
		DefaultTableModel dataModel = new DefaultTableModel();
		dataModel.setNumRows(0);
//		tableModel.setColumnCount(0);
		
		dataModel.addColumn("ID");
		dataModel.addColumn("Versao");
		dataModel.addColumn("Usuario");
		dataModel.addColumn("Modificado");

		
		for(Versao versao : versoes) {
			dataModel.addRow(new Object[] {
					versao.getId(),
					versao.getVersao(),
					versao.getUsuario().getUsername(),
					versao.getModificado()
//					cliente.getNome(),
//					cliente.getCnpj(),
//					cliente.getTelefone(),
//					cliente.getEmail(),
//					cliente.getModificado()
			});
		}
		view.getTable().setModel(dataModel);
	}
	
	public void initialize() {
//		view.getTxtId().setEditable(false);
//		view.getTxtModificado().setEditable(false);
//		view.getTxtUser().setText(view.getUsuario().getUsername());
////		view.getTxtUser().setEnabled(false);
//		view.getTxtUser().setEditable(false);
		view.getTxtVersao().setText("");;
		view.setId(-1);
	}
	
	public int tablePegaID(MouseEvent evt) {
		JTable table = view.getTable();
		int row = table.rowAtPoint(evt.getPoint());
        int col = table.columnAtPoint(evt.getPoint());
        if (row >= 0 && col >= 0) {
//        	System.out.println("Clico na Row: " + row + " Col: " + col);
        	return (Integer) table.getModel().getValueAt(row, 0);
        }
        return -1;
	}
	
	public void limpaTela() {
		view.getTxtVersao().setText("");;
		view.setId(-1);
	}
}
