package module.cliente;

import module.contador.Contador;
import module.contador.ContadorDAO;
import module.fiscal.Fiscal;
import module.versao.Versao;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class ClienteHelper {

    private final ClienteGUI view;
    private ClienteTableModel tableModel;

    public ClienteHelper(ClienteGUI view) {
        this.view = view;
        view.getTxtContador().setEditable(false);
    }

    public void preencherCmbVersao(List<Versao> lstVersao) {
        DefaultComboBoxModel<Versao> comboBoxModel = (DefaultComboBoxModel<Versao>) view.getCmbVersao().getModel();
        for (Versao versao : lstVersao) {
            comboBoxModel.addElement(versao);
        }
    }

    public void preencherTabela(List<Cliente> clientes) {
        tableModel = new ClienteTableModel(clientes);

        view.getTable().setModel(tableModel);
        view.getTable().setAutoCreateRowSorter(true);

        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));

        TableRowSorter<ClienteTableModel> sorter = (TableRowSorter) view.getTable().getRowSorter();
        sorter.setSortKeys( sortKeys );
    }

    private void preencherTabelaFiscal(List<Fiscal> lstFiscal){
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setNumRows(0);
        tableModel.addColumn("ID");
        tableModel.addColumn("Status");
        tableModel.addColumn("EnviadoEm");
        tableModel.addColumn("Modificado");

        lstFiscal
                .forEach(f -> tableModel.addRow(new Object[]{
                                f.getId(), f.getStatus().name(),f.getDataEnvio(),
                                f.getModificado()
                        })
                );
        JTable tblFiscal = view.getTblFiscal();
        tblFiscal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblFiscal.setModel(tableModel);

        tblFiscal.setRowHeight(20);
        tblFiscal.getColumnModel().getColumn(0).setPreferredWidth(60); // id
        tblFiscal.getColumnModel().getColumn(1).setPreferredWidth(390);
        tblFiscal.getColumnModel().getColumn(2).setPreferredWidth(148);
        tblFiscal.getColumnModel().getColumn(3).setPreferredWidth(148);

        tblFiscal.setAutoCreateRowSorter(true);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();

        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));

        TableRowSorter<ClienteTableModel> sorter = (TableRowSorter) tblFiscal.getRowSorter();
        sorter.setSortKeys( sortKeys );

//        view.getTblFiscal().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent evt) {
//                for (int i = 0; i < view.getTblFiscal().getColumnCount(); i++) {
//                    TableColumn column = view.getTblFiscal().getColumnModel().getColumn(i);
//                    System.out.println("tamanho da coluna " + i + " : " + column.getWidth());
//                }
//            }
//        });

    }

    public void preencherCampos(Cliente cliente) {
        view.setId(cliente.getId());

        view.getTxtNome().setText(cliente.getNome());
        view.getTxtCNPJ().setText(cliente.getCnpj());
        view.getTxtTelefone().setText(cliente.getTelefone());
        view.getTxtSerial().setText(cliente.getSerial());

        view.getCmbVersao().setSelectedItem(cliente.getVersao());

        this.setContador(cliente.getContador());

        view.getTxtEmail().setText(cliente.getEmail());
        view.getTxtIdRemoto().setText(cliente.getIdRemoto());
        view.getChbEnviaDadosContador().setSelected(cliente.getEnviarDadosContador());

        view.getChbAtivo().setSelected(cliente.getAtivo());

        preencherTabelaFiscal(cliente.getLstFiscal());
//		focusInTabCad(1);
    }

    public void focusInTabCad(int index) {
        view.getTabbedPane().setSelectedIndex(index);
    }

    public int getActiveTabCad() {
        return view.getTabbedPane().getSelectedIndex();
    }

    public void setContador(Contador contador) {
        view.setContadorId(contador.getId());
        view.getTxtContador().setText(contador.getNome());
    }

    public void bloquearCampos(Boolean value) {
        value = !value;
        view.getTxtNome().setEnabled(value);
        view.getTxtCNPJ().setEnabled(value);
        view.getTxtTelefone().setEnabled(value);
        view.getTxtSerial().setEnabled(value);

        view.getCmbVersao().setEnabled(value);
        view.getBtnProcuraContadorDetalhes().setEnabled(value);

        view.getTxtEmail().setEnabled(value);
        view.getTxtIdRemoto().setEnabled(value);
        view.getChbEnviaDadosContador().setEnabled(value);
        view.getChbAtivo().setEnabled(value);

    }

    public void obterDados(Cliente cliente) {
        cliente.setNome(view.getTxtNome().getText());

        cliente.setCnpj(view.getTxtCNPJ().getText());
        cliente.setTelefone(view.getTxtTelefone().getText());
        cliente.setSerial(view.getTxtSerial().getText().replaceAll("\\s+", ""));

        cliente.setVersao((Versao) view.getCmbVersao().getSelectedItem());


        /** @FIXME ATUALIZAR ComboModel Contador pode ser generico.. */
        ContadorDAO contadorDAO = new ContadorDAO();
        cliente.setContador(contadorDAO.selectPerID(view.getContadorId()));

        cliente.setEmail(view.getTxtEmail().getText());
        cliente.setIdRemoto(view.getTxtIdRemoto().getText().replaceAll("\\s+", ""));

        cliente.setEnviarDadosContador(view.getChbEnviaDadosContador().isSelected());
        cliente.setAtivo(view.getChbAtivo().isSelected());

//		return cliente;
    }

    public Boolean validarCampos() {
        // FIXME melhorar forma de validacao dos campos, e criar mensagens tipo toolkit
        // para informar oque precisa ser preenchido!

        if (view.getTxtNome().getText().isEmpty() || view.getTxtCNPJ().getText().isEmpty()
                || view.getTxtTelefone().getText().isEmpty() || view.getTxtSerial().getText().isEmpty()
                || view.getContadorId() == -1) {
            return false;
        }
        return true;
    }

    public void limparCampos() {
        view.setId(-1);
        view.getTxtNome().setText("");
        view.getTxtCNPJ().setText("");
        view.getTxtTelefone().setText("");
        view.getTxtSerial().setText("");

        view.getCmbVersao().setSelectedIndex(0);

        view.setContadorId(-1);
        view.getTxtContador().setText("");

        view.getTxtEmail().setText("");
        view.getTxtIdRemoto().setText("");
        view.getChbEnviaDadosContador().setSelected(false);
        view.getChbAtivo().setSelected(false);

        preencherTabelaFiscal(new ArrayList<>());
    }

    public Cliente getClienteFromModel(int rowIndex) {
        return tableModel.get(rowIndex);
    }

    public int getIndexFromModel(Cliente cliente) {
        return tableModel.indexOf(cliente);
    }

    public List<Cliente> getListFromModel() { return tableModel.getList(); }

    public void insertClienteTableModel(Cliente cliente) {
        tableModel.add(cliente);
    }

    public void updateClienteTableModel(int rowIndex, Cliente cliente) {
        tableModel.update(rowIndex, cliente);
    }

    public void removeClienteTableModel(int rowIndex) {
        tableModel.remove(rowIndex);
    }

}
