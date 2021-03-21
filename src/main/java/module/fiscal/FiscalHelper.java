package module.fiscal;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.swing.*;
import javax.swing.table.TableRowSorter;

import global.Status;
import module.versao.Versao;

public class FiscalHelper {

    private final FiscalGUI view;
    private FiscalTableModel tableModel;


    public FiscalHelper(FiscalGUI view) {
        this.view = view;
        tableModel = new FiscalTableModel();

        mostrarProgress(false);

        view.getTable().setModel(tableModel);
        view.getTable().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        view.getTable().setAutoCreateRowSorter(true);
        view.getTable().setRowHeight(20);
        view.getTable().getColumnModel().getColumn(0).setPreferredWidth(47); // id
        view.getTable().getColumnModel().getColumn(1).setPreferredWidth(122); // serial
        view.getTable().getColumnModel().getColumn(2).setPreferredWidth(216);// razao social
        view.getTable().getColumnModel().getColumn(3).setPreferredWidth(131);// contador
        view.getTable().getColumnModel().getColumn(4).setPreferredWidth(51);// enviardados
        view.getTable().getColumnModel().getColumn(5).setPreferredWidth(106);// status
        view.getTable().getColumnModel().getColumn(6).setPreferredWidth(86);// data
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING));

        TableRowSorter<FiscalTableModel> sorter = (TableRowSorter) view.getTable().getRowSorter();
        sorter.setSortKeys(sortKeys);
        bloquearCamposEdicao();
    }

    public void mostrarProgress(boolean t) {
        view.getProgressBar().setMinimum(0);
        view.getProgressBar().setMaximum(100);
        view.getProgressBar().setVisible(t);
        view.getLblProgress().setVisible(t);
    }

    public void atualizaProgress(int n, int max) {
        view.getLblProgress().setText("Processando " + n + " de " + max);
//		view.getProgressBar().setValue(n);
    }

    public JProgressBar getProgressBar() {
        return view.getProgressBar();
    }

    public void atualizaStatusProgress(String status) {
        view.getLblProgress().setText(status);
    }

    public void preencherCmbMesFiscal(ArrayList<String> lstMes) {
        DefaultComboBoxModel<String> cmbMesFiscalModel = new DefaultComboBoxModel<String>();

        for (String mes : lstMes) {
            cmbMesFiscalModel.addElement(mes);
        }
        view.getCmbMesFiscal().setModel(cmbMesFiscalModel);
        Calendar calendario = Calendar.getInstance();
        view.getCmbMesFiscal().setSelectedIndex(((int) calendario.get(Calendar.MONTH)) - 1);

    }

    public void preencherCmbAnoFiscal(ArrayList<String> lstAno) {
        DefaultComboBoxModel<String> cmbAnoFiscalModel = new DefaultComboBoxModel<String>();

        for (String ano : lstAno) {
            cmbAnoFiscalModel.addElement(ano);
        }
        view.getCmbAnoFiscal().setModel(cmbAnoFiscalModel);

        Calendar calendario = Calendar.getInstance();

        int index = lstAno.indexOf(String.valueOf(calendario.get(Calendar.YEAR)));
        view.getCmbAnoFiscal().setSelectedIndex(index);
    }

    public void preencherCmbVersao(List<Versao> lstVersao) {
        DefaultComboBoxModel<Versao> comboBoxModel = (DefaultComboBoxModel<Versao>) view.getCmbVersao().getModel();

        for (Versao versao : lstVersao) {
            comboBoxModel.addElement(versao);
        }
    }

    public Boolean preencherTabela(List<Fiscal> lstFiscal) {
//		lstFiscal.size();

//		tableModel = new FiscalTableModel(lstFiscal);
        tableModel.setList(lstFiscal);
        atualizaContadores();

//		if (view.getTable().getRowCount() > 0) {
//			view.getTable().setRowSelectionInterval(index, index);
//			view.getTable().scrollRectToVisible(view.getTable().getCellRect(index, 0, true));
//		}
        return true;
    }

    private void atualizaContadores() {
        long aguardando = tableModel.getList().stream()
                .filter(f -> f.getStatus() == Status.AGUARDANDO_ENVIO
                        || f.getStatus() == Status.NAO_GEROU_DOC_FISCAL)
                .count();

        long pendente = tableModel.getList().stream()
                .filter(f -> f.getStatus() == Status.PENDENTE).count();

        long nao_gerou = tableModel.getList().stream()
                .filter(f -> f.getStatus() == Status.NAO_GEROU_DOC_FISCAL
                        || f.getStatus() == Status.CONCLUIDO_NAO_GEROU_DOC_FISCAL)
                .count();

        long falha = tableModel.getList().stream()
                .filter(f -> f.getStatus() == Status.FALHA_ENVIO).count();

        long concluido = tableModel.getList().stream()
                .filter(f -> f.getStatus() == Status.CONCLUIDO
                        || f.getStatus() == Status.CONCLUIDO_MANUAL)
                .count();

        view.getLblQtdAguardando().setText("Aguardando: " + aguardando);
        view.getLblQtdPendente().setText("     Pendente: " + pendente);
        view.getLblQtdFalha().setText("             Falha: " + falha);
        view.getLblQtdConcluido().setText("Concluido: " + concluido);
        view.getLblQtdNaoGerou().setText("  SemMov: " + nao_gerou);
        view.getLblQtdTotal().setText("         Total: " + tableModel.getSize());
    }

    public void preencherCampos(Fiscal fiscal) {
        view.getTxtNome().setText(fiscal.getCliente().getNome());
        view.getTxtCNPJ().setText(fiscal.getCliente().getCnpj());
        view.getTxtTelefone().setText(fiscal.getCliente().getTelefone());
        view.getTxtSerial().setText(fiscal.getCliente().getSerial());

        view.getCmbVersao().setSelectedItem(fiscal.getCliente().getVersao());

        view.setContadorId(fiscal.getCliente().getContador().getId());
        view.getTxtContador().setText(fiscal.getCliente().getContador().getNome());


        view.getTxtEmail().setText(fiscal.getCliente().getEmail());
        view.getTxtIdRemoto().setText(fiscal.getCliente().getIdRemoto());
        view.getChbEnviaDadosContador().setSelected(fiscal.getCliente().getEnviarDadosContador());

        view.getLblFiscalStatusName().setText(fiscal.getStatus().toString());
        if (fiscal.getDataEnvio() != null)
            view.getLblFiscalStatusEnviadoEm().setText(fiscal.getDataEnvio().toString());
        view.getLblFiscalStatusUsername().setText("");
//		focusInTabCad(1);
    }

    public void limparCampos() {
        view.getTxtNome().setText("");
        view.getTxtCNPJ().setText("");
        view.getTxtTelefone().setText("");
        view.getTxtSerial().setText("");

//		view.getCmbVersao().setSelectedItem();

//		view.setContadorId("");
        view.getTxtContador().setText("");


        view.getTxtEmail().setText("");
        view.getTxtIdRemoto().setText("");
        view.getChbEnviaDadosContador().setSelected(false);

        view.getListFiles().setModel(new DefaultListModel<>());
        view.getLblFiscalStatusName().setText("");
        view.getLblFiscalStatusEnviadoEm().setText("");
        view.getLblFiscalStatusUsername().setText("");

//		focusInTabCad(1);
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
        view.getTxtSerial().setEnabled(value);

        view.getCmbVersao().setEnabled(value);
        view.getBtnProcuraContadorDetalhes().setEnabled(value);

        view.getTxtEmail().setEnabled(value);
        view.getTxtIdRemoto().setEnabled(value);
        view.getChbEnviaDadosContador().setEnabled(value);

    }

    private void bloquearCamposEdicao() {
        Boolean value = false;

        view.getTxtNome().setEditable(value);
//		view.getTxtNome().setHorizontalAlignment(JTextField.LEADING);

        view.getTxtContador().setEditable(value);

        view.getTxtCNPJ().setEditable(value);
        view.getTxtTelefone().setEditable(value);
        view.getTxtSerial().setEditable(value);
        view.getCmbVersao().setEditable(value);
        view.getTxtEmail().setEditable(value);
        view.getTxtIdRemoto().setEditable(value);

        view.getCmbVersao().setEnabled(value);
        view.getChbEnviaDadosContador().setEnabled(value);
        view.getBtnProcuraContadorDetalhes().setEnabled(value);
    }

    public int tablePegaID(MouseEvent evt) {
        JTable table = view.getTable();
        int row = table.rowAtPoint(evt.getPoint());
        int col = table.columnAtPoint(evt.getPoint());
        if (row >= 0 && col >= 0) {
            try {
                return (Integer) table.getModel().getValueAt(row, 0);
            } catch (Exception e) {
                return -1;
            }
        }
        return -1;
    }


    public void bloquearBotoes(Boolean value) {
        // TODO Auto-generated method stub
        value = !value;

    }

    public Fiscal getFiscalFromModel(int rowIndex) {
        return tableModel.get(rowIndex);
    }

    public int getIndexFromModel(Fiscal fiscal) {
        return tableModel.indexOf(fiscal);
    }

    public List<Fiscal> getListFromModel() {
        return tableModel.getList();
    }

    public void insertFiscalTableModel(Fiscal fiscal) {
        tableModel.add(fiscal);
        atualizaContadores();
    }

    public void updateFiscalTableModel(int rowIndex, Fiscal fiscal) {
        tableModel.update(rowIndex, fiscal);
        atualizaContadores();
    }

    public void removeFiscalTableModel(int rowIndex) {
        tableModel.remove(rowIndex);
        atualizaContadores();
    }
}
