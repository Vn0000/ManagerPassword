package com.vn000.android.passnotes.data

import com.vn000.android.passnotes.domain.PassItem


class PassListMapper {

    fun mapEntityToDbModel(passItem: PassItem) = PassItemDbModel(
        id = passItem.id,
        name = passItem.name,
        password = passItem.password,
        url = passItem.url,
        iconUrl = passItem.iconUrl
    )

    fun mapDbModelToEntity(passItemDbModel: PassItemDbModel) = PassItem(
        id = passItemDbModel.id,
        name = passItemDbModel.name,
        password = passItemDbModel.password,
        url = passItemDbModel.url,
        iconUrl = passItemDbModel.iconUrl
    )

    fun mapListDbModelToListEntity(list: List<PassItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}