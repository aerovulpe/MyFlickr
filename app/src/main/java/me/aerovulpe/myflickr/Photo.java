package me.aerovulpe.myflickr;

import java.io.Serializable;

/**
 * Created by Aaron on 17/11/2014.
 */
public class Photo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String mTitle;
    private String mAuthor;
    private String mAuthorID;
    private String mLink;
    private String mTags;
    private String mImage;

    public Photo(String title, String author, String authorID, String link, String tags, String image) {
        this.mTitle = title;
        this.mAuthor = author;
        this.mAuthorID = authorID;
        this.mLink = link;
        this.mTags = tags;
        this.mImage = image;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getAuthorID() {
        return mAuthorID;
    }

    public String getLink() {
        return mLink;
    }

    public String getTags() {
        return mTags;
    }

    public String getImage() {
        return mImage;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthor='" + mAuthor + '\'' +
                ", mAuthorID='" + mAuthorID + '\'' +
                ", mLink='" + mLink + '\'' +
                ", mTags='" + mTags + '\'' +
                ", mImage='" + mImage + '\'' +
                '}';
    }
}
