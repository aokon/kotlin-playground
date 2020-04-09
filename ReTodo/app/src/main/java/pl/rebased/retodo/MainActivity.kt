package pl.rebased.retodo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_todo.view.*

data class Todo(val name: String, val isCompleted: Boolean)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val todoAdapter = TodoAdapter()

        todoList.adapter = todoAdapter
        todoAdapter.submitList(makeTodoList())
        todoList.layoutManager = LinearLayoutManager(this as Context, LinearLayoutManager.VERTICAL, false)
    }

    private fun makeTodoList(): List<Todo> {
        return listOf<Todo>(
            Todo("Todo 1", true),
            Todo("Todo 2", true),
            Todo("Todo 3", false),
            Todo("Todo 4", false),
            Todo("Todo 5", false)
        )
    }
}

class TodoAdapter: ListAdapter<Todo, TodoViewHolder>(diff) {
    companion object diff: DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean = oldItem == newItem
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(itemView)
    }
}

class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindTo(item: Todo) {
        itemView.checkBox.isChecked = item.isCompleted
        itemView.textView.text = item.name
    }
}
