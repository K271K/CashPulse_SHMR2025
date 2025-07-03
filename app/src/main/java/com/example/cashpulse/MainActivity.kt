package com.example.cashpulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.cashpulse.navigation.DefaultNavigator
import com.example.cashpulse.navigation.MainNavigation
import com.example.cashpulse.ui.theme.CashPulseTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    /**
     * DefaultNavigator используется для организации навигации между модулями
     * Он в себе в качестве переменных содержит все фичи, на которые может быть произведена навигация
     * defaultNavigator предоставляется через DI
     */
    @Inject
    lateinit var defaultNavigator: DefaultNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CashPulseTheme {
                MainNavigation(
                    defaultNavigator = defaultNavigator
                )
            }
        }
    }
}



