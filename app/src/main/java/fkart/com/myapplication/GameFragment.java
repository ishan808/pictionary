package fkart.com.myapplication;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.fragment.app.Fragment;
import fkart.com.myapplication.viewmodels.ImageDetails;
import fkart.com.myapplication.viewmodels.PicViewModel;

public class GameFragment extends Fragment {

    PicViewModel mPicViewModel;
    private long mTime;
    private TextView mTimerView;
    private Button mSubmitButton;
    private EditText mEditText;
    private ImageView mImageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.game_fragment, container, false);
        mTimerView = view.findViewById(R.id.timer);
        mSubmitButton = view.findViewById(R.id.submitButton);
        mEditText = view.findViewById(R.id.editText);
        mImageView = view.findViewById(R.id.imgView);

        //mPicViewModel =  TODO get view model from provider

        //ImageDetails imageDetails = mPicViewModel.getNextImage();
        //mImageView.setImageBitmap(mPicViewModel.getNextImage());
        Picasso.with(getActivity()).load("https://acdn.list25.com/wp-content/uploads/2015/03/australian-cattle-dog.jpg").into(mImageView);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mPicViewModel.setScore(mEditText.getText().toString());
            }
        });
        return view;
    }
    
    private void startTimer(){
        new CountDownTimer(mTime, 1000) {

            public void onTick(long millisUntilFinished) {

                mTimerView.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                mTimerView.setText("done!");
                //mPicViewModel.timeUp();
            }
        }.start();
        
    }
}
