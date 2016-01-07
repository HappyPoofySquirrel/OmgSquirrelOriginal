package com.example.sleazypmartini.omgsquirrel2;

import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button; //added to use button
import android.content.Intent; //added to link button
import android.widget.TextView; //added to use textview

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);  //add these 2 lines to display icon
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


        Button button = (Button) findViewById(R.id.factsButton);    //added to link button +method below
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToFactsActivity();

            }
        });



      //  Button button2 = (Button) findViewById(R.id.ScrollingFacts);    //added to link button +method below
      //  button2.setOnClickListener(new View.OnClickListener() {
      //      @Override
      //      public void onClick(View v) {
      //          goToScrollingFacts();

       //     }
      //  });
        Button button3 = (Button) findViewById(R.id.Gallery);    //added to link button +method below
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGalleryActivity();

            }
        });

        Button button4 = (Button) findViewById(R.id.GridView);    //added to link button +method below
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGridViewActivity();

            }
        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

  //  @Override             this adds a settings button
  //  public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      //  int id = item.getItemId();

        //noinspection SimplifiableIfStatement
     //   if (id == R.id.action_settings) {
     //       return true;
     //   }

      //  return super.onOptionsItemSelected(item);
  //  }
    private void goToFactsActivity(){
        Intent intent =new Intent(this, FactsActivity.class);
        startActivity(intent);
    }
    private void goToScrollingFacts(){
        Intent intent =new Intent(this, ScrollingFacts.class);
        startActivity(intent);
    }
    private void goToGalleryActivity(){
        Intent intent =new Intent(this, GalleryActivity.class);
        startActivity(intent);
    }
    private void goToGridViewActivity(){
        Intent intent =new Intent(this, GridViewActivity.class);
        startActivity(intent);
    }




}
