package com.diehard04.workllama.data.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




/**
 * Created by DieHard_04 on 14-08-2021.
 */
class Meta {
    @SerializedName("success")
    @Expose
    private var success: Boolean? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("pageNumber")
    @Expose
    private var pageNumber: Int? = null

    @SerializedName("pageSize")
    @Expose
    private var pageSize: Int? = null

    fun getSuccess(): Boolean? {
        return success
    }

    fun setSuccess(success: Boolean?) {
        this.success = success
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getPageNumber(): Int? {
        return pageNumber
    }

    fun setPageNumber(pageNumber: Int?) {
        this.pageNumber = pageNumber
    }

    fun getPageSize(): Int? {
        return pageSize
    }

    fun setPageSize(pageSize: Int?) {
        this.pageSize = pageSize
    }

}