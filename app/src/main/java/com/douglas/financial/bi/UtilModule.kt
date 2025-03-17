package com.douglas.financial.bi

import com.douglas.financial.util.DateUtil
import com.douglas.financial.util.UuidUtil
import org.koin.dsl.module

val utilModule = module {
    factory {
        DateUtil()
    }

    factory {
        UuidUtil()
    }
}