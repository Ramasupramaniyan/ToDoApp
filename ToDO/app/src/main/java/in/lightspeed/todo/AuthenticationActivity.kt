package `in`.lightspeed.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth

class AuthenticationActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        auth = FirebaseAuth.getInstance()
        gotoLoginFrame()
    }
    fun gotoLoginFrame(){
        val loginFragment = LoginFragment()
        var fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val actionBar = supportActionBar
        actionBar?.setTitle("Login")
        fragmentTransaction.replace(R.id.fragment_main, loginFragment)
        fragmentTransaction.commit()
    }
    fun gotoRegisterFrame(){
        val signUpFragment = SignUpFragment()
        var fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val actionBar = supportActionBar
        actionBar?.setTitle("Register")
        fragmentTransaction.replace(R.id.fragment_main, signUpFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.commit()
    }
}