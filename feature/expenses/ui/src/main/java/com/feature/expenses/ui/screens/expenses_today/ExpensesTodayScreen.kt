package com.feature.expenses.ui.screens.expenses_today

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.core.ui.R
import com.core.ui.components.MyErrorBox
import com.core.ui.components.MyFloatingActionButton
import com.core.ui.components.MyListItemOnlyText
import com.core.ui.components.MyListItemWithLeadIcon
import com.core.ui.components.MyTopAppBar
import com.core.ui.theme.GreenLight
import com.core.ui.theme.GreenPrimary
import kotlin.system.exitProcess

@Composable
fun ExpensesTodayScreen(
    viewModelFactory: ExpensesTodayViewModelFactory,
    onGoToHistoryClick: () -> Unit,
    onGoToExpenseDetailScreen: (Int) -> Unit,
    onGoToAddExpenseClick: () -> Unit,
) {
    val viewModel: ExpensesTodayViewModel = viewModel(factory = viewModelFactory)
    val uiState by viewModel.expensesTodayScreenState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var lastBackPressTime by remember { mutableLongStateOf(0L) }

    //Если нажать назад два раза в течении 3 сек, приложение закроется
    BackHandler {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastBackPressTime < 3000) {
            exitProcess(0)
        } else {
            lastBackPressTime = currentTime
            Toast.makeText(
                context,
                "Для выхода нажмите еще раз",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    ExpensesTodayScreenContent(
        uiState = uiState,
        onGoToHistoryClick = onGoToHistoryClick,
        onGoToExpenseDetailScreen = onGoToExpenseDetailScreen,
        onGoToAddExpenseClick = onGoToAddExpenseClick,
    )
}

@Composable
fun ExpensesTodayScreenContent(
    uiState: ExpensesTodayScreenState,
    onGoToHistoryClick: () -> Unit,
    onGoToExpenseDetailScreen: (Int) -> Unit,
    onGoToAddExpenseClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            MyTopAppBar(
                text = "Расходы сегодня",
                trailingIcon = R.drawable.history,
                onTrailingIconClick = {
                    onGoToHistoryClick.invoke()
                }
            )
            when (uiState) {
                is ExpensesTodayScreenState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is ExpensesTodayScreenState.Error -> {
                    MyErrorBox(
                        modifier = Modifier
                            .fillMaxSize(),
                        message = uiState.message
                    )
                }

                is ExpensesTodayScreenState.ErrorResource -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "error")
                    }
                }

                is ExpensesTodayScreenState.Loaded -> {
                    MyListItemOnlyText(
                        modifier = Modifier
                            .height(56.dp)
                            .background(GreenLight),
                        content = {
                            Text(text = "Всего")
                        },
                        trailContent = {
                            Text(text = "${uiState.data.totalAmount} ${uiState.data.currency}")
                        }
                    )
                    HorizontalDivider()
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                    ) {
                        items(uiState.data.expenses, key = { it.id }) {
                            MyListItemWithLeadIcon(
                                modifier = Modifier
                                    .height(70.dp),
                                icon = it.icon,
                                iconBg = GreenLight,
                                content = {
                                    Column {
                                        Text(
                                            text = it.category
                                        )
                                        if (it.comment.isNotEmpty()) {
                                            Text(
                                                text = it.comment,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                },
                                trailContent = {
                                    Text(text = "${it.amount} ${it.currency}")
                                    Icon(
                                        painter = painterResource(R.drawable.more_right),
                                        contentDescription = null,
                                    )
                                },
                                onClick = {
                                    onGoToExpenseDetailScreen.invoke(it.id)
                                }
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
        MyFloatingActionButton(
            onClick = onGoToAddExpenseClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
        )
    }
}