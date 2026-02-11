package com.vishalpvijayan.notesproviderdemo

import android.content.ContentValues
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesScreen()
        }
    }

    @Composable
    fun NotesScreen() {

        var title by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        var notes by remember { mutableStateOf(listOf<Pair<String, String>>()) }

        val resolver = contentResolver

        Column(modifier = Modifier.padding(16.dp)) {

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                val values = ContentValues().apply {
                    put(NotesContract.NotesEntry.COLUMN_TITLE, title)
                    put(NotesContract.NotesEntry.COLUMN_DESCRIPTION, description)
                }

                resolver.insert(NotesContract.NotesEntry.CONTENT_URI, values)

                title = ""
                description = ""
                notes = loadNotes()
            }) {
                Text("Add Note")
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(notes) {
                    Text("${it.first}: ${it.second}")
                }
            }
        }

        LaunchedEffect(Unit) {
            notes = loadNotes()
        }
    }

    private fun loadNotes(): List<Pair<String, String>> {

        val list = mutableListOf<Pair<String, String>>()

        val cursor = contentResolver.query(
            NotesContract.NotesEntry.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val title = it.getString(
                    it.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_TITLE)
                )
                val description = it.getString(
                    it.getColumnIndexOrThrow(NotesContract.NotesEntry.COLUMN_DESCRIPTION)
                )
                list.add(title to description)
            }
        }

        return list
    }
}
