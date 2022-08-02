package com.androiddevs.shoppinglisttestingyt.ui.fragment

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.androiddevs.shoppinglisttestingyt.R
import com.androiddevs.shoppinglisttestingyt.adapter.ImageAdapter
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.androiddevs.shoppinglisttestingyt.launchFragmentInHiltContainer
import com.androiddevs.shoppinglisttestingyt.repository.FakeShoppingRepositoryAndroidTest
import com.androiddevs.shoppinglisttestingyt.ui.fragment.factory.AndroidTestShoppingFragmentFactory
import com.androiddevs.shoppinglisttestingyt.ui.viewmodel.ShoppingViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class ImagePickFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var androidTestShoppingFragmentFactory: AndroidTestShoppingFragmentFactory

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun clickImage_popBackStackAndSetImageUrl() {
        val navController = mock(NavController::class.java)
        val imageUrl = "test"
        val testViewModel = ShoppingViewModel(FakeShoppingRepositoryAndroidTest())
        launchFragmentInHiltContainer<ImagePickFragment>(fragmentFactory = androidTestShoppingFragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            imageAdapter.images = listOf(imageUrl)
            viewModel = testViewModel
        }

        onView(withId(R.id.rvImages)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageAdapter.ImageViewHolder>(
                0,
                click()
            )
        )

        verify(navController).popBackStack()
        assertThat(testViewModel.currentImageUrl.getOrAwaitValue()).isEqualTo(imageUrl)

    }

    @Test
    fun replaceText_imagesResponseValueIsSet() {
        val navController = mock(NavController::class.java)
        val text = "apple"
        var textViewModel : ShoppingViewModel? = null
        launchFragmentInHiltContainer<ImagePickFragment>(
            fragmentFactory = androidTestShoppingFragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
            textViewModel = viewModel
        }

        onView(withId(R.id.etSearch)).perform(
            replaceText(text)
        )

        assertThat(textViewModel!!.images.getOrAwaitValue()).isNotNull()
    }
//
//    @Test
//    fun replaceText_adapterListIsNotEmpty() {
//        val navController = mock(NavController::class.java)
//        val text = "apple"
//        var textViewModel : ShoppingViewModel? = null
//        var testImageAdapter: ImageAdapter? = null
//        launchFragmentInHiltContainer<ImagePickFragment>(
//            fragmentFactory = androidTestShoppingFragmentFactory
//        ) {
//            Navigation.setViewNavController(requireView(), navController)
//            textViewModel = viewModel
//            testImageAdapter = imageAdapter
//        }
//
//        onView(withId(R.id.etSearch)).perform(
//            replaceText(text)
//        )
//        var images = textViewModel!!.images.getOrAwaitValue()
//        Log.d("TESTING_", images.peekContent().status.name)
//        images = textViewModel!!.images.getOrAwaitValue()
//        Log.d("TESTING_", images.peekContent().status.name)
//        textViewModel!!.images.getOrAwaitValue {
//            Log.d("TESTING_", testImageAdapter!!.images.toString())
//            assertThat(testImageAdapter!!.images).isNotEmpty()
//        }
//    }
}