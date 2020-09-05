package org.richit.animal.AdUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.startapp.android.publish.ads.banner.Banner;
import com.startapp.android.publish.ads.banner.BannerListener;
import com.startapp.android.publish.ads.banner.Cover;
import com.startapp.android.publish.ads.banner.Mrec;
import com.startapp.android.publish.ads.splash.SplashConfig;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;
import com.startapp.android.publish.adsCommon.VideoListener;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;

public class MyStartAppAds {
    private static String TAG = "MyStartAppAds";

    public static void init(Activity prodhanActivity, Bundle savedInstanceState, int splash_screen, boolean showReturnAds) {
        Log.d(TAG, "init: ");
        StartAppSDK.init(prodhanActivity, AdHelper.getStartAppAdsId(), showReturnAds);

        if (splash_screen != 0) {
            StartAppAd.showSplash(prodhanActivity, savedInstanceState,
                    new SplashConfig()
                            .setTheme(SplashConfig.Theme.USER_DEFINED)
                            .setCustomScreen(splash_screen)
            );
        }
        StartAppAd.disableSplash();
    }

    public static class BannerAd {
        private String TAG = "MyStartAppAds: " + this.getClass().getSimpleName();

        private Banner banner;

        public BannerAd(Activity activity, LinearLayout linearLayoutAds, BannerListener listener) {
            Log.d(TAG, "BannerAd: ");
            if (listener == null)
                this.banner = new Banner(activity);
            else this.banner = new Banner(activity, listener);

            linearLayoutAds.removeAllViews();

            if (!AdHelper.isAdsRemoved())
                linearLayoutAds.addView(banner);
            else linearLayoutAds.setVisibility(View.GONE);
        }

        public BannerAd(Activity activity, LinearLayout linearLayoutAds, boolean clearAll, BannerListener listener) {
            Log.d(TAG, "BannerAd: ");
            if (listener == null)
                this.banner = new Banner(activity);
            else this.banner = new Banner(activity, listener);

            if (clearAll) {
                linearLayoutAds.removeAllViews();
            }

            if (!AdHelper.isAdsRemoved())
                linearLayoutAds.addView(banner);
            else linearLayoutAds.setVisibility(View.GONE);
        }
    }

    public static class MrecAd {
        private String TAG = "MyStartAppAds: " + this.getClass().getSimpleName();

        private Mrec mrec;

        public MrecAd(Activity activity, LinearLayout linearLayoutAds, BannerListener listener) {
            Log.d(TAG, "MrecAd: ");
            if (listener == null)
                this.mrec = new Mrec(activity);
            else this.mrec = new Mrec(activity, listener);

            linearLayoutAds.removeAllViews();

            if (!AdHelper.isAdsRemoved())
                linearLayoutAds.addView(mrec);
            else linearLayoutAds.setVisibility(View.GONE);
        }

        public MrecAd(Activity activity, LinearLayout linearLayoutAds, boolean clearAll, BannerListener listener) {
            Log.d(TAG, "MrecAd: ");
            if (listener == null)
                this.mrec = new Mrec(activity);
            else this.mrec = new Mrec(activity, listener);

            if (clearAll) {
                linearLayoutAds.removeAllViews();
            }

            if (!AdHelper.isAdsRemoved())
                linearLayoutAds.addView(mrec);
            else linearLayoutAds.setVisibility(View.GONE);
        }
    }

    public static class CoverAd {
        private String TAG = "MyStartAppAds: " + this.getClass().getSimpleName();

        private Cover cover;

        public CoverAd(Activity activity, LinearLayout linearLayoutAds, BannerListener listener) {
            Log.d(TAG, "CoverAd: ");
            if (listener == null)
                this.cover = new Cover(activity);
            else this.cover = new Cover(activity, listener);

            linearLayoutAds.removeAllViews();

            linearLayoutAds.addView(cover);
        }

        public CoverAd(Activity activity, LinearLayout linearLayoutAds, boolean clearAll, BannerListener listener) {
            Log.d(TAG, "CoverAd: ");
            if (listener == null)
                this.cover = new Cover(activity);
            else this.cover = new Cover(activity, listener);

            if (clearAll) {
                linearLayoutAds.removeAllViews();
            }

            linearLayoutAds.addView(cover);
        }
    }

    public static class InterAd {
        private String TAG = "MyStartAppAds: " + this.getClass().getSimpleName();
        private StartAppAd startAppAdInter;

        public InterAd(Context context, AdEventListener listener) {
            Log.d(TAG, "InterAd: ");
            this.startAppAdInter = new StartAppAd(context);

            startAppAdInter.loadAd(StartAppAd.AdMode.AUTOMATIC, listener);
        }
    }

    public static class Reward {
        private String TAG = "MyStartAppAds: " + this.getClass().getSimpleName();
        private StartAppAd startAppAdInter;

        public Reward(Context context, AdEventListener listener, VideoListener listener2) {
            Log.d(TAG, "InterAd: ");
            this.startAppAdInter = new StartAppAd(context);

            startAppAdInter.setVideoListener(listener2);
            startAppAdInter.loadAd(StartAppAd.AdMode.VIDEO, listener);
        }
    }
}
