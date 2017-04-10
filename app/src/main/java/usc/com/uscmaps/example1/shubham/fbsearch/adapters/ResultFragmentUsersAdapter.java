package usc.com.uscmaps.example1.shubham.fbsearch.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import usc.com.uscmaps.example1.shubham.fbsearch.R;

/**
 * Created by Shubham on 4/9/17.
 */

public class ResultFragmentUsersAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] values;

    public ResultFragmentUsersAdapter(Context context, String[] values) {
        super(context, R.layout.custom_view_listview_main, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.custom_view_listview_main, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.firstLine);
        TextView textView1 = (TextView) rowView.findViewById(R.id.secondLine);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView.setText(values[position]);
        textView1.setText(values[position]);

        // Change icon based on name
        String s = values[position];

        System.out.println(s);

        if (s.equals("WindowsMobile")) {
            imageView.setImageResource(R.drawable.users);
        } else if (s.equals("iOS")) {
            imageView.setImageResource(R.drawable.places);
        } else if (s.equals("Blackberry")) {
            imageView.setImageResource(R.drawable.users);
        } else {
            imageView.setImageResource(R.drawable.users);
        }

        return rowView;
    }
}
