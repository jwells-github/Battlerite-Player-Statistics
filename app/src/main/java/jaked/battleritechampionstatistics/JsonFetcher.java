package jaked.battleritechampionstatistics;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonFetcher {

    private static final String TAG = "JsonFetcher";
    private static final String API_KEY = "";
    // The correpsonding keys for each object in the JsonArray
    private static final String CHAMPION_XP = "110";
    private static final String CHAMPION_WINS = "120";
    private static final String CHAMPION_LOSSES = "130";
    private static final String CHAMPION_TIMED_PLAYED = "160";
    private static final String CHAMPION_LEVEL = "400";
    private static final String TOTAL_WINS = "2";
    private static final String TOTAL_LOSSES = "3";
    private static final String ACCOUNT_LEVEL = "26";

    // The corresponding Champion names for each key (not provided by the API)
    Map<String, String> championMap = new HashMap<String, String>() {{
        put("Lucie", "01");
        put("Sirius", "02");
        put("Iva", "03");
        put("Jade", "04");
        put("Ruh Kaan", "05");
        put("Oldur", "06");
        put("Ashka", "07");
        put("Varesh", "08");
        put("Pearl", "09");
        put("Taya","10");
        put("Poloma", "11");
        put("Croak", "12");
        put("Freya", "13");
        put("Jumong", "14");
        put("Shifu", "15");
        put("Ezmo", "16");
        put("Bakko", "17");
        put("Rook", "18");
        put("Pestilus", "19");
        put("Destiny", "20");
        put("Raigon", "21");
        put("Blossom", "22");
        put("Thorn", "25");
        put("Zander", "35");
        put("Ulric", "39");
        put("Alysia", "41");
        put("Jamila", "43");
    }};

    // gets the Jsonstring from the API
    public byte[] getUrlBytes(String urlSpec) throws IOException{
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", API_KEY);
        connection.setRequestProperty("Accept", "application/vnd.api+json");
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException((connection.getResponseMessage() + ": with" + urlSpec));
            }

            int bytesRead = 0;

            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        }finally{
            connection.disconnect();
        }
    }


    // Parses the json, setting the data for each champion
    public void fetchItems(String PlayerName){
        try{
            String jsonString = new String(getUrlBytes("https://api.developer.battlerite.com/shards/global/players?filter[playerNames]=" + PlayerName));
            Log.i(TAG, "received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);

            JSONArray dataJsonObject = jsonBody.getJSONArray("data");
            JSONObject photoJsonArray = dataJsonObject.getJSONObject(0);
            JSONObject AtrributeObject = photoJsonArray.getJSONObject("attributes");
            JSONObject stats = AtrributeObject.getJSONObject("stats");

            // Creates the championLab singleton if it doesn't already exist
            ChampionLab championLab = ChampionLab.get(null);
            // Set the Player's account stats
            championLab.setAccountLevel(stats.getString(ACCOUNT_LEVEL));
            championLab.setTotalLosses(stats.getString(TOTAL_LOSSES));
            championLab.setTotalWins(stats.getString(TOTAL_WINS));

            // Sets the statistics for each champion and adds them to the ChampionLab.
            // Also sets default values if it doesn't exist in the Json
            for (Map.Entry<String, String> entry : championMap.entrySet())
            {
                Champion champion = new Champion();
                champion.setName(entry.getKey());
                if(stats.has(CHAMPION_XP + entry.getValue())){
                    champion.setXP(stats.getString(CHAMPION_XP + entry.getValue()));
                }
                else{
                    champion.setXP("0");
                }
                if(stats.has(CHAMPION_WINS + entry.getValue())){
                    champion.setWins(stats.getString(CHAMPION_WINS + entry.getValue()));
                }
                else{
                    champion.setWins("0");
                }
                if(stats.has(CHAMPION_LOSSES + entry.getValue())){
                    champion.setLoses(stats.getString(CHAMPION_LOSSES + entry.getValue()));
                }
                else{
                    champion.setLoses("0");
                }
                if(stats.has(CHAMPION_TIMED_PLAYED + entry.getValue())){
                    champion.setTimePlayeD(stats.getString(CHAMPION_TIMED_PLAYED + entry.getValue()));
                }
                else{
                    champion.setTimePlayeD("0");
                }
                if(stats.has(CHAMPION_LEVEL + entry.getValue())){
                    champion.setLevel(stats.getString(CHAMPION_LEVEL + entry.getValue()));
                }
                else{
                    champion.setLevel("0");
                }
                championLab.addChampion(champion);
            }

        } catch (IOException ioe){
            Log.e(TAG, "Failed to fetch items ", ioe);
        } catch (JSONException je){
            Log.e(TAG, "FFailed to parse JSON", je);
        }

    }
}




