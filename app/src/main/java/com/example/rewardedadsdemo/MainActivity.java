package com.example.rewardedadsdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class MainActivity extends AppCompatActivity {

    Button rewardedAdBtn;
    private RewardedAd rewardedAd;


    /**
     * - change app and unit ids
     */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

                System.out.println("onInitializationComplete...............");
            }
        });
        loadAd();


        rewardedAdBtn = findViewById(R.id.rewardAd);
        rewardedAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("rewardedAd....."+rewardedAd);
                if (rewardedAd != null) {
                    Activity activityContext = MainActivity.this;
                    rewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            // Handle the reward.
                            System.out.println("The user earned the reward.......");
//                            int rewardAmount = rewardItem.getAmount();
//                            String rewardType = rewardItem.getType();

                            rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdClicked() {
                                    // Called when a click is recorded for an ad.
                                }

                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    // Called when ad is dismissed.
                                    // Set the ad reference to null so you don't show the ad a second time.

//                                    rewardedAd = null;

                                    Intent intent = new Intent(MainActivity.this,Next_Activity.class);
                                    startActivity(intent);

                                    loadAd();
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    // Called when ad fails to show.
                                    rewardedAd = null;


                                    Intent intent = new Intent(MainActivity.this,Next_Activity.class);
                                    startActivity(intent);

                                }

                                @Override
                                public void onAdImpression() {
                                    // Called when an impression is recorded for an ad.
                                }

                                @Override
                                public void onAdShowedFullScreenContent() {
                                    // Called when ad is shown.
                                }
                            });


                        }
                    });
                } else {

                    Intent intent = new Intent(MainActivity.this,Next_Activity.class);
                    startActivity(intent);

                    System.out.println("The rewarded ad wasn't ready yet...........");
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        moveTaskToBack(true);
    }

    public void loadAd(){

        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",//change unit id........
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        System.out.println("loadAdError.toString()........."+loadAdError.toString());

                        rewardedAd = null;

                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {

                        System.out.println("Ad was loaded............");

                        rewardedAd = ad;
                    }
                });
    }
}