package com.example.kamusbi.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Kamus {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("basanya")
    @Expose
    private String basanya;
    @SerializedName("indonesia")
    @Expose
    private String indo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBetawi() {
        return basanya;
    }

    public void setBetawi(String babasan) {
        this.basanya = babasan;
    }

    public String getIndo() {
        return indo;
    }

    public void setIndo(String indo) {
        this.indo = indo;
    }

}
