package com.example.currencyconverter.currencyConvertor.ui

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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

        viewModel.currencyList.observe(viewLifecycleOwner){ codes ->
            val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, codes)
            binding.fromCurrencyCodeSpinner.adapter = adapter
            binding.toCurrencyCodeSpinner.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
