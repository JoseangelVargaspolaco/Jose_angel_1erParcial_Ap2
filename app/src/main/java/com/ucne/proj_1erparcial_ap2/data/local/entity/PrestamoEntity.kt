package com.ucne.proj_1erparcial_ap2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Prestamo")
data class PrestamoEntity (
    @PrimaryKey(autoGenerate = true)
    val prestamoid : Int?=null,
    val Deudor : String,
    val Concepto : String,
    val Monto : Double,
)