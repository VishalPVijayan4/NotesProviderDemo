# NotesProviderDemo

A basic Android application demonstrating the use of a Content Provider with Kotlin and Jetpack Compose.

This project shows how to:
- Store data using SQLite
- Expose data through a ContentProvider
- Access data using ContentResolver
- Display data using Jetpack Compose
- Perform basic CRUD operations (Insert and Query)

------------------------------------------------------------

## 📱 What This App Does

This app allows users to:

- Enter a note title
- Enter a note description
- Save the note to a database
- Display all saved notes in a list

The key concept demonstrated is that the UI does NOT directly talk to the database.  
All database operations go through a ContentProvider.

------------------------------------------------------------

## 🧠 Architecture Overview

Flow of data:

Compose UI  
↓  
ContentResolver  
↓  
NotesContentProvider  
↓  
SQLite Database  

The ContentProvider acts as a middle layer between the UI and the database.

------------------------------------------------------------

## 📂 Project Structure

### 1. NotesContract.kt

Purpose:
- Defines database table structure
- Defines column names
- Defines authority and content URIs

Why it exists:
To provide a single source of truth for database schema and URIs.

Key Elements:
- AUTHORITY
- BASE_CONTENT_URI
- TABLE_NAME
- COLUMN names
- CONTENT_URI

Example URI:
content://com.example.notesproviderdemo.provider/notes

------------------------------------------------------------

### 2. NotesDatabaseHelper.kt

Extends: SQLiteOpenHelper

Responsibilities:
- Creates the database
- Creates the table
- Handles schema upgrades

Table Schema:

CREATE TABLE notes (
    _id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    description TEXT NOT NULL
)

Important:
The _id column comes from BaseColumns and is required for Android Cursor compatibility.

This class does not know about UI. It only manages SQLite.

------------------------------------------------------------

### 3. NotesContentProvider.kt

Extends: ContentProvider

Responsibilities:
- Handles insert()
- Handles query()
- Handles update()
- Handles delete()
- Routes requests using UriMatcher

How it works:

When the UI calls:

contentResolver.insert(CONTENT_URI, values)

Android routes the request to NotesContentProvider based on the authority defined in the manifest.

The provider then:
- Receives the request
- Talks to SQLite
- Returns a result

The UI never directly accesses the database.

------------------------------------------------------------

### 4. MainActivity.kt (Compose UI)

Responsibilities:
- Collect user input
- Call ContentResolver to insert notes
- Query notes using ContentResolver
- Display results using LazyColumn

Insert Flow:
1. Create ContentValues
2. Call contentResolver.insert()
3. Reload notes using query()
4. Update Compose state

Query Flow:
1. Call contentResolver.query()
2. Receive Cursor
3. Convert Cursor to List
4. Display in LazyColumn

------------------------------------------------------------

### 5. AndroidManifest.xml

Provider registration:

<provider
    android:name=".NotesContentProvider"
    android:authorities="com.example.notesproviderdemo.provider"
    android:exported="false" />

Important:
- authorities must match AUTHORITY in NotesContract
- exported=false means only this app can access the provider
- exported=true allows other apps to access it

------------------------------------------------------------

## 🔄 Detailed Data Flow Example

When User Clicks "Add Note":

1. User enters title and description
2. Button click triggers insert logic
3. ContentValues object is created
4. contentResolver.insert() is called
5. Android finds the matching ContentProvider
6. Provider inserts data into SQLite
7. UI reloads data using query()
8. Notes displayed in list

------------------------------------------------------------

## 🔍 Why Use Content Providers?

Content Providers are Android’s standard way to:

- Share structured data
- Enable inter-app communication
- Enforce permissions
- Use URI-based routing

System examples:
- Contacts
- MediaStore
- Calendar
- Settings

This project demonstrates the core mechanics behind those system APIs.

------------------------------------------------------------

## 🧱 Key Concepts Demonstrated

- Contract Pattern
- URI-based routing
- ContentResolver
- SQLite with SQLiteOpenHelper
- Compose UI integration
- Separation of concerns
- Abstraction of storage layer

------------------------------------------------------------

## 🚀 Possible Improvements

You can extend this project by:

- Supporting single-note queries (notes/#)
- Implementing MIME types in getType()
- Adding update and delete UI actions
- Replacing SQLiteOpenHelper with Room
- Exporting the provider and consuming it from another app
- Implementing ContentObserver to auto-refresh UI

------------------------------------------------------------

## 📌 Requirements

- Kotlin
- Android SDK 24+
- Jetpack Compose
- Android Studio (latest stable recommended)

------------------------------------------------------------

## 🎯 Learning Outcome

 completing this project, you will understand:

- How Android routes data requests using URIs
- How ContentProviders abstract data access
- How ContentResolver interacts with providers
- How to connect modern Compose UI with traditional Android storage APIs
- How system-level Android components share data

------------------------------------------------------------

Do clone and understand this component.
Thanks. Vishal P Vijayan
