package usc.com.uscmaps.example1.shubham.fbsearch.adapters;

import android.app.Activity;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import usc.com.uscmaps.example1.shubham.fbsearch.models.Group;
import usc.com.uscmaps.example1.shubham.fbsearch.R;
import usc.com.uscmaps.example1.shubham.fbsearch.models.Posts;

/**
 * Created by Shubham on 4/10/17.
 */

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    private final String TAG = getClass().getSimpleName();
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    ArrayList<Posts> list_posts = new ArrayList<>();
//    private Context mContext = this;


    private final SparseArray<Group> groups;
    public LayoutInflater inflater;
    public Activity activity;

    public MyExpandableListAdapter(Activity act, SparseArray<Group> groups) {
        activity = act;
        this.groups = groups;
        inflater = act.getLayoutInflater();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).imageUrl.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String children = (String) getChild(groupPosition, childPosition);
//        Log.e(TAG, "getChildView: "+children );
        ImageView imgView = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.details_fragment_albums_row_details, null);
        }
        imgView = (ImageView) convertView.findViewById(R.id.imgView_subgroup_image);
//        imgView.setImageResource(R.mipmap.wallpaper);
//        Picasso.with(this.activity).load("http://i.imgur.com/DvpvklR.png").into(imgView);
//        Picasso.with(this.activity).load(R.mipmap.wallpaper).into(imgView);
        Picasso.with(this.activity).load(children).into(imgView);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).imageUrl.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.details_fragment_albums_list_row, null);
        }
        Group group = (Group) getGroup(groupPosition);
        ((CheckedTextView) convertView).setText(group.groupName);
        ((CheckedTextView) convertView).setChecked(isExpanded);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }



}
