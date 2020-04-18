package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public final class QueryUtils {

    private static String LOG_TAG = QueryUtils.class.getSimpleName();
    /** Sample JSON response for a USGS query */
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    public static List<Earthquake> fetchEarthquakeDate(String stringUrl){
        List<Earthquake> earthquakes = new ArrayList<>();
        String jsonResponse = "";
        URL url = createURL(stringUrl);
        if(url!=null){
            try {
                jsonResponse = makeHTTPRequest(url);
            } catch (IOException e) {
                Log.e(LOG_TAG,"Error getting JSON");
            }
        }
        if(jsonResponse!=null){
            earthquakes = extractEarthquakes(jsonResponse);
        }
        return earthquakes;
    }


    //create URL
    private static URL createURL(String stringUrl){
        if(stringUrl.length()>0){
            try{
                URL url = new URL(stringUrl);
                return url;
            } catch (MalformedURLException e){
                Log.e(LOG_TAG,"Error creating URL object");
            }
        }
        return null;
    }

    //connect to the URL
    private static String makeHTTPRequest(URL url) throws IOException {

        String jsonResponse = "";
        if(url==null){
            return jsonResponse;
        }

        HttpsURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG,"Failed to connect to the URL");
            }
        } catch (IOException e){
            Log.e(LOG_TAG,"Problem retrieving JSON response");
        } finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while(line!=null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }




    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    private static ArrayList<Earthquake> extractEarthquakes(String json) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject root = new JSONObject(json);
            JSONArray featuresArray = root.getJSONArray("features");
            for(int i = 0; i<featuresArray.length(); i++){
                JSONObject earthquakeData = featuresArray.getJSONObject(i);
                JSONObject properties = earthquakeData.getJSONObject("properties");

                double magnitude = properties.getDouble("mag");
                String place = properties.getString("place");
                long time = properties.getLong("time");

                earthquakes.add(new Earthquake(magnitude,place,time));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }
}
