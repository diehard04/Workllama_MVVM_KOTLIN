package com.diehard04.workllama.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.diehard04.workllama.base.Resource
import com.diehard04.workllama.data.model.ContentIsStarModel
import com.diehard04.workllama.data.model.Meta
import com.diehard04.workllama.data.repository.NetworkCallRepository
import kotlinx.coroutines.Dispatchers

/**
 * Created by DieHard_04 on 13-08-2021.
 */
class ContactListViewModel(private val repository: NetworkCallRepository) : ViewModel() {

    fun getContactList(i: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val data = repository.getContactList(i)
            val meta: Meta? = data.getMeta()
            if (meta != null) {
                if (meta.getSuccess() == true) {
                    emit(Resource.success(data = data.getContent()))
                } else{
                    emit(Resource.error(data = null, message = meta.getMessage() ?: "Error Occurred!"))
                }
            }

        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun updateIsStart(userId: String, starType: Int) = liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            val data:ContentIsStarModel
            when (starType) {
                0 -> {
                    data = repository.makeStar(userId)
                }
                else -> {
                    data = repository.makeUnStar(userId)
                }
            }
            val meta: Meta? = data.getMeta()
            if (meta != null) {
                if (meta.getSuccess() == true) {
                    emit(Resource.success(data = data.getContent()))
                } else{
                    emit(Resource.error(data = null, message = meta.getMessage() ?: "Error Occurred!"))
                }
            }
        } catch (exception:Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}