package net.pst.cash.presentation.viewmodels

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.pst.cash.domain.AccountsInteractor
import javax.inject.Inject


@HiltViewModel
class AccountsViewModel @Inject constructor(
    private val application: Application,
    private val accountsInteractor: AccountsInteractor,
) : AndroidViewModel(application) {
    private val _addresses = MutableLiveData<List<String>>()
    val addresses: LiveData<List<String>>
        get() = _addresses

    private val _qrCodeLiveData = MutableLiveData<Bitmap?>()
    val qrCodeLiveData: LiveData<Bitmap?>
        get() = _qrCodeLiveData

    init {
        viewModelScope.launch {
            val sharedPref = application.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val token = sharedPref.getString("token", "")
            val addresses = accountsInteractor.getAccounts("Bearer $token")
            _addresses.value = addresses
        }
    }

    fun generateQrCodeInBackground(data: String) {
        viewModelScope.launch {
            val qrCode = withContext(Dispatchers.Default) {
                createQrCode(data)
            }
            _qrCodeLiveData.value = qrCode
        }
    }


    private fun createQrCode(encryptedString: String): Bitmap? {
        try {
            val bitMatrix =
                MultiFormatWriter().encode(encryptedString, BarcodeFormat.QR_CODE, 200, 200)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                }
            }
            return bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            return null
        }
    }
}