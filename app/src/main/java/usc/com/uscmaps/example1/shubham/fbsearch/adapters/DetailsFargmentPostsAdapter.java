package usc.com.uscmaps.example1.shubham.fbsearch.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import usc.com.uscmaps.example1.shubham.fbsearch.R;

/**
 * Created by Shubham on 4/15/17.
 */

public class DetailsFargmentPostsAdapter extends ArrayAdapter<String> {
    private Context mContext = null;
    private final ArrayList<String> values;


    public DetailsFargmentPostsAdapter(@NonNull Context context, ArrayList<String> values) {
        super(context, R.layout.details_fragment_posts_custom_view, values);
        this.mContext = context;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.details_fragment_posts_custom_view, parent, false);
        ImageView imgViewUser = (ImageView) rowView.findViewById(R.id.imgView_posts);
        TextView header = (TextView) rowView.findViewById(R.id.header_posts);
        TextView timeStamp = (TextView) rowView.findViewById(R.id.timestamp_posts);
        TextView content = (TextView) rowView.findViewById(R.id.content_posts);


//        ArrayList<Posts> curr = values.get(position);
//        textView.setText(curr.get(0));
//        Picasso.with(this.getContext()).load(curr.get(2)).into(imgViewUser);

        imgViewUser.setImageResource(R.drawable.places);
        header.setText("shubham");
        timeStamp.setText("shubham");
        content.setText("shubham");

        return rowView;
    }
}
