package com.jw.flickrviewr

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Main activity for the Flickr Viewr app.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.nav_host_fragment)
        nav_view.setupWithNavController(navController)
        setupActionBarWithNavController(navController)

        navController.addOnDestinationChangedListener { _, dest, _ ->
            // Show the BottomNavigationBar in only Gallery and Favorites screens.
            when (dest.id) {
                R.id.galleryFragment, R.id.favoritesFragment -> nav_view.visibility = View.VISIBLE
                else -> nav_view.visibility = View.GONE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean = NavHostFragment.findNavController(nav_host_fragment).navigateUp()

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        NavigationUI.onNavDestinationSelected(item, NavHostFragment.findNavController(nav_host_fragment))
                || super.onOptionsItemSelected(item)

}