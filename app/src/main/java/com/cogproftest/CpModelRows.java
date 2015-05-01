package com.cogproftest;

/**
 * Created by Krishna on 01/05/15.
 */
import com.google.gson.annotations.SerializedName;

public class CpModelRows {

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("imageHref")
    private String imageUrl;


    public CpModelRows(String title,String description,String imageUrl) {
        this.title = title;
        this.description=description;
        this.imageUrl=imageUrl;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
