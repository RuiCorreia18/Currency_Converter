package com.example.currencyconverter.currencyList.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.databinding.ItemCurrencyListBinding

class CurrencyListAdapter :
    ListAdapter<CurrencyUIModel, CurrencyListAdapter.CurrencyListViewHolder>(CurrencyDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyListViewHolder {
        val itemBinding = ItemCurrencyListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

        return CurrencyListViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CurrencyListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CurrencyListViewHolder(
        private val binding: ItemCurrencyListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currency: CurrencyUIModel) {
            with(binding) {
                currencyCodeTV.text = currency.code
                currencyRateTV.text = "${currency.rate} ${currency.symbol}"
            }
        }
    }

    class CurrencyDiffUtil : DiffUtil.ItemCallback<CurrencyUIModel>() {
        override fun areItemsTheSame(
            oldItem: CurrencyUIModel,
            newItem: CurrencyUIModel
        ): Boolean {
            return oldItem.code == newItem.code && oldItem.rate == oldItem.rate
        }

        override fun areContentsTheSame(
            oldItem: CurrencyUIModel,
            newItem: CurrencyUIModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}
