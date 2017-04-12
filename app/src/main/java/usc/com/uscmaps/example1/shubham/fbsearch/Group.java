package usc.com.uscmaps.example1.shubham.fbsearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shubham on 4/10/17.
 */

public class Group {

    public String string;
    public final List<String> children = new ArrayList<String>();

    public Group(String string) {
        this.string = string;
    }

}