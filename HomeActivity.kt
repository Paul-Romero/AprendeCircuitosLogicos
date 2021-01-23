package com.example.ihmapp

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.facebook.login.LoginManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

enum class ProviderType{
    BASIC,
    GOOGLE,
    FACEBOOK
}
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bundle:Bundle? = intent.extras
        val email:String? = bundle?.getString("email")
        val provider:String? = bundle?.getString("provider")
        setup(email?:"",provider?:"")

        //Guardar datos de sesión
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email",email)
        prefs.putString("provider",provider)
        prefs.apply()

        //setupToolbarMenu()
    }
    private fun setup(email: String, provider:String){
        title = "LearnLogicCircuits"
        /*StudyButt.setOnClickListener {
            //startActivity(ModuleStudy)
        }
        ExerButt.setOnClickListener {
            //startActivity(ModulePractice)
        }
        TestsButt.setOnClickListener {
            //startActivity(ModuleEvaluar)
        }*/
        LogoutButt.setOnClickListener {
            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()
            if(provider == ProviderType.FACEBOOK.name){LoginManager.getInstance().logOut()}
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }

    private fun setupToolbarMenu(){
        val toolbar:Toolbar = findViewById(R.id.drawer_toolbar)
        setSupportActionBar(toolbar)
        if (getSupportActionBar()!=null){supportActionBar?.setDisplayHomeAsUpEnabled(true)}
        val drawerLayout:DrawerLayout = findViewById(R.id.drawer_layout)
        val navigationView:NavigationView = findViewById(R.id.navigationView)
        val actionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.isDrawerIndicatorEnabled = true
        actionBarDrawerToggle.syncState()
    }
}