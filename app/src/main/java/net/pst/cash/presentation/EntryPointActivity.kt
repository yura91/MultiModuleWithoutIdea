package net.pst.cash.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import net.pst.cash.R
import net.pst.cash.presentation.viewmodels.EntryPointViewModel

@AndroidEntryPoint
class EntryPointActivity : AppCompatActivity() {
    val viewModel by viewModels<EntryPointViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_point)
        val navController = findNavController(R.id.nav_host_fragment)
        viewModel.successUserInfo.observe(this) {
            if (!it) {
                navController.navigate(R.id.signInFragment)
            }
        }
        /*if (условие) {
            navController.navigate(R.id.other_destination)
        } else {
            // Переход на start destination
            navController.navigate(R.id.start_destination)
        }*/
    }
}