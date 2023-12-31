package com.example.mycontacts.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contact: Contact)

    @Update
    suspend fun update(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Query("SELECT * from contacts WHERE id = :id")
    fun getContact(id: Int): Flow<Contact>

    @Query("SELECT * from contacts ORDER BY name ASC")
    fun getAllContacts(): Flow<List<Contact>>
}