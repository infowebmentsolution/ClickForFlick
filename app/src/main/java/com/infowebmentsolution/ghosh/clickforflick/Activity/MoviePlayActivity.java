package com.infowebmentsolution.ghosh.clickforflick.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.metadata.icy.IcyHeaders;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.common.internal.ImagesContract;
import com.infowebmentsolution.ghosh.clickforflick.Activity.SignInActivity;
import com.infowebmentsolution.ghosh.clickforflick.Adapter.CategoryWiseAdapter;
import com.infowebmentsolution.ghosh.clickforflick.Adapter.NextVideoAdapter;
import com.infowebmentsolution.ghosh.clickforflick.BlankActivity;
import com.infowebmentsolution.ghosh.clickforflick.R;
import com.infowebmentsolution.ghosh.clickforflick.Model.HomeMovieList;
import com.infowebmentsolution.ghosh.clickforflick.Model.LikeCountList;
import com.infowebmentsolution.ghosh.clickforflick.Model.NextVideoList;
import com.infowebmentsolution.ghosh.clickforflick.Response.LikeCountResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.NextVideoResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.VideoLikeByUserResponse;
import com.infowebmentsolution.ghosh.clickforflick.Response.isLoginCheckResponse;
import com.infowebmentsolution.ghosh.clickforflick.Utils.Constants;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RestAPI;
import com.infowebmentsolution.ghosh.clickforflick.Utils.RetrofitClient;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class MoviePlayActivity extends AppCompatActivity implements NextVideoAdapter.ClickedOnItemListener, CategoryWiseAdapter.ClickedOnItemListener {
    /* access modifiers changed from: private */
    public static int indexCount = 0;
    private String android_id;
    RecyclerView catRec;
    CategoryWiseAdapter categoryWiseAdapter;
    TextView catname;
    LinearLayout des;
    TextView episeoname;
    boolean fullscreen = false;
    ImageView fullscreenButton;
    private ArrayList<HomeMovieList> homeMovieLists;
    private ArrayList<HomeMovieList> homeMovieListsAdd = new ArrayList<>();
    Intent intentsUrl;
    String isFree;
    String lang;
    TextView likeCount;
    /* access modifiers changed from: private */
    public ArrayList<LikeCountList> likeCountList;
    ImageView likeImg;
    TextView nextName;
    RecyclerView nextRec;
    NextVideoAdapter nextVideoAdapter;
    /* access modifiers changed from: private */
    public ArrayList<NextVideoList> nextVideoList;
    /* access modifiers changed from: private */
    public ArrayList<NextVideoList> nextVideoListAdd = new ArrayList<>();
    SimpleExoPlayer player;
    PlayerView playerView;
    SharedPreferences sharedPreferences;
    private String subscriptionStatus;
    String trailerLink;
    String userId;
    TextView vDescription;
    TextView vName;
    String vid;
    String videoURl = "2021_08_04_03_55_40_2021_07_05_02_15_18_Kids.mp4";
    LinearLayout watchT;

    /* renamed from: wt */
    String f1wt;

    static /* synthetic */ int access$508() {
        int i = indexCount;
        indexCount = i + 1;
        return i;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        MediaSource videoSource;
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_movie_play);
        getWindow().setFlags(8192, 8192);
        Intent intent = getIntent();
        this.intentsUrl = intent;
        this.videoURl = intent.getStringExtra(ImagesContract.URL);
        this.player = ExoPlayerFactory.newSimpleInstance(getApplicationContext());
        PlayerView playerView2 = (PlayerView) findViewById(R.id.player);
        this.playerView = playerView2;
        this.fullscreenButton = (ImageView) playerView2.findViewById(R.id.exo_fullscreen_icon);
        this.nextRec = (RecyclerView) findViewById(R.id.playmovie_nextrec);
        this.likeCount = (TextView) findViewById(R.id.playmovie_likecount);
        this.vName = (TextView) findViewById(R.id.playmovie_vdoname);
        this.episeoname = (TextView) findViewById(R.id.playmovie_episeodename);
        this.vDescription = (TextView) findViewById(R.id.playmovie_description);
        this.watchT = (LinearLayout) findViewById(R.id.playmovie_watchtrailer);
        this.likeImg = (ImageView) findViewById(R.id.playmovie_likeimg);
        this.des = (LinearLayout) findViewById(R.id.playmovie_lin);
        this.nextName = (TextView) findViewById(R.id.playmovie_next);
        this.android_id = Settings.Secure.getString(getContentResolver(), "android_id");
        this.vName.setText(this.intentsUrl.getStringExtra("vname"));
        if (!(this.intentsUrl.getStringExtra("episodename") == null || this.intentsUrl.getStringExtra("episodename").length() == 0)) {
            TextView textView = this.episeoname;
            textView.setText("Episode : " + this.intentsUrl.getStringExtra("episodename"));
        }
        this.vDescription.setText(this.intentsUrl.getStringExtra("vdec"));
        this.trailerLink = this.intentsUrl.getStringExtra("tUrl");
        SharedPreferences sharedPreferences2 = getSharedPreferences(Constants.LOG_IN_DATA, 0);
        this.sharedPreferences = sharedPreferences2;
        this.lang = sharedPreferences2.getString(Constants.LANGUAGE, Constants.LANGUAGE);
        this.userId = this.sharedPreferences.getString(Constants.USER_ID, Constants.GOOGLE_SIGN_IN);
        this.isFree = this.intentsUrl.getStringExtra("isFree");
        this.vid = this.intentsUrl.getStringExtra("vId");
        this.likeImg.setBackgroundResource(R.drawable.like2);
        this.f1wt = this.intentsUrl.getStringExtra("W.T.");
        this.subscriptionStatus = this.sharedPreferences.getString(Constants.SUBSCRIPTION_ID, Constants.GOOGLE_SIGN_IN);
        this.fullscreenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MoviePlayActivity.this.fullscreen) {
                    MoviePlayActivity.this.fullscreenButton.setImageDrawable(ContextCompat.getDrawable(MoviePlayActivity.this, R.drawable.full_screen));
                    MoviePlayActivity.this.getWindow().getDecorView().setSystemUiVisibility(0);
                    if (MoviePlayActivity.this.getSupportActionBar() != null) {
                        MoviePlayActivity.this.getSupportActionBar().show();
                    }
                    MoviePlayActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//or SCREEN_ORIENTATION_PORTRAIT
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) MoviePlayActivity.this.playerView.getLayoutParams();
                    params.width = -1;
                    params.height = (int) (MoviePlayActivity.this.getApplicationContext().getResources().getDisplayMetrics().density * 200.0f);
                    MoviePlayActivity.this.playerView.setLayoutParams(params);
                    MoviePlayActivity.this.des.setVisibility(View.VISIBLE);
                    MoviePlayActivity.this.nextRec.setVisibility(View.VISIBLE);
                    MoviePlayActivity.this.nextName.setVisibility(View.VISIBLE);
                    MoviePlayActivity.this.vDescription.setVisibility(View.VISIBLE);
                    MoviePlayActivity.this.fullscreen = false;
                    return;
                }
                MoviePlayActivity.this.fullscreenButton.setImageDrawable(ContextCompat.getDrawable(MoviePlayActivity.this, R.drawable.close_fullscreen));
                MoviePlayActivity.this.getWindow().getDecorView().setSystemUiVisibility(4102);
                if (MoviePlayActivity.this.getSupportActionBar() != null) {
                    MoviePlayActivity.this.getSupportActionBar().hide();
                }
                MoviePlayActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) MoviePlayActivity.this.playerView.getLayoutParams();
                params2.width = -1;
                params2.height = -1;
                MoviePlayActivity.this.playerView.setLayoutParams(params2);
                MoviePlayActivity.this.des.setVisibility(View.VISIBLE);
                MoviePlayActivity.this.nextRec.setVisibility(View.VISIBLE);
                MoviePlayActivity.this.nextName.setVisibility(View.VISIBLE);
                MoviePlayActivity.this.vDescription.setVisibility(View.VISIBLE);
                MoviePlayActivity.this.fullscreen = true;
            }
        });
        this.nextVideoAdapter = new NextVideoAdapter(new NextVideoAdapter.ClickedOnItemListener() {
            public final void ClickedItem(NextVideoList nextVideoList) {
                MoviePlayActivity.this.ClickedItem(nextVideoList);
            }
        });
        this.categoryWiseAdapter = new CategoryWiseAdapter(new CategoryWiseAdapter.ClickedOnItemListener() {
            public final void ClickedItem(HomeMovieList homeMovieList) {
                MoviePlayActivity.this.ClickedItem(homeMovieList);
            }
        });
        this.playerView.setPlayer(this.player);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getApplicationContext(), Util.getUserAgent(getApplicationContext(), getApplicationContext().getString(R.string.app_name)));
        PrintStream printStream = System.out;
        printStream.println("------------------subscribe-----------------" + this.subscriptionStatus);
        if (this.f1wt.equals(Constants.SUBSCRIPTION_ID)) {
            ProgressiveMediaSource.Factory factory = new ProgressiveMediaSource.Factory(dataSourceFactory);
            videoSource = factory.createMediaSource(Uri.parse(Constants.TRAILER_URL + this.trailerLink));
            this.player.prepare(videoSource);
            this.player.setPlayWhenReady(true);
        } else if (!this.subscriptionStatus.equals(Constants.SUBSCRIPTION_ID)) {
            PrintStream printStream2 = System.out;
            printStream2.println("----------------isfree-------------" + this.isFree);
            if (this.isFree.equals(IcyHeaders.REQUEST_HEADER_ENABLE_METADATA_VALUE)) {
                this.watchT.setVisibility(View.VISIBLE);
                ProgressiveMediaSource.Factory factory2 = new ProgressiveMediaSource.Factory(dataSourceFactory);
                videoSource = factory2.createMediaSource(Uri.parse(Constants.VIDEO_URL + this.videoURl));
            } else {
                this.watchT.setVisibility(View.GONE);
                ProgressiveMediaSource.Factory factory3 = new ProgressiveMediaSource.Factory(dataSourceFactory);
                videoSource = factory3.createMediaSource(Uri.parse(Constants.TRAILER_URL + this.trailerLink));
            }
        } else {
            this.watchT.setVisibility(View.VISIBLE);
            ProgressiveMediaSource.Factory factory4 = new ProgressiveMediaSource.Factory(dataSourceFactory);
            videoSource = factory4.createMediaSource(Uri.parse(Constants.VIDEO_URL + this.videoURl));
        }
        this.player.prepare(videoSource);
        this.player.setPlayWhenReady(true);
        new LinearLayoutManager(this);
        //this.nextRec.setLayoutManager(new LinearLayoutManager(this, 0, false));
        this.nextRec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        loadNextVideoDataList(this.intentsUrl.getStringExtra("cname"));
        loadLikeCount(this.vid);
        loadVideoLikeCheck(this.userId, this.vid);
        this.likeImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MoviePlayActivity moviePlayActivity = MoviePlayActivity.this;
                moviePlayActivity.loadVideoLikedByUser(moviePlayActivity.userId, MoviePlayActivity.this.vid);
            }
        });
        this.watchT.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                MoviePlayActivity.this.lambda$onCreate$0$MoviePlayActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$onCreate$0$MoviePlayActivity(View view) {
        Intent intent = getIntent();
        intent.putExtra("tUrl", intent.getStringExtra("tUrl"));
        intent.putExtra(ImagesContract.URL, intent.getStringExtra(ImagesContract.URL));
        intent.putExtra("vId", this.vid);
        intent.putExtra("cname", this.intentsUrl.getStringExtra("cname"));
        intent.putExtra("W.T.", Constants.SUBSCRIPTION_ID);
        finish();
        startActivity(intent);
    }

    private void isUserLoginCheck() {
        getApiService().isLoginCheckApi(this.userId, this.android_id).enqueue(new Callback<isLoginCheckResponse>() {
            public void onResponse(Call<isLoginCheckResponse> call, Response<isLoginCheckResponse> response) {
                if (response.code() == 200 && response.body().getResult().equals("0")) {
                    SharedPreferences.Editor editor = MoviePlayActivity.this.getSharedPreferences(Constants.LOG_IN_DATA, 0).edit();
                    editor.putString(Constants.USER_ID, Constants.GOOGLE_SIGN_IN);
                    editor.apply();
                    editor.commit();
                    MoviePlayActivity.this.startActivity(new Intent(MoviePlayActivity.this, SignInActivity.class));
                }
            }

            public void onFailure(Call<isLoginCheckResponse> call, Throwable t) {
            }
        });
    }

    private void loadVideoLikeCheck(String userId2, String vid2) {
        getApiService().videoLikeCheckApi(userId2, vid2).enqueue(new Callback<VideoLikeByUserResponse>() {
            public void onResponse(Call<VideoLikeByUserResponse> call, Response<VideoLikeByUserResponse> response) {
                if (response.code() != 200) {
                    MoviePlayActivity.this.likeImg.setBackgroundResource(R.drawable.like2);
                } else if (response.body().getMsg().equals("Already liked this video!")) {
                    MoviePlayActivity.this.likeImg.setBackgroundResource(R.drawable.like1);
                }
            }

            public void onFailure(Call<VideoLikeByUserResponse> call, Throwable t) {
            }
        });
    }

    /* access modifiers changed from: private */
    public void loadVideoLikedByUser(String userId2, final String vid2) {
        getApiService().videoLikeByUserApi(userId2, vid2).enqueue(new Callback<VideoLikeByUserResponse>() {
            public void onResponse(Call<VideoLikeByUserResponse> call, Response<VideoLikeByUserResponse> response) {
                if (response.code() != 200) {
                    return;
                }
                if (response.body().getMsg().equals("success")) {
                    MoviePlayActivity.this.likeImg.setBackgroundResource(R.drawable.like1);
                    MoviePlayActivity.this.loadLikeCount(vid2);
                } else if (response.body().getMsg().equals("Already liked this video!")) {
                    MoviePlayActivity.this.likeImg.setBackgroundResource(R.drawable.like1);
                } else {
                    MoviePlayActivity.this.likeImg.setBackgroundResource(R.drawable.like2);
                }
            }

            public void onFailure(Call<VideoLikeByUserResponse> call, Throwable t) {
            }
        });
    }

    /* access modifiers changed from: private */
    public void loadLikeCount(String vId) {
        getApiService().likeCountAPi(vId).enqueue(new Callback<LikeCountResponse>() {
            public void onResponse(Call<LikeCountResponse> call, Response<LikeCountResponse> response) {
                if (response.code() == 200) {
                    ArrayList unused = MoviePlayActivity.this.likeCountList = new ArrayList(Arrays.asList(response.body().getResult()));
                    MoviePlayActivity.this.likeCount.setText(((LikeCountList) MoviePlayActivity.this.likeCountList.get(0)).getLikecount());
                }
            }

            public void onFailure(Call<LikeCountResponse> call, Throwable t) {
            }
        });
    }

    public void onPause() {
        super.onPause();
        this.player.setPlayWhenReady(false);
    }

    public void onDestroy() {
        this.player.release();
        super.onDestroy();
    }

    private void loadNextVideoDataList(String cname) {
        indexCount = 0;
        String catName = cname.replaceAll(" ", "").toLowerCase(Locale.ROOT);
        this.nextVideoListAdd = new ArrayList<>();
        getApiService().nextVideoListApi(this.vid).enqueue(new Callback<NextVideoResponse>() {
            static final /* synthetic */ boolean $assertionsDisabled = false;

            {
                Class<MoviePlayActivity> cls = MoviePlayActivity.class;
            }

            public void onResponse(Call<NextVideoResponse> call, Response<NextVideoResponse> response) {
                if (response.code() != 200) {
                    return;
                }
                if (response.body() != null) {
                    ArrayList unused = MoviePlayActivity.this.nextVideoList = new ArrayList(Arrays.asList(response.body().getresult1()));
                    for (int index = 0; index < MoviePlayActivity.this.nextVideoList.size(); index++) {
                        if (MoviePlayActivity.this.lang.equals(Constants.LANGUAGE)) {
                            MoviePlayActivity.this.nextVideoListAdd.add(index, (NextVideoList) MoviePlayActivity.this.nextVideoList.get(index));
                        } else if (((NextVideoList) MoviePlayActivity.this.nextVideoList.get(index)).getLang().equals(MoviePlayActivity.this.lang)) {
                            MoviePlayActivity.this.nextVideoListAdd.add(MoviePlayActivity.indexCount, (NextVideoList) MoviePlayActivity.this.nextVideoList.get(index));
                            MoviePlayActivity.access$508();
                        }
                    }
                    MoviePlayActivity.this.nextVideoAdapter.setData(MoviePlayActivity.this.nextVideoListAdd);
                    MoviePlayActivity.this.nextRec.setAdapter(MoviePlayActivity.this.nextVideoAdapter);
                    return;
                }
                throw new AssertionError();
            }

            public void onFailure(Call<NextVideoResponse> call, Throwable t) {
            }
        });
    }

    private static RestAPI getApiService() {
        return (RestAPI) RetrofitClient.getRetrofitClient(Constants.API_URL).create(RestAPI.class);
    }

    public void ClickedItem(NextVideoList nextVideoList2) {
        Intent intent = getIntent();
        intent.putExtra("tUrl", nextVideoList2.getTrailername());
        intent.putExtra(ImagesContract.URL, nextVideoList2.getVideoname());
        intent.putExtra("vId", nextVideoList2.getUploadid());
        intent.putExtra("cname", this.intentsUrl.getStringExtra("cname"));
        intent.putExtra("W.T.", "no");
        finish();
        startActivity(intent);
    }

    public void ClickedItem(HomeMovieList homeMovieList) {
        Intent intent = getIntent();
        intent.putExtra("tUrl", homeMovieList.getTrailername());
        intent.putExtra(ImagesContract.URL, homeMovieList.getVideoname());
        intent.putExtra("vId", homeMovieList.getUploadid());
        intent.putExtra("cname", this.intentsUrl.getStringExtra("cname"));
        intent.putExtra("W.T.", "no");
        finish();
        startActivity(intent);
    }

    public void onBackPressed() {
        startActivity(new Intent(this, BlankActivity.class));
    }
}
