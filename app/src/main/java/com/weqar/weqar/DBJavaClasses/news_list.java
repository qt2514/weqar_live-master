package com.weqar.weqar.DBJavaClasses;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by andriod on 6/4/18.
 */

public class news_list implements Parcelable{
    String Id;
    String UserId;
    String Image;
    String URL;
    String Attachment;
    String Name;
    String Title;
    String Content;
    String Author;
    String NewsTypeId;

    protected news_list(Parcel in) {
        Id = in.readString();
        UserId = in.readString();
        Image = in.readString();
        URL = in.readString();
        Attachment = in.readString();
        Name = in.readString();
        Title = in.readString();
        Content = in.readString();
        Author = in.readString();
        NewsTypeId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(UserId);
        dest.writeString(Image);
        dest.writeString(URL);
        dest.writeString(Attachment);
        dest.writeString(Name);
        dest.writeString(Title);
        dest.writeString(Content);
        dest.writeString(Author);
        dest.writeString(NewsTypeId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<news_list> CREATOR = new Creator<news_list>() {
        @Override
        public news_list createFromParcel(Parcel in) {
            return new news_list(in);
        }

        @Override
        public news_list[] newArray(int size) {
            return new news_list[size];
        }
    };

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getAttachment() {
        return Attachment;
    }

    public void setAttachment(String attachment) {
        Attachment = attachment;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getNewsTypeId() {
        return NewsTypeId;
    }

    public void setNewsTypeId(String newsTypeId) {
        NewsTypeId = newsTypeId;
    }
}
