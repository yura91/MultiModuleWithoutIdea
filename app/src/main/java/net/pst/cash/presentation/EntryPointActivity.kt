package net.pst.cash.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.R

@AndroidEntryPoint
class EntryPointActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_point)
        val navController = findNavController(R.id.nav_host_fragment)
        navController.navigate(R.id.getAcquaintedFragment)
        /*if (условие) {
            navController.navigate(R.id.other_destination)
        } else {
            // Переход на start destination
            navController.navigate(R.id.start_destination)
        }*/
    }
}