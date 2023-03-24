package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.rsschool.android2021.ui.firstscreen.FirstScreen

class FirstFragment : Fragment() {

    interface Callbacks {
        fun onGenerateButtonClicked(min: Int, max: Int)
    }

    private var callbacks: Callbacks? = null
    private var previousResult = mutableStateOf(0)
    private var minText = mutableStateOf("")
    private var maxText = mutableStateOf("")
    private var isGenerateButtonEnabled = mutableStateOf(false)

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
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                FirstScreen(
                    previousResult = previousResult,
                    minText = minText,
                    onMinValueChange = ::onMinValueChange,
                    maxText = maxText,
                    onMaxValueChange = ::onMaxValueChange,
                    isGenerateButtonEnabled = isGenerateButtonEnabled,
                    onGenerateButtonClick = ::onGenerateButtonClick,
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY) ?: 0
        previousResult.value = result
    }

    private fun onMinValueChange(text: String) {
        if (isTextValid(text)) {
            minText.value = text
        }
        onTextChange()
    }

    private fun onMaxValueChange(text: String) {
        if (isTextValid(text)) {
            maxText.value = text
        }
        onTextChange()
    }

    private fun isTextValid(text: String): Boolean {
        val number = text.toIntOrNull()
        return number != null || text == ""
    }

    private fun onTextChange() {
        val min = minText.value.toIntOrNull()
        val max = maxText.value.toIntOrNull()

        if (min != null && max != null && min <= max) {
            enableGenerateButton(true)
        } else {
            enableGenerateButton(false)
        }
    }

    private fun enableGenerateButton(enable: Boolean) {
        isGenerateButtonEnabled.value = enable
    }

    private fun onGenerateButtonClick() {
        callbacks?.onGenerateButtonClicked(minText.value.toInt(), maxText.value.toInt())
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    companion object {

        private const val PREVIOUS_RESULT_KEY = "previous_result"

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }
    }
}
