/**
 * 
 */
package jscl.gui.table.renderer;

import java.util.Map;

import javax.swing.table.TableCellRenderer;

/**
 * @author Sławomir Śledź
 * @since 1.0
 */
public interface ITableRendererFactory {

	public TableCellRenderer getTableCellRendererByClass(Class<?> clazz);
	public Map<Class<?>, TableCellRenderer> getClazz2TableCellRendererMap();
	public TableCellRenderer getTableHeaderCellRenderer();
	public ITableCellStyle getTableCellStyle();
	public void setTableCellStyle(ITableCellStyle tableCellStyle);
}
