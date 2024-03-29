/*
 * Copyright 2013 Sławomir Śledź <slawomir.sledz@sof-tech.pl>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.softech.swing.table.renderer;

import java.awt.Component;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public abstract class AbstractTableRendereFactory implements
		ITableRendererFactory {

	public interface StringExtractor {
		public String toString(Object o); 
	}
	
	protected final Map<Class<?>, TableCellRenderer> clazz2TableCellRenderer;
	
	protected AbstractTableRendereFactory(Map<Class<?>, TableCellRenderer> clazz2TableCellRenderer) {
		this.clazz2TableCellRenderer = clazz2TableCellRenderer;
	}
	
	/** 
	 * @see jscl.gui.table.renderer.ITableRendererFactory#getClazz2TableCellRendererMap()
	 */
	@Override
	public Map<Class<?>, TableCellRenderer> getClazz2TableCellRendererMap() {
		return clazz2TableCellRenderer;
	}

	/** 
	 * @see jscl.gui.table.renderer.ITableRendererFactory#getTableCellRendererByClass(java.lang.Class)
	 */
	@Override
	public TableCellRenderer getTableCellRendererByClass(Class<?> clazz) {
		return clazz2TableCellRenderer.get(clazz);
	}

	protected abstract class TableCellSimpleLabelRenderer implements TableCellRenderer, StringExtractor {

		private JLabel label;
		private ITableCellStyle cellStyle;
		
		public TableCellSimpleLabelRenderer(ITableCellStyle cellStyle) {
			this.cellStyle = cellStyle;
			init();
		}
		
		private final void init() {
			
			label = new JLabel();
			label.setOpaque(true);
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setVerticalAlignment(JLabel.CENTER);
		}
		
		/** 
		 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
		 */
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			
			label.setFont(cellStyle.getFont(row, column, isSelected));
			label.setBackground(cellStyle.getBackgroudColor(row, column, isSelected));
			label.setForeground(cellStyle.getForegroundColor(row, column, isSelected));
			label.setBorder(cellStyle.getBorder(row, column, isSelected));
			label.setText(toString(value));
			
			return label;
		}

	
		
	}
	
}
