package usc.com.uscmaps.example1.shubham.fbsearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ExpandableListView;

import usc.com.uscmaps.example1.shubham.fbsearch.adapters.MyExpandableListAdapter;

/**
 * Created by Shubham on 4/10/17.
 * This Fragment has ExpandableListView
 */

public class DetailsFragmentAlbums extends Fragment {

    SparseArray<Group> groups = new SparseArray<Group>();


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.details_fragment_albums, container, false);
        createData();

        ExpandableListView listView = (ExpandableListView) rootview.findViewById(R.id.expandableListView_details);
        MyExpandableListAdapter adapter = new MyExpandableListAdapter(this.getActivity(), groups);
        listView.setAdapter(adapter);

        return rootview;
    }

    private void createData() {
        for (int j = 0; j < 5; j++) {
            Group group = new Group("Timeline Photos");
            for (int i = 0; i <= 1; i++) {
                group.children.add("Sub Item" + i);
            }
            groups.append(j, group);
        }
    }
}
