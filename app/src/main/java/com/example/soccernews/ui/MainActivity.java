package com.example.soccernews.ui;

import static com.google.firebase.analytics.FirebaseAnalytics.*;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.soccernews.R;
import com.example.soccernews.databinding.ActivityMainBinding;
import com.example.soccernews.databinding.FragmentNewsBinding;
import com.example.soccernews.databinding.NewsItemBinding;
import com.example.soccernews.ui.news.NewsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.analytics.FirebaseAnalytics;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAnalytics = getInstance(this);
        logEvent();

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_news, R.id.navigation_favorites)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    private void logEvent() {
        Bundle bundle = new Bundle();
        bundle.putString(Param.ITEM_ID, String.valueOf(binding.navView));
        firebaseAnalytics.logEvent(Event.SELECT_CONTENT, bundle);
    }
}