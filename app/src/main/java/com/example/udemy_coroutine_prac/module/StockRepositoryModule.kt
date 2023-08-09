package com.example.udemy_coroutine_prac.module

import com.example.udemy_coroutine_prac.repository.stock.StockRepository
import com.example.udemy_coroutine_prac.repository.stock.StockRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

/**
 * Created by K.Kobayashi on 2023/06/25.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class StockRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        impl: StockRepositoryImpl
    ) : StockRepository
}
