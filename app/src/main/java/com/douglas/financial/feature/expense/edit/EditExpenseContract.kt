package com.douglas.financial.feature.expense.edit

class EditExpenseContract {
    data class State(
        val name: String = "",
        val nameError: String? = null,
        val value: String = "",
        val valueError: String? = null,
        val dueDate: String = "",
        val dueDateError: String? = null
    )

    sealed class Event {
        data class OnNameChange(val name: String): Event()
        data class OnValueChange(val value: String): Event()
        data class OnDueDateChange(val dueDate: String): Event()
        data object Cancel: Event()
        data object Save: Event()
    }
}