package pl.rebased.retodo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_todo.view.*

interface ChangeTodoHandler {
  fun onDoneChange(item: Todo, done: Boolean)
  fun onDescriptionChange(item: Todo, text: String)
}

data class Todo(var name: String, var isCompleted: Boolean)

typealias TodoList =  List<Todo>

class TodoViewModel : ViewModel() {
    val todos : LiveData<TodoList> by lazy {
        MutableLiveData(todoList())
    }

    fun setDone(todo: Todo, done: Boolean) {
        todo.isCompleted = done
    }

    fun setDescription(todo: Todo, text: String) {
        todo.name = text
    }

    private fun todoList(): List<Todo> {
        return listOf(
            Todo("Todo 1", true),
            Todo("Todo 2", true),
            Todo("Todo 3", false),
            Todo("Todo 4", false),
            Todo("Todo 5", false)
        )
    }
}

class TodoAdapter(private val handler: ChangeTodoHandler) : ListAdapter<Todo, TodoViewHolder>(diff) {
    companion object diff: DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean = oldItem == newItem
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(itemView, handler)
    }
}

class TodoViewHolder(itemView: View, val handler: ChangeTodoHandler) : RecyclerView.ViewHolder(itemView) {
    fun bindTo(item: Todo) {
        itemView.checkBox.setOnCheckedChangeListener { view, isChecked ->
            handler.onDoneChange(item, isChecked)
        }
        itemView.textView.setOnEditorActionListener { v, actionId, event ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE, EditorInfo.IME_ACTION_NEXT, EditorInfo.IME_NULL -> {
                        handler.onDescriptionChange(item, v.text.toString())
                        true
                    }
                    else -> false
                }
        }
        itemView.checkBox.isChecked = item.isCompleted
        itemView.textView.setText(item.name)
    }
}

class MainActivity : AppCompatActivity(), ChangeTodoHandler {
    // we want to avoid moving  model to GC when
    val model by lazy {
        ViewModelProvider(this).get(TodoViewModel::class.java)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val todoAdapter = TodoAdapter(this)

        todoList.adapter = todoAdapter
        todoList.layoutManager = LinearLayoutManager(this as Context, LinearLayoutManager.VERTICAL, false)

        model.todos.observe(this, Observer {
           todoAdapter.submitList(it)
        })
    }

    override fun onDoneChange(item: Todo, done: Boolean) {
        model.setDone(item, done)
    }

    override fun onDescriptionChange(item: Todo, text: String) {
        model.setDescription(item, text)
    }
}
