package com.mastergenova.cycleshare.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mastergenova.cycleshare.R
import com.mastergenova.cycleshare.StationsAPIResponse
import com.mastergenova.cycleshare.models.UserModel
import java.lang.ClassCastException
import java.lang.IllegalStateException

class BookSuccessDialogFragment: DialogFragment() {

    internal lateinit var listener: BookSuccessDialogListener

    interface BookSuccessDialogListener{
        fun onDialogOkClick(dialog: DialogFragment)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.book_success)
                    .setPositiveButton(R.string.ok,
                    DialogInterface.OnClickListener{ dialog, id ->
                        listener.onDialogOkClick(this)
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            listener = context as BookSuccessDialogListener
        }catch (e: ClassCastException){
            throw ClassCastException((context.toString() + " must implement BookSuccessDialogListener"))
        }
    }
}