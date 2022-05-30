package com.example.bankline_android.ui.statment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bankline_android.R
import com.example.bankline_android.data.remote.State
import com.example.bankline_android.databinding.ActivityBankStatmentBinding
import com.example.bankline_android.databinding.ActivityWelcomeBinding
import com.example.bankline_android.domain.Correntista
import com.example.bankline_android.domain.Movimentacao
import com.example.bankline_android.domain.TipoMovimentacao
import com.google.android.material.snackbar.Snackbar
import me.dio.bankline.ui.adapters.BankStatmentAdapter

class BankStatmentActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ACCOUNT_HOLDER = "com.example.bankline_android.ui.statment.EXTRA_ACCOUNT_HOLDER"
    }

    private val binding by lazy {
        ActivityBankStatmentBinding.inflate(layoutInflater)
    }

    private val accountHolders by lazy {
        intent.getParcelableExtra<Correntista>(EXTRA_ACCOUNT_HOLDER) ?: throw IllegalArgumentException()
    }

    private val viewModel by viewModels<BankStatmentViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        faz com que o recycler view receba algo
        binding.rvBankStatement.layoutManager = LinearLayoutManager(this)

        findBankStatement()

        binding.srlBankStatement.setOnRefreshListener { findBankStatement() }
    }

    private fun findBankStatement(){
        viewModel.findBankStatement(accountHolders.id).observe(this){ state ->
            when(state) {
                is State.Success -> {
                    binding.rvBankStatement.adapter = state.data?.let { BankStatmentAdapter(it) }
                    binding.srlBankStatement.isRefreshing = false
                }
                is State.Error -> {
                    state.message?.let { Snackbar.make(binding.rvBankStatement, it, Snackbar.LENGTH_LONG).show() }
                    binding.srlBankStatement.isRefreshing = false
                }
                is State.Wait -> {
                    binding.srlBankStatement.isRefreshing = true
                }
            }
        }

    }
}