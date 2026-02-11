package com.vishalpvijayan.notesproviderdemo


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class NotesDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "notes.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val SQL_CREATE_TABLE = """
            CREATE TABLE ${NotesContract.NotesEntry.TABLE_NAME} (
                ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${NotesContract.NotesEntry.COLUMN_TITLE} TEXT NOT NULL,
                ${NotesContract.NotesEntry.COLUMN_DESCRIPTION} TEXT NOT NULL
            )
        """.trimIndent()

        db.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${NotesContract.NotesEntry.TABLE_NAME}")
        onCreate(db)
    }
}
