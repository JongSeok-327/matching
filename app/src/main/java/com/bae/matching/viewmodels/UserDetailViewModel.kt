package com.bae.matching.viewmodels

import android.app.Application
import com.bae.matching.model.entities.UserDetailResponse
import com.bae.matching.model.network.RetrofitApi
import com.bae.matching.utils.Dlog
import com.bae.matching.utils.ListLoadState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserDetailViewModel(application: Application): BaseViewModel(application)
{
    private val _userInfo: MutableStateFlow<UserDetailResponse?> = MutableStateFlow(null)
    val userInfo = _userInfo.asStateFlow()
    private val _listStateLoading: MutableStateFlow<ListLoadState> = MutableStateFlow(ListLoadState.PREPARE)
    val listStateLoading = _listStateLoading.asStateFlow()

    fun getUserDetailData(userId: Int) {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Dlog.e("Retrofit Exception : ${throwable.localizedMessage}")
            _listStateLoading.value = ListLoadState.ERROR
        }

        launch(coroutineExceptionHandler) {
            val api = RetrofitApi.userApiService
            val response = api.getUserDetail(userId)
            _listStateLoading.value = ListLoadState.LOADING

            if (response.isSuccessful) {
                // Success
                Dlog.d("API is Success : ${response.body()}")
                _userInfo.value = response.body()
                _listStateLoading.value = ListLoadState.LOADED
            } else {
                // Failed
                Dlog.d("API is Error : ${response.errorBody()?.toString()}")
                _userInfo.value = null
                _listStateLoading.value = ListLoadState.LOADED
            }
        }
    }
}

