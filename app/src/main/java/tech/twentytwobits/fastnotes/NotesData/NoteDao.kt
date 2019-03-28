package tech.twentytwobits.fastnotes.NotesData

import androidx.room.*

@Dao
interface NoteDao {
    /**
     * Retorna todos las tuplas de Note en orden ascendente.
     */
    @Query("SELECT * FROM note ORDER BY id ASC")
    fun getNoteList(): List<Note>

    /**
     * Retorna una tupla desde la tabla Note
     * @param id el valor de la llave primaria del campo.
     */
    @Query("SELECT * FROM note WHERE id = :id")
    fun getNoteItem(id: Int): Note

    /**
     * Insertar una nueva tupla en la tabla Note.
     * @param note la tupla a insertar en la tabla.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveNote(note: Note)

    /**
     * Actualiza una tupla en la tabla Note.
     * @param note el valor de la tupla a actualizar.
     */
    @Update
    fun updateNote(note: Note)

    /**
     * Remueve una tupla de la tabla Note.
     * @param note el valor de la tupla a remover de la tabla.
     */
    @Delete
    fun deleteNote(note: Note)
}