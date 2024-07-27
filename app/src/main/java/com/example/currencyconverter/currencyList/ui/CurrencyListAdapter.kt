package com.example.currencyconverter.currencyList.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.currencyList.domain.CurrencyDomainModel
import com.example.currencyconverter.databinding.ItemCurrencyListBinding
import java.text.DecimalFormat

class CurrencyListAdapter :
    ListAdapter<CurrencyDomainModel, CurrencyListAdapter.CurrencyListViewHolder>(CurrencyDiffUtil()) {

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

        fun bind(currency: CurrencyDomainModel) {
            with(binding) {
                currencyCodeTV.text = currency.code
                currencyRateTV.text = DecimalFormat("#,##0.00").format(currency.rate)
            }
        }
    }

    class CurrencyDiffUtil : DiffUtil.ItemCallback<CurrencyDomainModel>() {
        override fun areItemsTheSame(
            oldItem: CurrencyDomainModel,
            newItem: CurrencyDomainModel
        ): Boolean {
            return oldItem.code == newItem.code && oldItem.rate == oldItem.rate
        }

        override fun areContentsTheSame(
            oldItem: CurrencyDomainModel,
            newItem: CurrencyDomainModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}
