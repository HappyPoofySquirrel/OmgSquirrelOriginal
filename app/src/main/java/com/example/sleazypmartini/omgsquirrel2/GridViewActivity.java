package com.example.sleazypmartini.omgsquirrel2;

import android.content.Context;
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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sleazypmartini.omgsquirrel2.util.DialogFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GridViewActivity extends AppCompatActivity {
    Context mContext;

    private static final String TAG = GridViewActivity.class.getSimpleName();
    private GridView mGridView;
    private ProgressBar mProgressBar;
    private GridViewAdapter mGridAdapter;
    private ArrayList<GridItem> mGridData;
    private String FEED_URL = "https://api.flickr.com/services/rest/?method=flickr.photosets.getPhotos&oauth_consumer_key=54f1d0146bcec3b405164e253e8ff710&photoset_id=72157661135553275&user_id=137813339%40N03&format=json&nojsoncallback=1&oauth_token=72157663053868089-63166f4e59a184ca&api_sig=d50c3cc7a51f089c28c3efe145e22b6f4aa95f0c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview);
        getSupportActionBar().setDisplayShowHomeEnabled(true);  //add these 2 lines to display icon
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        mContext = this;
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
                int[] screenLocation = new int[2];
                imageView.getLocationOnScreen(screenLocation);


                //Pass the image title and url to DetailsActivity
                intent.putExtra("left", screenLocation[0]).
                        putExtra("top", screenLocation[1]).
                        putExtra("width", imageView.getWidth()).
                        putExtra("height", imageView.getHeight()).
                        putExtra("title", item.getTitle()).
                        putExtra("image", item.getImage());

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
                DialogFactory.newOneBtnAlert(mContext, "Failed to load data, Check your internet connection");
            }
           mProgressBar.setVisibility(View.GONE);
        }
    }

    String streamToString(InputStream stream) throws IOException {
        String result="";
        try {
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder stringBuilder = new StringBuilder();

            String stringReadLine;
            while ((stringReadLine = bufferedreader.readLine()) != null) {
                stringBuilder.append(stringReadLine + "\n");
            }
            if (null != stream) {
                stream.close();
            }
            result += stringBuilder.toString();

        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        return result;
    }
     // Parsing the feed results and get the list
     // @param result

    void parseResult(String result) {

        try {
            JSONObject response = new JSONObject(result);
            JSONObject photoSet = response.getJSONObject("photoset");
            JSONArray photos = photoSet.getJSONArray("photo");

            GridItem item;
            for (int i = 0; i < photos.length(); i++) {
                item = new GridItem();
                JSONObject temp_photo = photos.optJSONObject(i);
                String id = temp_photo.getString("id");
                String secret = temp_photo.getString("secret");
                String server = temp_photo.getString("server");
                String farm = temp_photo.getString("farm");
                String title = temp_photo.getString("title");

                item.setTitle(title);
                item.setImage("http://farm" + farm + ".static.flickr.com/" + server + "/" + id + "_" + secret + ".jpg");

                mGridData.add(item);
            }

        } catch (JSONException e) {
   Log.d("didn't work","Try again");
            e.printStackTrace();
        }
    }
}