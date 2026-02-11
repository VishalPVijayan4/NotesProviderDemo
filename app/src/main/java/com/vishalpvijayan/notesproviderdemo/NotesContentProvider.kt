package com.vishalpvijayan.notesproviderdemo

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class NotesContentProvider : ContentProvider() {

    private lateinit var dbHelper: NotesDatabaseHelper

    companion object {
        private const val NOTES = 1

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(
                NotesContract.AUTHORITY,
                NotesContract.NotesEntry.TABLE_NAME,
                NOTES
            )
        }
    }

    override fun onCreate(): Boolean {
        dbHelper = NotesDatabaseHelper(context!!)
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbHelper.writableDatabase
        val id = db.insert(NotesContract.NotesEntry.TABLE_NAME, null, values)

        return Uri.withAppendedPath(
            NotesContract.NotesEntry.CONTENT_URI,
            id.toString()
        )
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {

        val db = dbHelper.readableDatabase

        return db.query(
            NotesContract.NotesEntry.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbHelper.writableDatabase
        return db.delete(NotesContract.NotesEntry.TABLE_NAME, selection, selectionArgs)
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        val db = dbHelper.writableDatabase
        return db.update(
            NotesContract.NotesEntry.TABLE_NAME,
            values,
            selection,
            selectionArgs
        )
    }

    override fun getType(uri: Uri): String? = null
}
