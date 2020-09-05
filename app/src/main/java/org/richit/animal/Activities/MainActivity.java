package org.richit.animal.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.json.JSONException;
import org.json.JSONObject;
import org.richit.animal.AdUtils.AdHelper;
import org.richit.animal.AdUtils.MyAdsListener;
import org.richit.animal.AdUtils.MyAllAdsUtil;
import org.richit.animal.Adapters.AnimalAdapter;
import org.richit.animal.Database.FavouriteDB;
import org.richit.animal.Models.AnimalModel;
import org.richit.animal.Models.OnlineData;
import org.richit.animal.Config;
import org.richit.animal.Others.GlobaMethods;
import org.richit.animal.Others.GlobalVariables;
import org.richit.animal.R;
import org.richit.materialofficeaboutlib.Others.LoadListener;
import org.richit.materialofficeaboutlib.Others.OfficeAboutHelper;

import java.util.ArrayList;
import java.util.Collections;

import p32929.myhouseads2lib.FayazSP;
import p32929.myhouseads2lib.HouseAds;
import p32929.myhouseads2lib.InterListener;
import p32929.updaterlib.AppUpdater;
import p32929.updaterlib.UpdateListener;
import p32929.updaterlib.UpdateModel;

public class MainActivity extends AppCompatActivity {

    // Views
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    ImageView imageViewFavourite, imageViewBack, imageViewSearch;
    EditText editTextSearch;
    LinearLayout linearLayoutAds;

    // Adapters
    AnimalAdapter animalAdapter;

    // External Library Classes
    HouseAds houseAds;
    OfficeAboutHelper officeAboutHelper;
    Drawer drawerResources;

    // Data variables
    OnlineData onlineData;
    String jsonData = "";
    int drawerItemPosition = 0;
    ArrayList<AnimalModel> animals = new ArrayList<>();
    boolean inFavourite = false;
    boolean inSearch = true;
    boolean isDataLoaded = true;

    // these variables needed persistance
    private static final int FLAG_SHOWING_ANIMAL_IN_LIST_ITEM = 0;
    private static final int FLAG_SHOWING_AD_IN_LIST_ITEM = 1;

    // Dummy
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);

        //Initialize Shared Preference for showing data if network unavailable
        FayazSP.init(this);

        //Initialize FavouriteDB for adding data to favourite list
        FavouriteDB.init(this);

        //Getting drawerResources item navDrawerCurrentPosition from Animal Adapter Class
        drawerItemPosition = getIntent().getIntExtra("navDrawerCurrentPosition", 0);

        //Initializing Views
        imageViewFavourite = findViewById(R.id.ivFavourite);
        imageViewBack = findViewById(R.id.ivLeft);
        imageViewSearch = findViewById(R.id.ivSearch);
        editTextSearch = findViewById(R.id.app_name);
        linearLayoutAds = findViewById(R.id.adsLL);
        recyclerView = findViewById(R.id.recyclerView);

        //Setting views properties
        imageViewBack.setImageResource(R.drawable.ic_menu_white_18dp);
        editTextSearch.setEnabled(false);
        editTextSearch.setHint(R.string.app_name);

        //Progress Dialog settings
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        //See Config.java for know more about OfficeAboutHelper
        officeAboutHelper = new OfficeAboutHelper(this, Config.officeAboutUrl);

        //Ads settings
        //See Config.java to know more about HouseAds
        houseAds = new HouseAds(this,
                Config.houseAdsUrl
        );
        houseAds.setFeedbackEmail(Config.feedbackEmail);
        houseAds.setMenInBlack(true);
        houseAds.setListener(new InterListener() {
            @Override
            public void onShow(LinearLayout linearLayoutAboveList) {
                MyAllAdsUtil.addAnyBannerAd(MainActivity.this, true, linearLayoutAboveList, new MyAdsListener());
            }
        });

        //Adapter settings
        animalAdapter = new AnimalAdapter(this, animals);
        recyclerView.setAdapter(animalAdapter);

        //Layout settings
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        //Here we add ads after a specific grid span count
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            //Set Ads after a specific grid span count
            public int getSpanSize(int position) {
                switch (animalAdapter.getItemViewType(position)) {
                    case FLAG_SHOWING_ANIMAL_IN_LIST_ITEM:
                        return 1;
                    case FLAG_SHOWING_AD_IN_LIST_ITEM:
                        return gridLayoutManager.getSpanCount();
                    default:
                        return -1;
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);

        //Hiding Keyboard at the beginning
        //At the beginning of the app it focused on the search bar first and open the input method
        //by using this we force to hide the input method not to open at he beginning of the app
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
        );

        //Setting View listeners
        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //by clicking search button user can change the search mode
                if (inSearch) {
                    onSearch();

                    //Show keyboard when searching
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, InputMethodManager.SHOW_IMPLICIT);

                } else {
                    notOnSearch();
                    //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_IMPLICIT);
                }
            }
        });


        imageViewFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inFavourite) {

                    //if user in favourite mode and intended to go back to the main activity
                    onBack();
                } else {

                    //Otherwise if the user click that image view it'll set the image view icon as menu
                    //and open the drawerResources so that we don't need to add extra image icon at the same navDrawerCurrentPosition
                    editTextSearch.setHint(getName());
                    notOnSearch();
                    drawerResources.openDrawer();
                }

            }
        });
        populateData();
        addDrawerComponent();
    }

    // function to avoid searching
    private void notOnSearch() {
        //by changing insSearch to true , set the search icon and set the search bar disabled and
        // set the search bar text as the Animal's class name that we get from getName() method
        imageViewSearch.setImageResource(R.drawable.ic_magnify_white_24dp);
        imageViewFavourite.setVisibility(View.VISIBLE);
        editTextSearch.setHint(getName());
        editTextSearch.setText("");
        editTextSearch.setEnabled(false);
        inSearch = true;
    }

    //Searching animal by typing animal's name
    private void onSearch() {

        //Here we enabling the searching mode for user by setting search bar hint, search image view
        editTextSearch.setHint("Search");
        imageViewFavourite.setVisibility(View.GONE);
        imageViewSearch.setImageResource(R.drawable.ic_close_circle_outline_white_24dp);
        editTextSearch.setEnabled(true);
        editTextSearch.requestFocus();

        //If user is on searching anything then setting it to not searching mode after the search is end
        inSearch = false;
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //Typing character for search
                search(s.toString());
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    //Name of Animal Classes
    private int getName() {
        if (GlobalVariables.navDrawerCurrentPosition == 0) return R.string.app_name;
        else if (GlobalVariables.navDrawerCurrentPosition == 1) return R.string.mammalia;
        else if (GlobalVariables.navDrawerCurrentPosition == 2) return R.string.amphibia;
        else if (GlobalVariables.navDrawerCurrentPosition == 3) return R.string.actinopterygii;
        else if (GlobalVariables.navDrawerCurrentPosition == 4) return R.string.aves;
        else if (GlobalVariables.navDrawerCurrentPosition == 5) return R.string.insecta;
        else if (GlobalVariables.navDrawerCurrentPosition == 6) return R.string.reptilia;
        else if (GlobalVariables.navDrawerCurrentPosition == 7) return R.string.arachnida;
        else if (GlobalVariables.navDrawerCurrentPosition == 8) return R.string.crustacea;
        else if (GlobalVariables.navDrawerCurrentPosition == 9) return R.string.chondrichthyes;
        else if (GlobalVariables.navDrawerCurrentPosition == 10) return R.string.osteichthyes;
        else if (GlobalVariables.navDrawerCurrentPosition == 11) return R.string.urochordata;
        else if (GlobalVariables.navDrawerCurrentPosition == 12) return R.string.anthozoa;
        else if (GlobalVariables.navDrawerCurrentPosition == 13) return R.string.asteroidea;
        else if (GlobalVariables.navDrawerCurrentPosition == 14) return R.string.bivalvia;
        else if (GlobalVariables.navDrawerCurrentPosition == 15) return R.string.demospongiae;
        else if (GlobalVariables.navDrawerCurrentPosition == 16) return R.string.echinoidea;
        else if (GlobalVariables.navDrawerCurrentPosition == 17) return R.string.gastropoda;
        else if (GlobalVariables.navDrawerCurrentPosition == 18) return R.string.holothuroidea;
        else if (GlobalVariables.navDrawerCurrentPosition == 19) return R.string.lissamphibia;
        else if (GlobalVariables.navDrawerCurrentPosition == 20) return R.string.malacostraca;
        else if (GlobalVariables.navDrawerCurrentPosition == 21) return R.string.merostomata;
        else if (GlobalVariables.navDrawerCurrentPosition == 22) return R.string.myriapoda;
        else if (GlobalVariables.navDrawerCurrentPosition == 23) return R.string.sauropsida;
        else if (GlobalVariables.navDrawerCurrentPosition == 24) return R.string.scyphozoa;
        else if (GlobalVariables.navDrawerCurrentPosition == 25) return R.string.cephalopoda;
        else return R.string.search;
    }


    //Adding component to Navigation Drawer
    private void addDrawerComponent() {
        int identifierCount = 1;
        //Check if there is no component in the drawerResources
        if (drawerResources == null)
            drawerResources = new DrawerBuilder()
                    .withActivity(this).addDrawerItems(
                            new PrimaryDrawerItem().withName(R.string.allanimals).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.mammalia).withIcon(R.drawable.lion).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.amphibia).withIcon(R.drawable.frog).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.actinopterygii).withIcon(R.drawable.surgeon_fish).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.aves).withIcon(R.drawable.hummingbird).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.insecta).withIcon(R.drawable.ladybird).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.reptilia).withIcon(R.drawable.gecko).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.arachnida).withIcon(R.drawable.scorpion).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.crustacea).withIcon(R.drawable.crustacea).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.chondrichthyes).withIcon(R.drawable.hummerhead).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.osteichthyes).withIcon(R.drawable.catfish).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.urochordata).withIcon(R.drawable.sponge).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.anthozoa).withIcon(R.drawable.anthozoa).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.asteroidea).withIcon(R.drawable.asteroidea).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.bivalvia).withIcon(R.drawable.bivalvia).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.demospongiae).withIcon(R.drawable.sponge1).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.echinoidea).withIcon(R.drawable.echinoidea).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.gastropoda).withIcon(R.drawable.gastropoda).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.holothuroidea).withIcon(R.drawable.holothuroidea).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.lissamphibia).withIcon(R.drawable.lissamphibia).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.malacostraca).withIcon(R.drawable.malacostraca).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.merostomata).withIcon(R.drawable.merostomata).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.myriapoda).withIcon(R.drawable.myriapoda).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.sauropsida).withIcon(R.drawable.crocodile).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.scyphozoa).withIcon(R.drawable.scyphozo).withIdentifier(identifierCount++),
                            new SecondaryDrawerItem().withName(R.string.cephalopoda).withIcon(R.drawable.cephalopoda).withIdentifier(identifierCount++)
                    )
                    .withHeader(R.layout.header)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            //Set
                            GlobalVariables.navDrawerCurrentPosition = drawerItemPosition = position - 1;

                            //populate specific name by clicking individual drawerResources item
                            editTextSearch.setHint(getName());

                            //Set user is not searching
                            notOnSearch();

                            //populating the animal data
                            populateData();
                            MyAllAdsUtil.showInterIfOk(MainActivity.this, new MyAdsListener());
                            return false;
                        }
                    }).build();
    }


    public void showMenu(View view) {
        //Showing the pop up menu top right corner of the app
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.mainmenu, popup.getMenu());
        popup.setGravity(Gravity.RIGHT);
        popup.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        onBack();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Check if the user is in favourite mode when the app is in on resume state
        if (inFavourite) {

            //if the user is in favourite mode then check the favourite data list is not empty or not
            //if favourite data list is not empty then user can see the favourite list data
            //otherwise app showing online data
            if (FavouriteDB.getData().size() > 0) {
                editTextSearch.setHint(R.string.favouriteList);
                imageViewSearch.setVisibility(View.GONE);
                imageViewBack.setImageResource(R.drawable.ic_keyboard_backspace_white_24dp);
                animalAdapter.setAnimals(FavouriteDB.getData());
                recyclerView.smoothScrollToPosition(0);
            } else {
                imageViewBack.performClick();
                animalAdapter.setAnimals(onlineData.getData());
                recyclerView.smoothScrollToPosition(0);
            }
        }

    }

    public void onBack() {

        //Here we checking if user in favourite list showing mode or not
        if (inFavourite) {

            //if user in favourite list mode then set the favourite list mode false before we back to our main list
            inFavourite = false;

            //Setting the image view icon as menu so that it can perform drawerResources operation further
            imageViewBack.setImageResource(R.drawable.ic_menu_white_24dp);

            //By using setVisibility property we can show/hide search image icon and the search bar
            //Here we use setVisibility property so that we can show our animal favourite list
            // at the same layout where we are showing all our animal contents by using single button click
            editTextSearch.setVisibility(View.VISIBLE);
            imageViewSearch.setVisibility(View.VISIBLE);

            //This is for searching the animal name in favourite list
            editTextSearch.setHint(getName());
            recyclerView.smoothScrollToPosition(0);
            animalAdapter.setAnimals(onlineData.getData());
        } else if (!editTextSearch.getText().toString().isEmpty()) {

            editTextSearch.setText("");
        } else {

            //This houseAds are showing if user finally intended to exit the app
            houseAds.shuffleBeforeShowingDialog();
            houseAds.showInterAds();
        }

    }


    private void populateData() {
        if (isOnline()) {

            //Showing data from online when device is connected with online
            if (isDataLoaded) {

                //showing dialog for the first time when the data loaded
                progressDialog.show();
                isDataLoaded = false;
            }

            Log.d(TAG, "populateData: ");

            //Update data when device is connected with the online
            new AppUpdater(this, Config.animalsDataUrl[drawerItemPosition], new UpdateListener() {
                @Override
                public void onJsonDataReceived(UpdateModel updateModel, JSONObject jsonObject) {
                    Log.d(TAG, "onJsonDataReceived: " + jsonObject);

                    //onlineData are those retrieved JSON data that uploaded to github
                    //Here we use Gson for parsing the data from internet
                    //Because it's easy for parsing JSON data by using GSON library
                    //You can use your own JSON data parsing library
                    onlineData = new Gson().fromJson(jsonObject.toString(), OnlineData.class);

                    //Shuffling data that get from github via OnlineData class for better user view
                    Collections.shuffle(onlineData.getData());
                    animalAdapter.setAnimals(onlineData.getData());

                    //Save data to device using FayazSP for further retrieving data
                    FayazSP.put("json", jsonObject.toString());
                    progressDialog.dismiss();

                    recyclerView.smoothScrollToPosition(0);

                    try {

                        //Getting Encrypted ad IDs
                        String gs = jsonObject.getString("gs");
                        Log.d(TAG, "onJsonDataReceived: " + gs);

                        //Initializing the ads
                        initAds(gs);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String error) {
                    Log.d(TAG, "onError: " + error);
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Error loading.", Toast.LENGTH_SHORT).show();
                }
            }).execute();
        } else {

            //getting data from device shared preference
            jsonData = FayazSP.getString("json", "");

            //Check if there is any data in shared preferences
            if (jsonData.isEmpty()) {

                //If there is no data in shared preference then show alert for connecting the device with internet
                showAlertDialog();
            } else {

                //if there is data in shared preferences then set that data to the Online data model
                //
                onlineData = new Gson().fromJson(jsonData, OnlineData.class);

                //Set the retrieved data to animal adapter for showing the data
                animalAdapter.setAnimals(onlineData.getData());
                Toast.makeText(this, "All images may not show without internet", Toast.LENGTH_LONG).show();
                recyclerView.smoothScrollToPosition(0);
            }
        }


    }

    //Alert for internet connection
    private void showAlertDialog() {

        //This dialog will be shown if the device is not connected with the online
        new AlertDialog.Builder(this).setTitle("No Internet Connection")
                .setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (isOnline()) {

                            //If the device is online then it can populate data from the github
                            populateData();
                        } else {
                            finish();
                        }
                    }
                }).show();
    }

    //Showing favourite list items
    public void itemShowFavourite(MenuItem item) {

        //Showing Interstitial Ads at the top of the favourite item list
        MyAllAdsUtil.showInterIfOk(MainActivity.this, new MyAdsListener());

        //Inserting Favourite animal data for showing the favourite animals that added by the user in FavouriteDB
        ArrayList<AnimalModel> animals = FavouriteDB.getData();
        if (animals.size() > 0) {
            inFavourite = true;

            //By using setVisibility property we can hide search image icon and show the search bar
            //Here we use setVisibility property so that we can show our animal favourite list
            //at the same layout where we are showing all our animal contents by using single button click
            editTextSearch.setVisibility(View.VISIBLE);
            editTextSearch.setHint(R.string.favouriteList);
            imageViewSearch.setVisibility(View.GONE);

            //Changing the top left image view icon
            imageViewBack.setImageResource(R.drawable.ic_keyboard_backspace_white_18dp);

            //Set favourite data list to the adapter so that user can see his listed data
            animalAdapter.setAnimals(animals);
            recyclerView.smoothScrollToPosition(0);
        } else {
            Toast.makeText(this, "No favourite data added", Toast.LENGTH_SHORT).show();
        }
    }

    public void itemRate(MenuItem item) {

        //This is for how user rate the app
        //It'll be google ratings or any other app store rating
        houseAds.showRateDialog();
    }


    //Support developer by watching ads
    public void itemSupport(MenuItem item) {

        //This alert dialog contain ads for supporting the developer
        //if user watch a video from this app, developer can get little cent from google or any other app store
        // where the app is uploaded
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Support us")
                .setCancelable(false)
                .setMessage("You can support us by watching a video ad. Would you like to continue?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    //By selecting okay button you can help the developer by watching ads
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Thank you for your support", Toast.LENGTH_SHORT).show();

                        //Showing the Interstitial Ads
                        MyAllAdsUtil.showAnyInter(MainActivity.this, new MyAdsListener());

                        //Showing the Reward app into a given time limit
                        MyAllAdsUtil.showAnyReward(MainActivity.this, new MyAdsListener() {
                            @Override
                            public void onAnyRewarded(String which) {
                                super.onAnyRewarded(which);
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onTotalFailureReward() {
                                super.onTotalFailureReward();
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onClosed(String which) {
                                super.onClosed(which);
                                progressDialog.dismiss();
                            }
                        });
                        progressDialog.show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MyAllAdsUtil.showAnyInter(MainActivity.this, new MyAdsListener());
                    }
                });

        MyAllAdsUtil.addBannerAdsInDialog(this, builder);
        builder.show();

    }

    //report bug to the developer if there is any
    public void itemBug(MenuItem item) {

        //Showing the Interstitial Ads
        MyAllAdsUtil.showInterIfOk(MainActivity.this, new MyAdsListener());

        //This is for sending feedback or any bug reports about the app to the developer
        GlobaMethods.sendEmailFeedback(this, "", "saikatd397@gmail.com");
    }

    //Showing about of the developer team
    public void itemAbout(MenuItem item) {

        //Showing the Interstitial Ads
        MyAllAdsUtil.showInterIfOk(MainActivity.this, new MyAdsListener());

        //this is for the details of the developer team you can check here https://github.com/p32929/OfficeAbout
        officeAboutHelper.showAboutActivity(true, new LoadListener() {
            @Override
            public void onLoad(LinearLayout linearLayoutDummy) {

                //Show BannerAds at the beginning when the data is loaded
                MyAllAdsUtil.addAllBannerAds(MainActivity.this, linearLayoutDummy, new MyAdsListener());
            }

            @Override
            public void onError(String error) {
                Toast.makeText(MainActivity.this, "" + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Check the internet connection for retrieving data from online
    public boolean isOnline() {

        //Get the connectivity service from the device
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        //Return true if there is no connectivity issues with the internet
        return info != null && info.isConnected();

    }

    public void search(String text) {

        //If online data is available then proceed for search
        if (onlineData != null) {

            //Create a list of animal data from data model
            ArrayList<AnimalModel> animalList = new ArrayList<>();

            //Loop through each data that comes from online
            for (AnimalModel animal : onlineData.getData()) {

                //check if search text contains the animal name if the name is available to online data
                if (animal.getName().toLowerCase().contains(text.toLowerCase()))
                    animalList.add(animal);
            }

            //If there is animal data then set it to animal adapter so that user can view the specific data he intended to see
            animalAdapter.setAnimals(animalList);
        }
    }

    public void shareApp(MenuItem item) {

        //This is for sharing app link e.g.google play link with others
        houseAds.shareApp();
    }

    // This function initializes all the IDs for Ads ( Like Admob, StartUp )
    private void initAds(String gs) {
        String adIds[] = AdHelper.decrypt(gs).split(",");

        // If you have NOT added all the ads IDs ( Banner, Interstitial, Reward ) for Google Admob
        // We will provide some dummy ads ID from the internet so that it doesn't stay blank
        if (Config.admobAppId.isEmpty() || Config.admobBannerAds.isEmpty() ||
                Config.admobInterAds.isEmpty() || Config.admobRewardAds.isEmpty()) {

            // These IDs come from the internet as BASE64 encripted values
            int i = 0;
            AdHelper.initAdmobIdsEnc(
                    adIds[i++],
                    adIds[i++],
                    adIds[i++],
                    adIds[i++]
            );
        }

        // But if you have added all the IDs for Google Admob
        else {
            AdHelper.initAdmobIds(
                    Config.admobAppId,
                    Config.admobBannerAds,
                    Config.admobInterAds,
                    Config.admobRewardAds
            );

            // But If the IDs are encripted (Base64), uncomment the lines below and comment the lines above
//            AdHelper.initAdmobIdsEnc(
//                    Config.admobAppId,
//                    Config.admobBannerAds,
//                    Config.admobInterAds,
//                    Config.admobRewardAds
//            );
        }

        // Same goes for StartApp. If you don't add any ads ID for startapp, we provide a dummy Ad ID from the internet
        if (Config.startAppAds.isEmpty()) {
            AdHelper.initStartappIdEnc(adIds[4]);
        }

        // If you have added all the ID for StartApp
        else {
            AdHelper.initStartappId(Config.startAppAds);

            // // But If the ID is encripted (Base64), uncomment the line below and comment the lines above
//            AdHelper.initStartappIdEnc(Config.startAppAds);
        }

        // At last, we need to call this to initialize the Admob & StartApp SDKs
        MyAllAdsUtil.init(this);

        // We're also adding a banner, below all the contents
        MyAllAdsUtil.addAnyBannerAd(this, true, linearLayoutAds, new MyAdsListener());
    }
}
