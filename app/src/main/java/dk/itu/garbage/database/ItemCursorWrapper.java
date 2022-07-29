package dk.itu.garbage.database;
import android.database.Cursor;
import android.database.CursorWrapper;
import dk.itu.garbage.Item;
import dk.itu.garbage.database.ItemsDbSchema.ItemTable;

public class ItemCursorWrapper extends CursorWrapper {
  public ItemCursorWrapper(Cursor cursor) {
    super(cursor);
  }

  public Item getItem() {
    String what = getString(getColumnIndex(ItemTable.Cols.WHAT));
    String where = getString(getColumnIndex(ItemTable.Cols.WHERE));
    return new Item(what, where);
  }
}