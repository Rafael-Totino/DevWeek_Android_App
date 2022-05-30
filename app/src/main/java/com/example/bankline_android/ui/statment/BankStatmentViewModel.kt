package com.example.bankline_android.ui.statment

import androidx.lifecycle.ViewModel
import com.example.bankline_android.data.BanklineRepository

class BankStatmentViewModel : ViewModel() {

    fun findBankStatement(accountHolderId: Int) =
        BanklineRepository.findBankStatment(accountHolderId)

}