package com.feature.expenses.ui.screens.expenses_expense_deatils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.core.ui.R
import com.core.ui.components.MyTopAppBar

@Composable
fun ExpensesExpenseDetailScreen(
    expenseId: Int,
    onCancelClick: ()-> Unit,
    onDoneClick: ()-> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MyTopAppBar(
            text = "Мои расходы",
            leadingIcon = R.drawable.cross,
            onLeadingIconClick = {
                onCancelClick.invoke()
            },
            trailingIcon = R.drawable.check,
            onTrailingIconClick = {
                onDoneClick.invoke()
            }
        )
        Text(text = "ID транзакции = $expenseId")
    }
}