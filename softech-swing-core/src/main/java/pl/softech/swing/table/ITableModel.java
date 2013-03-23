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
import javax.swing.table.TableModel;
import pl.softech.reflection.IMetaData;

/**
 *
 * @author Sławomir Śledź <slawomir.sledz@sof-tech.pl>
 * @since 1.0
 */
public interface ITableModel<T> extends TableModel {

	public abstract class AbstractTableEvent<T> {
		protected IMetaData<TableColumn> metaData;
		protected int row;
		protected int column;
		protected T data;
		public AbstractTableEvent(IMetaData<TableColumn> metaData, int row, int column, T data) {
			this.metaData = metaData;
			this.row = row;
			this.column = column;
			this.data = data;
		}
	}
	
	public void setData(ArrayList<T> data);

}
