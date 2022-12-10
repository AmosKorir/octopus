package com.octopus.baseclasses.BaseViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.octopus.domain.models.Follower
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

open class BaseViewModel: ViewModel() {
    private val imHandleErrorLiveData = MutableLiveData<Throwable>()
    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    val uiState = MutableLiveData<LoadingState>(LoadingState.Default)

    sealed class LoadingState {
        object Default : LoadingState()
        object ShowLoading : LoadingState()
        data class Error(val message: String) : LoadingState()
        data class Success(val data: Any?) : LoadingState()
        data class Followers(val data: List<Follower>) : LoadingState()
    }


    fun <T> Single<T>.background(): Single<T> = this
        .subscribeOn(Schedulers.io())

    fun <T> Observable<T>.background():Observable<T> = this
        .subscribeOn(Schedulers.io())


    fun <T> Observable<T>.resultSubscribe(success: () -> Unit = {}) {
        uiState.value = LoadingState.ShowLoading

        val disposable = this.observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                success()
                uiState.value = LoadingState.Success(it as T)
            },
                {
                    uiState.value = LoadingState.Error(it.message ?: "")
                })
        compositeDisposable.add(disposable)
    }

    fun <T> Single<T>.resultSubscribe(success: () -> Unit = {}) {
        uiState.value = LoadingState.ShowLoading

        val disposable = this.observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                success()
                uiState.value = LoadingState.Success(it as T)
            },
                {
                    uiState.value = LoadingState.Error(it.message ?: "")
                })
        compositeDisposable.add(disposable)
    }
    fun defaultState() {
        uiState.value = LoadingState.Default
    }

//    fun getErrorMessage(throwable: Throwable): String {
//        return ErrorMessageHandler.getErrorMessage(throwable)
//    }


}