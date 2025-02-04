package com.douglas.financial.bi

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

fun setupDi(androidApplication: Application)  = startKoin {
    androidLogger()
    androidContext(androidApplication)
    modules(listOf(
        daoModule(androidApplication),
        apiModule,
        useCaseModule,
        viewModelModule
    ))
}