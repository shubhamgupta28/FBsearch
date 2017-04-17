package usc.com.uscmaps.example1.shubham.fbsearch.models;

/**
 * Created by Shubham on 4/15/17.
 */

public class Posts {
    private String message;
    private String created_time;
    private String header;

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    private String profile_image;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "message='" + message + '\'' +
                ", created_time='" + created_time + '\'' +
                ", header='" + header + '\'' +
                '}';
    }
}
