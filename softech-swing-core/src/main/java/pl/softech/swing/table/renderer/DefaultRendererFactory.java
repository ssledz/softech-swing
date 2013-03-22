package jscl.gui.table.renderer;

import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

/**
 * @author Sławomir Śledź
 * @since 1.0
 */
public class DefaultRendererFactory extends AbstractTableRendereFactory {

    private final TableCellRenderer tableHeaderCellRenderer;
    private ITableCellStyle tableCellStyle;
    private ITableCellStyle tableHeaderCellStyle;

    public DefaultRendererFactory() {
        super(new HashMap<Class<?>, TableCellRenderer>());
        this.tableCellStyle = new TableCellStyleImpl();
        this.tableHeaderCellStyle = new TableHeaderCellStyleImpl();
        this.tableHeaderCellRenderer = new StringTableCellRenderer(tableHeaderCellStyle);
        init();
    }

    private final void init() {
        clazz2TableCellRenderer.put(String.class, new StringTableCellRenderer(tableCellStyle));
        clazz2TableCellRenderer.put(Object.class, new StringTableCellRenderer(tableCellStyle));
        clazz2TableCellRenderer.put(Integer.class, new StringTableCellRenderer(tableCellStyle));
        clazz2TableCellRenderer.put(Float.class, new StringTableCellRenderer(tableCellStyle));
        clazz2TableCellRenderer.put(Date.class, new DateTableCellRenderer(tableCellStyle));
    }

    /**
     * @see jscl.gui.table.renderer.ITableRendererFactory#getTableCellStyle()
     */
    @Override
    public ITableCellStyle getTableCellStyle() {
        return tableCellStyle;
    }

    /**
     * @see jscl.gui.table.renderer.ITableRendererFactory#getTableHeaderCellRenderer()
     */
    @Override
    public TableCellRenderer getTableHeaderCellRenderer() {
        return tableHeaderCellRenderer;
    }

    @Override
    public void setTableCellStyle(ITableCellStyle tableCellStyle) {
        this.tableCellStyle = tableCellStyle;
    }

    protected class TableHeaderCellStyleImpl implements ITableCellStyle {

        private Font font;
        
        private Border border;

        public TableHeaderCellStyleImpl() {
            font = new Font(Font.SANS_SERIF, Font.PLAIN, 18);
            border = UIManager.getBorder("TableHeader.cellBorder");
        }

        /**
         * @see jscl.gui.table.renderer.ITableCellStyle#getBackgroudColor(int,
         *      int, boolean)
         */
        @Override
        public Color getBackgroudColor(int row, int column, boolean isSelected) {
            return Color.WHITE;
        }

        /**
         * @see jscl.gui.table.renderer.ITableCellStyle#getBorder(int, int,
         *      boolean)
         */
        @Override
        public Border getBorder(int row, int column, boolean isSelected) {
            
             return border;
        }

        /**
         * @see jscl.gui.table.renderer.ITableCellStyle#getFont(int, int,
         *      boolean)
         */
        @Override
        public Font getFont(int row, int column, boolean isSelected) {
            return font;
        }

        /**
         * @see jscl.gui.table.renderer.ITableCellStyle#getForegroundColor(int,
         *      int, boolean)
         */
        @Override
        public Color getForegroundColor(int row, int column, boolean isSelected) {
            return Color.BLACK;
        }

    }

    public static class TableCellStyleImpl implements ITableCellStyle {

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

            if (isSelected)
                return Color.WHITE;

            Color c = evenRowColor;

            if (row % 2 == 0)
                c = oddRowColor;

            return c;
        }

        @Override
        public Color getForegroundColor(int row, int column, boolean isSelected) {
            return Color.BLACK;
        }

        public Border getBorder(int row, int column, boolean isSelected) {
            return null;
            // int tmp = 1;
            // return BorderFactory.createMatteBorder(tmp, tmp, tmp, tmp,
            // Color.WHITE);
        }

        @Override
        public Font getFont(int row, int column, boolean isSelected) {
            return font;
        }

    }

    private final class StringTableCellRenderer extends TableCellSimpleLabelRenderer {

        /**
         * @param cellStyle
         */
        public StringTableCellRenderer(ITableCellStyle cellStyle) {
            super(cellStyle);
        }

        /**
         * @see jscl.gui.table.renderer.AbstractTableRendereFactory.StringExtractor#toString(java.lang.Object)
         */
        @Override
        public String toString(Object o) {
            if (o == null)
                return "-";
            return o.toString();
        }

    }

    private final class DateTableCellRenderer extends TableCellSimpleLabelRenderer {

        private DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

        /**
         * @param cellStyle
         */
        public DateTableCellRenderer(ITableCellStyle cellStyle) {
            super(cellStyle);
        }

        /**
         * @see jscl.gui.table.renderer.AbstractTableRendereFactory.StringExtractor#toString(java.lang.Object)
         */
        @Override
        public String toString(Object o) {
            if (o == null)
                return "-";
            return DATE_FORMAT.format(o);
        }

    }

}
