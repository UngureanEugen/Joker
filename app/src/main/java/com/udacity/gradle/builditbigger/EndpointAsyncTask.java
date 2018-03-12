package com.udacity.gradle.builditbigger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.com.jokedisplay.JokeActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.widget.ProgressBar;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import java.io.IOException;

public class EndpointAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private static MyApi myApiService = null;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    @SuppressLint("StaticFieldLeak")
    private ProgressBar progressBar;

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if (!isCancelled()) {
            if (myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(
                            AbstractGoogleClientRequest<?> abstractGoogleClientRequest)
                            throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
                // end options for devappserver

                myApiService = builder.build();
            }
            context = params[0].first;
            String name = params[0].second;
            publishProgress();
            try {
                return myApiService.sayJoke(name).execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }
        return "";
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        progressBar = ((Activity) context).findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(String joke) {
        try {
            if (!isCancelled() && context != null) {
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(context, JokeActivity.class);
                intent.putExtra(JokeActivity.JOKE_KEY, joke);
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            progressBar = null;
            context = null;
        }
    }
}
