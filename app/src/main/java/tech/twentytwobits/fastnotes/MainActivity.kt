package tech.twentytwobits.fastnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import tech.twentytwobits.fastnotes.NotesData.Note
import tech.twentytwobits.fastnotes.NotesData.NotesDatabase

class MainActivity : AppCompatActivity(), NoteAdapter.OnNoteItemClickListener {

    private var notesDatabase: NotesDatabase? = null
    private var noteAdapter: NoteAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notesDatabase = NotesDatabase.getInstance(this)
        // Cargar los valores de la base al adaptador
        noteAdapter = NoteAdapter(notesDatabase?.getNoteDao()?.getNoteList()!!)
        noteAdapter?.setNoteItemClickListenter(this)

        // Llamar el activity de agregar notas desde el FAB
        fabAgregarNota.setOnClickListener {
            startActivity(Intent(this, NoteAddActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        noteAdapter?.noteList = notesDatabase?.getNoteDao()?.getNoteList()!!
        rvNotas.adapter= noteAdapter
        rvNotas.layoutManager = LinearLayoutManager(this)
        rvNotas.hasFixedSize()
    }

    override fun onNoteItemClickListener(note: Note) {
        val intent= Intent(this, NoteAddActivity::class.java)
        intent.putExtra("id", note.id)
        intent.putExtra("titulo", note.titulo)
        intent.putExtra("detalle", note.detalle)
        startActivity(intent)
    }

    override fun onNoteItemLongClickListener(note: Note) {
        // AlertDialog
        val builder = AlertDialog.Builder(this)

        // Colocar el título del AlertDialog
        builder.setTitle(getString(R.string.alert_dialog_delete_title))

        // Mensaje a desplegar en el cuerpo del AlertDialog
        builder.setMessage(getString(R.string.alert_dialog_delete_body))

        // Los AlertDialog pueden tener hasta 3 botones, uno positivo (SI), uno
        // negativo (NO) y un botón neutro (CANCEL) los cuales utilizaremos para
        // Modificar, Eliminar y Cancelar.
        builder.setPositiveButton(getString(R.string.alert_dialog_delete_positive_button)) { dialog, which ->
            // Realizar el llamado a la activity NoteAddActivity
            val intent= Intent(this, NoteAddActivity::class.java)
            intent.putExtra("id", note.id)
            intent.putExtra("titulo", note.titulo)
            intent.putExtra("detalle", note.detalle)
            startActivity(intent)
        }

        builder.setNegativeButton(getString(R.string.alert_dialog_delete_negative_button)) {dialog, which ->
            notesDatabase?.getNoteDao()?.deleteNote(note)
            onResume()
            Toast.makeText(this, getString(R.string.alert_dialog_delete_note), Toast.LENGTH_SHORT).show()
        }

        builder.setNeutralButton(getString(R.string.alert_dialog_delete_neutral_button)) { dialog, which ->
            Toast.makeText(this, getString(R.string.alert_dialog_delete_cancel), Toast.LENGTH_SHORT).show()
        }

        // Crear el AlertDialog con todos los parámetros previamente establecidos
        // en el builder.
        val dialog: AlertDialog = builder.create()

        // Mostrar el AlertDialog
        dialog.show()
    }
}
