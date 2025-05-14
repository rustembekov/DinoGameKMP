package di

import GameEngineImpl
import GameEngineRepository
import org.koin.dsl.module

val dataModule = module {
    single<GameEngineRepository> { GameEngineImpl() }
}
