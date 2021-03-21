package module.contador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;

import module.cliente.Cliente;

public class ContadorHelper {

    private final ContadorGUI view;
    private ContadorTableModel tableModel;

    public ContadorHelper(ContadorGUI view) {
        this.view = view;
    }

    public void preencherTabela(List<Contador> lista) {
        tableModel = new ContadorTableModel(lista);

        view.getTable().setModel(tableModel);
        view.getTable().setAutoCreateRowSorter(true);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));

        TableRowSorter<ContadorTableModel> sorter = (TableRowSorter) view.getTable().getRowSorter();
        sorter.setSortKeys(sortKeys);
    }

    private void preencherTabelaClientes(List<Cliente> lista) {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setNumRows(0);
        tableModel.addColumn("ID");
        tableModel.addColumn("Cliente");
        tableModel.addColumn("CNPJ");
        tableModel.addColumn("Modificado");

        lista
                .forEach(f -> tableModel.addRow(new Object[]{
                                f.getId(), f.getNome(), f.getCnpj(),
                                f.getModificado()
                        })
                );
        JTable tblCliente = view.getTblClientes();
        tblCliente.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblCliente.setModel(tableModel);
        tblCliente.setRowHeight(20);
        tblCliente.getColumnModel().getColumn(0).setPreferredWidth(60); // id
        tblCliente.getColumnModel().getColumn(1).setPreferredWidth(390);
        tblCliente.getColumnModel().getColumn(2).setPreferredWidth(148);
        tblCliente.getColumnModel().getColumn(3).setPreferredWidth(148);

        tblCliente.setAutoCreateRowSorter(true);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();

        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));

        TableRowSorter<ContadorTableModel> sorter = (TableRowSorter) tblCliente.getRowSorter();
        sorter.setSortKeys(sortKeys);
    }

    public void preencherCampos(Contador contador) {
        view.setId(contador.getId());

        view.getTxtNome().setText(contador.getNome());
        view.getTxtCNPJ().setText(contador.getCnpj());
        view.getTxtTelefone().setText(contador.getTelefone());
        view.getTxtEmail().setText(contador.getEmail());
        view.getChbAtivo().setSelected(contador.getAtivo());

        preencherTabelaClientes(contador.getLstClientes());

    }

    public void focusInTabCad(int index) {
        view.getTabbedPane().setSelectedIndex(index);
    }

    public int getActiveTabCad() {
        return view.getTabbedPane().getSelectedIndex();
    }

    public void bloquearCampos(Boolean value) {
        value = !value;
        view.getTxtNome().setEnabled(value);
        view.getTxtCNPJ().setEnabled(value);
        view.getTxtTelefone().setEnabled(value);

        view.getTxtEmail().setEnabled(value);
        view.getChbAtivo().setEnabled(value);
    }

    public void obterDados(Contador contador) {
        contador.setNome(view.getTxtNome().getText());

        contador.setCnpj(view.getTxtCNPJ().getText());
        contador.setTelefone(view.getTxtTelefone().getText());

        contador.setEmail(view.getTxtEmail().getText());
        contador.setAtivo(view.getChbAtivo().isSelected());
    }

    public Boolean validarCampos() {
        // FIXME melhorar forma de validacao dos campos, e criar mensagens tipo toolkit
        // para informar oque precisa ser preenchido!

        if (view.getTxtNome().getText().isEmpty() || view.getTxtCNPJ().getText().isEmpty()
                || view.getTxtTelefone().getText().isEmpty()) {
            return false;
        }
        return true;
    }

    public void limparCampos() {
        view.setId(-1);
        view.getTxtNome().setText("");
        view.getTxtCNPJ().setText("");
        view.getTxtTelefone().setText("");
        preencherTabelaClientes(new ArrayList<>());
    }

    public Contador getContadorFromModel(int rowIndex) {
        return tableModel.get(rowIndex);
    }

    public int getIndexFromModel(Contador contador) {
        return tableModel.indexOf(contador);
    }

    public List<Contador> getListFromModel() { return tableModel.getList(); }

    public void insertContadorTableModel(Contador contador) {
        tableModel.add(contador);
    }

    public void updateContadorTableModel(int rowIndex, Contador contador) {
        tableModel.update(rowIndex, contador);
    }

    public void removeContadorTableModel(int rowIndex) {
        tableModel.remove(rowIndex);
    }

}
