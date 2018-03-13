package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    static final String TAG = "JsonUtils";
    static final String SANDWICH_NAME = "name";
    static final String SANDWICH_MAIN_NAME = "mainName";
    static final String SANDWICH_ALSO_KNOWN_AS = "alsoKnownAs";
    static final String SANDWICH_PLACE_OF_ORIGIN = "placeOfOrigin";
    static final String SANDWICH_DESCRIPTION = "description";
    static final String SANDWICH_IMAGE = "image";
    static final String SANDWICH_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        try {

            Sandwich sandwich = new Sandwich();

            JSONObject sandwichJson = new JSONObject(json);

            //Get the name json object
            JSONObject sandwichNameJson = sandwichJson.optJSONObject(SANDWICH_NAME);
            if(sandwichNameJson!=null) {
                
                //Get the main name
                sandwich.setMainName(sandwichNameJson.optString(SANDWICH_MAIN_NAME));

                //Get the also known name list
                JSONArray alsoKnownJson = sandwichNameJson.optJSONArray(SANDWICH_ALSO_KNOWN_AS);
                if (alsoKnownJson != null) {
                    List<String> alsoKnownNames = new ArrayList<>(alsoKnownJson.length());
                    for (int i = 0; i < alsoKnownJson.length(); i++) {
                        alsoKnownNames.add(alsoKnownJson.optString(i));
                    }
                    sandwich.setAlsoKnownAs(alsoKnownNames);
                }
            }

            //Get description
            sandwich.setDescription(sandwichJson.optString(SANDWICH_DESCRIPTION));

            //Get image
            sandwich.setImage(sandwichJson.optString(SANDWICH_IMAGE));

            //Get place of origin
            sandwich.setPlaceOfOrigin(sandwichJson.optString(SANDWICH_PLACE_OF_ORIGIN));

            //Get ingredient list
            JSONArray ingredientsJson = sandwichJson.optJSONArray(SANDWICH_INGREDIENTS);
            if(ingredientsJson!=null) {
                List<String> ingredients = new ArrayList<>(ingredientsJson.length());
                for (int i = 0; i < ingredientsJson.length(); i++) {
                    ingredients.add(ingredientsJson.optString(i));
                }
                sandwich.setIngredients(ingredients);
            }

            printSandwichData(sandwich);
            return sandwich;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    static void printSandwichData(Sandwich sandwich){

        Log.d(TAG, "Main Name: "+sandwich.getMainName());
        Log.d(TAG, "Also known as: "+ TextUtils.join(",", sandwich.getAlsoKnownAs()));
        Log.d(TAG, "Place of Origin: "+sandwich.getPlaceOfOrigin());
        Log.d(TAG, "Ingredients: "+ TextUtils.join(",", sandwich.getIngredients()));
        Log.d(TAG, "Description: "+sandwich.getDescription());
        Log.d(TAG, "Image: "+sandwich.getImage());
    }
}
