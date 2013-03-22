/**
 * 
 */
package jscl.gui.table.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import jscl.gui.table.renderer.ITableCellStyle;

/**
 * @author Sławomir Śledź
 * @since 1.0
 */
public abstract class AbstractTableEditorFactory implements ITableEditorFactory {

	protected final Map<Class<?>, TableCellEditor> clazz2TableCellEditor;
	
	protected AbstractTableEditorFactory(Map<Class<?>, TableCellEditor> clazz2TableCellEditor) {
		this.clazz2TableCellEditor = clazz2TableCellEditor;
	}

	
	/**
	 * @return the clazz2TableCellEditor
	 */
	public Map<Class<?>, TableCellEditor> getClazz2TableCellEditorMap() {
		return clazz2TableCellEditor;
	}
	
	protected interface JOptionInputValueValidator {
		public boolean isValid(String value);
		public String getMessage();
	}
	
	protected abstract class TableCellSimpleJOptionEditor extends AbstractCellEditor implements TableCellEditor {
		
		private static final long serialVersionUID = 1L;

		protected ITableCellStyle cellStyle;
		protected final String message;
		protected final String title;
		protected JLabel label;
		protected Object currentValue;
		protected boolean isSelected;
		boolean shouldAcceptValue = false;
		boolean isInEdition;
		
		
		public TableCellSimpleJOptionEditor(ITableCellStyle cellStyle, String message, String title) {
			this.cellStyle = cellStyle;
			this.message = message;
			this.title = title;
			init();
		}

		private final void init() {
		        
			label = new JLabel();
			label.setOpaque(true);
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setVerticalAlignment(JLabel.CENTER);
			label.addMouseListener(new MouseAdapter() {
				
				private Color background;
				
				/** 
				 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
				 */
				@Override
				public void mouseClicked(MouseEvent e) {
					isInEdition = true;
					showDialog();
					isInEdition = false;
				}
				
				/** 
				 * @see java.awt.event.MouseAdapter#mouseEntered(java.awt.event.MouseEvent)
				 */
				@Override
				public void mouseEntered(MouseEvent e) {
					background = label.getBackground();
					label.setBackground(Color.ORANGE);
					label.requestFocusInWindow();

				}
				
				/** 
				 * @see java.awt.event.MouseAdapter#mouseExited(java.awt.event.MouseEvent)
				 */
				@Override
				public void mouseExited(MouseEvent e) {
					label.setBackground(background);
				}
				
			});
		}
		
		protected void showDialog() {
			Object value = JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE, null, null, currentValue);
			if(value == null) {
				cancelCellEditing();
				return;
			}
			
			currentValue = value;
			shouldAcceptValue = true;
			stopCellEditing();
		}
		
		public Component getLabel(JTable table, Object value, int row, int column) {
			
			label.setFont(cellStyle.getFont(row, column, true));
			label.setBackground(cellStyle.getBackgroudColor(row, column, true));
			label.setForeground(cellStyle.getForegroundColor(row, column, true));
			label.setBorder(cellStyle.getBorder(row, column, true));
			label.setText(value.toString());
			
			return label;
		}
		
		protected abstract JOptionInputValueValidator getJOptionInputValueValidator();
		
		/** 
		 * @see javax.swing.AbstractCellEditor#stopCellEditing()
		 */
		@Override
		public boolean stopCellEditing() {
			JOptionInputValueValidator validator = getJOptionInputValueValidator(); 
			if(validator != null && !validator.isValid(currentValue.toString())) {
				JOptionPane.showMessageDialog(null, validator.getMessage(),"Warning", JOptionPane.WARNING_MESSAGE);
				showDialog();
				return false;
			}
			return super.stopCellEditing();
		}
		
		/** 
		 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
		 */
		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			this.currentValue = value;
			this.isSelected = isSelected;
			return getLabel(table, value, row, column);
		}
		
		/** 
		 * @see javax.swing.CellEditor#getCellEditorValue()
		 */
		@Override
		public Object getCellEditorValue() {
			return currentValue;
		}
		
		
		
	}
	
}
