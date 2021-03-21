package global;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class CustomTableModel<T> extends AbstractTableModel {
    protected List<T> lista;
    protected String[] colunas;
    protected Class[] colunasClass;

    /**
     * Será que um vetor de metodos não seria mais util? Do que vetor de String/Class..
     * colunas/colunasClass
     */

    public CustomTableModel() {
        this.lista = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int column) {
        if (column < 0 && column >= colunas.length) throw new IndexOutOfBoundsException("Coluna não existe!");
        return colunas[column];
    }

    @Override
    public Class<?> getColumnClass(int column) {
        if (column < 0 && column >= colunas.length) throw new IndexOutOfBoundsException("Coluna não existe!");
        return colunasClass[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }

    public T get(int rowIndex) {
        return lista.get(rowIndex);
    }

    public void add(T registro) {
        lista.add(registro);
        int lastIndex = getRowCount() - 1;
        // fireTable* é importante, se não o table não atualiza o render na tela
        fireTableRowsInserted(lastIndex, lastIndex);
    }

    public void update(int rowIndex, T registro) {
        lista.set(rowIndex, registro);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void remove(int rowIndex) {
        lista.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public List<T> getList() {
        return lista;
    }

    public void setList(List<T> lista) {
        this.lista = lista;
        fireTableDataChanged();
    }

    public int indexOf(T obj){
        return lista.indexOf(obj);
    }

    public int getSize(){
        return lista.size();
    }

    public void clear() {
        lista.clear();
        fireTableDataChanged();
    }
}
