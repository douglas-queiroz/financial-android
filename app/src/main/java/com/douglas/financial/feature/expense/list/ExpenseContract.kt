package com.douglas.financial.feature.expense.list

class ExpenseContract {
    data class State(
        val expenses: List<ExpenseItem> = emptyList(),
        val total: String = "0,00",
        val editExpenseId: String? = null,
        val showEditDialog: Boolean = false,
        val expenseToBeDeleted: ExpenseItem? = null
    )

    sealed class Events {
        data object Sync: Events()
        data class OnMarkPaid(val expense: ExpenseItem): Events()
        data class OnMarkUnpaid(val expense: ExpenseItem): Events()
        data class OnDelete(val expense: ExpenseItem): Events()
        data class Edit(val expense: ExpenseItem): Events()
        data object OnDismiss: Events()
        data object AddExpense: Events()
        data class OnDeleteExpenseConfirmed(val expense: ExpenseItem): Events()
        data object OnDeleteDialogClose: Events()
    }
}