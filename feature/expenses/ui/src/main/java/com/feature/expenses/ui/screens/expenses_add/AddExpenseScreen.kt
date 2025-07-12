package com.feature.expenses.ui.screens.expenses_add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.core.ui.R
import com.core.ui.components.MyListItemOnlyText
import com.core.ui.components.MyTopAppBar

@Composable
fun AddExpenseScreen(
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit
) {

    AddExpenseScreenContent(
        onCancelClick = onCancelClick,
        onSaveClick = onSaveClick
    )
}

@Composable
fun AddExpenseScreenContent(
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    val stateMock = true
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MyTopAppBar(
            text = "Мои расходы",
            leadingIcon = R.drawable.cross,
            trailingIcon = R.drawable.check,
            onLeadingIconClick = {
                onCancelClick()
            },
            onTrailingIconClick = {
                onSaveClick()
            }
        )
        when (stateMock) {
            true -> {
                MyListItemOnlyText(
                    modifier = Modifier
                        .height(70.dp),
                    content = {
                        Text(text = "Счёт")
                    },
                    trailContent = {
                        Text(text = "Сбербанк")
                        Icon(
                            painter = painterResource(R.drawable.more_right),
                            contentDescription = null
                        )
                    }
                )
                HorizontalDivider()
                MyListItemOnlyText(
                    modifier = Modifier
                        .height(70.dp),
                    content = {
                        Text(text = "Статья")
                    },
                    trailContent = {
                        Text(text = "Ремонт")
                        Icon(
                            painter = painterResource(R.drawable.more_right),
                            contentDescription = null
                        )
                    }
                )
                HorizontalDivider()
                MyListItemOnlyText(
                    modifier = Modifier
                        .height(70.dp),
                    content = {
                        Text(text = "Сумма")
                    },
                    trailContent = {
                        Text(text = "25 700 ₽")
                    }
                )
                HorizontalDivider()
                MyListItemOnlyText(
                    modifier = Modifier
                        .height(70.dp),
                    content = {
                        Text(text = "Дата")
                    },
                    trailContent = {
                        Text(text = "25.02.2025")
                    }
                )
                HorizontalDivider()
                MyListItemOnlyText(
                    modifier = Modifier
                        .height(70.dp),
                    content = {
                        Text(text = "Время")
                    },
                    trailContent = {
                        Text(text = "23:41")
                    }
                )
                HorizontalDivider()
                MyListItemOnlyText(
                    modifier = Modifier
                        .height(70.dp),
                    content = {
                        Text(text = "Комментарий")
                    },
                    trailContent = {

                    }
                )
                HorizontalDivider()
            }
            false -> {
            }
        }
    }

}
