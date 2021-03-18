package com.example.notetaking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)


        val navController = findNavController(R.id.root_navigation_container)
        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.noteListFragment
        )
            .setOpenableLayout(drawerLayout)
            .build()

        setupActionBarWithNavController(navController, appBarConfiguration)

//        findViewById<NavigationView>(R.id.navigation_view).setupWithNavController(navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.root_navigation_container).navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}