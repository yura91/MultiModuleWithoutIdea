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
        viewModel.navigateToLoginScreen.observe(this) {
            navController.navigate(R.id.signInFragment)
        }
        viewModel.navigateToGetAcquaintedScreen.observe(this) {
            navController.navigate(R.id.getAcquaintedFragment)
        }
    }
}