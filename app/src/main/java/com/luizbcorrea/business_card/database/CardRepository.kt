package com.luizbcorrea.business_card.database

import androidx.lifecycle.LiveData
import com.luizbcorrea.business_card.database.daos.CardDao
import com.luizbcorrea.business_card.database.models.Card

class CardRepository(private val cardDao: CardDao) {

    val getList: LiveData<List<Card>> = cardDao.getList()

    suspend fun insertCard(card: Card) {
        cardDao.insertCard(card)
    }

    suspend fun updateCard(card: Card) {
        cardDao.updateCard(card)
    }

    suspend fun deleteCard(card: Card) {
        cardDao.deleteCard(card)
    }

    fun getCardById(id: Long): LiveData<Card?> {
        return cardDao.getCardById(id)
    }

    fun searchByDateQuery(searchDate: String): LiveData<List<Card>> {
        return cardDao.searchDataBase(searchDate)
    }
}