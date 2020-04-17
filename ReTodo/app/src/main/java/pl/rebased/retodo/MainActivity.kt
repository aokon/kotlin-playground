package pl.rebased.retodo

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.CompoundButton
import android.widget.TextView
import androidx.lifecycle.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.room.RoomDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_todo.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.rebased.retodo.data.TodoDatabase
import pl.rebased.retodo.data.Todo
import pl.rebased.retodo.data.TodoList
import pl.rebased.retodo.databinding.ActivityMainBinding
import pl.rebased.retodo.databinding.ItemTodoBinding


interface ChangeTodoHandler {
  fun onDoneChange(item: Todo, done: Boolean)
  fun onDescriptionChange(item: Todo, text: String)
}

class TodoViewModel(application: Application): AndroidViewModel(application) {
    val task = CoroutineScope(Dispatchers.IO)

    val todos : LiveData<TodoList> by lazy {
        // MutableLiveData(dao.getIncomplete())

        dao.getLiveIncomplete()
    }

    private val db by lazy {
        TodoDatabase.getInstance(application)
    }

    private val dao by lazy { db.dao() }

    fun setDone(todo: Todo, done: Boolean) {
        todo.completed = done
        task.launch { dao.update(todo) }
    }

    fun setDescription(todo: Todo, text: String) {
        todo.name = text
        task.launch { dao.update(todo) }
    }

    fun addTodo() {
        val newTodo = Todo(id = 0, name = "New todo", completed = false)
        task.launch { dao.insert(newTodo) }
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
        val binding = ItemTodoBinding.bind(itemView)
        binding.todo = item

        binding.onCheckedHandler = CompoundButton.OnCheckedChangeListener { _, isChecked ->
            handler.onDoneChange(item, isChecked)
        }

        binding.onEditTextHandler = TextView.OnEditorActionListener { v, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE, EditorInfo.IME_ACTION_NEXT, EditorInfo.IME_NULL -> {
                    handler.onDescriptionChange(item, v.text.toString())
                    true
                }
                else -> false
            }
        }

        /*
            itemView.checkBox.setOnCheckedChangeListener { _, isChecked ->
                handler.onDoneChange(item, isChecked)
            }
            itemView.textView.setOnEditorActionListener { v, actionId, _ ->
                    when (actionId) {
                        EditorInfo.IME_ACTION_DONE, EditorInfo.IME_ACTION_NEXT, EditorInfo.IME_NULL -> {
                            handler.onDescriptionChange(item, v.text.toString())
                            true
                        }
                        else -> false
                    }
            }
            itemView.checkBox.isChecked = item.completed
            itemView.textView.setText(item.name)
         */
    }
}

class MainActivity : AppCompatActivity(), ChangeTodoHandler {
    // we want to avoid moving model to GC, so it's lazy
    val model by lazy {
        ViewModelProvider(this).get(TodoViewModel::class.java)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // run custom code after the super
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // setContentView(R.layout.activity_main)
        val todoAdapter = TodoAdapter(this)

        binding.adapter = todoAdapter
        binding.layoutManager = LinearLayoutManager(this as Context, LinearLayoutManager.VERTICAL, false)
        binding.onAddNewTodo = View.OnClickListener {
           model.addTodo()
        }

        // todoList.adapter = todoAdapter
        // todoList.layoutManager = LinearLayoutManager(this as Context, LinearLayoutManager.VERTICAL, false)

        model.todos.observe(this, Observer {
           todoAdapter.submitList(it)
        })

        // addTodoButton?.setOnClickListener {
        //    model.addTodo()
        // }
    }

    override fun onDoneChange(item: Todo, done: Boolean) {
        model.setDone(item, done)
    }

    override fun onDescriptionChange(item: Todo, text: String) {
        model.setDescription(item, text)
    }
}
