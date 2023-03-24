package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.rsschool.android2021.ui.SecondScreen

class SecondFragment() : Fragment() {

    interface Callbacks {
        fun onBackButtonClicked(previousNumber: Int)
    }

    private var callbacks: Callbacks? = null
    private var result: Int = 0

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
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
            val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0
            result = generateRandomBetweenInts(min, max)

            setContent {
                SecondScreen(
                    result = result.toString(),
                    onBackButtonClick = ::onBackButtonClick,
                )
            }
        }
    }

    private fun onBackButtonClick() {
        callbacks?.onBackButtonClicked(result)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner) { onBackButtonClick() }
    }

    private fun generateRandomBetweenInts(min: Int, max: Int): Int {
        return (min..max).random()
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
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
