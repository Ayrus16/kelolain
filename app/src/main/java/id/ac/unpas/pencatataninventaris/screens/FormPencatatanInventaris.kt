package id.ac.unpas.pencatataninventaris.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.benasher44.uuid.uuid4
import id.ac.unpas.pencatataninventaris.model.Inventaris
import id.ac.unpas.pencatataninventaris.persistences.inventarisDao
import id.ac.unpas.pencatataninventaris.ui.theme.Purple700
import id.ac.unpas.pencatataninventaris.ui.theme.Teal200
import kotlinx.coroutines.launch

@Composable
fun FormPencatatanBarang(inventarisDao: inventarisDao) {
    val nama = remember { mutableStateOf(TextFieldValue("")) }
    val jumlah = remember { mutableStateOf(TextFieldValue("")) }
    val jenis = remember { mutableStateOf(TextFieldValue("")) }
    val keterangan = remember { mutableStateOf(TextFieldValue("")) }
    val scope = rememberCoroutineScope()
    val dialogState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            label = { Text(text = "Nama Barang") },
            value = nama.value,
            onValueChange = {
                nama.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            placeholder = { Text(text = "ex : Apple Macbook Air M1 2020") }
        )
        OutlinedTextField(
            label = { Text(text = "Jumlah Barang") },
            value = jumlah.value,
            onValueChange = {
                jumlah.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = { Text(text = "5") }
        )
        OutlinedTextField(
            label = { Text(text = "Jenis") },
            value = jenis.value,
            onValueChange = {
                jenis.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            placeholder = { Text(text = "ex : Laptop") }
        )
        OutlinedTextField(
            label = { Text(text = "Keterangan") },
            value = keterangan.value,
            onValueChange = {
                keterangan.value = it
            },
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            placeholder = { Text(text = "ex : Rusak") }
        )

        val loginButtonColors = ButtonDefaults.buttonColors(
            backgroundColor = Purple700,
            contentColor = Teal200
        )
        val resetButtonColors = ButtonDefaults.buttonColors(
            backgroundColor = Teal200,
            contentColor = Purple700
        )
        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            Button(
                modifier = Modifier.weight(5f), onClick = {
                    if (nama.value.text.isNotBlank() && jumlah.value.text.isNotBlank() &&
                        jenis.value.text.isNotBlank() && keterangan.value.text.isNotBlank()
                    ) {
                        val id = uuid4().toString()
                        val item = Inventaris(
                            id,
                            nama.value.text,
                            jumlah.value.text,
                            jenis.value.text,
                            keterangan.value.text
                        )
                        scope.launch {
                            inventarisDao.insertAll(item)
                        }
                        nama.value = TextFieldValue("")
                        jumlah.value = TextFieldValue("")
                        jenis.value = TextFieldValue("")
                        keterangan.value = TextFieldValue("")
                    } else {
                        // Show error dialog
                        dialogState.value = true
                    }
                }, colors = loginButtonColors
            ) {
                Text(
                    text = "Simpan",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp
                    ), modifier = Modifier.padding(8.dp)
                )
            }
            Button(
                modifier = Modifier.weight(5f), onClick = {
                    nama.value = TextFieldValue("")
                    jumlah.value = TextFieldValue("")
                    jenis.value = TextFieldValue("")
                    keterangan.value = TextFieldValue("")
                }, colors = resetButtonColors
            ) {
                Text(
                    text = "Reset",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 18.sp
                    ), modifier = Modifier.padding(8.dp)
                )
            }
        }
    }

    // Error dialog
    if (dialogState.value) {
        AlertDialog(
            onDismissRequest = {
                // Dismiss the dialog when user taps outside the dialog or presses the back button
                dialogState.value = false
            },
            title = {
                Text("Peringatan")
            },
            text = {
                Text("Mohon lengkapi form")
            },
            confirmButton = {
                Button(
                    onClick = {
                        dialogState.value = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}
