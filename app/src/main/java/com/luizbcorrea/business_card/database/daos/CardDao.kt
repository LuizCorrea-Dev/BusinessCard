package com.luizbcorrea.business_card.database.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.luizbcorrea.business_card.database.models.Card

//  responsável por colocar e tirar dados da entidade
@Dao
interface CardDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCard(card: Card)

    @Query("SELECT * FROM cards_table WHERE name LIKE :searchQuery")
    fun searchDataBase(searchQuery: String): LiveData<List<Card>>

    @Query("SELECT * from cards_table where id Like :id")
    fun getCardById(id: Long): LiveData<Card?>

    //---------------UPDATE-------------------------/
        @Update
        suspend fun updateCard(card: Card)

    //---------------CONTAGEM-------------------------/
        @Query("SELECT * FROM cards_table ORDER BY id DESC")
        fun allCards(): LiveData<List<Card>>


        // Contar os itens tem na tabela task
        @Query("SELECT COUNT(id) FROM cards_table")
        suspend fun getTotalItems() : Long

    //---------------LISTAS-------------------------/

        @Query("SELECT * FROM cards_table ORDER BY name ASC")
        fun getList(): LiveData<List<Card>>

        // gerando lista de task em ordem alfabética
        @Query("SELECT * FROM cards_table ORDER BY name ASC")
        fun getAlphabetizedCards():List<Card>

        // gerando lista de task em ordem só de data
        @Query("SELECT * FROM cards_table ORDER BY name ASC")
        fun getCardsByDate():List<Card>

        // gerando lista de task em ordem de hora e data
        @Query("SELECT * FROM cards_table ORDER BY email, phone ASC")
        fun getCardsByDateAndHour():List<Card>

    //---------------DELETE-------------------------/

        @Delete
        suspend fun deleteCard(card: Card)

        // deletar todas as tasks
        @Query("DELETE FROM cards_table")
        fun deleteAll()



}