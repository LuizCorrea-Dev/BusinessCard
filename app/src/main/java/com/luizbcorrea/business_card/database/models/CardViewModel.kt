package com.luizbcorrea.business_card.database.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.luizbcorrea.business_card.database.CardDatabase
import com.luizbcorrea.business_card.database.CardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CardViewModel(application: Application) : AndroidViewModel(application) {
    private val cardDao = CardDatabase.getDatabase(application).taskDao()
    private val repository: CardRepository

    private val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    val getAllData: LiveData<List<Card>>

    init {
        repository = CardRepository(cardDao)
        getAllData = repository.getList
    }

    fun checkDatabaseEmpty(todoList: List<Card>) {
        emptyDatabase.value = todoList.isEmpty()
    }



    fun insertCard(card: Card) {
        if (card.id.toInt() == 0) {
            viewModelScope.launch(Dispatchers.IO) { repository.insertCard(card) }
        } else {
            updateCard(card)
        }
    }

    fun updateCard(card: Card) {
        viewModelScope.launch(Dispatchers.IO) { repository.updateCard(card) }
    }

    fun deleteCard(card: Card) {
        viewModelScope.launch(Dispatchers.IO) { repository.deleteCard(card) }
    }

    fun findCardById(id: Long): Card? {
        getAllData.value?.forEach {
            if (it.id == id) {
                return it
            }
        }

        return null
    }

    fun searchByDate(date: String): LiveData<List<Card>> {
        return repository.searchByDateQuery(date)
    }


    fun getList(): List<Card> {
        return getAllData.value!!
    }
}

