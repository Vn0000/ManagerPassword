import com.vn000.android.passnotes.domain.PassItem
import com.vn000.android.passnotes.domain.PassListRepository

class GetPassItemUseCase (private val passListRepository: PassListRepository)  {
    suspend fun getPassItem(passItemId: Long): PassItem {
        return passListRepository.getPassItem(passItemId)
    }
}