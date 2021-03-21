package module.cliente;

import global.CustomTableModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClienteTableModel extends CustomTableModel<Cliente> {

    public ClienteTableModel() {
        this(new ArrayList<>());
    }

    public ClienteTableModel(List<Cliente> lista) {
        this.lista = lista;
        this.colunas = new String[]{"ID", "NOME", "CNPJ", "TELEFONE", "EMAIL",
                "ID REMOTO", "CONTADOR", "Enviar", "Modificado"};
        this.colunasClass = new Class[]{Integer.class, String.class, String.class,
                String.class, String.class, String.class, String.class, Boolean.class, Date.class};
    }

    @Override
    public Object getValueAt(int rowIndex, int column) {
        Cliente cliente = lista.get(rowIndex);

        switch (column) {
            case 0:
                return cliente.getId();
            case 1:
                return cliente.getNome();
            case 2:
                return cliente.getCnpj();
            case 3:
                return cliente.getTelefone();
            case 4:
                return cliente.getEmail();
            case 5:
                return cliente.getIdRemoto();
            case 6:
                return cliente.getContador().getNome();
            case 7:
                return cliente.getEnviarDadosContador();
            case 8:
                return cliente.getModificado();
            default:
                throw new IndexOutOfBoundsException("Coluna não existe!");

        }
    }
}
