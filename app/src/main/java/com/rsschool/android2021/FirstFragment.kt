package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import java.lang.RuntimeException

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var minValueEditText: EditText? = null
    private var maxValueEditText: EditText? = null

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
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews(view)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        val previousResultString = "Previous result: $result"
        previousResult?.text = previousResultString

        enableGenerateButton(false)
        val numberTextWatcher = NumberTextWatcher()
        minValueEditText?.addTextChangedListener(numberTextWatcher)
        maxValueEditText?.addTextChangedListener(numberTextWatcher)

        generateButton?.setOnClickListener {
            val min = minValueEditText?.text.toString().toInt()
            val max = maxValueEditText?.text.toString().toInt()
            callbacks.onGenerateButtonClicked(min, max)
        }
    }

    private fun bindViews(view: View) {
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        minValueEditText = view.findViewById(R.id.min_value)
        maxValueEditText = view.findViewById(R.id.max_value)
    }

    private fun enableGenerateButton(enable: Boolean) {
        generateButton?.isEnabled = enable
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

                if (p0 == minValueEditText?.editableText) {
                    min = p0.toString().toInt()
                } else if (p0 == maxValueEditText?.editableText) {
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