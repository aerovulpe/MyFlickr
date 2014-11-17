package me.aerovulpe.myflickr;

/**
 * Created by Aaron on 17/11/2014.
 */
public class Photo {
    private String title;
    private String author;
    private String authorID;
    private String link;
    private String tags;
    private String image;

    public Photo(String title, String author, String authorID, String link, String tags, String image) {
        this.title = title;
        this.author = author;
        this.authorID = authorID;
        this.link = link;
        this.tags = tags;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorID() {
        return authorID;
    }

    public String getLink() {
        return link;
    }

    public String getTags() {
        return tags;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", authorID='" + authorID + '\'' +
                ", link='" + link + '\'' +
                ", tags='" + tags + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
