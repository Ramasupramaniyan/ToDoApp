package `in`.lightspeed.todo

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList

class HomeActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<UserData>
    private lateinit var myAdapter: MyAdapter
    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var addToDoFab:FloatingActionButton
    private lateinit var mProgressDialog:ProgressDialog
    private lateinit var userCard:CardView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        recyclerView = findViewById(R.id.user_recycler)
        addToDoFab = findViewById(R.id.addToDoFab)
        val actionBar = supportActionBar
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_action_bar)
        val switch:Switch = findViewById(R.id.switch2)
        switch.setOnCheckedChangeListener { compoundButton, b ->
            activateDarkMode(b)
        }
        actionBar?.setTitle("Users")
        userCard = findViewById(R.id.current_user_card)
        mProgressDialog = ProgressDialog(this)
        mProgressDialog.setTitle("Message")
        mProgressDialog.setMessage("Collecting User Data...")
        mProgressDialog.show()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        userArrayList = arrayListOf()
        myAdapter = MyAdapter(userArrayList)
        recyclerView.adapter = myAdapter
        EventChangeListener()
        userCard.setOnClickListener {
            val intent = Intent(this, TodoPage::class.java)
            intent.putExtra("userID", FirebaseAuth.getInstance().currentUser?.uid)
            startActivity(intent)
        }
        addToDoFab.setOnClickListener {
            val intent = Intent(this, AddNewToDo::class.java)
            intent.putExtra("userID", FirebaseAuth.getInstance().currentUser?.uid)
            startActivity(intent)
        }
    }

    private fun activateDarkMode(checked: Boolean) {
        if (checked){
            AppCompatDelegate
                .setDefaultNightMode(
                    AppCompatDelegate
                        .MODE_NIGHT_YES);
        }else{
            AppCompatDelegate
                .setDefaultNightMode(
                    AppCompatDelegate
                        .MODE_NIGHT_NO);
        }
    }

    private fun EventChangeListener() {
        mFirebaseDatabase = FirebaseDatabase.getInstance()
        val userListListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                mProgressDialog.cancel()
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot!!.children
                children.forEach {
                    val userData: UserData? = it.getValue(UserData::class.java)
                    if(userData!!.userID != FirebaseAuth.getInstance().currentUser!!.uid){
                        userArrayList.add(userData)
                    }else{
                        findViewById<TextView>(R.id.name).text = "Name: " + userData.name
                        findViewById<TextView>(R.id.mail).text = "Mail: " + userData.email
                        findViewById<TextView>(R.id.age).text = "Age: " + userData.age
                        findViewById<TextView>(R.id.dateofbirth).text = "DOB: " + userData.dateofbirth
                    }
                    println(it.getValue(UserData::class.java))
                }
                //sin
                myAdapter.notifyDataSetChanged()
                mProgressDialog.cancel()
            }
        }
        mFirebaseDatabase.getReference("users").addValueEventListener(userListListener)
    }
}