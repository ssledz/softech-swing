package pl.softech.swing.table;

import java.util.ArrayList;
import javax.swing.table.TableModel;
import pl.softech.reflection.IMetaData;

/**
 * 
 * @author Sławomir Śledź
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
