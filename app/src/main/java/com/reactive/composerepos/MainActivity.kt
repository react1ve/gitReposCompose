package com.reactive.composerepos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.navigation.compose.rememberNavController
import com.reactive.composerepos.ui.navigation.Navigation
import com.reactive.composerepos.ui.theme.KotlinRepositoriesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KotlinRepositoriesTheme {
                Surface {
                    val navController = rememberNavController()
                    Navigation(navController)
                }
            }
        }
    }
}
