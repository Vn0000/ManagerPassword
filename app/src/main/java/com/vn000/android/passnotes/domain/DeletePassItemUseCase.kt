import com.vn000.android.passnotes.domain.PassItem
import com.vn000.android.passnotes.domain.PassListRepository

class DeletePassItemUseCase (private val passListRepository: PassListRepository) {
    suspend fun deletePassItem (passItem: PassItem) {
        passListRepository.deletePassItem(passItem)
    }
}