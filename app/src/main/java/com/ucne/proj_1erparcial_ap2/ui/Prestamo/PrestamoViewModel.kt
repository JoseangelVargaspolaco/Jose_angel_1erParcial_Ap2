package com.ucne.proj_1erparcial_ap2.ui.Prestamo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.proj_1erparcial_ap2.data.local.entity.PrestamoEntity
import com.ucne.proj_1erparcial_ap2.data.repository.PrestamoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PrestamoUiState(
    val prestamoList: List<PrestamoEntity> = emptyList()
)

@HiltViewModel
class PrestamoViewModel @Inject constructor(
    private val prestamoRepository: PrestamoRepository
) : ViewModel() {
    var Deudor by mutableStateOf("")
    var Concepto by mutableStateOf("")
    var Monto by mutableStateOf("")

    var conceptoError by mutableStateOf("")
    var montoError by mutableStateOf("")
    var deudorError by mutableStateOf("")

    var uiState = MutableStateFlow(PrestamoUiState())
        private set
    init {
        getListPrestamo()
    }
    fun getListPrestamo() {
        viewModelScope.launch(Dispatchers.IO) {
            prestamoRepository.getList().collect{lista ->
                uiState.update {
                    it.copy(prestamoList = lista)
                }
            }
        }
    }

    fun onDeudorChanged(Deudor: String) {
        this.Deudor = Deudor
        Validation()
    }
    fun onMontoChanged(Monto: String) {
        this.Monto = Monto
        Validation()
    }

    fun onConceptoChanged(Concepto: String) {
        this.Concepto = Concepto
        Validation()
    }

    fun insertar() {
        if (Validation())
            return

        val prestamo = PrestamoEntity(
            Deudor = Deudor,
            Concepto = Concepto,
            Monto = Monto.toDoubleOrNull()?:0.0
        )

        viewModelScope.launch(Dispatchers.IO) {
            prestamoRepository.insert(prestamo)
            Limpiar()
        }
    }

    fun eliminar() {
        if (Validation())
            return

        val prestamo = PrestamoEntity(
            Deudor = Deudor,
            Concepto = Concepto,
            Monto = Monto.toDoubleOrNull()?:0.0
        )

        viewModelScope.launch(Dispatchers.IO) {
            prestamoRepository.delete(prestamo)
            Limpiar()
        }
    }

    private fun Validation(): Boolean {

        var hayError = false

        deudorError = ""

        if (Deudor.isBlank()) {
            deudorError = "Debe indicar el deudor"
            hayError = true
        }else{
            hayError
        }

        conceptoError = ""

        if (Concepto.isBlank()) {
            conceptoError = "Debe indicar el concepto"
            hayError = true
        }else{
            hayError
        }

        montoError = ""

        if ((Monto.toDoubleOrNull() ?: 0.0) <= 0.0) {
            montoError = "Debe indicar un monto mayor que cero"
            hayError = true
        }else{
            hayError
        }

        return hayError
    }

    fun nuevo(){
        Deudor = ""
        Concepto = ""
        Monto = ""
    }

    private fun Limpiar() {
        Deudor = ""
        Concepto = ""
        Monto = ""
    }
}