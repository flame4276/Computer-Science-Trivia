package com.sammoore.finalproject.models;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "tags",
        foreignKeys = { @ForeignKey(
                entity = QuestionModel.class,
                parentColumns = "id",
                childColumns = "question_id",
                onDelete = CASCADE)
})
public class TagModel {
    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "question_id")
    public long questionId;

    @ColumnInfo(name = "tag_name")
    public String tagName;

    public TagModel() {}

    public TagModel(long questionId, String tagName) {
        this.questionId = questionId;
        this.tagName = tagName;
    }
}
