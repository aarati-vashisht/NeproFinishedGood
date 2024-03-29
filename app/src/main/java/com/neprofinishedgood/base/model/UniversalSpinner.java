package com.neprofinishedgood.base.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UniversalSpinner {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;

    public UniversalSpinner(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
