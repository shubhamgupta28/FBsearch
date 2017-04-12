package usc.com.uscmaps.example1.shubham.fbsearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Shubham on 4/10/17.
 */

public class DetailsFragmentPosts extends Fragment {

    ListView listViewPosts;

    // Defined Array values to show in ListView
    String[] values = new String[]{"Android List View",
            "Adapter implementation",
            "Simple List View In Android",
            "Create List View Android",
            "Android Example",
            "List View Source Code",
            "List View Array Adapter",
            "Android Example List View"
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details_fragment_posts, container, false);

        listViewPosts = (ListView) rootView.findViewById(R.id.listView_details_posts);
        ArrayAdapter<String> tempAdapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listViewPosts.setAdapter(tempAdapter);

        return rootView;

    }
}
