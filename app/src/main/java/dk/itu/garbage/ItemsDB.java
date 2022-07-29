package dk.itu.garbage;
import android.content.ContentValues;
import android.content.Context;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.lifecycle.ViewModel;
import dk.itu.garbage.database.ItemBaseHelper;
import dk.itu.garbage.database.ItemCursorWrapper;
import dk.itu.garbage.database.ItemsDbSchema;

public class ItemsDB extends ViewModel {
  private static SQLiteDatabase mDatabase;

  //creating the database
  public void initialize(Context context){
    if (mDatabase == null) {
      mDatabase= new ItemBaseHelper(context.getApplicationContext()).getWritableDatabase();
      if (getValues().size() == 0) fillItemsDB();
    }
  }

  public void addItem(String what, String where){
    Item newItem= new Item(what, where);
    ContentValues values= getContentValues(newItem);
    mDatabase.insert(ItemsDbSchema.ItemTable.NAME, null, values);
  }

  public void removeItem(String what){
    Item newItem= new Item(what, "");
    String selection= ItemsDbSchema.ItemTable.Cols.WHAT + " LIKE ?";
    int changed= mDatabase.delete(ItemsDbSchema.ItemTable.NAME, selection, new String[]{newItem.getWhat()});
  }

  public void fillItemsDB() {
    addItem("coffee", "Irma");
    addItem("carrots", "Netto");
    addItem("milk", "Netto");
    addItem("bread", "bakery");
    addItem("butter", "Irma");
    addItem("oranges", "Irma");
    addItem("cheese", "Netto");
    addItem("bread", "bakery");
    addItem("apples", "Menu");
  }

  public ArrayList<Item> getValues() {
    ArrayList<Item> items= new ArrayList<Item>();
    ItemCursorWrapper cursor= queryItems(null, null);
    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      items.add(cursor.getItem());
      cursor.moveToNext();
    }
    cursor.close();
    return items;
  }

  // Database helper methods to convert between Items and database rows
  private static ContentValues getContentValues(Item item) {
    ContentValues values=  new ContentValues();
    values.put(ItemsDbSchema.ItemTable.Cols.WHAT, item.getWhat());
    values.put(ItemsDbSchema.ItemTable.Cols.WHERE, item.getWhere());
    return values;
  }

  static private ItemCursorWrapper queryItems(String whereClause, String[] whereArgs) {
    Cursor cursor= mDatabase.query(
        ItemsDbSchema.ItemTable.NAME,
        null, // Columns - null selects all columns
        whereClause, whereArgs,
        null, // groupBy
        null, // having
        null  // orderBy
    );
    return new ItemCursorWrapper(cursor);
  }

  public int size() {
    return getValues().size();
  }

  public void close() {   mDatabase.close();   }
}