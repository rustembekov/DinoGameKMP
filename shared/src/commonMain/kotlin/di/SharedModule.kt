package di

import org.koin.dsl.module
import viewmodel.GameViewModel

val viewModelModule = module {
    single { GameViewModel(get(), get(), get()) }
}
val sharedModule = viewModelModule + domainModule + dataModule

