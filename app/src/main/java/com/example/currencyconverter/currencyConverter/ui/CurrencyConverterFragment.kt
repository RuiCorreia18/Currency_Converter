package com.example.currencyconverter.currencyConverter.ui

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.MainApplication
import com.example.currencyconverter.databinding.FragmentCurrencyConverterBinding
import javax.inject.Inject

class CurrencyConverterFragment : Fragment() {

    private var _binding: FragmentCurrencyConverterBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: CurrencyConverterViewModel by viewModels { viewModelFactory }

    private lateinit var spinnerAdapter: SpinnerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyConverterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as MainApplication).appComponent.inject(this)

        //Update spinner with currency list
        viewModel.currencyList.observe(viewLifecycleOwner) { codes ->
            spinnerAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, codes)
            with(binding) {
                fromCurrencyCodeSpinner.adapter = spinnerAdapter
                fromCurrencyCodeSpinner.setSelection(0)
                toCurrencyCodeSpinner.adapter = spinnerAdapter
                toCurrencyCodeSpinner.setSelection(1)
            }
        }

        viewModel.currencyConversion.observe(viewLifecycleOwner) { conversion ->
            binding.toCurrencyValueTV.setText(conversion.toCurrencyValue.toString())
        }

        binding.fromCurrencyCodeSpinner.onItemSelectedListener = clearToValue()
        binding.toCurrencyCodeSpinner.onItemSelectedListener = clearToValue()

        /**
         * TODO
         *  - on spinner change clear input values
         *  - on values change clear other values
         *  - fetch value to ask conversion
         *  - send and fill result of conversion
         */

        binding.currencyConvertButton.setOnClickListener {
            val fromCurrency = binding.fromCurrencyCodeSpinner.selectedItem.toString()
            val toCurrency = binding.toCurrencyCodeSpinner.selectedItem.toString()

            val fromCurrencyValue = binding.fromCurrencyValueTV.text.toString()

            viewModel.getCurrencyConversion(fromCurrency, toCurrency, fromCurrencyValue)
        }
    }

    private fun clearToValue() = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            binding.toCurrencyValueTV.setText("")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}