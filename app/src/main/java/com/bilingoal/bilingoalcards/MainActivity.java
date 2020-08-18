package com.bilingoal.bilingoalcards;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;


import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, initializationStatus -> {});

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                interstitialAd.show();
            }
        });
        interstitialAd.loadAd(new AdRequest.Builder().build());

        Set<Integer> destinations = new HashSet<>();
        destinations.add(R.id.mainFragment);
        destinations.add(R.id.lessonFragment);
        destinations.add(R.id.resultFragment);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(destinations).build();
        navController = Navigation.findNavController(this, R.id.host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}