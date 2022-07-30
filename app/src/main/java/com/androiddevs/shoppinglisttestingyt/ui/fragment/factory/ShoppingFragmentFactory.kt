package com.androiddevs.shoppinglisttestingyt.ui.fragment.factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.androiddevs.shoppinglisttestingyt.adapter.ImageAdapter
import com.androiddevs.shoppinglisttestingyt.ui.fragment.AddShoppingItemFragment
import com.androiddevs.shoppinglisttestingyt.ui.fragment.ImagePickFragment
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ShoppingFragmentFactory @Inject constructor(
    private val imageAdapter: ImageAdapter,
    private val glide: RequestManager
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ImagePickFragment::class.java.name -> ImagePickFragment(imageAdapter)
            AddShoppingItemFragment::class.java.name -> AddShoppingItemFragment(glide)
            else -> super.instantiate(classLoader, className)
        }
    }

}