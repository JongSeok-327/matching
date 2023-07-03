package com.bae.matching.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bae.matching.model.entities.UserResponse
import com.bae.matching.model.network.RetrofitApi
import com.bae.matching.repository.MatchingRepository
import com.bae.matching.utils.Dlog
import com.bae.matching.utils.ListLoadState
import com.bae.matching.utils.SharedPreferencesHelper
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.NumberFormatException

class SearchViewModel(
    application: Application,
    private val repository: MatchingRepository
): BaseViewModel(application)
{
    private var prefHelper = SharedPreferencesHelper(getApplication())
    private val refreshTimeLimitMs: Long = 30000 // 30 Seconds

    private val _userList: MutableStateFlow<List<UserResponse>?> = MutableStateFlow(listOf())
    val userList = _userList.asStateFlow()
    private val _listStateLoading: MutableStateFlow<ListLoadState> = MutableStateFlow(ListLoadState.PREPARE)
    val listStateLoading = _listStateLoading.asStateFlow()

    fun getUserData() {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Dlog.e("Retrofit Exception : ${throwable.localizedMessage}")
            _listStateLoading.value = ListLoadState.ERROR
        }
        _listStateLoading.value = ListLoadState.LOADING

        launch(coroutineExceptionHandler) {
            val result = repository.fetchUserData()
            _userList.value = result
            _listStateLoading.value = ListLoadState.LOADED
        }
        prefHelper.saveUpdateTime(System.currentTimeMillis())
    }

    fun refresh() {
        val lastStoredTime = prefHelper.getUpdateTime()?:0L
        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - lastStoredTime

        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Dlog.e("Retrofit Exception : ${throwable.localizedMessage}")
            _listStateLoading.value = ListLoadState.ERROR
        }
        _listStateLoading.value = ListLoadState.LOADING

        launch(coroutineExceptionHandler) {
            // データ更新の場合は30秒に制限する。
            if (elapsedTime >= refreshTimeLimitMs) {
                Dlog.d("refresh data on remote")
                _userList.value = repository.fetchUserDataFromRemote()
                prefHelper.saveUpdateTime(System.currentTimeMillis())
            } else {
                Dlog.d("refresh data on database")
                _userList.value = repository.fetchUserDataFromDatabase()
            }

            _listStateLoading.value = ListLoadState.LOADED
        }
    }
}

class SearchViewModelFactory(
    private val application: Application,
    private val repository: MatchingRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}