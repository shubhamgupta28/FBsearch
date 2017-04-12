package usc.com.uscmaps.example1.shubham.fbsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Shubham on 4/9/17.
 *
 * Good example with Async and load more http://www.androidhive.info/2012/03/android-listview-with-load-more-button/
 *
 * ListView working with pagination buttons https://rakhi577.wordpress.com/2013/05/20/listview-pagination-ex-1/
 *
 */

public class ResultsFragmentUsers extends Fragment {
    ListView listView;
    private final String TAG = getClass().getSimpleName();

    private Button btn_prev;
    private Button btn_next;

    private ArrayList<String> data;
    ArrayAdapter<String> sd;

    private int pageCount;
    private int increment = 0;


    public int TOTAL_LIST_ITEMS = 25;
    public int NUM_ITEMS_PAGE = 10;

    static final String[] MOBILE_OS_array =
            new String[]{"Android", "iOS", "WindowsMobile", "Blackberry"};


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.results_fragment_users, container, false);

        listView = (ListView) rootView.findViewById(R.id.listView_users);
        btn_prev = (Button) rootView.findViewById(R.id.bt_prev1);
        btn_next = (Button) rootView.findViewById(R.id.bt_next1);

        btn_prev.setEnabled(false);
        data = new ArrayList<String>();

        /**
         * this block is for checking the number of pages
         */
        int val = TOTAL_LIST_ITEMS % 2;
        val = val == 0 ? 0 : 1;
        pageCount = TOTAL_LIST_ITEMS / NUM_ITEMS_PAGE + val;


        /**
         * The ArrayList data contains all the list items
         */
        for (int i = 0; i < TOTAL_LIST_ITEMS; i++) {
            data.add("This is Item " + (i + 1));
        }

        loadList(0);

        btn_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.e(TAG, "clicked Next Button");
                increment++;
                loadList(increment);
                CheckEnable();
            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                increment--;
                loadList(increment);
                CheckEnable();
            }
        });

//        The basic arrayadapter for a Listview
//        // Defined Array values to show in ListView
//        String[] values = new String[] { "Android List View",
//                "Adapter implementation",
//                "Simple List View In Android",
//                "Create List View Android",
//                "Android Example",
//                "List View Source Code",
//                "List View Array Adapter",
//                "Android Example List View"
//        };
//
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, values);
//        // Assign adapter to ListView
//        listView.setAdapter(adapter);


        /**
         * The custom adapter
         */
//        final ResultFragmentUsersAdapter adapter = new ResultFragmentUsersAdapter(this.getActivity(), MOBILE_OS_array);
//        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "clicked: "+ ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    /**
     * Method for enabling and disabling Buttons
     */
    private void CheckEnable() {
        if (increment + 1 == pageCount) {
            btn_next.setEnabled(false);
        } else if (increment == 0) {
            btn_prev.setEnabled(false);
        } else {
            btn_prev.setEnabled(true);
            btn_next.setEnabled(true);
        }
    }

    /**
     * Method for loading data in listview
     *
     * @param number
     */
    private void loadList(int number) {
        ArrayList<String> sort = new ArrayList<>();

        int start = number * NUM_ITEMS_PAGE;
        for (int i = start; i < (start) + NUM_ITEMS_PAGE; i++) {
            if (i < data.size()) {
                sort.add(data.get(i));
            } else {
                break;
            }
        }
        sd = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_list_item_1, sort);
        listView.setAdapter(sd);
    }
}
