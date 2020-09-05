package org.richit.animal.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.richit.animal.AdUtils.MyAdsListener;
import org.richit.animal.AdUtils.MyAllAdsUtil;
import org.richit.animal.Config;
import org.richit.animal.Database.FavouriteDB;
import org.richit.animal.Models.AnimalModel;
import org.richit.animal.R;
import org.richit.animal.Youtube.YoutubeApiHelper;
import org.richit.animal.Youtube.YoutubeListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AnimalDetailsActivity extends AppCompatActivity {

    //Data Model
    AnimalModel animalModel;

    //Views
    TextView textViewTitle, textViewName,
            textViewDescription, textViewOtherDesc;
    ImageView imageViewAnimal,
            imageViewMap, imageViewBack,
            imageViewFavourite, imageViewMedia,
            imageViewShare;
    LinearLayout linearLayoutExtraData;
    ScrollView scrollView;

    //Library Classes
    YouTubePlayerView playerView;
    YouTubePlayer player;

    //Data variables
    String videoID;
    boolean tryToPlayVideo = false;
    boolean inVideoMode = true;
    private static boolean errorDialogShownOnce = false;

    //Bitmap Uri
    Uri uriBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_2);

        //Initializing Animal Model data
        animalModel = getIntent().getParcelableExtra("animalTypes");

        //Initialize FavouriteDB for adding data to favourite list
        FavouriteDB.init(this);

        //Initializing views
         initViews();

        //Showing animal image at the beginning that's why video playing button is invisible
        playerView.setVisibility(View.GONE);

        //Sending extra available data via this method as an array so that
        //we don't need multiple text view and we can use only one textView for showing
        //different data one by one
        addExtraDataIfAvailable(
                new ExtraData("Kingdom", animalModel.getKingdom()),
                new ExtraData("Phylum", animalModel.getPhylum()),
                new ExtraData("Class", animalModel.getAnimalClass()),
                new ExtraData("Order", animalModel.getOrder()),
                new ExtraData("Family", animalModel.getFamily()),
                new ExtraData("Genus", animalModel.getGenus()),
                new ExtraData("Scientific name", animalModel.getScientific()),
                new ExtraData("Common Name", animalModel.getCommonname()),
                new ExtraData("Number of Species", animalModel.getNumberofspecies()),
                new ExtraData("Location", animalModel.getLocation()),
                new ExtraData("Type", animalModel.getType()),
                new ExtraData("Origin", animalModel.getOrigin()),
                new ExtraData("Habitat", animalModel.getHabitat()),
                new ExtraData("Color", animalModel.getColor()),
                new ExtraData("Skin", animalModel.getSkin()),
                new ExtraData("Top Speed", animalModel.getSpeed()),
                new ExtraData("Diet", animalModel.getDiet()),
                new ExtraData("Wing Span", animalModel.getWingspan()),
                new ExtraData("Prey", animalModel.getPrey()),
                new ExtraData("Main Prey", animalModel.getMainprey()),
                new ExtraData("Predators", animalModel.getPredators()),
                new ExtraData("Life Style", animalModel.getLifestyle()),
                new ExtraData("Water Type", animalModel.getWater()),
                new ExtraData("ph Level", animalModel.getPhlevel()),
                new ExtraData("Conservation Status", animalModel.getConservation()),
                new ExtraData("Lifespan", animalModel.getLifespan()),
                new ExtraData("Average Weight", animalModel.getWeight()),
                new ExtraData("Temperament", animalModel.getTemperament()),
                new ExtraData("Training", animalModel.getTraining()),
                new ExtraData("Features", animalModel.getFeatures()),
                new ExtraData("Special Feature", animalModel.getSpecialfeature()),
                new ExtraData("Distinctive Feature", animalModel.getDistinctivefeature()),
                new ExtraData("Most Distinctive Feature", animalModel.getMostdistinctivefeature()),
                new ExtraData("Fun Fact", animalModel.getFunfact())

        );

        //Load YouTube video before playing
          initYoutubeVideoLoader();

        //Load bitmap image of the streaming link for sharing
        Picasso.get().load(Config.youTubeVideoUrl + animalModel.getImage()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmapImage, Picasso.LoadedFrom from) {
                uriBitmap = getLocalBitmapUri(bitmapImage);

            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });

        //Setting Click Listeners
        imageViewFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //By clicking the favourite image view icon here user can set that animal is favourite or not
                //Checking if that data we are looking for is in user's favourite list or not
                if (FavouriteDB.isFavourite(animalModel.getName())) {

                    //here we removing animal data from favourite list so that
                    // data is no longer available to user favourite list
                    boolean deleted = FavouriteDB.deleteData(animalModel.getName());
                    Toast.makeText(AnimalDetailsActivity.this, "Removed from favourite", Toast.LENGTH_SHORT).show();
                    if (deleted)

                        //if data is not in favourite changing the favourite image icon color
                        notFavourite();
                } else {

                    //Adding animal data to favourite list so that
                    // user can see which data is in the favourite list
                    boolean added = FavouriteDB.addData(animalModel);
                    Toast.makeText(AnimalDetailsActivity.this, "Added to favourite", Toast.LENGTH_SHORT).show();
                    if (added)

                        //if data is in favourite changing the image icon color
                        favourite();
                }
                MyAllAdsUtil.showInterIfOk(AnimalDetailsActivity.this, new MyAdsListener());
            }
        });

        imageViewMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toggle image view and player view
                if (inVideoMode) {
                    //Playing animal video from youtube
                    if (player != null)
                        player.play();
                    else
                        tryToPlayVideo = true;
                    scrollView.smoothScrollTo(0, 0);

                    //Using Visible/Gone property of image view here
                    // we are toggling between image  mode and video playing mode
                    //Here it's now on video playing mode so we are showing the animal fact's related video and
                    //hiding the animal image
                    imageViewAnimal.setVisibility(View.GONE);
                    playerView.setVisibility(View.VISIBLE);
                    imageViewMedia.setImageResource(R.drawable.ic_image_size_select_actual_white_24dp);
                    inVideoMode = false;

                } else {

                    if (player != null) {

                        //if user switch over image mode then we pause the playing video
                        player.pause();
                    }

                    //Using Visible/Gone property of image view here
                    // we are toggling between image  mode and video playing mode
                    //Here it's now on image view mode so we are showing the animal image and
                    //hiding the video playing
                    imageViewAnimal.setVisibility(View.VISIBLE);
                    imageViewMedia.setImageResource(R.drawable.ic_play_white_24dp);
                    playerView.setVisibility(View.GONE);
                    scrollView.smoothScrollTo(0, 0);

                    //setting the inVideoMode true so that when user press the image view media icon
                    // it can start playing video again
                    inVideoMode = true;
                }
                MyAllAdsUtil.showInterIfOk(AnimalDetailsActivity.this, new MyAdsListener());

            }
        });

        //Share images with description and youtube video link using shareable media
        imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAllAdsUtil.showInterIfOk(AnimalDetailsActivity.this, new MyAdsListener());
                final Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_TEXT, animalModel.getDesc1() + animalModel.getDesc2() + "\n" + Config.youTubeVideoUrl + videoID);

                //video streaming link
                intent.putExtra(Intent.EXTRA_STREAM, uriBitmap);
                //in.setType("image/*");
                try {
                    startActivity(Intent.createChooser(intent, "Share"));
                } catch (Exception e) {
                    Toast.makeText(AnimalDetailsActivity.this, "Please wait until the images are loaded", Toast.LENGTH_LONG).show();
                }
            }
        });

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Checking if images are from wikipedia or not
        if (animalModel.getImage().toLowerCase().contains("wikipedia")) {
            Picasso.get().load(animalModel.getImage())
                    .placeholder(R.drawable.loading)
                    .into(imageViewAnimal);
            Picasso.get().load(animalModel.getMap())
                    .placeholder(R.drawable.loading)
                    .into(imageViewMap);

        } else {

            //If data is not from wikipedia then data is from a-z-animals.com
            //Setting loaded image data of animal to specific view
            Picasso.get().load(Config.animalImagePath+ animalModel.getImage())
                    .placeholder(R.drawable.loading)
                    .into(imageViewAnimal);
            Picasso.get().load(Config.animalImagePath + animalModel.getMap())
                    .placeholder(R.drawable.loading)
                    .into(imageViewMap);

        }

        textViewName.setText("Facts About " + animalModel.getName());
        textViewTitle.setText(animalModel.getName());

        /*Descriptions of specific animal collected as paragraph
        * such as desc1, desc2,desc3
        * checking if description present or not
        * and set it to the specific text view*/
        if (animalModel.getDesc2() != null) {
            textViewDescription.setText(animalModel.getDesc1() + "\n" + animalModel.getDesc2());
        } else {
            textViewDescription.setText(animalModel.getDesc1());
        }
        if (animalModel.getDesc4() != null) {
            textViewOtherDesc.setText(animalModel.getDesc3() + "\n" + animalModel.getDesc4());
        } else {
            if (animalModel.getDesc3() != null)
                textViewOtherDesc.setText(animalModel.getDesc3());
            else
                textViewOtherDesc.setText("");
        }


        if (FavouriteDB.isFavourite(animalModel.getName())) {
            favourite();
        } else
            notFavourite();

        //Showing Ads
        MyAllAdsUtil.addAnyBannerAd(this, true, (LinearLayout) findViewById(R.id.adsLL), new MyAdsListener());
        MyAllAdsUtil.addAnyBannerAd(this, true, (LinearLayout) findViewById(R.id.adsLL2), new MyAdsListener());

    }

    private void initYoutubeVideoLoader() {
        new YoutubeApiHelper(AnimalDetailsActivity.this, Config.youTubeApiUrl + animalModel.getName() + "s Animal Facts", new YoutubeListener() {
            @Override
            public void onJsonDataReceived(String updateModel) {
                try {
                    JSONObject jsonObject = new JSONObject(updateModel);

                    //Here we get the items from JSON object and put that into the JSON array
                    JSONArray jsonArray = jsonObject.getJSONArray("items");

                    //for each JSON array we get an id of that item and get the video ID for that id
                    //we use that video id for loading and playing the video from youtube the
                    videoID = jsonArray.getJSONObject(0).getJSONObject("id").getString("videoId");
                    Log.d("Video", "onJsonDataReceived: " + videoID);
                    getLifecycle().addObserver(playerView);
                    playerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(YouTubePlayer youTubePlayer) {

                            //if video id is available then the youtube player play the video
                            youTubePlayer.loadVideo(videoID, 0);
                            player = youTubePlayer;

                            //checking whether or not the user is in video mode or not
                            if (tryToPlayVideo) {

                                player.play();
                            } else
                                player.pause();

                        }

                        @Override
                        public void onError(YouTubePlayer youTubePlayer, PlayerConstants.PlayerError error) {

                            //Some country doesn't provide playing youtube video directly
                            //may be that's an issue of the library or the Internet service provider
                            //so here we checking first if the video is playing without a vpn
                            //if not then we show dialog for using vpn
                            if (errorDialogShownOnce) {
                                Toast.makeText(AnimalDetailsActivity.this, "Error loading video. Please use a VPN", Toast.LENGTH_LONG).show();
                            } else {
                                new AlertDialog.Builder(AnimalDetailsActivity.this)
                                        .setTitle("Error loading")
                                        .setMessage(String.format(
                                                "Error: %s\n\nThis may cause because of your ISP. Sometimes, using a VPN can fix the problem...", error.name()
                                        ))
                                        .setPositiveButton("OK", null)
                                        .show();
                            }
                            errorDialogShownOnce = !errorDialogShownOnce;
                            super.onError(youTubePlayer, error);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                Log.d("What?", "onError: ");
            }
        }).execute();
    }

    private void initViews() {
        scrollView = findViewById(R.id.scrollView);
        textViewTitle = findViewById(R.id.showName);
        textViewName = findViewById(R.id.animalName);
        textViewDescription = findViewById(R.id.animalDescription);
        textViewOtherDesc = findViewById(R.id.tvOtherDesc);
        linearLayoutExtraData = findViewById(R.id.extraDataLayout);
        imageViewAnimal = findViewById(R.id.animalImageView);
        imageViewMap = findViewById(R.id.mapImageView);
        imageViewBack = findViewById(R.id.imageViewBack);
        imageViewShare = findViewById(R.id.imageViewShare);
        imageViewMedia = findViewById(R.id.imageViewMedia);
        playerView = findViewById(R.id.youtube_player_view);
        imageViewFavourite = findViewById(R.id.ivFavourite);
    }

    //This method is for getting bitmap uri for sharing video link
    public Uri getLocalBitmapUri(Bitmap bmp) {
        Uri bmpUri = null;
        try {
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "share_image_" + System.currentTimeMillis() + ".png");
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    //Change Icon Color when content added to favourite
    public void favourite() {
        DrawableCompat.setTint(imageViewFavourite.getDrawable(),
                ContextCompat.getColor(this, R.color.colorAccent));
    }

    //Change Icon Color when content removed from favourite
    public void notFavourite() {
        DrawableCompat.setTint(imageViewFavourite.getDrawable(),
                ContextCompat.getColor(this, android.R.color.white));
    }

    //By using this method we can show available extra data of specific animal with only
    //one text view for several times
    //Here data comes as key value pair From Animal Model Class
    private void addExtraDataIfAvailable(ExtraData... data) {

        for (int i = 0; i < data.length; i++) {

            //check if the value of that data is available or not
            //if the value is available then show the value with its own key
            //otherwise we are not showing the data
            if (data[i].value != null) {
                TextView textView = new TextView(this);
                textView.setTextSize(14);
                textView.setText(data[i].key + ": " + data[i].value);
                linearLayoutExtraData.addView(textView);
            }
        }
    }

    //This internal class is for those extra data that related to specific animal
    // which we scrape from "https://a-z-animals.com/" site
    //we use this class for parsing the JSON data as key value pairs
    private class ExtraData {
        String key;
        String value;

        public ExtraData(String key, String value) {

            this.key = key;
            this.value = value;
        }
    }
}
