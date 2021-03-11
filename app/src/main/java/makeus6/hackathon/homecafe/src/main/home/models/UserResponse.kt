package com.softsquared.template.kotlin.src.main.home.models

import com.google.gson.annotations.SerializedName

data class UserResponse(
        @SerializedName("isSuccess") val isSuccess: Boolean,
        @SerializedName("code") val code: Int,
        @SerializedName("message") val message: String,
        @SerializedName("result") val result: ArrayList<ResultUser>
)
