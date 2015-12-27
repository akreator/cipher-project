package other;

import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Audrey
 */
public class FrequencyTable {
    private Object[][] messageFrequency;
    private String originalText;
    private String[] columnNames;
    private MyModel tModel = new MyModel();
    private JTable table;
    
    public FrequencyTable(Object[][] frequency, String[] names) {
        messageFrequency = frequency;
        columnNames = names;
    }
    
    public JTable createTable() {
        table = new JTable(messageFrequency, columnNames);
        table.setModel(tModel);
        table.setPreferredScrollableViewportSize(new Dimension(20, 125));
        table.setAutoCreateRowSorter(true);
        return table;
    }
    
    public void updateTable(Object[][] newFrequency) {
        tModel.changeData(newFrequency);
        table.setAutoCreateRowSorter(true);
        table.revalidate();
    }
    
    public Object getValueAt(int row, int column) {
        return tModel.getValueAt(row, column);
    }
    
    public void updateTableLook() {
        tModel.fireTableStructureChanged();
        table.updateUI();
    }
    
    class MyModel extends AbstractTableModel {
        @Override
        public int getRowCount() {
            return messageFrequency.length;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return messageFrequency[rowIndex][columnIndex];
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        @Override
        public Class getColumnClass(int column) {
            return getValueAt(0, column).getClass();
        }
        
        @Override
        public String getColumnName(int columnIndex) {
            return columnNames[columnIndex];
        }
        
        public void changeData(Object[][] newFrequency) {
            messageFrequency = newFrequency;
            fireTableDataChanged();
        }
    }
}
