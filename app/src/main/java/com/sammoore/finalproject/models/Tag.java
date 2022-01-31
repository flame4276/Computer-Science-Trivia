package com.sammoore.finalproject.models;

public class Tag {
    private String tagName;

    public Tag(TagModel model) {
        this.tagName = model.tagName;
    }

    public String getTagName() {
        return tagName;
    }
}
