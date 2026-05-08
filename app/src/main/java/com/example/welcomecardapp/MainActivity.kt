/***************************************************************************************************
 * Program name :           MainActivity.kt
 * Description :            Simple welcome app in Kotlin
 * Author :                 Thierry Perroud
 * Creation date :          08.05.2026
 * Modified by :            Thierry Perroud
 * Modification date :      08.05.2026
 * Version :                1.3
 **************************************************************************************************/
package com.example.welcomecardapp

/***************************************************************************************************
 * Imports
 **************************************************************************************************/
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

/***************************************************************************************************
 * Main class
 **************************************************************************************************/
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WelcomeCardApp()
        }
    }
}

/***************************************************************************************************
 * Composables
 **************************************************************************************************/
/**
 * Main composable of the app, which manages the two screens, and contains the form.
 */
@Composable
fun WelcomeCardApp() {
    /*** VARIABLES ***/
    var userName by remember { mutableStateOf("") }
    var selectedMood by remember { mutableStateOf("Heureux") }
    var showCard by remember { mutableStateOf(false) }
    var currentScreen by remember { mutableStateOf("form") }

    /*** SCREEN SELECTOR LOGIC ***/
    when (currentScreen) {
        "form" -> {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                /*** WELCOME ***/
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Welcome Card App")

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Entrez votre prénom, choisissez votre humenr, puis générez une carte " +
                        "personnalisée.")

                Spacer(modifier = Modifier.height(16.dp))

                /*** USER INPUT ***/
                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = { Text("Votre prénom") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                /*** CHARACTER COUNTER ***/
                Text("Nombre de caractères : ${userName.length}")

                Spacer(modifier = Modifier.height(6.dp))

                /*** MOOD SELECTION ***/
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = selectedMood == "Heureux",
                        onClick = { selectedMood = "Heureux" },
                        label = { Text("Heureux") }
                    )

                    FilterChip(
                        selected = selectedMood == "Concentré",
                        onClick = { selectedMood = "Concentré" },
                        label = { Text("Concentré") }
                    )

                    FilterChip(
                        selected = selectedMood == "Fatigué",
                        onClick = { selectedMood = "Fatigué" },
                        label = { Text("Fatigué") }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                /*** CREATE CARD BUTTON ***/
                val context = LocalContext.current

                Button(
                    enabled = userName.isNotBlank(),
                    onClick = {
                        showCard = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Créer la carte")
                }

                Spacer(modifier = Modifier.height(16.dp))

                /*** CARD DISPLAY ***/
                if (showCard) {
                    WelcomeCard(
                        userName = userName,
                        selectedMood = selectedMood,
                        onOpenDetails = {
                            currentScreen = "details"
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    /*** RESET BUTTON ***/
                    Button(
                        onClick = {
                            userName = ""
                            selectedMood = "Heureux"
                            showCard = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Réinitialiser")
                    }
                }
            }
        }

        "details" -> {
            DetailsScreen(
                userName = userName,
                selectedMood = selectedMood,
                onBack = {
                    currentScreen = "form"
                }
            )
        }
    }


}

/**
 * Composable that displays a welcome card
 */
@Composable
fun WelcomeCard(
    userName: String,
    selectedMood: String,
    onOpenDetails: () -> Unit
) {
    /*** MOOD MESSAGE ***/
    val moodMessage = when (selectedMood) {
        "Heureux" -> "Continue à partager cette énergie positive."
        "Concentré" -> "Reste concentré et continue à progresser."
        "Fatigué" -> "Prends une courte pause puis reprends calmement."
        else -> "Bienvenue."
    }

    /*** CARD BODY ***/
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            /*** CARD CONTENT ***/
            Text(text = "Bonjour, $userName")
            Text(text = "Humeur : $selectedMood")
            Text(text = moodMessage)

            Spacer(modifier = Modifier.height(16.dp))

            /*** OPEN DETAILS BUTTON ***/
            Button(
                onClick = onOpenDetails
            ) {
                Text("Ouvrir les détails")
            }
        }
    }
}

/**
 * Composable that displays the details screen
 */
@Composable
fun DetailsScreen(
    userName: String,
    selectedMood: String,
    onBack: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            /*** DETAILS ***/
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Détails du profil")
            Text(text = "Prénom : $userName")
            Text(text = "Humeur : $selectedMood")

            Spacer(modifier = Modifier.height(8.dp))

            /*** GO BACK BUTTON ***/
            Button(onClick = onBack) {
                Text("Retour")
            }
        }
    }
}