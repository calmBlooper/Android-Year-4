package com.example.levchukapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TblPrefs")
class Word(
    @PrimaryKey val prefKey: String,
    @ColumnInfo(name = "prefValue") val prefValue: String

)
