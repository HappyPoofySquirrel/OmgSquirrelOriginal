package com.example.sleazypmartini.omgsquirrel2;

import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GridViewActivity extends AppCompatActivity {
    private static final String TAG = GridViewActivity.class.getSimpleName();
    private GridView mGridView;
    private ProgressBar mProgressBar;
    private GridViewAdapter mGridAdapter;
    private ArrayList<GridItem> mGridData;
    private String FEED_URL = "https://api.flickr.com/services/rest/?method=flickr.photosets.getPhotos&api_key=206dcb0ade4879da809339697bb014e3&photoset_id=72157661135553275&user_id=137813339%40N03&format=json&nojsoncallback=1&auth_token=72157663290515282-01572e12a642b83f&api_sig=3ff57d063f8af8e088599c7fd2d5a553";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);
        getSupportActionBar().setDisplayShowHomeEnabled(true);  //add these 2 lines to display icon
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        mGridView = (GridView) findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Initialize with empty data
        mGridData = new ArrayList<>();
        mGridAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, mGridData);
        mGridView.setAdapter(mGridAdapter);

        //Grid view click event
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //Get item at position
                GridItem item = (GridItem) parent.getItemAtPosition(position);

                Intent intent = new Intent(GridViewActivity.this, DetailsActivity.class);
                ImageView imageView = (ImageView) v.findViewById(R.id.grid_item_image);

                // Interesting data to pass across are the thumbnail size/location, the
                // resourceId of the source bitmap, the picture description, and the
                // orientation (to avoid returning back to an obsolete configuration if
                // the device rotates again in the meantime)

                int[] screenLocation = new int[2];
                imageView.getLocationOnScreen(screenLocation);

                //Pass the image title and url to DetailsActivity
                intent.putExtra("image", item.getImage());

                //Start details activity
                startActivity(intent);
            }
        });

        //Start download
        new AsyncHttpTask().execute(FEED_URL);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    //Downloading data asynchronously
    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {


        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            try {
                // Create Apache HttpClient
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse httpResponse = httpclient.execute(new HttpGet(params[0]));
                int statusCode = httpResponse.getStatusLine().getStatusCode();

                // 200 represents HTTP OK
                if (statusCode == 200) { //was ==
                    String response = streamToString(httpResponse.getEntity().getContent());
                    parseResult(response);
                    result = 1; // Successful
                } else {
                    result = 0;
                     }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result;
        }

        @Override  //this happens last
        protected void onPostExecute(Integer result) { //Integer Result
            // Download complete. Let us update UI
            if (result == 1) {
                mGridAdapter.setGridData(mGridData);
            } else {
                Toast.makeText(GridViewActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
            }
           mProgressBar.setVisibility(View.GONE);
        }
    }

    String streamToString(InputStream stream) throws IOException {

        BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder stringBuilder = new StringBuilder();
        String result="";
        String stringReadLine = null;
        while ((stringReadLine = bufferedreader.readLine()) != null)                     {
            stringBuilder.append(stringReadLine + "\n");
        }
        result = stringBuilder.toString();

        return result;
    }

    /**
     * Parsing the feed results and get the list
     * @param result
     **/


    void parseResult(String result) {
        GridItem item =null;
        try {
            JSONObject response = new JSONObject(result);
            JSONObject photoSet = response.getJSONObject("photoset");
            JSONArray photos = photoSet.getJSONArray("photo");


            for (int i = 0; i < photos.length(); i++) {

                JSONObject temp_photo = photos.optJSONObject(i);

                String id = temp_photo.getString("id");
                String secret = temp_photo.getString("secret");
                String server = temp_photo.getString("server");
                String farm = temp_photo.getString("farm");
                String title = temp_photo.getString("title");



                        item.setImage("http://farm" + farm + ".static.flickr.com/" + server + "/" + id + "_" + secret + ".jpg");

                mGridData.add(item);

            }


        } catch (JSONException e) {
   Log.d("didn't work","Try again");
            Log.e("JSON Parser", "Error parsing data " + e.toString());
            e.printStackTrace();
        }


    }

    /**
     * Parsing the feed results and get the list
     * @param result
     */
  /**  private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONArray photos = response.optJSONArray("photo");
            GridItem item;
            for (int i = 0; i < photos.length(); i++) {
                JSONObject photo = photos.getJSONObject(i);
                item = new GridItem();

                String farm_id = photo.getString(farm_id);
                String server_id = photo.getString(server_id);
                String id = photo.getString(id);
                String secret = photo.getString(secret);

                item.setImage("http://farm" + item.farm_id + ".static.flickr.com/" + item.server_id + "/" + item.id + "_" + item.secret + "_m.jpg");

                mGridData.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    } **/
}