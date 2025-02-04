package com.douglas.financial

import android.app.Application
import com.douglas.financial.bi.setupDi

class FinancialApp: Application() {

    override fun onCreate() {
        super.onCreate()
        setupDi(this)
    }
}