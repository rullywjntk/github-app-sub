package com.rullywjntk.mygithub.view.alarm

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rullywjntk.mygithub.R
import com.rullywjntk.mygithub.data.Alarm
import com.rullywjntk.mygithub.data.AlarmPreference
import com.rullywjntk.mygithub.databinding.ActivityAlarmBinding
import com.rullywjntk.mygithub.fragment.TimePickerFragment
import com.rullywjntk.mygithub.receiver.AlarmReceiver
import java.text.SimpleDateFormat
import java.util.*

class AlarmActivity : AppCompatActivity(), View.OnClickListener, TimePickerFragment.DialogTimeListener {

    private lateinit var alarmBinding: ActivityAlarmBinding
    private lateinit var alarm: Alarm
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        alarmBinding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(alarmBinding.root)
        setSupportActionBar(alarmBinding.toolbar)
        supportActionBar?.title = getString(R.string.alarm)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        alarmBinding.btnAlarm.setOnClickListener(this)
        val alarmPreference = AlarmPreference(this)
        if (alarmPreference.getAlarm().isChecked) {
            alarmBinding.switchWidget.isChecked = true
        } else {
            alarmBinding.switchWidget.isChecked = false
        }

        alarmReceiver = AlarmReceiver()
        
        alarmBinding.switchWidget.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                setAlarm(true)
                val repeatTime = alarmBinding.tvAlarm.text.toString()
                alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING, repeatTime, "Github Reminder")
            } else {
                setAlarm(false)
                alarmReceiver.cancelAlarm(this)
            }
        }


    }

    private fun setAlarm(b: Boolean) {
        val alarmPreference = AlarmPreference(this)
        alarm = Alarm()
        alarm.isChecked = b
        alarmPreference.setAlarm(alarm)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btnAlarm -> {
                val timePickerFragmentRepeat = TimePickerFragment()
                timePickerFragmentRepeat.show(supportFragmentManager, TIME_PICKER_REPEAT_TAG)
            }
        }
    }

    override fun onDialogTimeSet(tag: String?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (tag) {
            TIME_PICKER_REPEAT_TAG -> alarmBinding.tvAlarm.text = dateFormat.format(calendar.time)
        }
    }

    companion object {
        private const val TIME_PICKER_REPEAT_TAG = "TimePickerRepeat"
    }

}