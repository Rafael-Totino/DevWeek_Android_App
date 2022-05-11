package com.example.bankline_android.data

import androidx.lifecycle.liveData
import com.example.bankline_android.data.remote.BanklineApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object BanklineRepository {
    private val restApi = Retrofit.Builder()
        .baseUrl("http://localhost:8081/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BanklineApi::class.java)

    fun findBankStatment(accountHolder: Int) = liveData<> {
        emit("")
    }
}