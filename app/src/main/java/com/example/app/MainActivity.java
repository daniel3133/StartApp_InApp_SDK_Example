package com.example.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.startapp.sdk.adsbase.Ad;
import com.startapp.sdk.adsbase.StartAppAd;
import com.startapp.sdk.adsbase.StartAppSDK;
import com.startapp.sdk.adsbase.VideoListener;
import com.startapp.sdk.adsbase.adlisteners.AdEventListener;
import com.startapp.sdk.adsbase.adlisteners.AdDisplayListener;

@SuppressLint("SetTextI18n")
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        // NOTE always use test ads during development and testing
        StartAppSDK.setTestAdsEnabled(BuildConfig.DEBUG);

        setContentView(R.layout.main);
    }

    public void showInterstitial(View view) {
        final StartAppAd interstitial = new StartAppAd(this);

        interstitial.setAdDisplayListener(new AdDisplayListener() {
            @Override
            public void adHidden(Ad ad) {
                startActivity(new Intent(MainActivity.this, OtherActivity.class));
            }

            @Override
            public void adDisplayed(Ad ad) { /* no-op */ }

            @Override
            public void adClicked(Ad ad) { /* no-op */ }

            @Override
            public void adNotDisplayed(Ad ad) {
                startActivity(new Intent(MainActivity.this, OtherActivity.class));
            }
        });

        interstitial.loadAd(StartAppAd.AdMode.AUTOMATIC, new AdEventListener() {
            @Override
            public void onReceiveAd(Ad ad) {
                interstitial.showAd();
            }

            @Override
            public void onFailedToReceiveAd(Ad ad) {
                startActivity(new Intent(MainActivity.this, OtherActivity.class));
            }
        });
    }

    public void showRewardedVideo(View view) {
        final StartAppAd rewardedVideo = new StartAppAd(this);

        rewardedVideo.setVideoListener(new VideoListener() {
            @Override
            public void onVideoCompleted() {
                Toast.makeText(getApplicationContext(), "Grant the reward to user", Toast.LENGTH_SHORT).show();
            }
        });

        rewardedVideo.loadAd(StartAppAd.AdMode.REWARDED_VIDEO, new AdEventListener() {
            @Override
            public void onReceiveAd(Ad ad) {
                rewardedVideo.showAd();
            }

            @Override
            public void onFailedToReceiveAd(Ad ad) {
                Toast.makeText(getApplicationContext(), "Can't show rewarded video", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showRecyclerViewWithBanner(View view) {
        startActivity(new Intent(this, RecyclerViewWithBannerActivity.class));
    }

    public void showRecyclerViewWithNativeAd(View view) {
        startActivity(new Intent(this, RecyclerViewWithNativeAdActivity.class));
    }
}
