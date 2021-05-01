package com.example.personalisedmobilepaindiary.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.personalisedmobilepaindiary.entity.PainRecord;

import java.util.List;

@Dao
public interface PainRecordDAO {
    @Query("SELECT * FROM painrecord ORDER BY date DESC")
    LiveData<List<PainRecord>> getAll();

    @Query("SELECT * FROM painrecord WHERE date = :date AND email = :email LIMIT 1")
    PainRecord findByDate(String date,String email);

    @Insert
    void insert(PainRecord painRecord);

    @Delete
    void delete (PainRecord painRecord);

    @Update
    void updatePainRecord(PainRecord painRecord);
}
