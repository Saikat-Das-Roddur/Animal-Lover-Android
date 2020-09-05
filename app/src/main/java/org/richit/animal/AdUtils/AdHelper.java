package org.richit.animal.AdUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import org.richit.animal.BuildConfig;

import java.io.IOException;


public class AdHelper {
    // TAG
    private static String TAG = "AdHelper";

    // Google ads //
    private static String admobAppId = "";
    private static String admobAppIdDemo = "ca-app-pub-3940256099942544~3347511713";

    // banner
    private static String admobBannerAds = "";
    private static String admobBannerAdsDemo = "ca-app-pub-3940256099942544/6300978111";

    // inter
    private static String admobInterAds = "";
    private static String admobInterAdsDemo = "ca-app-pub-3940256099942544/1033173712";

    // Reward Ads
    private static String admobRewardAds = "";
    private static String admobRewardAdsDemo = "ca-app-pub-3940256099942544/5224354917";

    // StartApp Ads
    private static String startAppAds = "";

    // Other vars
    private static boolean adsRemoved = false;
    private static int clickedCounter = 0;

    //
    public static void initAdmobIds(String admobAppIdReal, String admobBannerAdsReal, String admobInterAdsReal, String admobRewardAdsReal) {
        admobAppId = admobAppIdReal;
        admobBannerAds = admobBannerAdsReal;
        admobInterAds = admobInterAdsReal;
        admobRewardAds = admobRewardAdsReal;
    }

    public static void initAdmobIdsEnc(String admobAppIdReal, String admobBannerAdsReal, String admobInterAdsReal, String admobRewardAdsReal) {
        admobAppId = decrypt(admobAppIdReal);
        admobBannerAds = decrypt(admobBannerAdsReal);
        admobInterAds = decrypt(admobInterAdsReal);
        admobRewardAds = decrypt(admobRewardAdsReal);
    }

    //
    public static void initStartappId(String startAppAdsReal) {
        startAppAds = startAppAdsReal;
    }

    public static void initStartappIdEnc(String startAppAdsReal) {
        startAppAds = decrypt(startAppAdsReal);
    }

    //
    public static boolean isDebugMode() {
        return BuildConfig.DEBUG;
    }

    public static String decrypt(String enctext) {
        String decTxt = new String(Base64.decode(enctext, Base64.DEFAULT));
        Log.d(TAG, "decrypt: " + decTxt);
        return decTxt;
    }

    //
    public static String getAdmobAppId() {
        if (isDebugMode()) {
            Log.d(TAG, "getAdmobAppId: Debug");
            return admobAppIdDemo;
        } else {
            Log.d(TAG, "getAdmobAppId: Release");
            return admobAppId;
        }
    }

    public static String getAdmobBannerAdsId() {
        if (isDebugMode()) {
            Log.d(TAG, "getAdmobBannerAdsId: Debug");
            return admobBannerAdsDemo;
        } else {
            Log.d(TAG, "getAdmobBannerAdsId: Release");
            return admobBannerAds;
        }
    }

    public static String getAdmobInterAdsId() {
        if (isDebugMode()) {
            Log.d(TAG, "getAdmobInterAdsId: Debug");
            return admobInterAdsDemo;
        } else {
            Log.d(TAG, "getAdmobInterAdsId: Release");
            return admobInterAds;
        }
    }

    public static String getAdmobRewardAdsId() {
        if (isDebugMode()) {
            Log.d(TAG, "getAdmobRewardAdsId: Debug");
            return admobRewardAdsDemo;
        } else {
            Log.d(TAG, "getAdmobRewardAdsId: Release");
            return admobRewardAds;
        }
    }

    //
    public static String getStartAppAdsId() {
        Log.d(TAG, "getStartAppAdsId: ");
        if (startAppAds.isEmpty())
            return "209347656";
        else return startAppAds;
    }

    //
    public static boolean isAdsRemoved() {
        return adsRemoved;
    }

    public static void setAdsRemoved(boolean adsRemovedFn, LinearLayout linearLayout) {
        adsRemoved = adsRemovedFn;
        linearLayout.setVisibility(View.GONE);
    }

    public static void setClickedCounter(int clickedCounter) {
        AdHelper.clickedCounter = clickedCounter;
    }

    //
    public static boolean shouldShowInter() {
        Log.d(TAG, "shouldShowInter: " + clickedCounter);
        if (++clickedCounter % 4 == 0 && !isAdsRemoved()) {
            return true;
        } else return false;
    }

    //
    public static Handler handler = new Handler();
    private static Runnable mRunnable;

    public static void doSomethingAfter(double seconds, Runnable runnable) {
        handler.removeCallbacks(mRunnable);
        mRunnable = runnable;
        handler.postDelayed(runnable, (long) (seconds * 1000));
    }

    //
    public static void showAdvertisingID(final Context context) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
                    String adId = adInfo != null ? adInfo.getId() : null;
                    Log.d(TAG, "run: ID: " + adId);
                    // Use the advertising id
                } catch (IOException | GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException exception) {
                    // Error handling if needed
                    Log.d(TAG, "run: Exception: " + exception);
                }
            }
        });
    }
}
