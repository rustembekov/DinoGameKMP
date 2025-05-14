package di

import JumpUseCase
import ResetGameUseCase
import UpdateGameUseCase
import org.koin.dsl.module

val domainModule = module {
    single { UpdateGameUseCase(get()) }
    single { ResetGameUseCase() }
    single { JumpUseCase() }
}