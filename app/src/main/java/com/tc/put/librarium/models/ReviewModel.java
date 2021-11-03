package com.tc.put.librarium.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by Martin on 06-Feb-18.
 */

public class ReviewModel {
    String autor;
    String knjiga;
    String rezencija;
    String ts_created;

    public ReviewModel(String autor, String knjiga, String rezencija) {
        this.autor = autor;
        this.knjiga = knjiga;
        this.rezencija = rezencija;

        SimpleDateFormat dayTime = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        ts_created = dayTime.format(new java.util.Date());

    }
    public ReviewModel(JSONObject json){
        try {
            this.autor = json.getString("autor");
            this.knjiga = json.getString("knjiga");
            this.rezencija = json.getString("rezencija");
            this.ts_created = json.getString("ts_created");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject toJSONObject(){
        JSONObject json = new JSONObject();
        try {
            json.put("autor",this.autor);
            json.put("knjiga",this.knjiga);
            json.put("rezencija",this.rezencija);
            json.put("ts_created",this.ts_created);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
