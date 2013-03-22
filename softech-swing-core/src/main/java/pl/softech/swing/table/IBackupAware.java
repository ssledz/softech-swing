package pl.softech.swing.table;


import java.util.Collection;
import pl.softech.reflection.IMetaData;
import pl.softech.swing.table.ITableModel.AbstractTableEvent;

/**
 * @author Sławomir Śledź
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
