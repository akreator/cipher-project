package CipherGui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Audrey
 */
public class FrequencyTable extends JTable {
    private Object[][] messageFrequency;
    private String originalText;
    private String[] columnNames;
    private JTable table;
    private MyModel tModel = new MyModel();

    
    public FrequencyTable(Object[][] frequency, String[] names) {
        messageFrequency = frequency;
        columnNames = names;
        setModel(tModel);
        setPreferredScrollableViewportSize(new Dimension(20, 125));
        setAutoCreateRowSorter(true);
    }
    
    public void updateTable(Object[][] newFrequency) {
        tModel.changeData(newFrequency);
        table.setAutoCreateRowSorter(true);
        table.revalidate();
    }
    
    public void updateTableLook() {
        tModel.fireTableStructureChanged();
        updateUI();
    }
    
    @Override
    public Object getValueAt(int row, int column) {
        return tModel.getValueAt(row, column);
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
