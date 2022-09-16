package `in`.lightspeed.todo

import `in`.lightspeed.todo.R
import `in`.lightspeed.todo.UserData
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val userList: ArrayList<UserData>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {
        val user:UserData = userList[position]
        holder.name.text = "Name: " + user.name
        holder.email.text = "Mail: " +user.email
        holder.age.text = "Age: " +user.age
        holder.dateofbirth.text = "DOB: " +user.dateofbirth
        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, TodoPage::class.java)
            intent.putExtra("userID",userList[position].userID)
            holder.itemView.context.startActivity(intent)
            Log.d("test", "item clicked" + position)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name:TextView = itemView.findViewById(R.id.name)
        val email:TextView = itemView.findViewById(R.id.mail)
        val age:TextView = itemView.findViewById(R.id.age)
        val dateofbirth:TextView = itemView.findViewById(R.id.dateofbirth)
    }
}