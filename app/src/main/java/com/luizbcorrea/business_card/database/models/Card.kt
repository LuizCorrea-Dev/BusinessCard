package com.luizbcorrea.business_card.database.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

// INFORMANDO QUE SE TRATA DE UMA TABELA
@Entity (tableName = "cards_table")

@Parcelize
data class Card(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var name: String,
    var company: String,
    var email: String,
    var phone: String,
    var colorSelected: String,
    var colorTxt:String,
): Parcelable
