package com.bae.matching.viewmodels

import android.app.Application
import com.bae.matching.model.entities.UserResponse
import com.bae.matching.model.network.RetrofitApi
import com.bae.matching.utils.Dlog
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(application: Application): BaseViewModel(application)
{
    private val _userList: MutableStateFlow<List<UserResponse>?> = MutableStateFlow(listOf())
    val userList = _userList.asStateFlow()
    private val _listStateLoading: MutableStateFlow<ListLoadState> = MutableStateFlow(ListLoadState.PREPARE)
    val listStateLoading = _listStateLoading.asStateFlow()

    fun getUserData() {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Dlog.e("Retrofit Exception : ${throwable.localizedMessage}")
            _listStateLoading.value = ListLoadState.ERROR
        }

        launch(coroutineExceptionHandler) {
            val api = RetrofitApi.userApiService
            val response = api.getUsers()
            _listStateLoading.value = ListLoadState.LOADING

            if (response.isSuccessful) {
                // Success
                Dlog.d("API is Success : ${response.body()?.size}")
                _userList.value = response.body()
                _listStateLoading.value = ListLoadState.LOADED
            } else {
                // Failed
                Dlog.d("API is Error : ${response.errorBody()?.toString()}")
                _userList.value = listOf()
                _listStateLoading.value = ListLoadState.LOADED
            }
        }
    }
}

enum class ListLoadState {
    PREPARE,
    LOADING,
    LOADED,
    ERROR
}