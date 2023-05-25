package com.example.team14_turpakkeliste.EntityClass


import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "WeatherInfo")
data class WeatherInfo(
    @PrimaryKey val date: String,
    @ColumnInfo(name = "location") val location: String?,
    @ColumnInfo(name = "daynumber") val daynumber: Int?,
    @ColumnInfo(name = "temperature") val temperature: Double?,
    @ColumnInfo(name = "windspeed") val windspeed: Double?,
    @ColumnInfo(name = "watermilimeter") val watermilimeter: Double?,
    @ColumnInfo(name = "imageCode") val image: String?
)
@Dao
interface UserDao {

    /**Henter all data fra databasen.
     */
    @Query("SELECT * FROM WeatherInfo")
    fun getAll(): List<WeatherInfo>

    /**
     * Legger til elementer i databasen
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: WeatherInfo)


    /**
     * Sletter alt
     */
    @Query("DELETE FROM WeatherInfo")
    suspend fun deleteAll()

}