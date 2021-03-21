package module.fiscal;

import global.CustomTableModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FiscalTableModel extends CustomTableModel<Fiscal> {

    public FiscalTableModel() {
        this(new ArrayList<>());
    }

    public FiscalTableModel(List<Fiscal> lista) {
        this.lista = lista;
        this.colunas = new String[]{"ID", "SERIAL", "NOME", "CONTADOR", "ENVIAR",
                "STATUS", "MODIFICADO"};
        this.colunasClass = new Class[]{Integer.class, String.class, String.class, String.class,
                Boolean.class, String.class, Date.class};
    }

    @Override
    public Object getValueAt(int rowIndex, int column) {
        Fiscal fiscal = lista.get(rowIndex);
//        "ID", "SERIAL", "NOME", "CONTADOR", "ENVIAR", "STATUS", "MODIFICADO"
        switch (column) {
            case 0:
                return fiscal.getId();
            case 1:
                return fiscal.getCliente().getSerial();
            case 2:
                return fiscal.getCliente().getNome();
            case 3:
                return fiscal.getCliente().getContador().getNome();
            case 4:
                return fiscal.getCliente().getEnviarDadosContador();
            case 5:
                return fiscal.getStatus().name();
            case 6:
                return fiscal.getModificado();
            default:
                throw new IndexOutOfBoundsException("Coluna não existe!");
        }
    }

}
