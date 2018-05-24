package com.weqar.weqar.DBJavaClasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andriod on 14/3/18.
 */

public class discountcard_list implements Parcelable{
    String Id;
    String Description;
    String Image;
    String Percentage;
    String StartDate;
    String EndDate;
    String CreatedOn;
    String Modifiedon;
    String Title;
    String Logo;
    String DiscountType;
    Boolean IsFollowed;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPercentage() {
        return Percentage;
    }

    public void setPercentage(String percentage) {
        Percentage = percentage;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public String getModifiedon() {
        return Modifiedon;
    }

    public void setModifiedon(String modifiedon) {
        Modifiedon = modifiedon;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getLogo() {
        return Logo;
    }

    public void setLogo(String logo) {
        Logo = logo;
    }

    public String getDiscountType() {
        return DiscountType;
    }

    public void setDiscountType(String discountType) {
        DiscountType = discountType;
    }

    public Boolean getFollowed() {
        return IsFollowed;
    }

    public void setFollowed(Boolean followed) {
        IsFollowed = followed;
    }

    public static Creator<discountcard_list> getCREATOR() {
        return CREATOR;
    }

    protected discountcard_list(Parcel in) {
        Id = in.readString();
        Description = in.readString();
        Image = in.readString();
        Percentage = in.readString();
        StartDate = in.readString();
        EndDate = in.readString();
        CreatedOn = in.readString();
        Modifiedon = in.readString();
        Title = in.readString();
        Logo = in.readString();
        DiscountType = in.readString();
        byte tmpIsFollowed = in.readByte();
        IsFollowed = tmpIsFollowed == 0 ? null : tmpIsFollowed == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(Description);
        dest.writeString(Image);
        dest.writeString(Percentage);
        dest.writeString(StartDate);
        dest.writeString(EndDate);
        dest.writeString(CreatedOn);
        dest.writeString(Modifiedon);
        dest.writeString(Title);
        dest.writeString(Logo);
        dest.writeString(DiscountType);
        dest.writeByte((byte) (IsFollowed == null ? 0 : IsFollowed ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<discountcard_list> CREATOR = new Creator<discountcard_list>() {
        @Override
        public discountcard_list createFromParcel(Parcel in) {
            return new discountcard_list(in);
        }

        @Override
        public discountcard_list[] newArray(int size) {
            return new discountcard_list[size];
        }
    };
}
