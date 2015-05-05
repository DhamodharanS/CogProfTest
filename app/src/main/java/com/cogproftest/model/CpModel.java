package com.cogproftest.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/*
 * Implementation of Model Class
 */
public class CpModel {

    @SerializedName("title")
    private String title;

    @SerializedName("rows")
    private ArrayList<CpModelRows> cpModelRows;


    public CpModel(ArrayList<CpModelRows> cpModelRows, String title) {
        this.cpModelRows = cpModelRows;
        this.title = title;

    }

    public String getTitle() {
        return title;
    }
    public void setSubject(String title) {
        this.title = title;
    }

    public ArrayList<CpModelRows> getCpModelRows() {
        return cpModelRows;
    }


    public void setCpModelRows(ArrayList<CpModelRows> cpModelRows) {

              this.cpModelRows = cpModelRows;

    }

}
