package com.example.currencyconverter.currencyList.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyconverter.MainApplication
import com.example.currencyconverter.databinding.FragmentCurrencyListBinding
import javax.inject.Inject

class CurrencyListFragment : Fragment() {

    private var _binding: FragmentCurrencyListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val currencyListAdapter = CurrencyListAdapter()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: CurrencyListViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCurrencyListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as MainApplication).appComponent.inject(this)

        binding.currencyListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = currencyListAdapter
        }

        viewModel.currencyList.observe(viewLifecycleOwner){ currencyList ->
            currencyListAdapter.submitList(currencyList)
        }

        viewModel.getCurrencyList()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
