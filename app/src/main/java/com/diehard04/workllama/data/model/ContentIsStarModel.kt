package com.diehard04.workllama.data.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




/**
 * Created by DieHard_04 on 18-08-2021.
 */
class ContentIsStarModel {
    @SerializedName("meta")
    @Expose
    private var meta: Meta? = null

    @SerializedName("content")
    @Expose
    private var content: Content? = null

    fun getMeta(): Meta? {
        return meta
    }

    fun setMeta(meta: Meta?) {
        this.meta = meta
    }

    fun getContent(): Content? {
        return content
    }

    fun setContent(content: Content?) {
        this.content = content
    }

}