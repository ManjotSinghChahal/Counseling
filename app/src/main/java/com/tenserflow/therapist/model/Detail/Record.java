package com.tenserflow.therapist.model.Detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Record {


    @SerializedName("Sub_CategoryArray")
    @Expose
    private List<SubCategoryArray> subCategoryArray = null;

    public List<SubCategoryArray> getSubCategoryArray() {
        return subCategoryArray;
    }

    public void setSubCategoryArray(List<SubCategoryArray> subCategoryArray) {
        this.subCategoryArray = subCategoryArray;
    }

}

