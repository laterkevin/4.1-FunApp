package com.example.a41_funapp.ui

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.* // ktlint-disable no-wildcard-imports
import java.lang.NumberFormatException
import java.util.concurrent.TimeUnit

class TimerFragmentViewModel() : ViewModel() {
    private var timeInMillis: Long = 1L

    // TODO
    //  erstelle eine Varbiable namens currentJob
    lateinit var currentJob: Job

    // TODO
    //  erstelle eine Variable delayTime
    var delayTime: Long = 1000

    private var _stringTime = MutableLiveData<String>("")
    val stringTime: LiveData<String>
        get() = _stringTime

    private var _countDownInitiated: Boolean = false
    val countDownInitiated: Boolean
        get() = _countDownInitiated

    private var _countDownActive = MutableLiveData<Boolean>(false)
    val countDownActive: LiveData<Boolean>
        get() = _countDownActive

    fun countDownTime(timeString: String) {
        // TODO
        _stringTime.value = timeString
        convertTimeToMillis(timeString)
        _countDownInitiated = true
        currentJob = viewModelScope.launch(Dispatchers.Main) {
            while (timeInMillis > 0) {
                delay(delayTime)
                timeInMillis -= 1000
                convertTimeToString(timeInMillis)
            }
            _countDownActive.value = false
            delayTime = 1000
        }
    }

    fun stopCurrentJob() {
        // TODO
        currentJob.cancel()
    }

    fun fastRunCurrentJob() {
        // TODO
        delayTime = 10
    }

    private fun convertTimeToString(millis: Long) {
        _stringTime.value = String.format(
            "%02d",
            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        )
    }

    private fun convertTimeToMillis(timeString: String) {

        var seconds = try {
            timeString.toInt()
        } catch (ex: NumberFormatException) {
            0
        }

        timeInMillis = (seconds * 1000L)

        println("Millis: $timeInMillis")
    }

    fun resetCountDown() {
        _countDownInitiated = false
    }
}
