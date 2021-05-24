package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.rsschool.android2021.databinding.FragmentSecondBinding
import java.lang.RuntimeException
import kotlin.random.Random

class SecondFragment() : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var callbacks: Callbacks

    interface Callbacks {
        fun onBackButtonClicked(previousNumber: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Callbacks) {
            callbacks = context
        } else {
            throw RuntimeException("$context must implement SecondFragment.Callbacks")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0

        binding.result.text = generate(min, max).toString()

        binding.backButton.setOnClickListener {
            callbacks.onBackButtonClicked(binding.result.text.toString().toInt())
        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    callbacks.onBackButtonClicked(binding.result.text.toString().toInt())
                }
            })

    }

    private fun generate(min: Int, max: Int): Int {
        return Random.nextInt(min, max)
    }

    companion object {

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"

        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle()
            args.putInt(MIN_VALUE_KEY, min)
            args.putInt(MAX_VALUE_KEY, max)
            fragment.arguments = args
            return fragment
        }
    }
}