package fkart.com.myapplication.viewmodels;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PicViewModel extends androidx.lifecycle.ViewModel {

    private ExecutorService mExecutorService;
    private HashMap<Integer, HashSet<ImageDetails>> mPicDetails;
    private Future<Boolean> mFetchTask;
    private Context mContext;
    private int mCurrentLevel;
    private int mCurrentImage;
    private int mImageShown;
    private int maximumImageToBeShown = 5;
    private final String JSON_FNAME = "pictionary.json";
    private String mExpectedAnswer;

    public PicViewModel(Context context){
        mExecutorService =  Executors.newSingleThreadExecutor();
        mPicDetails = new HashMap<>();
        mContext = context;

    }

    public void fetchData(int randomLevel) {
        mCurrentLevel = randomLevel;
        mImageShown = 0;
        mCurrentImage = 0;
        mFetchTask = mExecutorService.submit(new Callable<Boolean>() {

            @Override
            public Boolean call() throws Exception {

                mPicDetails.clear();
                String data = readJSONFromAsset();
                if(data == null) {
                    return false;
                }
                JSONArray jsonArray = new JSONArray(data);
                JSONObject jObject;
                for(int i=0;i<jsonArray.length();i++) {
                    jObject = jsonArray.getJSONObject(i);
                    int id = jObject.getInt("id");
                    int difficulty = jObject.getInt("difficulty");
                    String imageUrl = jObject.getString("imageUrl");
                    String answer = jObject.getString("answer");
                    if(!mPicDetails.containsKey(difficulty)) {
                        mPicDetails.put(difficulty, new HashSet<ImageDetails>());
                    }
                    mPicDetails.get(difficulty).add(new ImageDetails(id,difficulty,imageUrl,answer));
                }

                return true;
            }
        });

    }

    public String readJSONFromAsset() {
        String json = null;
        try {
            InputStream is = mContext.getAssets().open(JSON_FNAME);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    public ImageDetails getNextImage() {
        if(mImageShown == maximumImageToBeShown) {
            return null;
        }
        if(mCurrentLevel == 0) {
            // to show Error
            return null;
        }
        if(mCurrentLevel > 5) {
            mCurrentLevel = 5;
        }
        if(mPicDetails.get(mCurrentLevel).size() == 0) return null;
        ImageDetails imgDetails = mPicDetails.get(mCurrentLevel).iterator().next();
        mPicDetails.get(mCurrentLevel).remove(imgDetails);
        return imgDetails;
    }

    public void setScore(String answer){
        if(answer.equalsIgnoreCase(mExpectedAnswer)){
            mCurrentLevel += 1;
        } else {
            mCurrentLevel -= 1;
        }

    }



    public void timeUp() {
        mCurrentLevel -= 1;
    }
}
