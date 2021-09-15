package com.qstest.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import com.qstest.model.AllProductsIDs
import com.qstest.model.ErrorState
import com.qstest.model.Product
import com.qstest.model.ResultType
import com.qstest.repository.ProductsRepository
import com.qstest.utils.Constants
import com.qstest.utils.CoroutineDispatchersProvider
import com.qstest.utils.SingleLiveData
import kotlinx.coroutines.launch

class ProductsViewModel constructor(
    private val productsRepository: ProductsRepository,
    private val coroutineDispatchersProvider: CoroutineDispatchersProvider,
) : ViewModel() {

    private val _allProduct = SingleLiveData<ArrayList<Product>>()
    val allProduct: LiveData<ArrayList<Product>> = _allProduct

    private val _event = SingleLiveData<ProductEvent>()
    val event: LiveData<ProductEvent> = _event

    fun getIDs() {
        viewModelScope.launch(coroutineDispatchersProvider.main) {
            when (val response = productsRepository.getProductIDs()) {
                is ResultType.Success -> handleProductIDsApiSuccess(response.value)
                is ResultType.Error -> handleError(response.state)
            }
        }
    }

    fun getProduct(productIDs: ArrayList<String>) {
        val database = FirebaseDatabase.getInstance()
        val refDB = database.reference
        _allProduct.value= ArrayList()
        for (item in productIDs) {
            _allProduct.value?.add(Product(item, "", "", "", ""))
            getProductFromDB(refDB, item, Constants.productNameNode)
            getProductFromDB(refDB, item, Constants.productDescNode)
            getProductFromDB(refDB, item, Constants.productPriceNode)
            getProductFromDB(refDB, item, Constants.productImageNode)
        }
    }

    private fun getProductFromDB(refDB: DatabaseReference, ID: String, key: String) {
        refDB.child(key).child(ID)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        if (snapshot.value != null) {
                            try {
                                if (key == Constants.productNameNode) {
                                    _allProduct.value?.find { it.id == ID }?.name =
                                        snapshot.value?.toString()
                                }
                                if (key == Constants.productDescNode) {
                                    _allProduct.value?.find { it.id == ID }?.desc =
                                        snapshot.value?.toString()
                                }
                                if (key == Constants.productImageNode) {
                                    _allProduct.value?.find { it.id == ID }?.img =
                                        snapshot.value?.toString()
                                }
                                if (key == Constants.productPriceNode) {
                                    _allProduct.value?.find { it.id == ID }?.price =
                                        snapshot.value?.toString()
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        } else {
                            Log.e("TAG", " it's null.")
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onCancelled(p0: DatabaseError) {
                    Log.e("onCancelled", " cancelled")
                }
            })
    }

    private fun handleProductIDsApiSuccess(response: AllProductsIDs) {
        response.let {
            _event.value = ProductEvent.ProductIDsAPISuccess(response)
        }
    }

    private fun handleError(state: ErrorState) {
        _event.value = when (state) {
            is ErrorState.ErrorResponse -> ProductEvent.ResponseError(state.response)
            is ErrorState.GenericError -> ProductEvent.APIError(state.exception)
        }
    }
}