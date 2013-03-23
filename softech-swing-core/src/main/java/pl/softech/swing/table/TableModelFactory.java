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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;
import pl.softech.reflection.IMetaData;
import pl.softech.reflection.IMetaData.IllegalMetaDataUsage;
import pl.softech.reflection.IMetaDataFactory;
import pl.softech.reflection.MetaDataFactory;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public class TableModelFactory<E> {

	protected final Logger logger = Logger.getLogger(TableModelFactory.class
			.getName());

	@SuppressWarnings("hiding")
	protected class TableModelImpl<E> extends AbstractTableModel implements
			ITableModel<E>, IBackupAware<E> {

		private static final long serialVersionUID = 1L;
		protected IMetaData<TableColumn>[] tableColumns;
		protected ArrayList<E> data;
		protected List<IBackupAction<E>> backupActionListeners; 

		protected TableModelImpl(IMetaData<TableColumn>... tableColumns) {
			this.tableColumns = tableColumns;
			backupActionListeners = new LinkedList<IBackupAction<E>>();
		}

		public void setData(ArrayList<E> data) {
			this.data = data;
			fireTableDataChanged();
		}

		private String getColumnName(IMetaData<TableColumn> metadata) {
			String name = metadata.getAnnotation().name();
			if(metadata.getParent() == null) return name;
			String delimiter = metadata.getAnnotation().delimiter();
			return getColumnName(metadata.getParent()) + delimiter + name; 
		}
		
		@Override
		public String getColumnName(int column) {
			return getColumnName(tableColumns[column]);
			
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return tableColumns[columnIndex].getType();
		}

		public int getRowCount() {
			return data == null ? 0 : data.size();
		}

		public int getColumnCount() {
			return tableColumns.length;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return tableColumns[columnIndex].getAnnotation().editable();
		}

		/** 
		 * @see jscl.gui.table.IBackupAware#fireBackup(jscl.gui.table.ITableModel.AbstractTableEvent)
		 */
		@Override
		public void fireBackup(AbstractTableEvent<E> ev) {
			for(IBackupAction<E> a : backupActionListeners)
				a.makeBackup(ev);
		}
		
		/** 
		 * @see jscl.gui.table.IBackupAware#fireResotreFromBackup(java.util.Collection)
		 */
		@Override
		public void fireRestoreFromBackup() {
			for(IBackupAction<E> a : backupActionListeners)
				a.restoreFromBackup(data);
			fireTableDataChanged();
		}
		
		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			try {
				fireBackup(
					new BackupTableEvent<E>(
							tableColumns[columnIndex],
							rowIndex, columnIndex, data.get(rowIndex)
					)
				);
				tableColumns[columnIndex].setValue(data.get(rowIndex), aValue);
				fireTableCellUpdated(rowIndex, columnIndex);
			} catch (IllegalMetaDataUsage ex) {
				logger.log(Level.SEVERE, null, ex);
			}
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			Object ret = null;
			try {
				ret = tableColumns[columnIndex].getValue(data.get(rowIndex));
			} catch (IllegalMetaDataUsage ex) {
				logger.log(Level.SEVERE, null, ex);
				ex.printStackTrace();
			}
			return ret;
		}

		/** 
		 * @see jscl.gui.table.IBackupAware#addBackupActionListener(jscl.gui.table.IBackupAware.IBackupAction)
		 */
		@Override
		public void addBackupActionListener(IBackupAction<E> listener) {
			backupActionListeners.add(listener);
			
		}
	}

	protected final IMetaDataFactory<TableColumn> metaDataFactory;
	
	private final void removeEntryByKeyPattern(Map<String, IMetaData<TableColumn>> data, String pattern) {
		
		Iterator<String> it = data.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			if(key.contains(pattern) && !key.equals(pattern))
				it.remove();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private final IMetaData<TableColumn>[] filterByPolicy(final Map<String, IMetaData<TableColumn>> path2metaData) {
		
		Map<String, IMetaData<TableColumn>> ret = new TreeMap<String, IMetaData<TableColumn>>(path2metaData);
		
		Iterator<Map.Entry<String, IMetaData<TableColumn>>> it = path2metaData.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, IMetaData<TableColumn>> e = it.next();
			IMetaData<TableColumn> metaData = e.getValue();
			TableColumn ann = metaData.getAnnotation();
			if(ann.policy() == TableColumn.Policy.CHILDREN_FOLLOW)
				ret.remove(e.getKey());
			else if(ann.policy() == TableColumn.Policy.DONT_FOLLOW)
				removeEntryByKeyPattern(ret, e.getKey());
		}
		
		IMetaData<TableColumn>[] tab = new IMetaData[ret.size()];
		return ret.values().toArray(tab);
	}
	
	public ITableModel<E> createModel(Class<?> clazz) {
		Map<String, IMetaData<TableColumn>> path2metaData = null;
		path2metaData = metaDataFactory.class2MetaDataByFullPath(clazz);
		IMetaData<TableColumn>[] tab = filterByPolicy(path2metaData);
		
		Arrays.sort(tab, new Comparator<IMetaData<TableColumn>>() {

			@Override
			public int compare(IMetaData<TableColumn> o1, IMetaData<TableColumn> o2) {
				return o1.getAnnotation().order() - o2.getAnnotation().order();
			}
		});
		
		return new TableModelImpl<E>(tab);

	}

	protected TableModelFactory() {
		metaDataFactory = new MetaDataFactory<TableColumn>(TableColumn.class);
	}

}
