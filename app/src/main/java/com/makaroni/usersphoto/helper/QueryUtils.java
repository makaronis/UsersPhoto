package com.makaroni.usersphoto.helper;

import android.util.Log;

import com.makaroni.usersphoto.data.PhotoImage;
import com.makaroni.usersphoto.data.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    public static final String JSON_BASE = "http://jsonplaceholder.typicode.com/";
    public static final String JSON_USERS = "users";
    public static final String JSON_ALBUMS = "albums";
    public static final String JSON_PHOTOS = "photos";
    private QueryUtils() { }

    public static List<PhotoImage> loadPhotos(List<Integer> albumIds){
        String json = "";
        List<PhotoImage> photoImages = new ArrayList<>();
        //Загружаем фотографии из всех альбомов выбраннаого пользователя
        for (Integer albumId : albumIds){
            URL url = createUrl(JSON_PHOTOS + "?albumId=" + albumId);
            try {
                json = makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                JSONArray photosArray = new JSONArray(json);
                for (int a = 0; a < photosArray.length(); a++){
                    JSONObject currentPhoto = photosArray.getJSONObject(a);
                    photoImages.add(new PhotoImage(
                            currentPhoto.getString("title"),
                            currentPhoto.getString("url")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return photoImages;
    }

    public static List<Integer> extractAlbumIds(int userId){
        ArrayList<Integer> albumIds = new ArrayList<>();
        URL url = createUrl(JSON_ALBUMS + "?userId=" + userId);
        String json = null;
        // Загружаем id всех альбомов пользователя
        try {
            json = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONArray albumsArray = new JSONArray(json);
            for(int a = 0 ; a < albumsArray.length(); a++){
                JSONObject currentAlbum = albumsArray.getJSONObject(a);
                albumIds.add(currentAlbum.getInt("id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return albumIds;
    }

    // Загружаем юзеров
    public static List<User> extractUsers() {
        ArrayList<User> users = new ArrayList<>();
        URL url = createUrl(JSON_USERS);
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONArray usersArray = new JSONArray(jsonResponse);
            for (int j = 0 ; j < usersArray.length(); j++){
                JSONObject currentUser = usersArray.getJSONObject(j);
                String name = currentUser.getString("name");
                int id = currentUser.getInt("id");
                users.add(new User(name,id));
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing JSON results", e);
        }
        return users;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        stringUrl = JSON_BASE + stringUrl;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static void copyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes = new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

}
