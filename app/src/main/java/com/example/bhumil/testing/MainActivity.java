package com.example.bhumil.testing;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
ImageView poster;
TextView moviename;
String movietitle,moviedesc,movieposterurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        poster = (ImageView)findViewById(R.id.poster);
        moviename = (TextView)findViewById(R.id.moviename);
        poster.setOnClickListener(this);

        String url = new Uri.Builder()
                .scheme("http")
                .appendPath("api.themoviedb.org")
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("api_key","<api_key>")
                .appendQueryParameter("sort_by","popularity.desc")
                //.appendQueryParameter("page","5")
                .build().toString();


        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray movies = response.getJSONArray("results");
                            JSONObject popularmovie = movies.getJSONObject(3);
                            movietitle = popularmovie.getString("title");
                            moviedesc = popularmovie.getString("overview");
                            movieposterurl = popularmovie.getString("poster_path");
                            movieposterurl=movieposterurl.substring(1);
                            Log.e("movieTitle",movietitle);
                            Log.e("movieDesc",moviedesc);
                            Log.e("movieposterurl",movieposterurl);


                            String purl = new Uri.Builder()
                                    .scheme("http")
                                    .authority("image.tmdb.org")
                                    .appendPath("t")
                                    .appendPath("p")
                                    .appendPath("w342")
                                    .appendPath(movieposterurl).build().toString();

                            Picasso.with(getApplicationContext())
                                    .load(purl)
                                    .into(poster);
                            moviename.setText(movietitle);
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.e("Error - volley",error.getMessage());

                    }
                });
        Appcontroller.getInstance().addToRequestQueue(jsObjRequest);

    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(MainActivity.this, MovieDescription.class);
        i.putExtra("movietitle",movietitle);
        i.putExtra("moviedesc",moviedesc);
        startActivity(i);
    }
}
