package com.tc.put.librarium;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tc.put.librarium.models.ReviewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewReviewActivity extends AppCompatActivity {
/*
Todo
kada korisnik spremi rezenciju i vrati se ostanu upisani podaci u input fieldove,
da li očistit input fieldove kod spremanja rezencije?

todo
poravnati text inputa za rezenciju gore lijevo

todo
sredit scrollbarove
 */


    EditText autor;
    EditText knjiga;
    EditText rezencija;
    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_review);

        autor = findViewById(R.id.txtAutor);
        knjiga = findViewById(R.id.txtKnjiga);
        rezencija = findViewById(R.id.txtRezencija);

        if (getIntent().getExtras() != null){
            index = getIntent().getExtras().getInt("editIndex");
        } else {
            index = 0;
        }
        Log.d("TCV", "onCreate: index>>"+index);
        if(index < 1){
            /*
            todo handle err
             */
        } else {
            try {
                SharedPreferences pm = PreferenceManager.getDefaultSharedPreferences(this);
                JSONArray json = new JSONArray(pm.getString("storage",""));
                JSONObject review = json.getJSONObject(index-1);
                autor.setText(review.getString("autor"));
                knjiga.setText(review.getString("knjiga"));
                rezencija.setText(review.getString("rezencija"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveReview(View v){
        String autor = this.autor.getText().toString();
        String knjiga = this.knjiga.getText().toString();
        String rezencija = this.rezencija.getText().toString();
        ReviewModel rm = new ReviewModel(autor,knjiga,rezencija);
        SharedPreferences pm = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            JSONArray json;
            String jsonString = pm.getString("storage","");
            if (jsonString == ""){jsonString="[]";}
            json = new JSONArray(jsonString);

            if (index > 0){
                JSONArray newJson = new JSONArray("[]");
                for (int i=0; i<json.length(); i++){
                    if (i != (index-1)){
                        newJson.put(json.getJSONObject(i));
                    } else {
                        newJson.put(rm.toJSONObject());
                    }
                }
                json = newJson;
            } else {
                json.put(rm.toJSONObject());
            }
            pm.edit().clear().apply();
            pm.edit().putString("storage", json.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (index == 0){
            startActivity(new Intent(NewReviewActivity.this, ReviewListActivity.class));
        } else {
            this.finish();
        }
    }
}
