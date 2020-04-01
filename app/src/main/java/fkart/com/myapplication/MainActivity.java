package fkart.com.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import fkart.com.myapplication.viewmodels.PicViewModel;

import android.os.Bundle;

public class MainActivity extends FragmentActivity  {

    PicViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = new PicViewModel(getApplicationContext());
        mViewModel.fetchData(3);
        GameFragment fragment = new GameFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.game_container, fragment).commit();

    }




}
