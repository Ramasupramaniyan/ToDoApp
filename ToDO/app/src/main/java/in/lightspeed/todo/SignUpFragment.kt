package `in`.lightspeed.todo

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var name_ed: EditText? = null
    private var age_ed: EditText? = null
    private var email_ed: EditText? = null
    private var password_ed: EditText? = null
    private var confirm_password_ed: EditText? = null
    private var dateofbirth_ed: EditText? = null
    lateinit var auth: FirebaseAuth
    private lateinit var mFirebaseDatabaseInstance:FirebaseDatabase
    private lateinit var  mFirebaseDatabase:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_sign_up, container, false)
        mFirebaseDatabaseInstance = FirebaseDatabase.getInstance()
        email_ed = view.findViewById(R.id.email_reg) as EditText
        password_ed = view.findViewById(R.id.password_reg) as EditText
        confirm_password_ed = view.findViewById(R.id.confirm_password_reg) as EditText
        name_ed = view.findViewById(R.id.name_reg) as EditText
        age_ed = view.findViewById(R.id.age_reg) as EditText
        dateofbirth_ed = view.findViewById(R.id.dateofbirth_ed) as EditText
        auth = FirebaseAuth.getInstance()
        dateofbirth_ed!!.setOnClickListener { view ->
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = context?.let {
                DatePickerDialog(
                    it,
                    { view, year, monthOfYear, dayOfMonth ->
                        val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                        dateofbirth_ed!!.setText(dat)
                    },
                    year,
                    month,
                    day
                )
            }
            datePickerDialog!!.show()
        }
        view.findViewById<Button>(R.id.registerBtn).setOnClickListener { view ->
            val email:String = email_ed!!.text.toString()
            val  password:String = password_ed!!.text.toString()
            val confirm_password:String = confirm_password_ed!!.text.toString()
            val age:String = age_ed!!.text.toString()
            val name:String = name_ed!!.text.toString()
            val dateofbirth = dateofbirth_ed!!.text.toString()
            if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(confirm_password) && !TextUtils.isEmpty(age)
                && !TextUtils.isEmpty(name) && !TextUtils.isEmpty(dateofbirth)){
                if(password.equals(confirm_password)){
                    Log.d("result", password.length.toString())
                    if(password.length >= 6){
                        val mProgressDialog = ProgressDialog((activity as AuthenticationActivity))
                        mProgressDialog.setTitle("Account")
                        mProgressDialog.setMessage("Creating User...")
                        mProgressDialog.show()
                        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity as AuthenticationActivity) {
                            if (it.isSuccessful) {
                                mFirebaseDatabase = mFirebaseDatabaseInstance.getReference("users")
                                val user = FirebaseAuth.getInstance().currentUser
                                val myUser = User(email, name, age, dateofbirth, user!!.uid)
                                mFirebaseDatabase.child(user!!.uid).setValue(myUser)
                                val builder = AlertDialog.Builder(context)
                                builder.setTitle("Success")
                                builder.setMessage("Successfully created user!.Move to login page?")
                                builder.setPositiveButton("Ok"){dialogInterface, which ->
                                    dialogInterface.dismiss()
                                    (activity as AuthenticationActivity).gotoLoginFrame()
                                }

                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()
                            }else{
                                Log.d("result", it.result.toString())
                                Toast.makeText(context, " Login failed", Toast.LENGTH_SHORT).show()
                            }
                            mProgressDialog.cancel()
                        }
                    }else{
                        Toast.makeText(context, "Password must be greater than 6 letters", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context, "Passwords doesn't match", Toast.LENGTH_SHORT).show()
                }
                Log.d("passwords", password + " " + confirm_password)
            }else{
                Toast.makeText(context, "All fields must be filled", Toast.LENGTH_SHORT).show()
            }
        }
        view.findViewById<TextView>(R.id.loginLink).setOnClickListener { view ->
            (activity as AuthenticationActivity)!!.gotoLoginFrame()

        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignUpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignUpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}