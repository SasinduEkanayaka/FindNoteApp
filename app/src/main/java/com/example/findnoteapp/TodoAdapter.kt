package com.example.todolistappication

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistappication.database.Todo
import com.example.todolistappication.database.TodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class find(
    private val items: List<Todo>,
    private val repository: TodoRepository,
    private val viewModel: MainActivityData
) : RecyclerView.Adapter<TodoViewHolder>() {


    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item, parent, false)
        context = parent.context
        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentItem = items[position]
        holder.cbTodo.text = currentItem.item

        holder.cbTodo.setOnLongClickListener {
            val builder = AlertDialog.Builder(context!!)
            builder.setTitle("Update Todo Item")
            val input = EditText(context)
            input.setText(currentItem.item)
            builder.setView(input)

            builder.setPositiveButton("Update") { dialog, which ->
                val updatedItem = input.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    repository.update(currentItem.id ?: 0, updatedItem) // Assuming 0 as default value if id is null
                    val data = repository.getAllTodoItems()
                    withContext(Dispatchers.Main) {
                        viewModel.setData(data)
                    }
                }
            }

            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }

            builder.show()
            true // Return true to indicate that the long click event is consumed
        }

        holder.ivEdit.setOnClickListener {
            val builder = AlertDialog.Builder(context!!)
            builder.setTitle("Update Todo Item")
            val input = EditText(context)
            input.setText(currentItem.item)
            builder.setView(input)

            builder.setPositiveButton("Update") { dialog, which ->
                val updatedItem = input.text.toString()
                CoroutineScope(Dispatchers.IO).launch {
                    repository.update(currentItem.id ?: 0, updatedItem) // Assuming 0 as default value if id is null
                    val data = repository.getAllTodoItems()
                    withContext(Dispatchers.Main) {
                        viewModel.setData(data)
                        Toast.makeText(context, "Update Successful", Toast.LENGTH_SHORT).show()
                    }
                }

            }

            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }

            builder.show()
        }

        holder.ivDelete.setOnClickListener {
            val isChecked = holder.cbTodo.isChecked

            if (isChecked) {
                CoroutineScope(Dispatchers.IO).launch {
                    repository.delete(currentItem)
                    val data = repository.getAllTodoItems()
                    withContext(Dispatchers.Main) {
                        viewModel.setData(data)
                    }
                }
            } else {
                Toast.makeText(context, "Select the item to be deleted", Toast.LENGTH_LONG).show()
            }
        }


    }
}
