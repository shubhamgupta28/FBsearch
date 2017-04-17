package usc.com.uscmaps.example1.shubham.fbsearch.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import usc.com.uscmaps.example1.shubham.fbsearch.R;
import usc.com.uscmaps.example1.shubham.fbsearch.models.Posts;

/**
 * Created by Shubham on 4/15/17.
 */

public class DetailsFargmentPostsAdapter extends ArrayAdapter<Posts> {
    private Context mContext = null;
    private final ArrayList<Posts> values;


    public DetailsFargmentPostsAdapter(@NonNull Context context, ArrayList<Posts> values) {
        super(context, R.layout.details_fragment_posts_custom_view, values);
        this.mContext = context;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.details_fragment_posts_custom_view, parent, false);
        ImageView imgViewUser = (ImageView) rowView.findViewById(R.id.imgView_posts);
        TextView header = (TextView) rowView.findViewById(R.id.header_posts);
        TextView timeStamp = (TextView) rowView.findViewById(R.id.timestamp_posts);
        TextView content = (TextView) rowView.findViewById(R.id.content_posts);

        Log.e("ufyjgkbvklhijhvj", "getView: "+values );
        for(int i=0;i<values.size();i++){
            Log.e("fgjhkjgfdghjk", "getView: "+ values.get(i));
        }

//        ArrayList<Posts> curr = values.get(position);
//        textView.setText(curr.get(0));
        Picasso.with(this.getContext()).load(values.get(position).getProfile_image()).into(imgViewUser);

//        imgViewUser.setImageResource(values.get(position).getProfile_image());
        header.setText(values.get(position).getHeader());
        timeStamp.setText(values.get(position).getCreated_time());
        content.setText(values.get(position).getMessage());

        return rowView;
    }
}
