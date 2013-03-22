/**
 * 
 */
package jscl.gui.table.editor;

import java.util.Map;

import javax.swing.table.TableCellEditor;

/**
 * @author Sławomir Śledź
 * @since 1.0
 */
public interface ITableEditorFactory {

	public Map<Class<?>, TableCellEditor> getClazz2TableCellEditorMap();
	
}
