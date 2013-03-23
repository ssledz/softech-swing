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
package pl.softech.swing.table.editor;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.table.TableCellEditor;
import pl.softech.swing.table.renderer.ITableCellStyle;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class DefaultEditorFactory extends AbstractTableEditorFactory {

	protected final Logger logger = Logger.getLogger(DefaultEditorFactory.class.getName());
	
	private ITableCellStyle tableCellStyle;
	
	/**
	 * @param clazz2TableCellEditor
	 */
	public DefaultEditorFactory() {
		super(new HashMap<Class<?>, TableCellEditor>());
		tableCellStyle = new TableCellStyleImpl();
		init();
	}

	private final void init() {
		clazz2TableCellEditor.put(String.class, new TableCellSimpleJOptionStringEditor(tableCellStyle, "Please input value", "Input"));
		clazz2TableCellEditor.put(Integer.class, new TableCellSimpleJOptionIntegerEditor(tableCellStyle, "Please input value", "Input"));
		clazz2TableCellEditor.put(Double.class, new TableCellSimpleJOptionDoubleEditor(tableCellStyle, "Please input value", "Input"));
		clazz2TableCellEditor.put(Float.class, new TableCellSimpleJOptionFloatEditor(tableCellStyle, "Please input value", "Input"));
	}
	
	protected class TableCellSimpleJOptionStringEditor extends TableCellSimpleJOptionEditor {

		private static final long serialVersionUID = 1L;
		
		/**
		 * @param cellStyle
		 * @param message
		 * @param title
		 */
		public TableCellSimpleJOptionStringEditor(ITableCellStyle cellStyle, String message, String title) {
			super(cellStyle, message, title);
		}

		/** 
		 * @see jscl.gui.table.editor.AbstractTableEditorFactory.TableCellSimpleJOptionEditor#getJOptionInputValueValidator()
		 */
		@Override
		protected JOptionInputValueValidator getJOptionInputValueValidator() {
			return null;
		}

	}
	
	protected class TableCellSimpleJOptionIntegerEditor extends TableCellSimpleJOptionEditor  {

		private static final long serialVersionUID = 1L;
		
		/**
		 * @param cellStyle
		 * @param message
		 * @param title
		 */
		public TableCellSimpleJOptionIntegerEditor(ITableCellStyle cellStyle, String message, String title) {
			super(cellStyle, message, title);
		}
		
		/** 
		 * @see jscl.gui.table.editor.AbstractTableEditorFactory.TableCellSimpleJOptionEditor#getJOptionInputValueValidator()
		 */
		@Override
		protected JOptionInputValueValidator getJOptionInputValueValidator() {
			return new JOptionInputValueValidator() {

				@Override
				public String getMessage() {
					return "Value: " + currentValue + " is not valid integer";
				}

				@Override
				public boolean isValid(String value) {
					try {
						currentValue = Integer.parseInt(value);
					} catch (Exception ex) {
						return false;
					}
					return true;
				}
				
			};
		}
	}
	
	protected class TableCellSimpleJOptionDoubleEditor extends TableCellSimpleJOptionEditor {

		private static final long serialVersionUID = 1L;
		
		/**
		 * @param cellStyle
		 * @param message
		 * @param title
		 */
		public TableCellSimpleJOptionDoubleEditor(ITableCellStyle cellStyle, String message, String title) {
			super(cellStyle, message, title);
		}

		/** 
		 * @see jscl.gui.table.editor.AbstractTableEditorFactory.TableCellSimpleJOptionEditor#getJOptionInputValueValidator()
		 */
		@Override
		protected JOptionInputValueValidator getJOptionInputValueValidator() {
			return new JOptionInputValueValidator() {

				@Override
				public String getMessage() {
					return "Value: " + currentValue + " is not valid double";
				}

				@Override
				public boolean isValid(String value) {
					try {
						currentValue = Double.parseDouble(value);
					} catch (Exception ex) {
						return false;
					}
					return true;
				}
				
			};
		}
	}
	
	protected class TableCellSimpleJOptionFloatEditor extends TableCellSimpleJOptionEditor {

		private static final long serialVersionUID = 1L;
		
		/**
		 * @param cellStyle
		 * @param message
		 * @param title
		 */
		public TableCellSimpleJOptionFloatEditor(ITableCellStyle cellStyle, String message, String title) {
			super(cellStyle, message, title);
		}

		/** 
		 * @see jscl.gui.table.editor.AbstractTableEditorFactory.TableCellSimpleJOptionEditor#getJOptionInputValueValidator()
		 */
		@Override
		protected JOptionInputValueValidator getJOptionInputValueValidator() {
			return new JOptionInputValueValidator() {

				@Override
				public String getMessage() {
					return "Value: " + currentValue + " is not valid foat";
				}

				@Override
				public boolean isValid(String value) {
					try {
						currentValue = Float.parseFloat(value);
					} catch (Exception ex) {
						return false;
					}
					return true;
				}
				
			};
		}
	}
	
	private class TableCellStyleImpl implements ITableCellStyle {
		
		private Font font;
		private final Color oddRowColor;
		private final Color evenRowColor; 
		
		public TableCellStyleImpl() {
			oddRowColor = new Color(0xD8, 0xE2, 0xF1);
			evenRowColor = new Color(0xE8, 0xEE, 0xF7);
			font = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
		}
		
		@Override
		public Color getBackgroudColor(int row, int column, boolean isSelected) {
			
			if(isSelected) return Color.WHITE;
			
			Color c = evenRowColor;
			
			if(row % 2 == 0) c = oddRowColor;
			
			return c;
		}
		
		@Override
		public Color getForegroundColor(int row, int column, boolean isSelected) {
			return Color.BLACK;
		}
		
		public Border getBorder(int row, int column, boolean isSelected) {
			int tmp = 1;
			Color color = Color.RED;
			return BorderFactory.createMatteBorder(tmp, tmp, tmp, tmp, color);
		}
		
		@Override
		public Font getFont(int row, int column, boolean isSelected) {
			return font;
		}

	}

}
