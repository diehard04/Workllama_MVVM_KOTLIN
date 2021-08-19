package com.diehard04.workllama.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by DieHard_04 on 14-08-2021.
 */
class ContactModel {
    @SerializedName("meta")
    @Expose
    private var meta: Meta? = null

    @SerializedName("content")
    @Expose
    private var content: List<Content?>? = null

    fun getMeta(): Meta? {
        return meta
    }

    fun setMeta(meta: Meta?) {
        this.meta = meta
    }

    fun getContent(): List<Content?>? {
        return content
    }

    fun setContent(content: List<Content?>?) {
        this.content = content
    }
}