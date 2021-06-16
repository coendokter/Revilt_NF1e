package com.example.revilt_inf1e;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;


public class SecondActivity extends Activity{

    ArrayAdapter<String> adapter;
    private Button button;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ListView listView;

        listView = (ListView)findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        new Connection().execute();

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });
    }

    class Connection extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            String host = "http://revilt.serverict.nl/ReviltApi/api/read.php";

            try {
                HttpClient client = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI(new URI(host));
                HttpResponse response = client.execute(request);
                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer stringBuffer = new StringBuffer("");

                String line = "";

                while((line = reader.readLine()) != null){
                    stringBuffer.append(line);
                    break;
                }
                reader.close();
                result = stringBuffer.toString();
            }
            catch(Exception e){
                return new String("There exception" + e.getMessage());
            }
                return result;
        }


        @Override
        protected void onPostExecute(String result){
            //parsing json data here
            try {
                JSONObject jsonResult = new JSONObject(result);
                int success = jsonResult.getInt("success");
                if(success == 1)
                {
                    JSONArray viltjes = jsonResult.getJSONArray("viltjes");
                    for(int i=0; i<viltjes.length(); i++)
                    {
                        JSONObject viltje = viltjes.getJSONObject(i);
                        int gewichtglas = viltje.getInt("Gewicht_glas");
                        int id = viltje.getInt("Vilt_id");
                        String line = id + "Glas gewicht = " + gewichtglas;
                        adapter.add(line);
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Er zijn geen bierfiltjes", Toast.LENGTH_LONG).show();
                }
            }
            catch (JSONException e)
            {
                Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void openActivity(){
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }


}


