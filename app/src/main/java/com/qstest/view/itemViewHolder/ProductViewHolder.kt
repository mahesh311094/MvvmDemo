package com.qstest.view.itemViewHolder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.qstest.R
import com.qstest.databinding.RowProductItemBinding
import com.qstest.model.Product

class ProductViewHolder(private val binding: RowProductItemBinding, private val context:Context) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Product) {
        if (data.name.isNullOrEmpty() && data.desc.isNullOrEmpty() && data.price.isNullOrEmpty() && data.img.isNullOrEmpty()) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.productNameTxtView.text = data.name
            binding.productDescTxtView.text = data.desc
            if (data.price.isNullOrEmpty()) {
                binding.productPriceTxtView.text = "-"
            } else {
                binding.productPriceTxtView.text = context.getString(R.string.price, data.price)
            }
            Glide.with(context).load(data.img).placeholder(R.drawable.placeholde)
                .into(binding.productImgView)
        }
    }
}