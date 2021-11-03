package com.tc.put.librarium;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.tc.put.librarium.models.ReviewModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewListActivity extends AppCompatActivity {


    ListView lista;
    ArrayList<ReviewModel> reviewLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);
        lista = findViewById(R.id.reviewList);
        reviewLista = new ArrayList<ReviewModel>();
        refreshListView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshListView();
    }

    private void deleteItem(int i){
        reviewLista.remove(i);
        saveList();
        refreshListView();
    }

    private void saveList(){
        SharedPreferences pm = PreferenceManager.getDefaultSharedPreferences(this);
        try {
            JSONArray json;
            json = new JSONArray("[]");
            for(int j = 0; j< reviewLista.size(); j++){
                json.put(reviewLista.get(j).toJSONObject());
            }
            pm.edit().clear().apply();
            pm.edit().putString("storage", json.toString()).apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void refreshListView(){
        try {
            JSONArray json = new JSONArray(PreferenceManager.getDefaultSharedPreferences(this).getString("storage",""));
            Log.d("TCV", json.toString());

            List<Map<String, String>> data = new ArrayList<Map<String, String>>();
            reviewLista.clear();
            for(int i=0; i<json.length(); i++){
                reviewLista.add(new ReviewModel(json.getJSONObject(i)));
                String autor = json.getJSONObject(i).getString("autor");
                String knjiga = json.getJSONObject(i).getString("knjiga");
                String ts_created = json.getJSONObject(i).getString("ts_created");
                Map<String, String> item = new HashMap<String, String>(2);
                item.put("line1", autor + " - " + knjiga);
                item.put("line2",ts_created);
                data.add(item);
            }

            SimpleAdapter adapter = new SimpleAdapter(
                    this,
                    data,
                    android.R.layout.simple_list_item_2,
                    new String[]{"line1","line2"},
                    new int[] {android.R.id.text1, android.R.id.text2 });
            lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int j, long l) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ReviewListActivity.this);
                    alertDialogBuilder.setMessage("Želite li izbrisati rezenciju?");
                    alertDialogBuilder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteItem(j);
                        }
                    });
                    alertDialogBuilder.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialogBuilder.show();
                    return true;
                }
            });

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    startActivity(new Intent(ReviewListActivity.this, ReadReviewActivity.class).putExtra("index",i));
                }
            });
            lista.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
