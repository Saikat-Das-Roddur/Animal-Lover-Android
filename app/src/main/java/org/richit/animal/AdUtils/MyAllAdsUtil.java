package org.richit.animal.AdUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.startapp.android.publish.ads.banner.BannerListener;
import com.startapp.android.publish.adsCommon.Ad;
import com.startapp.android.publish.adsCommon.VideoListener;
import com.startapp.android.publish.adsCommon.adListeners.AdEventListener;

import org.richit.animal.Config;
import org.richit.animal.R;

import p32929.myhouseads2lib.HouseAds;

public class MyAllAdsUtil {
    private static String TAG = "MyAllAdsUtil";

    //
    private static String adsLog = "";
    private static int bannerAdsTried = 0;
    private static int interAdsTried = 0;
    private static int rewardAdsTried = 0;

    //
    public static String getAdsLog() {
        return MyAllAdsUtil.adsLog;
    }

    public static void setAdsLog(String adsLog) {
        MyAllAdsUtil.adsLog += adsLog + "\n";
    }

    //
    public static String GOOGLE = "GOOGLE";
    public static String STARTAPP = "STARTAPP";

    //
    public static HouseAds houseAds;

    //
    public static void init(Context context) {
        MyGoogleAds.init(context);
        MyStartAppAds.init((Activity) context, null, 0, false);
        houseAds = new HouseAds(context, Config.houseAdsUrl);
    }

    public static void init(Context context, boolean showStartappReturnAd) {
        MyGoogleAds.init(context);
        MyStartAppAds.init((Activity) context, null, 0, showStartappReturnAd);
        houseAds = new HouseAds(context, Config.houseAdsUrl);
    }

    public static void init(Context context, Bundle savedInstanceState, int splash_screen, boolean showStartappReturnAd) {
        MyGoogleAds.init(context);
        MyStartAppAds.init((Activity) context, savedInstanceState, splash_screen, showStartappReturnAd);
        houseAds = new HouseAds(context, Config.houseAdsUrl);
    }

    //
    public static void addAnyBannerAd(Context context, boolean clearAll, LinearLayout linearLayout, MyAdsListener listener) {
        switch (bannerAdsTried % 3) {
            case 0:
                addGoogleBannerAd(true, context, clearAll, linearLayout, listener);
                break;

            case 1:
                addStartappBannerAd(true, context, clearAll, linearLayout, listener);
                break;

            case 2:
                addBigStartappAds(false, context, clearAll, linearLayout, listener);
                break;
        }
        bannerAdsTried++;
    }

    public static void addAllBannerAds(Context context, LinearLayout linearLayout, MyAdsListener listener) {
        addGoogleBannerAd(false, context, false, linearLayout, listener);
        addStartappBannerAd(false, context, false, linearLayout, listener);
        addBigStartappAds(false, context, false, linearLayout, listener);
    }

    //
    public static void addGoogleBannerAd(final boolean next, final Context context, final boolean clearAll, final LinearLayout linearLayout, final MyAdsListener listener) {
        listener.onAnyTrying("B: " + GOOGLE);
        new MyGoogleAds.BannerAd(context, clearAll, linearLayout).load(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                listener.onAnyFailure("B: " + GOOGLE, i);

                if (next) {
                    addAnyBannerAd(context, clearAll, linearLayout, listener);
                } else {
                    listener.onTotalFailureBanner(houseAds, linearLayout);
                }
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                listener.onClosed("B: " + GOOGLE);
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                listener.onAnyClicked("B: " + GOOGLE);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                listener.onAnySuccess("B: " + GOOGLE);
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                listener.onAnyClicked("B: " + GOOGLE);
            }
        });
    }

    public static void addStartappBannerAd(final boolean next, final Context context, final boolean clearAll, final LinearLayout linearLayout, final MyAdsListener listener) {
        listener.onAnyTrying("B: " + STARTAPP);
        new MyStartAppAds.BannerAd((Activity) context, linearLayout, clearAll, new BannerListener() {
            @Override
            public void onReceiveAd(View view) {
                listener.onAnySuccess("B: " + STARTAPP);
            }

            @Override
            public void onFailedToReceiveAd(View view) {
                listener.onAnyFailure("B: " + STARTAPP, -99);

                if (next) {
                    addAnyBannerAd(context, clearAll, linearLayout, listener);
                } else {
                    listener.onTotalFailureBanner(houseAds, linearLayout);
                }
            }

            @Override
            public void onClick(View view) {
                listener.onAnyClicked("B: " + STARTAPP);
            }
        });
    }

    public static void addBigStartappAds(final boolean next, final Context context, final boolean clearAll, final LinearLayout linearLayout, final MyAdsListener listener) {
        listener.onAnyTrying(STARTAPP + "-BIG");
        new MyStartAppAds.MrecAd((Activity) context, linearLayout, clearAll, new BannerListener() {
            @Override
            public void onReceiveAd(View view) {
                listener.onAnySuccess(STARTAPP + "-BIG");
            }

            @Override
            public void onFailedToReceiveAd(View view) {
                listener.onAnyFailure(STARTAPP + "-BIGM", -99);

                new MyStartAppAds.CoverAd((Activity) context, linearLayout, clearAll, new BannerListener() {
                    @Override
                    public void onReceiveAd(View view) {
                        listener.onAnySuccess(STARTAPP + "-BIG");
                    }

                    @Override
                    public void onFailedToReceiveAd(View view) {
                        listener.onAnyFailure(STARTAPP + "-BIGC", -99);

                        if (next) {
                            addAnyBannerAd(context, clearAll, linearLayout, listener);
                        } else {
                            listener.onTotalFailureBanner(houseAds, linearLayout);
                        }
                    }

                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "onClick: ");
                        listener.onAnyClicked(STARTAPP + "-BIGC");
                    }
                });
            }

            @Override
            public void onClick(View view) {
                listener.onAnyClicked(STARTAPP + "-BIG");
            }
        });
    }

    public static boolean showInterIfOk(Context context, final MyAdsListener listener) {
        if (AdHelper.shouldShowInter()) {
            showAnyInter(context, listener);
            return true;
        }
        return false;
    }

    //
    public static void showAnyInter(Context context, final MyAdsListener listener) {
        switch (interAdsTried % 2) {
            case 0:
                showGoogleInter(true, context, listener);
                break;

            case 1:
                showStartappInter(false, context, listener);
                break;
        }
        interAdsTried++;
    }

    public static void showGoogleInter(final boolean next, final Context context, final MyAdsListener listener) {
        listener.onAnyTrying("I: " + GOOGLE);
        final MyGoogleAds.InterAd interAd = new MyGoogleAds.InterAd(context);
        interAd.load(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                listener.onAnyFailure("I: " + GOOGLE, i);

                if (next) {
                    showAnyInter(context, listener);
                } else {
                    listener.onTotalFailureInter();
                }
            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();
                listener.onAnyClicked("I: " + GOOGLE);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                listener.onAnySuccess("I: " + GOOGLE);
                interAd.show();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                listener.onAnyClicked("I: " + GOOGLE);
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                listener.onClosed("I: " + GOOGLE);
            }
        });
    }

    public static void showStartappInter(final boolean next, final Context context, final MyAdsListener listener) {
        listener.onAnyTrying("I: " + STARTAPP);
        new MyStartAppAds.InterAd(context, new AdEventListener() {
            @Override
            public void onReceiveAd(Ad ad) {
                listener.onAnySuccess("I: " + STARTAPP);
                ad.show();
            }

            @Override
            public void onFailedToReceiveAd(Ad ad) {
                listener.onAnyFailure("I: " + STARTAPP, -99);

                if (next) {
                    showAnyInter(context, listener);
                } else {
                    listener.onTotalFailureInter();
                }
            }
        });
    }

    //
    public static void showAnyReward(Context context, final MyAdsListener listener) {
        switch (rewardAdsTried % 2) {
            case 0:
                showGoogleReward(true, context, listener);
                break;

            case 1:
                showStartappReward(false, context, listener);
                break;
        }
        rewardAdsTried++;
    }

    public static void showGoogleReward(final boolean next, final Context context, final MyAdsListener listener) {
        listener.onAnyTrying("R: " + GOOGLE);
        final MyGoogleAds.RewardAd rewardAd = new MyGoogleAds.RewardAd(context);
        rewardAd.load(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                listener.onAnySuccess("R: " + GOOGLE);
                rewardAd.show();
            }

            @Override
            public void onRewardedVideoAdOpened() {
                //
            }

            @Override
            public void onRewardedVideoStarted() {
                //
            }

            @Override
            public void onRewardedVideoAdClosed() {
                listener.onClosed("R: " + GOOGLE);
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                listener.onAnyRewarded("R: " + GOOGLE);
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                listener.onAnyClicked("R: " + GOOGLE);
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                listener.onAnyFailure("R: " + GOOGLE, i);

                if (next) {
                    showAnyReward(context, listener);
                } else {
                    listener.onTotalFailureReward();
                }
            }

            @Override
            public void onRewardedVideoCompleted() {
                //
            }
        });
    }

    public static void showStartappReward(final boolean next, final Context context, final MyAdsListener listener) {
        listener.onAnyTrying("R: " + STARTAPP);
        new MyStartAppAds.Reward(context, new AdEventListener() {
            @Override
            public void onReceiveAd(Ad ad) {
                listener.onAnySuccess("R: " + STARTAPP);
                ad.show();
            }

            @Override
            public void onFailedToReceiveAd(Ad ad) {
                listener.onAnyFailure("R: " + STARTAPP, -99);

                if (next) {
                    showAnyReward(context, listener);
                } else {
                    listener.onTotalFailureReward();
                }
            }
        }, new VideoListener() {
            @Override
            public void onVideoCompleted() {
                listener.onAnyRewarded("R: " + STARTAPP);
            }
        });
    }

    //
    public static void addBannerAdsInDialog(Context context, AlertDialog.Builder builder) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_ad_container, null);
        builder.setView(view);

        LinearLayout linearLayout = view.findViewById(R.id.adsLL);
        addAnyBannerAd(context, true, linearLayout, new MyAdsListener());
    }

    //
    private static int shareClickCount = 0;

    public static void showAdsLogDialog(Context context) {
        if (++shareClickCount % 3 == 0) {
            new AlertDialog.Builder(context)
                    .setTitle("Ads LOG")
                    .setMessage("" + getAdsLog())
                    .show();
        }
    }
}
