package com.awcindia.myapp.model

import android.os.Parcel
import android.os.Parcelable

 class OrderDetails() : Parcelable{

    var userId : String? = null
    var userName: String? = null
    var foodNames : MutableList<String>? = null
    var foodImages : MutableList<String>? = null
    var foodPrices : MutableList<String>? = null
    var foodQuantities : MutableList<Int>? = null
    var address: String? = null
    var totalPrice: String? = null
    var phoneNumber : String? = null
    var orderAccepted : Boolean = false
    var paymentsReceived : Boolean = false
    var itemPushKey: String? = null
    var currentTime : Long = 0

    constructor(parcel: Parcel) : this() {
        userId = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        orderAccepted = parcel.readByte() != 0.toByte()
        paymentsReceived = parcel.readByte() != 0.toByte()
        itemPushKey = parcel.readString()
        currentTime = parcel.readLong()
    }

     constructor(
         userId: String,
         name: String,
         foodItemName: MutableList<String>,
         foodItemPrice: MutableList<String>,
         foodItemImage: MutableList<String>,
         foodItemQuantities: MutableList<Int>,
         address: String,
         totalAmount : String,
         phoneNumber: String,
         time: Long,
         itemPushKey: String?,
         oderAccepted: Boolean,
        paymentRecived: Boolean,
     ) : this(){
         this.userId = userId
         this.userName = name
         this.foodNames = foodItemName
         this.foodPrices = foodItemPrice
         this.foodImages = foodItemImage
         this.foodQuantities = foodItemQuantities
         this.address = address
         this.phoneNumber = phoneNumber
         this.totalPrice = totalAmount
         this.currentTime = time
         this.itemPushKey = itemPushKey
         this.orderAccepted = oderAccepted
         this.paymentsReceived = paymentRecived



     }

     override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(userName)
        parcel.writeString(address)
        parcel.writeString(totalPrice)
        parcel.writeString(phoneNumber)
        parcel.writeByte(if (orderAccepted) 1 else 0)
        parcel.writeByte(if (paymentsReceived) 1 else 0)
        parcel.writeString(itemPushKey)
        parcel.writeLong(currentTime)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }


}
