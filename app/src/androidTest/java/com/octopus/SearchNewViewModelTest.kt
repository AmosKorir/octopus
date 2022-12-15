package com.octopus

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.octopus.baseclasses.BaseViewModel.BaseViewModel
import com.octopus.data.repositories.db.UserDbRepository
import com.octopus.data.repositories.pagingsource.Paginater
import com.octopus.domain.repositories.UserRepository
import com.google.common.truth.Truth.assertThat
import com.octopus.domain.models.User
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchNewViewModelTest {

    private val mockUserApiRepository = mockk<UserRepository>(relaxed = true)
    private val mockUserDbRepository = mockk<UserDbRepository>(relaxed = true)
    private val mockPaginater = mockk<Paginater>(relaxed = true)
    private lateinit var viewModel: SearchNewViewModel
    private val username = "amoskorir"

    @Before
    fun setup() {
        viewModel = SearchNewViewModel(
            userApiRepository = mockUserApiRepository,
            userDbRepository = mockUserDbRepository,
            paginater = mockPaginater
        )
        every { mockUserApiRepository.searchUser(username) } returns Single.just(SampleData.user)
        every { mockPaginater.fetchFollowingData(username) } returns Observable.just(SampleData.pagingDataFollowing)
        every { mockPaginater.fetchFollowerData(username) } returns Observable.just(SampleData.pagingDataFollower)
    }
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Test
    fun searchUser_calls_searchUser_on_userApiRepository() {
        viewModel.searchUser(username)
        viewModel.uiState.observeForever{}
        viewModel.uiState.wait()

        val data=viewModel.uiState.value as BaseViewModel.LoadingState.Success
        assertThat(data.data).isInstanceOf(User::class.java)
        assertThat((data.data as User).id).isEqualTo(SampleData.user.id)


    }

    @Test
    fun getFollowing_calls_fetchFollowingData_on_paginater_and_maps_result() {
        viewModel.getFollowing(username)

        viewModel.uiState.observeForever{}
        viewModel.uiState.wait()
        val data=viewModel.uiState.value as BaseViewModel.LoadingState.Success
        assertThat(data.data).isInstanceOf(BaseViewModel.PageDataType::class.java)
    }



    @Test
    fun getFollowers_calls_fetchFollowerData_on_paginater_and_maps_result_() {
        viewModel.getFollowers(username)
        viewModel.uiState.observeForever{}
        viewModel.uiState.wait()
        val data=viewModel.uiState.value as BaseViewModel.LoadingState.Success
        assertThat(data.data).isInstanceOf(BaseViewModel.PageDataType::class.java)

    }
}

private fun <T> MutableLiveData<T>.wait() {
    while(this.value is BaseViewModel.LoadingState.ShowLoading){

    }
}
