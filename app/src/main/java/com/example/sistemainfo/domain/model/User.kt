package com.example.sistemainfo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sistemainfo.feature.commons.Constants.Companion.USER_DB

@Entity(tableName = USER_DB)
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var code: String,
    var name: String,
    var cpf: String,
    var address: String,
    var phone: String
)