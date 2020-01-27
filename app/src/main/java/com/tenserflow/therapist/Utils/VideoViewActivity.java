package com.tenserflow.therapist.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoViewActivity extends AppCompatActivity {


    String videoString = "";

    SimpleExoPlayer player;
    long playbackPosition = 0;
    int currentWindow = 0;
    boolean playWhenReady;
    @BindView(R.id.video_view)
    SimpleExoPlayerView playerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.rel_fullScreen)
    RelativeLayout rel_fullScreen;
    @BindView(R.id.img_fullScreen)
    ImageView img_fullScreen;
/*    @BindView(R.id.exo_next)
    ImageView exo_next;*/


    String orientationStatus = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Global.hideTitleBar(this);
        setContentView(R.layout.activity_video_viewer);
        ButterKnife.bind(this);

        orientationStatus = "PORTRAIT";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
            img_fullScreen.setImageDrawable(getApplicationContext().getDrawable(R.drawable.maximize_screen));
        else
            img_fullScreen.setImageDrawable(getResources().getDrawable(R.drawable.maximize_screen));


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            videoString = bundle.getString(Global.KEY_VIDEO_URL);
            if (videoString != null && !videoString.equalsIgnoreCase("")) {

            }
        }

        simpleExoPlayerInIt();


      /*  exo_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("Rvectefbrvc","rvcrfbvc");

            }
        });
*/


    }


    @OnClick({R.id.video_close, R.id.rel_fullScreen})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.video_close:
                finish();
                break;

            case R.id.rel_fullScreen:


                if (orientationStatus.equalsIgnoreCase("PORTRAIT")) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    orientationStatus = "LANDSCAPE";

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
                        img_fullScreen.setImageDrawable(getApplicationContext().getDrawable(R.drawable.minimise_screen));
                    else
                        img_fullScreen.setImageDrawable(getResources().getDrawable(R.drawable.minimise_screen));

                } else {

                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    orientationStatus = "PORTRAIT";

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
                        img_fullScreen.setImageDrawable(getApplicationContext().getDrawable(R.drawable.maximize_screen));
                    else
                        img_fullScreen.setImageDrawable(getResources().getDrawable(R.drawable.maximize_screen));

                }


                break;


        }
    }


    public static void start(Context context, String url, String way_url_path) {
        Intent intent = new Intent(context, VideoViewActivity.class);
        intent.putExtra(way_url_path, url);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();


            try {
                if (player != null)
                    player.seekTo(playbackPosition);
            } catch (Exception e) {

            }

        }

    }

    @Override
    public void onResume() {
        super.onResume();

        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();


            try {
                if (player != null)
                    player.seekTo(playbackPosition);
            } catch (Exception e) {

            }

        }

    }

    private MediaSource buildMediaSource(Uri uri) {


        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    private void initializePlayer() {

        if (player == null)
            simpleExoPlayerInIt();


        playerView.setPlayer(player);
        player.seekTo(currentWindow, playbackPosition);

        Uri uri = Uri.parse(videoString);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
        player.setPlayWhenReady(true);


        //  Uri uri = Uri.parse("http://therapy.gangtask.com/public/videos/laravel.mp4");
        //  Uri uri = Uri.parse("http://therapy.gangtask.com/public/videos/video%201.1.3gp");
        //  Uri uri = Uri.parse("http://therapy.gangtask.com/public/videos/SampleVideo_1280x720_2mb.mp4");

    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {

            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }


    @Override
    public void onBackPressed() {
        //  super.onBackPressed();


        if (orientationStatus.equalsIgnoreCase("LANDSCAPE")) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            orientationStatus = "PORTRAIT";

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP)
                img_fullScreen.setImageDrawable(getApplicationContext().getDrawable(R.drawable.maximize_screen));
            else
                img_fullScreen.setImageDrawable(getResources().getDrawable(R.drawable.maximize_screen));
        } else
            super.onBackPressed();
    }


    private void simpleExoPlayerInIt() {

        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());

        player.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {
                    case Player.STATE_BUFFERING:
                        //Player is in state State buffering show some loading progress
                        //showProgress();
                        progressBar.setVisibility(View.VISIBLE);
                        break;
                    case Player.STATE_READY:
                        //Player is ready to Play. Remove loading progress
                        // hideProgress();
                        progressBar.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {


            }
        });

    }


}
