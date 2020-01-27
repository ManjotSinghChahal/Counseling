package com.tenserflow.therapist.model.Detail;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewsArray implements Cloneable,Parcelable {


    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("Review_User_Name")
    @Expose
    private String reviewUserName;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("Review_description")
    @Expose
    private String reviewDescription;
    @SerializedName("reviews_count")
    @Expose
    private String reviewsCount;

    protected ReviewsArray(Parcel in) {
        if (in.readByte() == 0) {
            userId = null;
        } else {
            userId = in.readInt();
        }
        reviewUserName = in.readString();
        rating = in.readString();
        reviewDescription = in.readString();
        reviewsCount = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (userId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(userId);
        }
        dest.writeString(reviewUserName);
        dest.writeString(rating);
        dest.writeString(reviewDescription);
        dest.writeString(reviewsCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ReviewsArray> CREATOR = new Creator<ReviewsArray>() {
        @Override
        public ReviewsArray createFromParcel(Parcel in) {
            return new ReviewsArray(in);
        }

        @Override
        public ReviewsArray[] newArray(int size) {
            return new ReviewsArray[size];
        }
    };

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getReviewUserName() {
        return reviewUserName;
    }

    public void setReviewUserName(String reviewUserName) {
        this.reviewUserName = reviewUserName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReviewDescription() {
        return reviewDescription;
    }

    public void setReviewDescription(String reviewDescription) {
        this.reviewDescription = reviewDescription;
    }

    public String getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(String reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

}


