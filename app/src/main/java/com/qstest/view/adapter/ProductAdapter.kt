package com.qstest.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.qstest.databinding.RowProductItemBinding
import com.qstest.model.Product
import com.qstest.view.itemViewHolder.ProductViewHolder
import com.qstest.viewmodel.ProductsViewModel

class ProductAdapter(
    private val context: Context,
    viewModel: ProductsViewModel,
    lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<ProductViewHolder>() {

    private var productList: ArrayList<Product> = ArrayList()

    init {
        viewModel.allProduct.observe(lifecycleOwner, {
            productList = it
            Log.e("===","in")
            notifyItemChanged(0)
            notifyItemInserted(it.size-1)
            notifyDataSetChanged()
        })
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductViewHolder {
        val binding = RowProductItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProductViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        productList[position].let { data ->
            holder.bind(data)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}