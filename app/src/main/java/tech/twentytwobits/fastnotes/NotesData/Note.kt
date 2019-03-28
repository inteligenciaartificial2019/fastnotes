package tech.twentytwobits.fastnotes.NotesData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
class Note(
    @ColumnInfo(name = "titulo")
    var titulo: String = "",
    @ColumnInfo(name = "detalle")
    var detalle: String = "",
    @ColumnInfo(name = "prioridad")
    var prioridad: Int = 0)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
