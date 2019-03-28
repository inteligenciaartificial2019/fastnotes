package tech.twentytwobits.fastnotes.NotesData

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NotesDatabase: RoomDatabase() {
    abstract fun getNoteDao(): NoteDao

    // Singleton o solamente una instancia de esta clase
    companion object {
        val database = "notesdatabase"
        var notesDatabase: NotesDatabase? = null

        fun getInstance(context: Context): NotesDatabase? {
            if (notesDatabase == null) {
                notesDatabase = Room.databaseBuilder(
                    context,
                    NotesDatabase::class.java,
                    NotesDatabase.database
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
                return notesDatabase
        }
    }
}