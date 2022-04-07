package es.alvarorodriguez.lastijerasdenuria.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import es.alvarorodriguez.lastijerasdenuria.R
import es.alvarorodriguez.lastijerasdenuria.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var preferences: SharedPreferences
    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }
    private var itemOlvidar: MenuItem? = null
    private var itemLogout: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences = getPreferences(Context.MODE_PRIVATE)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun observeDestinationChange() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                    itemLogout?.isVisible = false
                    itemOlvidar?.isVisible = false
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                R.id.registerFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                    itemLogout?.isVisible = false
                    itemOlvidar?.isVisible = false
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                R.id.homeFragment -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                    itemLogout?.isVisible = true
                    itemOlvidar?.isVisible = true
                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
                else -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                    itemLogout?.isVisible = true
                    itemOlvidar?.isVisible = true
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        itemOlvidar = menu?.findItem(R.id.clearShared)
        itemLogout = menu?.findItem(R.id.logout)
        observeDestinationChange()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        android.R.id.home -> {
            Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
            true
        }
        R.id.logout -> {
            logout()
            true
        }
        R.id.clearShared -> {
            clearSharedPreferences()
            true
        }
        else -> false
    }

    private fun clearSharedPreferences() {
        preferences.edit().clear().apply()
        Toast.makeText(this, "Clear credentials", Toast.LENGTH_SHORT).show()
        firebaseAuth.signOut()
        navController.navigate(R.id.loginFragment)
    }

    private fun logout() {
        firebaseAuth.signOut()
        Toast.makeText(this, "Logout", Toast.LENGTH_LONG).show()
        navController.navigate(R.id.loginFragment)
    }
}