package com.example.a41_funapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.* // ktlint-disable no-wildcard-imports
import java.lang.NumberFormatException
import java.util.concurrent.TimeUnit

class TimerFragmentViewModel() : ViewModel() {
    private var timeInMillis: Long = 1L

    // TODO
    //  erstelle eine Varbiable namens currentJob

    // TODO
    //  erstelle eine Variable delay

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
    }

    fun stopCurrentJob() {
        // TODO
    }

    fun fastRunCurrentJob() {
        // TODO
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
