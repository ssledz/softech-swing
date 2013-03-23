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


import java.util.Collection;
import pl.softech.reflection.IMetaData;
import pl.softech.swing.table.ITableModel.AbstractTableEvent;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public interface IBackupAware<E> {

	public class BackupTableEvent<E extends Object> extends AbstractTableEvent<E> {

		/**
		 * @param metaData
		 * @param row
		 * @param column
		 * @param data
		 */
		public BackupTableEvent(IMetaData<TableColumn> metaData, int row, int column,
				E data) {
			super(metaData, row, column, data);
		}
		
	}
	
	public interface IBackupAction<E> {
		public void makeBackup(AbstractTableEvent<E> ev);
		public void restoreFromBackup(Collection<E> data);
	}
	
	public void addBackupActionListener(IBackupAction<E> listenr);
	public void fireBackup(AbstractTableEvent<E> ev);
	public void fireRestoreFromBackup();
	
}
