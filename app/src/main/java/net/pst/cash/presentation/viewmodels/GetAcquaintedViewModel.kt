package net.pst.cash.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.pst.cash.presentation.SingleLiveEvent
import java.util.Calendar

class GetAcquaintedViewModel : ViewModel() {
    private var currentYear: Int = 0
    private var currentMonth: Int = 0
    private var currentDay: Int = 0

    private var selectedYear: Int = 0
    private var selectedMonth: Int = 0
    private var selectedDay: Int = 0

    var formattedDate: String? = null

    init {
        val calendar = Calendar.getInstance()
        currentYear = calendar.get(Calendar.YEAR)
        currentMonth = calendar.get(Calendar.MONTH)
        currentDay = calendar.get(Calendar.DAY_OF_MONTH)
    }

    val canGoNext: SingleLiveEvent<Boolean>
        get() = _canGoNext

    private val _canGoNext: SingleLiveEvent<Boolean> = SingleLiveEvent()

    val errorFirstname: LiveData<Boolean>
        get() = _errorFirstname

    private val _errorFirstname: MutableLiveData<Boolean> = MutableLiveData()

    val errorLastName: LiveData<Boolean>
        get() = _errorLastname

    private val _errorLastname: MutableLiveData<Boolean> = MutableLiveData()

    private val _errorAge: MutableLiveData<Boolean> = MutableLiveData()

    fun validateFirstName(text: String) {
        _errorFirstname.value = text.length < 3
    }

    fun validateLastName(text: String) {
        _errorLastname.value = text.length < 3
    }

    fun setSelectedDate(selectedYear: Int, selectedMonth: Int, selectedDay: Int) {
        this.selectedYear = selectedYear
        this.selectedMonth = selectedMonth
        this.selectedDay = selectedDay
    }

    fun validate(firstName: String, lastName: String) {
        validateFirstName(firstName)
        validateLastName(lastName)
        var age = currentYear - selectedYear
        if (currentMonth < selectedMonth ||
            (currentMonth == selectedMonth && currentDay < selectedDay)
        ) {
            age--
        }
        _errorAge.value = age < 18

        _canGoNext.value =
            _errorFirstname.value == false && _errorLastname.value == false && _errorAge.value == false
    }
}