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

    fun insertar() {
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

    private fun Limpiar() {
        Deudor.toString()?:null
        Concepto.toString()?:null
        Monto.toString()?:null
    }
}