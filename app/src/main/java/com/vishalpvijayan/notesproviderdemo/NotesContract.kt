package com.vishalpvijayan.notesproviderdemo

import android.net.Uri
import android.provider.BaseColumns

object NotesContract {

    const val AUTHORITY = "com.vishalpvijayan.notesproviderdemo.provider"
    val BASE_CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY")

    object NotesEntry : BaseColumns {
        const val TABLE_NAME = "notes"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"

        val CONTENT_URI: Uri =
            Uri.withAppendedPath(BASE_CONTENT_URI, TABLE_NAME)
    }
}