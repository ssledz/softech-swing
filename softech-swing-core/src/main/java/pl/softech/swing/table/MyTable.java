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
package pl.softech.swing.table;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import pl.softech.swing.table.editor.DefaultEditorFactory;
import pl.softech.swing.table.editor.ITableEditorFactory;
import pl.softech.swing.table.renderer.DefaultRendererFactory;
import pl.softech.swing.table.renderer.ITableRendererFactory;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class MyTable<T> extends JPanel {

	private static final long serialVersionUID = 1L;
	private final TableModelFactory<T> tableModelFactory;
	private final ITableModel<T> model;
    private JTable delegate;
    private static final int DEFAULT_ROW_HEIGHT = 30;
    private ITableRendererFactory rendereFactory;
    private ITableEditorFactory editorFactory; 

    public MyTable(Class<?> clazz) {
        super(new BorderLayout());
        tableModelFactory = new TableModelFactory<T>();
        model = tableModelFactory.createModel(clazz);
        init();
    }

    private final void init() {

    	rendereFactory = new DefaultRendererFactory();
    	editorFactory = new DefaultEditorFactory();
    	
        delegate = new JTable(model);
        delegate.setColumnSelectionAllowed(false);
        delegate.setCellSelectionEnabled(false);
        delegate.setRowSelectionAllowed(true);
        delegate.setFillsViewportHeight(true);
        add(new JScrollPane(delegate));
        initDefaultRowHeight();
        initCellRenderers();
        initCellEditors();
        initColumnSorters();
    }
    
    private final void initDefaultRowHeight() {
    	delegate.setRowHeight(DEFAULT_ROW_HEIGHT);
    }
    
//	private void initColumnWidth() {
//
//		TableColumn column = null;
//		Component comp = null;
//		int headerWidth = 0;
//		int cellWidth = 0;
//		Object[] longValues = model.getLongestValues();
//
//		TableCellRenderer headerRenderer = delegate.getTableHeader()
//				.getDefaultRenderer();
//
//		for (int i = 0; i < delegate.getColumnCount(); i++) {
//			column = delegate.getColumnModel().getColumn(i);
//
//			comp = headerRenderer.getTableCellRendererComponent(null, column
//					.getHeaderValue(), false, false, 0, 0);
//			headerWidth = comp.getPreferredSize().width;
//
//			comp = delegate.getDefaultRenderer(model.getColumnClass(i))
//					.getTableCellRendererComponent(delegate, longValues[i], false,
//							false, 0, i);
//
//			if (comp != null)
//				cellWidth = comp.getPreferredSize().width;
//
//			column.setPreferredWidth(Math.max(headerWidth, cellWidth));
//		}
//	}

    
    private final void initCellRenderers() {
    	TableCellRenderer headerTableCellRender = rendereFactory.getTableHeaderCellRenderer();
    	TableColumnModel columnModel = delegate.getColumnModel();
    	for(int i = 0; i < columnModel.getColumnCount(); i++) {
    		columnModel.getColumn(i).setHeaderRenderer(headerTableCellRender);
    	}
    	
    	Map<Class<?>, TableCellRenderer> map = rendereFactory.getClazz2TableCellRendererMap();
    	
    	for(Map.Entry<Class<?>, TableCellRenderer> e : map.entrySet()) {
    		delegate.setDefaultRenderer(e.getKey(), e.getValue());
    	}
    	
    }
    
    private final void initCellEditors() {
    	
    	Map<Class<?>, TableCellEditor> map = editorFactory.getClazz2TableCellEditorMap();
    	for(Map.Entry<Class<?>, TableCellEditor> e : map.entrySet()) {
    		delegate.setDefaultEditor(e.getKey(), e.getValue());
    	}
    }
    
    private void initColumnSorters() {
    	delegate.setAutoCreateRowSorter(true);
    }

    public void setData(ArrayList<T> data) { 
        model.setData(data);
    }

}
