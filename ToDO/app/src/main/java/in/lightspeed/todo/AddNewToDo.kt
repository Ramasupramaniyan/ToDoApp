package `in`.lightspeed.todo

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDateTime
import java.util.*

class AddNewToDo : AppCompatActivity() {
    private lateinit var todo_ed:EditText
    private lateinit var date_ed:EditText
    private lateinit var startTime_ed:EditText
    private lateinit var endTime_ed:EditText
    private lateinit var submitBtn:Button
    private lateinit var mFirebaseDatabaseInstance: FirebaseDatabase
    private lateinit var  mFirebaseDatabase: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_to_do)
        todo_ed = findViewById(R.id.todo_ed)
        date_ed = findViewById(R.id.date_ed)
        startTime_ed = findViewById(R.id.startTime_ed)
        endTime_ed = findViewById(R.id.endTime_ed)
        submitBtn = findViewById(R.id.submitBtn)
        val actionBar = supportActionBar
        actionBar?.setTitle("Add New ToDo")
        mFirebaseDatabaseInstance = FirebaseDatabase.getInstance()
        val userID: String? = intent.getStringExtra("userID")
        date_ed.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = this?.let {
                DatePickerDialog(
                    it,
                    { view, year, monthOfYear, dayOfMonth ->
                        val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                        date_ed!!.setText(dat)
                    },
                    year,
                    month,
                    day
                )
            }
            datePickerDialog!!.show()
        }
        submitBtn.setOnClickListener {
            var todo = todo_ed.text.toString()
            var date = date_ed.text.toString()
            var startTime = startTime_ed.text.toString()
            var endTime = endTime_ed.text.toString()
            if (!TextUtils.isEmpty(todo) && !TextUtils.isEmpty(date)
                && !TextUtils.isEmpty(startTime) && !TextUtils.isEmpty(endTime)){
                mFirebaseDatabase = mFirebaseDatabaseInstance.getReference("todos")
                var toDoData = ToDoData(todo,date,startTime,endTime)
                val c = Calendar.getInstance()
                val year = c.get(Calendar.YEAR).toString()
                val month = c.get(Calendar.MONTH).toString()
                val day = c.get(Calendar.DAY_OF_MONTH).toString()
                val hour = c.get(Calendar.HOUR_OF_DAY).toString()
                val minute = c.get(Calendar.MINUTE).toString()
                val second = c.get(Calendar.SECOND).toString()
                var currentTime = year + month + day + hour + minute + second
                mFirebaseDatabase.child(userID!!).child(currentTime).setValue(toDoData).addOnCompleteListener {
                    if (it.isSuccessful){
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("Success")
                        builder.setMessage("Your todo added successfully")

                        builder.setPositiveButton("Yes"){dialogInterface, which ->
                            dialogInterface.dismiss()
                        }

                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.setCancelable(false)
                        alertDialog.show()
                    }else{
                        Toast.makeText(this, "Oops! can't add todo", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show()
            }

        }
    }
}