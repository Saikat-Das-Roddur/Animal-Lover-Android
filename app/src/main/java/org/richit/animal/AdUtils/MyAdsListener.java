package org.richit.animal.AdUtils;

import android.util.Log;
import android.widget.LinearLayout;

import p32929.myhouseads2lib.HouseAds;

public class MyAdsListener {

    private String TAG = this.getClass().getSimpleName();

    public void onAnyTrying(String which) {
        Log.d(TAG, "onAnyTrying: " + which);
        MyAllAdsUtil.setAdsLog("onAnyTrying: " + which);
    }

    public void onAnySuccess(String which) {
        Log.d(TAG, "onAnySuccess: " + which);
        MyAllAdsUtil.setAdsLog("onAnySuccess: " + which);
    }

    public void onAnyFailure(String which, int errorCode) {
        Log.d(TAG, "onAnyFailure: " + which + " Error: " + errorCode);
        MyAllAdsUtil.setAdsLog("onAnyFailure: " + which + " Error: " + errorCode);
    }

    public void onAnyClicked(String which) {
        Log.d(TAG, "onAnyClicked: " + which);
        MyAllAdsUtil.setAdsLog("onAnyClicked: " + which);
    }

    public void onAnyRewarded(String which) {
        Log.d(TAG, "onAnyRewarded: " + which);
        MyAllAdsUtil.setAdsLog("onAnyRewarded: " + which);
    }

    public void onClosed(String which) {
        Log.d(TAG, "onClosed: " + which);
        MyAllAdsUtil.setAdsLog("onClosed: " + which);
    }

    public void onTotalFailureBanner(HouseAds houseAds, LinearLayout linearLayoutAds) {
        Log.d(TAG, "onTotalFailureBanner: ");
        MyAllAdsUtil.setAdsLog("onTotalFailureBanner: ");
        try {
            houseAds.putBannerAds(linearLayoutAds);
        } catch (Exception e) {
            //
        }
    }

    public void onTotalFailureInter() {
        Log.d(TAG, "onTotalFailureInter: ");
        MyAllAdsUtil.setAdsLog("onTotalFailureInter: ");
    }

    public void onTotalFailureReward() {
        Log.d(TAG, "onTotalFailureReward: ");
        MyAllAdsUtil.setAdsLog("onTotalFailureReward: ");
    }
}
