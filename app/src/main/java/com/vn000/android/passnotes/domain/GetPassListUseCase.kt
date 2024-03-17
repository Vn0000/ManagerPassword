import androidx.lifecycle.LiveData
import com.vn000.android.passnotes.domain.PassItem
import com.vn000.android.passnotes.domain.PassListRepository


class GetPassListUseCase (private val passListRepository: PassListRepository) {
    fun getPassList(): LiveData<List<PassItem>> {
        return passListRepository.getPassList()
    }
}