package com.sammoore.finalproject.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "database_seeded_flags")
public class DatabaseSeededFlag {
    @PrimaryKey(autoGenerate = true)
    public long id;
}
