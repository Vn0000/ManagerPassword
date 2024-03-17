package com.vn000.android.passnotes.data

import android.app.Application
import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.vn000.android.passnotes.domain.PassItem
import com.vn000.android.passnotes.domain.PassListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.net.URI

class PassListRepositoryImpl(
    application: Application
) : PassListRepository {

    private val coroutine = Dispatchers.IO

    private val passListDao = AppDatabase.getInstance(application).passListDao()
    private val mapper = PassListMapper()


    override suspend fun addPassItem(passItem: PassItem) = withContext(coroutine) {
        try {
            val document = Jsoup.connect(passItem.url).get()
            var icon: String? = null
            document.head().getElementsByTag("link").forEach {
                if (it.getElementsByAttribute("rel").attr("rel") == "shortcut icon") {
                    icon = it.getElementsByAttribute("rel").attr("href")
                }
            }
            if (icon != null) {
                if (icon?.contains("http") != true) {
                    icon = passItem.url + icon
                }
            }

            val title = document.title()
            passListDao.addPassItem(mapper.mapEntityToDbModel(passItem.copy(name = title,
            iconUrl = icon)))
        } catch (e: Throwable){
            passListDao.addPassItem(mapper.mapEntityToDbModel(passItem))
        }
    }

    override suspend fun deletePassItem(passItem: PassItem) = withContext(coroutine) {
        passListDao.deletePassItem(passItem.id)
    }


    override suspend fun getPassItem(passItemId: Long): PassItem = withContext(coroutine) {
        val dbModel = passListDao.getPassItem(passItemId)
        return@withContext mapper.mapDbModelToEntity(dbModel)
    }

    override fun getPassList(): LiveData<List<PassItem>> =
        MediatorLiveData<List<PassItem>>().apply {
            addSource(passListDao.getPassList()) {
                value =mapper.mapListDbModelToListEntity(it)
            }
        }
}