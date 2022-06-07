package com.example.a41_funapp.ui

import android.graphics.drawable.AnimatedVectorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.a41_funapp.R
import com.example.a41_funapp.databinding.FragmentTimerBinding
import com.example.a41_funapp.model.Filter
import java.lang.Exception

class TimerFragment : Fragment() {
    private lateinit var binding: FragmentTimerBinding
    private val timerViewModel: TimerFragmentViewModel by viewModels()
    private lateinit var explosionSound: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_timer, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.secondsInput.filters = arrayOf(Filter("0", "60"))

        binding.timerButton.setOnClickListener {
            val seconds = binding.secondsInput.text.toString()

            var timeString = "$seconds"

            timerViewModel.countDownTime(timeString)
            binding.timerButton.isEnabled = false
        }

        binding.cutBlueWireButton.setOnClickListener {
            if (timerViewModel.countDownInitiated) {
                binding.imageView.setImageResource(R.drawable.timebomb_bluewire_cut)
                timerViewModel.stopCurrentJob()
                binding.timerButton.isEnabled = false
                binding.cutBlueWireButton.isEnabled = false
                binding.cutRedWireButton.isEnabled = false
                binding.resetButton.isEnabled = true
            }
        }

        binding.cutRedWireButton.setOnClickListener {
            if (timerViewModel.countDownInitiated) {
                binding.imageView.setImageResource(R.drawable.timebomb_redwirde_cut)
                timerViewModel.fastRunCurrentJob()
                binding.timerButton.isEnabled = false
                binding.cutBlueWireButton.isEnabled = false
                binding.cutRedWireButton.isEnabled = false
            }
        }

        binding.resetButton.setOnClickListener {
            binding.resetButton.isEnabled = false
            binding.imageView.setImageResource(R.drawable.timebomb)
            binding.secondsTv.visibility = View.VISIBLE
            binding.secondsTv.setText("")
            binding.secondsInput.setText("")
            binding.cutRedWireButton.isEnabled = true
            binding.cutBlueWireButton.isEnabled = true
            binding.timerButton.isEnabled = true
            timerViewModel.resetCountDown()
            try {
                if (explosionSound.isPlaying) {
                    explosionSound.stop()
                }
                explosionSound.release()
            } catch (ex: Exception) {
                Log.e("TimerFragment", ex.toString())
            }
        }

        timerViewModel.stringTime.observe(
            viewLifecycleOwner
        ) {
            println(it)
            binding.secondsTv.text = it
        }
        timerViewModel.countDownActive.observe(
            viewLifecycleOwner
        ) {
            if (!it) {
                if (timerViewModel.countDownInitiated) {
                    binding.cutRedWireButton.isEnabled = false
                    binding.cutBlueWireButton.isEnabled = false
                    binding.secondsTv.visibility = View.INVISIBLE
                    binding.imageView.setImageResource(R.drawable.explosion_anim)
                    val explosion = binding.imageView.drawable as AnimatedVectorDrawable
                    explosion.start()
                    explosionSound = MediaPlayer.create(requireContext(), R.raw.explosion)
                    explosionSound.start()
                    binding.resetButton.isEnabled = true
                }
            }
        }
    }
}
