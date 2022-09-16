package `in`.lightspeed.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TodoPage : AppCompatActivity() {
    private lateinit var todoList:RecyclerView
    private lateinit var todoListData: ArrayList<ToDoData>
    private lateinit var todoListAdapter:ToDoListAdapter
    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var userID:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_page)
        userID = intent.getStringExtra("userID").toString()
        todoList = findViewById(R.id.todo_list)
        val actionBar = supportActionBar
        actionBar?.setTitle("ToDo List")
        todoList.layoutManager = LinearLayoutManager(this)
        todoList.setHasFixedSize(true)
        todoListData = arrayListOf()
        todoListAdapter = ToDoListAdapter(todoListData)
        todoList.adapter = todoListAdapter
        EventChangeListener()
    }

    private fun EventChangeListener() {
        mFirebaseDatabase = FirebaseDatabase.getInstance()
        val todoDataListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                //mProgressDialog.cancel()
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot!!.children
                children.forEach {
                    val toDoData: ToDoData? = it.getValue(ToDoData::class.java)
                    todoListData.add(toDoData!!)
                }
                //sin
                todoListAdapter.notifyDataSetChanged()
                //mProgressDialog.cancel()
            }
        }
        mFirebaseDatabase.getReference("todos").child(userID).addValueEventListener(todoDataListener)
    }
}