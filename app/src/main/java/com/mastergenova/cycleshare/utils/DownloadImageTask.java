package com.mastergenova.cycleshare.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.mastergenova.cycleshare.R;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    Context context;
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage, Context context) {
        this.bmImage = bmImage;
        this.context = context;
    }

    protected Bitmap doInBackground(String... urls) {
        String urlDisplay = urls[0];
        Bitmap mIcon = null;

        URL url = stringToURL(urlDisplay);
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream in = connection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
            mIcon = BitmapFactory.decodeStream(bufferedInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mIcon;
    }

    protected void onPostExecute(Bitmap result){
        if(result != null){
            bmImage.setImageBitmap(result);
        }else{
            bmImage.setImageResource(R.drawable.pngegg);
        }
    }

    protected URL stringToURL(String urlDisplay){
        URL url = null;
        try{
            url = new URL(urlDisplay);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

}