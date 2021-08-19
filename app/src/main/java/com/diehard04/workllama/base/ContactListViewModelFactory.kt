package com.diehard04.workllama.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.diehard04.workllama.data.network.ApiHelper
import com.diehard04.workllama.data.repository.NetworkCallRepository
import com.diehard04.workllama.viewmodel.ContactListViewModel

/**
 * Created by DieHard_04 on 14-08-2021.
 */
class ContactListViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactListViewModel::class.java)) {
            return ContactListViewModel(NetworkCallRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}