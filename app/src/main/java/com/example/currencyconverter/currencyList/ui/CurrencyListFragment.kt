package com.example.currencyconverter.currencyList.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyconverter.currencyList.domain.CurrencyDomainModel
import com.example.currencyconverter.databinding.FragmentCurrencyListBinding

class CurrencyListFragment : Fragment() {

    private var _binding: FragmentCurrencyListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val currencyListAdapter = CurrencyListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val mockCurrency = listOf(
            CurrencyDomainModel("EUR", 1.00),
            CurrencyDomainModel("USD", 1.20),
            CurrencyDomainModel("GBP", 1.50),
        )
        binding.currencyListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = currencyListAdapter
        }

        currencyListAdapter.submitList(mockCurrency)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
