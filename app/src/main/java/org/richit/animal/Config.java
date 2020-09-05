package org.richit.animal;

public class Config {

    public static String admobAppId = "";

    // banner
    public static String admobBannerAds = "";

    // inter
    public static String admobInterAds = "";

    // Reward Ads
    public static String admobRewardAds = "";

    // StartApp Ads
    public static String startAppAds = "";

    /*YouTube Video Url for watching you tube video
     * after the '=' sign in this Url we can add the content what we want to view */
    public static String youTubeVideoUrl = "https://www.youtube.com/watch?v=";

    /*Add YouTube features to your application, including
     * the ability to upload videos, create and manage playlists, and more.
     * check the documentations for getting your own youtube api
     * https://developers.google.com/youtube/v3/
     * */
    public static String youTubeApiUrl = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=1&key=AIzaSyBMETrltp2CUUA4yIkYKLDspu1u5ESsk3I&q=";

    // For showing interstitial ads only
    // For more details please check https://github.com/p32929/HouseAds2
    public static String houseAdsUrl = "https://raw.githubusercontent.com/p32929/SomeHowTosAndTexts/master/HouseAdsJson/house_ads2.json";
    public static String feedbackEmail = "saikatd397@gmail.com";

    //About Url--> This url contain developers details
    //For more details please check https://github.com/p32929/OfficeAbout
    public static String officeAboutUrl = "https://raw.githubusercontent.com/p32929/SomeHowTosAndTexts/master/Office/OfficeInfoMaterial.json";

    /*Animal related Urls
     * Animal Image Path url used for animal to get animal images
     * All kinds of animal data collected from "https://a-z-animals.com/"
     * Animal Data Url is a bunch of urls that are JSON Data collected by using
     * web scraping from "https://a-z-animals.com/" site
     * You can get those data here https://github.com/rich-it/OnlineData/tree/master/Tesla/Animal%20Lover */
    public static String animalImagePath = "https://a-z-animals.com/";
    public static String animalsDataUrl[] = {
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/allanimal.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/mammalia.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/amphibia.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/actinopterygii.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/aves.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/insecta.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/reptilia.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/arachnida.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/crustacea.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/chondrichthyes.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/osteichthyes.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/urochordata.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/anthozoa.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/asteroidea.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/bivalvia.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/demospongiae.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/echinoidea.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/gastropoda.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/holothuroidea.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/lissamphibia.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/malacostraca.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/merostomata.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/myriapoda.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/sauropsida.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/scyphozoa.json",
            "https://raw.githubusercontent.com/rich-it/OnlineData/master/Tesla/Animal%20Lover/cephalopoda.json"


    };

}
