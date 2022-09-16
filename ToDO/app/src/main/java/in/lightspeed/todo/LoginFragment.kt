package `in`.lightspeed.todo

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var button1: Button? = null
    private var email_ed: EditText? = null
    private var password_ed:EditText? = null
    lateinit var auth: FirebaseAuth
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
        val view: View = inflater.inflate(R.layout.fragment_login, container, false)
        email_ed = view.findViewById(R.id.email_login) as EditText
        password_ed = view.findViewById(R.id.password_login) as EditText
        auth = FirebaseAuth.getInstance()
        view.findViewById<Button>(R.id.loginBtn).setOnClickListener { view ->
            val email:String = email_ed!!.text.toString()
            val  password:String = password_ed!!.text.toString()
            if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                val mProgressDialog = ProgressDialog(context)
                mProgressDialog.setTitle("Account")
                mProgressDialog.setMessage("Log In...")
                mProgressDialog.show()
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity as AuthenticationActivity) {
                    if (it.isSuccessful) {
                        Toast.makeText(context, "Successfully logged in", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context,HomeActivity::class.java)
                        startActivity(intent)
                        (activity as AuthenticationActivity).finish()
                    }else{
                        Log.d("Login Error", it.exception!!.message.toString())
                        val builder = AlertDialog.Builder(context)
                        builder.setTitle("Error")
                        builder.setMessage("No user found, please register user")
                        builder.setPositiveButton("Ok"){dialogInterface, which ->
                            dialogInterface.dismiss()
                        }

                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.setCancelable(false)
                        alertDialog.show()
                    }
                    mProgressDialog.cancel()
                }
            }else{
                Toast.makeText(context, "All fields must be filled", Toast.LENGTH_SHORT).show()
            }
        }

        view.findViewById<TextView>(R.id.registerLink).setOnClickListener { view ->
            (activity as AuthenticationActivity)!!.gotoRegisterFrame()
            Log.d("test", "test")
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
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}