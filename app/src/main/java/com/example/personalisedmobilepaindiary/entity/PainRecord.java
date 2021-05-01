package com.example.personalisedmobilepaindiary.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "PainRecord")
public class PainRecord {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name="pain_level")
    @NonNull
    public int level;

    @ColumnInfo(name="pain_location")
    @NonNull
    public String location;

    public String mood;

    public int step;

    public String date;

    public String temp;

    public String humidity;

    public String pressure;

    public String email;

    public PainRecord(@NonNull int level, @NonNull String location, @NonNull String mood,
                      @NonNull int step, @NonNull String date, @NonNull String temp,
                      @NonNull String humidity, @NonNull String pressure, @NonNull String email) {
        this.level = level;
        this.location = location;
        this.mood = mood;
        this.step = step;
        this.date = date;
        this.temp = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        this.email = email;
    }



}
