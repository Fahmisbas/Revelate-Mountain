package com.revelatestudio.revelatemountain.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.revelatestudio.revelatemountain.data.remote.HitsItem
import com.revelatestudio.revelatemountain.data.repository.MainRepository
import com.revelatestudio.revelatemountain.util.DisptacherProvider
import com.revelatestudio.revelatemountain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel@Inject constructor(
    private val repository: MainRepository,
    private val dispatcher : DisptacherProvider
) : ViewModel() {

//    private var _popularMountainPictures = MutableLiveData<RetrievalEvent>(RetrievalEvent.Empty)
//    val popularMountainPictures : LiveData<RetrievalEvent> get() = _popularMountainPictures
//
//    private var _latestMountainPictures = MutableLiveData<RetrievalEvent>(RetrievalEvent.Empty)
//    val latestMountainPictures : LiveData<RetrievalEvent> get() = _popularMountainPictures


    val pouplarMountainPictures = repository.getPopularMountainPictures().cachedIn(viewModelScope)

    val latestMountainPictures = repository.getLatestMountainPictures().cachedIn(viewModelScope)



    sealed class RetrievalEvent {
        class Success(val mountainPictures: List<HitsItem?>): RetrievalEvent()
        class Failure(val errorText: String): RetrievalEvent()
        object Loading : RetrievalEvent()
        object Empty : RetrievalEvent()
    }

}