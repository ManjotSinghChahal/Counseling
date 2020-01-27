package com.tenserflow.therapist.model.Detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubCategoryArray {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("ratings")
    @Expose
    private String ratings;
    @SerializedName("ReviewsArray")
    @Expose
    private List<ReviewsArray> reviewsArray = null;
    @SerializedName("SessionArray")
    @Expose
    private List<SessionArray> sessionArray = null;
    @SerializedName("images")
    @Expose
    private List<ImageArray> images = null;
    @SerializedName("videos")
    @Expose
    private List<VideoArray> videos = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public List<ReviewsArray> getReviewsArray() {
        return reviewsArray;
    }

    public void setReviewsArray(List<ReviewsArray> reviewsArray) {
        this.reviewsArray = reviewsArray;
    }

    public List<SessionArray> getSessionArray() {
        return sessionArray;
    }

    public void setSessionArray(List<SessionArray> sessionArray) {
        this.sessionArray = sessionArray;
    }

    public List<ImageArray> getImages() {
        return images;
    }

    public void setImages(List<ImageArray> images) {
        this.images = images;
    }

    public List<VideoArray> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoArray> videos) {
        this.videos = videos;
    }
}





   /* @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("ratings")
    @Expose
    private String ratings;
    @SerializedName("ReviewsArray")
    @Expose
    private List<ReviewsArray> reviewsArray = null;
    @SerializedName("SessionArray")
    @Expose
    private List<SessionArray> sessionArray = null;
    @SerializedName("images")
    @Expose
    private List<ImageArray> images = null;
    @SerializedName("videos")
    @Expose
    private List<VideoArray> videos = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public List<ReviewsArray> getReviewsArray() {
        return reviewsArray;
    }

    public void setReviewsArray(List<ReviewsArray> reviewsArray) {
        this.reviewsArray = reviewsArray;
    }

    public List<SessionArray> getSessionArray() {
        return sessionArray;
    }

    public void setSessionArray(List<SessionArray> sessionArray) {
        this.sessionArray = sessionArray;
    }

    public List<ImageArray> getImages() {
        return images;
    }

    public void setImages(List<ImageArray> images) {
        this.images = images;
    }

    public List<VideoArray> getVideos() {
        return videos;
    }

    public void setVideos(List<VideoArray> videos) {
        this.videos = videos;
    }

}

*/