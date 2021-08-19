package com.diehard04.workllama.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**
 * Created by DieHard_04 on 14-08-2021.
 */
class Content():Serializable {

    @SerializedName("id")
    @Expose
    private var id: Int? = null

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("phone")
    @Expose
    private var phone: String? = null

    @SerializedName("thumbnail")
    @Expose
    private var thumbnail: String? = null

    @SerializedName("email")
    @Expose
    private var email: String? = null

    @SerializedName("isStarred")
    @Expose
    private var isStarred: Int? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()
        phone = parcel.readString()
        thumbnail = parcel.readString()
        email = parcel.readString()
        isStarred = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getPhone(): String? {
        return phone
    }

    fun setPhone(phone: String?) {
        this.phone = phone
    }

    fun getThumbnail(): String? {
        return thumbnail
    }

    fun setThumbnail(thumbnail: String?) {
        this.thumbnail = thumbnail
    }

    fun getEmail(): String? {
        return email
    }

    fun setEmail(email: String?) {
        this.email = email
    }

    fun getIsStarred(): Int? {
        return isStarred
    }

    fun setIsStarred(isStarred: Int?) {
        this.isStarred = isStarred
    }
}