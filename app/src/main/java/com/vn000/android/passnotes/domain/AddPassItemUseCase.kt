package com.vn000.android.passnotes.domain


class AddPassItemUseCase(private val passListRepository: PassListRepository) {
    suspend fun addPassItem(passItem: PassItem) {
        passListRepository.addPassItem(passItem)
    }
}