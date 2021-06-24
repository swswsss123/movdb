package space.tarnovsky.moviedb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // линк на популярные фильмы https://api.themoviedb.org/3/movie/popular?api_key=5250a97df6ac375b55bcd5780f4950b2
    private static String JSON_URL = "https://api.themoviedb.org/3/movie/popular?api_key=5250a97df6ac375b55bcd5780f4950b2";

    List<MovieModeClass>movieList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);

        GetData getData = new GetData();
        getData.execute();
    }

    public class GetData extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {

            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection =null;
                try{
                    url = new URL(JSON_URL);
                    urlConnection=(HttpURLConnection)url.openConnection();


                    InputStream is=urlConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    int data =isr.read();
                    while (data != -1){
                        current+=(char) data;
                        data = isr.read();
                    }
                    return current;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(urlConnection!=null){
                        urlConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for(int i = 0;i<jsonArray.length();i++){

                    JSONObject jsonObject1 =jsonArray.getJSONObject(i);
                    MovieModeClass model = new MovieModeClass();
                    model.setId(jsonObject1.getString("vote_average"));
                    model.setName(jsonObject1.getString("title"));
                    model.setImg(jsonObject1.getString("poster_path"));

                    movieList.add(model);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            PutDataIntoRecyclerView(movieList);
        }
    }
    private void PutDataIntoRecyclerView(List<MovieModeClass>movieList){
        Adapter adapter=new Adapter(this,movieList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
