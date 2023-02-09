package com.ucne.proj_1erparcial_ap2.ui.Prestamo

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ucne.proj_1erparcial_ap2.data.local.entity.PrestamoEntity

@Composable
fun PrestamoScreen(viewModel: PrestamoViewModel = hiltViewModel()) {

    Column(Modifier.fillMaxSize()) {
        PrestamoBody(viewModel)

        Spacer(modifier = Modifier.padding(14.dp))
        val uiState by viewModel.uiState.collectAsState()
        PrestamoListScreen(uiState.prestamoList)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PrestamoBody(
    viewModel: PrestamoViewModel
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            value = viewModel.Deudor,
            onValueChange = {  viewModel.Deudor = it },
            label = { Text("Deudor") }
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            value = viewModel.Concepto,
            onValueChange = { viewModel.Concepto = it },
            label = { Text("Concepto") }
        )

        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            value = viewModel.Monto,
            onValueChange = { viewModel.Monto = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("Monto") }
        )

        ExtendedFloatingActionButton(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.End),
            text = { Text("") },
            icon = { Icon(imageVector = Icons.Filled.Save, contentDescription = "Save") },
            onClick = { viewModel.insertar() }
        )
    }
}
@Composable
private fun PrestamoListScreen(prestamoList: List<PrestamoEntity>) {
    LazyColumn {
        items(prestamoList) { prestamo ->
            PrestamoRow(prestamo)
        }
    }
}
@Composable
private fun PrestamoRow(prestamo: PrestamoEntity) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = prestamo.Deudor,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(3f)
            )
            Text(
                text = prestamo.Concepto,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.weight(3f)
            )
            Text(
                String.format("%.2f", prestamo.Monto),
                textAlign = TextAlign.End,
                modifier = Modifier.weight(2f)
            )
        }
        Divider(Modifier.fillMaxWidth())
    }
}