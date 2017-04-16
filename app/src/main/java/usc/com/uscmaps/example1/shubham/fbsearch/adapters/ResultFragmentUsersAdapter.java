package usc.com.uscmaps.example1.shubham.fbsearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import usc.com.uscmaps.example1.shubham.fbsearch.R;

/**
 * Created by Shubham on 4/9/17.
 */

public class ResultFragmentUsersAdapter extends ArrayAdapter<ArrayList<String>> {

    private final Context mContext;
    private final ArrayList<ArrayList<String>> values;

    public ResultFragmentUsersAdapter(Context mContext, ArrayList<ArrayList<String>> values) {
        super(mContext, R.layout.custom_view_listview_main, values);
        this.mContext = mContext;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.custom_view_listview_main, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.firstLine);
        ImageView imgViewUser = (ImageView) rowView.findViewById(R.id.icon);
        ImageView imgViewStarred = (ImageView) rowView.findViewById(R.id.imgView_starred);
        ImageView imgViewMoreDetails = (ImageView) rowView.findViewById(R.id.imgView_more_details);



        //Making button a square shaped object
//        int btnSize=btMoreDetails.getLayoutParams().width;
//        btMoreDetails.setLayoutParams(new ViewGroup.LayoutParams(btnSize, btnSize));

        ArrayList<String> curr = values.get(position);
        textView.setText(curr.get(0));
        Picasso.with(this.getContext()).load(curr.get(2)).into(imgViewUser);

        return rowView;
    }
}
