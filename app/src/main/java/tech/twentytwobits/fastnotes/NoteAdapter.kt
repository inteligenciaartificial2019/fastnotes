package tech.twentytwobits.fastnotes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tech.twentytwobits.fastnotes.NotesData.Note

class NoteAdapter(var noteList: List<Note> = ArrayList<Note>()): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private var onNoteItemClickListener: OnNoteItemClickListener? = null

    override fun getItemCount(): Int {
        return noteList.count()
    }

    override fun onBindViewHolder(holder: NoteAdapter.ViewHolder, position: Int) {
        // Obtener la posición del item seleccionado
        holder.view.setOnClickListener {
            onNoteItemClickListener?.onNoteItemClickListener(noteList[position])
        }
        holder.view.setOnLongClickListener {
            onNoteItemClickListener?.onNoteItemLongClickListener(noteList[position])
            true
        }
        holder.onBindViews(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.template_notes_items, parent, false)

        return ViewHolder(view, noteList)
    }

    class ViewHolder(val view: View, val notesList: List<Note>): RecyclerView.ViewHolder(view) {
        fun onBindViews(position: Int) {
            view.findViewById<TextView>(R.id.tvTitulo).text = notesList[position].titulo
            view.findViewById<TextView>(R.id.tvPrimeraLetra).text = notesList[position].titulo.first().toUpperCase().toString()
            view.findViewById<ImageView>(R.id.ivPrioridad).setImageResource(getImagePrioridad(notesList[position].prioridad))
        }

        private fun getImagePrioridad(prioridad: Int): Int
        = if (prioridad == 1) R.drawable.low_priority else if (prioridad == 2) R.drawable.medium_priority else R.drawable.high_priority
    }

    fun setNoteItemClickListenter(onNoteItemClickListener: OnNoteItemClickListener) {
        this.onNoteItemClickListener = onNoteItemClickListener
    }

    interface OnNoteItemClickListener {
        fun onNoteItemClickListener(note: Note)
        fun onNoteItemLongClickListener(note: Note)
    }
}