package com.krashkrosh748199.shoption.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val user_id: String? = "",
    val name: String? = "",
    val mobileNumber: String? = "",

    val address: String? = "",
    val pinCode: String? = "",
    val additionalNote: String? = "",

    val type: String? = "",
    val otherDetails: String? = "",
    var id: String? = "",
) : Parcelable