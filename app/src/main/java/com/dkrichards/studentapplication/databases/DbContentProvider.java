package com.dkrichards.studentapplication.databases;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Abstract content provider. This class provides factory methods and aliases for Android
 * SQLiteDatabase methods.
 *
 * This Database provider abstract was largely derived from best practices laid out in
 * https://wale.oyediran.me/2015/04/02/android-sqlite-dao-design/
 */
public abstract class DbContentProvider {
    private SQLiteDatabase mDb;

    /**
     * Construct the provider
     * @param db
     */
    public DbContentProvider(SQLiteDatabase db) {
        this.mDb = db;
    }

    /**
     * Convert a SQLite Cursor to an instance of the appropriate model class
     *
     * @param cursor
     * @param <T>
     * @return
     */
    protected abstract <T> T cursorToEntity(Cursor cursor);

    /**
     * Delete an entry
     *
     * @param tableName
     * @param selection
     * @param selectionArgs
     * @return
     */
    protected int delete(String tableName, String selection,
                         String[] selectionArgs) {
        
        return mDb.delete(tableName, selection, selectionArgs);
    }

    /**
     * Insert an entry
     *
     * @param tableName
     * @param values
     * @return
     */
    protected long insert(String tableName, ContentValues values) {
        
        return mDb.insert(tableName, null, values);
    }

    /**
     * Select entry(ies)
     *
     * @param tableName
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    protected Cursor query(String tableName, String[] columns,
                           String selection, String[] selectionArgs,
                           String sortOrder) {

        return mDb.query(tableName, columns,
                selection, selectionArgs, null, null, sortOrder);
    }

    /**
     * Update an entry
     *
     * @param tableName
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    protected int update(String tableName, ContentValues values,
                         String selection, String[] selectionArgs) {
        
        return mDb.update(tableName, values, selection, selectionArgs);
    }
}