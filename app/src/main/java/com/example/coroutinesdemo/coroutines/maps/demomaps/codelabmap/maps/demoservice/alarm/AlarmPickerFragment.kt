package com.example.coroutinesdemo.coroutines.maps.demomaps.codelabmap.maps.demoservice.alarm

import android.app.Dialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.DialogFragment
import java.util.*
class AlarmPickerFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var c = Calendar.getInstance();
        var hour = c.get(Calendar.HOUR_OF_DAY);
        var minute = c.get(Calendar.MINUTE);
        return TimePickerDialog(
            activity,
            activity as OnTimeSetListener?,
            hour,
            minute,
            DateFormat.is24HourFormat(activity))
    }
}