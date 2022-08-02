package com.androiddevs.shoppinglisttestingyt.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingDao
import com.androiddevs.shoppinglisttestingyt.data.local.ShoppingItem
import com.androiddevs.shoppinglisttestingyt.data.remote.PixabalAPI
import com.androiddevs.shoppinglisttestingyt.data.remote.responses.ImageResponse
import com.androiddevs.shoppinglisttestingyt.other.Resource
import javax.inject.Inject

class DefaultShoppingRepository  @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabalAPI: PixabalAPI
) : ShoppingRepository{
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    @SuppressLint("LogNotTimber")
    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try{
            val response = pixabalAPI.searchForImage(imageQuery)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                }?: Resource.error("An unknown error occured", null)
            } else{
                Resource.error("An unknown error occured", null)
            }
        }catch(e: Exception) {
            e.message?.let { Log.e("EXCEPTION", it) }
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }

}