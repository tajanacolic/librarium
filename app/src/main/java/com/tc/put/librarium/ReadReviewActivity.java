package com.tc.put.librarium;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReadReviewActivity extends AppCompatActivity {

    JSONObject review;
    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_review);
        index = getIntent().getExtras().getInt("index");
        if (index < 0){
            /*
            todo handle err
             */
        } else {
            try {
                SharedPreferences pm = PreferenceManager.getDefaultSharedPreferences(this);
                JSONArray json = new JSONArray(pm.getString("storage",""));
                review = json.getJSONObject(index);
                String autor = review.getString("autor");
                String knjiga = review.getString("knjiga");
                String rezencija = review.getString("rezencija");
                String ts_created = review.getString("ts_created");

                TextView viewTitle = findViewById(R.id.txtTitle);
                TextView viewDatum = findViewById(R.id.txtDatum);
                EditText viewReview = findViewById(R.id.txtReview);

                viewTitle.setText(autor+" - "+knjiga);
                viewDatum.setText(ts_created);
                viewReview.setText(rezencija);
                /*
                todo
                sredit nazive view-ova, uskladit s nazivima u drugim activitijima
                 */
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void editReview(View v){
        startActivity(new Intent(ReadReviewActivity.this, NewReviewActivity.class).putExtra("editIndex", index+1));
        this.finish();
    }
}
