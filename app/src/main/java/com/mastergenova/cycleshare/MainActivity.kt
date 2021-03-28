package com.mastergenova.cycleshare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mastergenova.cycleshare.models.Account
import com.mastergenova.cycleshare.models.UserModel

class MainActivity : AppCompatActivity() {

    private val userModel: UserModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        findViewById<BottomNavigationView>(R.id.bottom_nav).setupWithNavController(navController)

        userModel.signOut.observe(this, Observer {
            if (it){
                signOut()
            }
        })

        getUserInfo()
    }

    private fun getUserInfo(){
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if(acct != null){
            val userAccount = Account(acct.givenName,
                    acct.familyName,
                    acct.displayName,
                    acct.email,
                    acct.id,
                    acct.photoUrl)
            userModel.setAccountInfo(userAccount)
        }
    }

    private fun signOut() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        mGoogleSignInClient.signOut().addOnCompleteListener(this, OnCompleteListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })
    }
}