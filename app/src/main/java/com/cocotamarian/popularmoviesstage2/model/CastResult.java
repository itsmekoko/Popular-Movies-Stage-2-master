package com.cocotamarian.popularmoviesstage2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastResult {
    @SerializedName("cast")
    private List<Cast> casts;

    public CastResult(List<Cast> casts) {
        this.casts = casts;
    }

    public List<Cast> getCasts() {
        return casts;
    }

}

