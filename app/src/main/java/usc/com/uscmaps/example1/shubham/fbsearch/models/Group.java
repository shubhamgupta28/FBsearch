package usc.com.uscmaps.example1.shubham.fbsearch.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shubham on 4/10/17.
 */

public class Group {

    public String groupName;
    public final List<String> imageUrl = new ArrayList<>();

    public Group(String string) {
        this.groupName = string;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupName='" + groupName + '\'' +
                ", imageUrl=" + imageUrl +
                '}';
    }
}