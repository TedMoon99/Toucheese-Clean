package com.tedmoon99.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TestConcept(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "name")  val name: String,
    @ColumnInfo(name = "imageData") val imageData: ByteArray?,
)