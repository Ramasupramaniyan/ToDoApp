package `in`.lightspeed.todo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ToDoListAdapter(private val todoDataList: ArrayList<ToDoData>): RecyclerView.Adapter<ToDoListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoListAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ToDoListAdapter.MyViewHolder, position: Int) {
        val user:ToDoData = todoDataList[position]
        holder.todo.text = "ToDo: " + user.todo
        holder.date.text = "Date: " +user.date
        holder.startTime.text = "Start Time: " +user.startTime
        holder.endTime.text = "End Time: " +user.endTime
    }

    override fun getItemCount(): Int {
        return todoDataList.size
    }
    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val todo:TextView = itemView.findViewById(R.id.todo_tv)
        val date:TextView = itemView.findViewById(R.id.date_tv)
        val startTime:TextView = itemView.findViewById(R.id.startTime_tv)
        val endTime:TextView = itemView.findViewById(R.id.endTime_tv)
    }
}