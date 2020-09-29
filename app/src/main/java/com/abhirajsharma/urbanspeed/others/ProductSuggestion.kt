package com.abhirajsharma.urbanspeed.others

import android.os.Parcel
import android.os.Parcelable
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion

class ProductSuggestion : SearchSuggestion {

    private var productName: String
//    var isHistory = false

    constructor(suggestion: String) {
        productName = suggestion.toLowerCase()
    }

    constructor(source: Parcel) {
        productName = source.readString()!!
//        isHistory = source.readInt() != 0
    }

    override fun getBody(): String {
        return productName
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(productName)
//        dest.writeInt(if (isHistory) 1 else 0)
    }

    companion object {
        val CREATOR: Parcelable.Creator<ProductSuggestion> =
                object : Parcelable.Creator<ProductSuggestion> {
                    override fun createFromParcel(`in`: Parcel): ProductSuggestion? {
                        return ProductSuggestion(`in`)
                    }

                    override fun newArray(size: Int): Array<ProductSuggestion?> {
                        return arrayOfNulls(size)
                    }
                }
    }
}