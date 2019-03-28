package tech.twentytwobits.fastnotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_add_note.*
import tech.twentytwobits.fastnotes.NotesData.Note
import tech.twentytwobits.fastnotes.NotesData.NotesDatabase

class NoteAddActivity: AppCompatActivity(), RadioGroup.OnCheckedChangeListener {

    private var notesDatabase: NotesDatabase? = null
    private var prioridad = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        notesDatabase = NotesDatabase.getInstance(this)
        rgPrioridad.setOnCheckedChangeListener(this)

        // Verificar si se nos envía información mediante un Intent
        val titulo = intent.getStringExtra("titulo")
        val detalle = intent.getStringExtra("detalle")

        // Si no está definido el título o viene en blanco o el usuario
        // accede mediante el FAB
        if (titulo == null || titulo == "") {
            btnAgregar.setOnClickListener{
                val nota = Note(etTitulo.text.toString(), etDescripcion.text.toString(), prioridad)
                notesDatabase?.getNoteDao()?.saveNote(nota)
                finish()
            }
        } else {
            val id = intent.getIntExtra("id", 0)
            // Cargar los valores en el layout
            etTitulo.setText(titulo)
            etDescripcion.setText(detalle)
            btnAgregar.setOnClickListener {
                val nota = Note(etTitulo.text.toString(), etDescripcion.text.toString(), prioridad)
                nota.id = id
                notesDatabase?.getNoteDao()?.updateNote(nota)
                finish()
            }
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        if (checkedId == R.id.rbMedia) {
            prioridad = 2
        } else if (checkedId == R.id.rbAlta) {
            prioridad = 3
        }
    }
}