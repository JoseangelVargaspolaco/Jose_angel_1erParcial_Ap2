package com.ucne.proj_1erparcial_ap2.ui.Prestamo

import androidx.lifecycle.ViewModel
import com.ucne.proj_1erparcial_ap2.data.local.entity.PrestamoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class PrestamoUiState(
    val PrestamoList: List<PrestamoEntity> = emptyList()
)

@HiltViewModel
class PrestamoViewModel @Inject constructor(
) : ViewModel() {
}