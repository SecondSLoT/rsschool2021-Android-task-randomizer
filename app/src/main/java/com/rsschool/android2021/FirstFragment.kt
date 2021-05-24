package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rsschool.android2021.databinding.FragmentFirstBinding
import java.lang.RuntimeException

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var callbacks: Callbacks

    interface Callbacks {
        fun onGenerateButtonClicked(min: Int, max: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Callbacks) {
            callbacks = context
        } else {
            throw RuntimeException("$context must implement FirstFragment.Callbacks")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        val previousResultString = "Previous result: $result"
        binding.previousResult.text = previousResultString

        enableGenerateButton(false)
        val numberTextWatcher = NumberTextWatcher()
        binding.minValueEditText.addTextChangedListener(numberTextWatcher)
        binding.maxValueEditText.addTextChangedListener(numberTextWatcher)

        binding.generateButton.setOnClickListener {
            val min = binding.minValueEditText.text.toString().toInt()
            val max = binding.maxValueEditText.text.toString().toInt()
            callbacks.onGenerateButtonClicked(min, max)
        }
    }

    private fun enableGenerateButton(enable: Boolean) {
        binding.generateButton.isEnabled = enable
    }

    companion object {

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class NumberTextWatcher : TextWatcher {
        private var min = 0
        private var max = 0

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(p0: Editable?) {
            val value = p0.toString().toIntOrNull()

            if (value == null) {
                enableGenerateButton(false)
                return
            }

            if (value < Int.MAX_VALUE) {

                if (p0 == binding.minValueEditText.editableText) {
                    min = p0.toString().toInt()
                } else if (p0 == binding.maxValueEditText.editableText) {
                    max = p0.toString().toInt()
                }

                if (min < max) {
                    enableGenerateButton(true)
                } else {
                    enableGenerateButton(false)
                }
            } else {
                enableGenerateButton(false)
            }
        }
    }
}