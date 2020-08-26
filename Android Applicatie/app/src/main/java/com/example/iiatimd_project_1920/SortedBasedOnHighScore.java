package com.example.iiatimd_project_1920;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;

public class SortedBasedOnHighScore implements Comparator<JSONObject> {
    @Override
    public int compare(JSONObject lhs, JSONObject rhs) {

        try {
            return lhs.getInt("highscore") > rhs.getInt("highscore") ? 1 : (lhs
                    .getInt("highscore") < rhs.getInt("highscore") ? -1 : 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;

    }
}
