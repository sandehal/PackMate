package com.example.team14_turpakkeliste.EntityClass


import androidx.room.ColumnInfo;
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "pakklister")
data class Pakkliste (
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "First_name") val firstName: String?,
    @ColumnInfo(name= "last_name") val lastName: String?
    )

@Dao
interface UserDao {
    @Query("SELECT * FROM pakklister")
    fun getAll(): List<Pakkliste>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: Pakkliste)

    @Delete
    suspend fun delete(user: Pakkliste)

    @Query("DELETE FROM pakklister")
    suspend fun deleteAll()

}