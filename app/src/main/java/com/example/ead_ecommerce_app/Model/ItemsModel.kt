package com.example.ead_ecommerce_app.Model

import android.os.Parcel
import android.os.Parcelable

data class ItemsModel(
    val id: String,
    val product_Name: String,
    val product_Category: String,
    val vendor_Name: String,
    val vendor_Id: String,
    val image: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val visibility: Boolean
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(product_Name)
        parcel.writeString(product_Category)
        parcel.writeString(vendor_Name)
        parcel.writeString(vendor_Id)
        parcel.writeString(image)
        parcel.writeString(description)
        parcel.writeDouble(price)
        parcel.writeInt(stock)
        parcel.writeByte(if (visibility) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemsModel> {
        override fun createFromParcel(parcel: Parcel): ItemsModel {
            return ItemsModel(parcel)
        }

        override fun newArray(size: Int): Array<ItemsModel?> {
            return arrayOfNulls(size)
        }
    }
}
