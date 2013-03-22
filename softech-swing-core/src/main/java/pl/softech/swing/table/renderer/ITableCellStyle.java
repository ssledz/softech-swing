/**
 * 
 */
package jscl.gui.table.renderer;

import java.awt.Color;
import java.awt.Font;

import javax.swing.border.Border;

/**
 * @author Sławomir Śledź
 * @since 1.0
 */
public interface ITableCellStyle {

	public Font getFont(int row, int column, boolean isSelected);
	public Color getBackgroudColor(int row, int column, boolean isSelected);
	public Color getForegroundColor(int row, int column, boolean isSelected);
	public Border getBorder(int row, int column, boolean isSelected);
}
