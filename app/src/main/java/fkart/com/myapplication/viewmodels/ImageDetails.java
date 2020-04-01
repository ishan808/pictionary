package fkart.com.myapplication.viewmodels;

public class ImageDetails {
    int mId;
    public String mImageURL;
    String mAnswer;
    int mDifficulty;

    ImageDetails(int id, int difficulty, String imageURL, String answer) {
        mId = id;
        mImageURL = imageURL;
        mAnswer = answer;
        mDifficulty = difficulty;
    }




}
