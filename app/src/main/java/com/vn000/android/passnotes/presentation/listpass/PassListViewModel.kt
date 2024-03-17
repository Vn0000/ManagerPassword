package com.vn000.android.passnotes.presentation.listpass

import DeletePassItemUseCase
import GetPassListUseCase
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.vn000.android.passnotes.data.PassListRepositoryImpl
import com.vn000.android.passnotes.domain.PassItem
import kotlinx.coroutines.launch

class PassListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PassListRepositoryImpl(application)

    private val getPassListUseCase = GetPassListUseCase(repository)
    private val deletePassItemUseCase = DeletePassItemUseCase(repository)

    val passList = getPassListUseCase.getPassList()



    fun deletePassItem(passItem: PassItem) {
        viewModelScope.launch { deletePassItemUseCase.deletePassItem(passItem) }
    }
}