package com.abhirajsharma.urbanspeed.model

data class NewAddressModel(
        var name: String? = null,
        var mobile: String? = null,
        var altMobile: String? = "",
        var pinCode: String? = null,
        var address: String? = null,
        var city: String? = null,
        var state: String? = null,
        var landmark: String? = "",
        var addressType: String? = null,
)