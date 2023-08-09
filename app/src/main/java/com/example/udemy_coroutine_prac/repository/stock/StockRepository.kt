package com.example.udemy_coroutine_prac.repository.stock

import kotlinx.coroutines.flow.Flow

/**
 * Created by K.Kobayashi on 2023/06/25.
 */
interface StockRepository {
    suspend fun getStock1(): Int
    suspend fun getStock2(): Int

    fun fitchFlowPracInt(): Flow<Int>
}
