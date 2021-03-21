package module.contador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import global.CustomTableModel;

public class ContadorTableModel extends CustomTableModel<Contador> {
    public ContadorTableModel() {
        this(new ArrayList<>());
    }

    public ContadorTableModel(List<Contador> lista) {
        this.lista = lista;
        colunas = new String[]{"ID", "NOME", "CNPJ", "TELEFONE", "EMAIL", "ATIVO", "Modificado"};
        colunasClass = new Class[]{Integer.class, String.class, String.class,
                String.class, String.class, Boolean.class, Date.class};
    }

    @Override
    public Object getValueAt(int rowIndex, int column) {
        Contador contador = lista.get(rowIndex);

        switch (column) {
            case 0:
                return contador.getId();
            case 1:
                return contador.getNome();
            case 2:
                return contador.getCnpj();
            case 3:
                return contador.getTelefone();
            case 4:
                return contador.getEmail();
            case 5:
                return contador.getAtivo();
            case 6:
                return contador.getModificado();
            default:
                throw new IndexOutOfBoundsException("Coluna não existe!");
        }
    }

}
