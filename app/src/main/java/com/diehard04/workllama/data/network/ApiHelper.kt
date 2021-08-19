package com.diehard04.workllama.data.network

/**
 * Created by DieHard_04 on 14-08-2021.
 */
class ApiHelper(private val apiService: ApiService) {

    suspend fun getContactList(i:String) = apiService.getUsers(i)

    suspend fun updateMakeStar(userId:String) = apiService.updateMakeStar(userId)

    suspend fun updateMakeUnStar(userId:String) = apiService.updateMakeUnStar(userId)

}