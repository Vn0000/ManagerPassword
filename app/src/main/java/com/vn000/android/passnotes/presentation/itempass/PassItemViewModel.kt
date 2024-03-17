package com.vn000.android.passnotes.presentation.itempass

import GetPassItemUseCase
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vn000.android.passnotes.data.PassListRepositoryImpl
import com.vn000.android.passnotes.domain.AddPassItemUseCase
import com.vn000.android.passnotes.domain.PassItem
import kotlinx.coroutines.launch

class PassItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PassListRepositoryImpl(application)

    private val getPassItemUseCase = GetPassItemUseCase(repository)
    private val addPassItemUseCase = AddPassItemUseCase(repository)

    private val _isCloseScreen = MutableLiveData<Boolean>()
    val isCloseScreen: LiveData<Boolean>
        get() = _isCloseScreen

    private val _passItem = MutableLiveData<PassItem>()
    val passItem: LiveData<PassItem>
        get() = _passItem

    fun getPassItem(passItemId: Long) {
        viewModelScope.launch {
            val item = getPassItemUseCase.getPassItem(passItemId)
            _passItem.value = item
        }
    }

    fun addPassItem(
        password: String?,
        url: String?
    ) {
        viewModelScope.launch {
            val passItem = url?.let {
                PassItem(
                    password = password,
                    url = it
                )
            }
            if (passItem != null) {
                addPassItemUseCase.addPassItem(passItem)
                _isCloseScreen.value = true
            }

        }
    }
}