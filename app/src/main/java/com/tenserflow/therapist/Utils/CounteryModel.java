
package com.tenserflow.therapist.Utils;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CounteryModel {

    @SerializedName("countries")
    @Expose
    private List<Country> countries = null;

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

}
