package com.diehard04.workllama.data.repository

import com.diehard04.workllama.data.network.ApiHelper

/**
 * Created by DieHard_04 on 14-08-2021.
 */
class NetworkCallRepository(private val apiHelper: ApiHelper) {

    suspend fun getContactList(i:String) = apiHelper.getContactList(i)

    suspend fun makeStar(userId:String) = apiHelper.updateMakeStar(userId)

    suspend fun makeUnStar(userId:String) = apiHelper.updateMakeUnStar(userId)

}