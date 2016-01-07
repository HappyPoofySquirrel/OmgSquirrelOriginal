package com.example.sleazypmartini.omgsquirrel2;


        import android.os.Bundle;
        import android.support.v7.app.ActionBar;
        import android.support.v7.app.ActionBarActivity;
        import android.support.v7.app.AppCompatActivity;
        import android.text.Html;
        import android.widget.ImageView;
        import android.widget.TextView;
        import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    private TextView titleTextView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        String image = getIntent().getStringExtra("image");
        titleTextView = (TextView) findViewById(R.id.title);
        imageView = (ImageView) findViewById(R.id.grid_item_image);
        

        Picasso.with(this).load(image).into(imageView);
    }
}
