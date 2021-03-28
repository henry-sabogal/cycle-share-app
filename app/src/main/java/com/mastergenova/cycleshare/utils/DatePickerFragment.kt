package com.mastergenova.cycleshare.utils

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.mastergenova.cycleshare.models.UserModel
import java.util.*

class DatePickerFragment(private val _context: Context?): DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val userModel: UserModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(_context!!, this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val date = year.toString() + "-" + convertNumberToString(month+1) + "-" + convertNumberToString(dayOfMonth)
        userModel.setDateSelected(date)
    }

    private fun convertNumberToString(n: Int): String{
        return if (n < 10) "0" + n.toString() else n.toString()
    }
}