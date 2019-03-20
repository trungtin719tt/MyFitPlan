package mobile.myfitplan.myfitplan;

import android.support.design.widget.BottomNavigationView;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    Animation rotateAnimation;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RequestParams rp = new RequestParams();
        HttpUtils.get("api/Foods", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(getApplicationContext(),"Kiểm tra lại kết nối mạng",Toast.LENGTH_LONG).show();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24px);
        //set title and color for toolbar
        int textColor = getResources().getColor(R.color.textColorOnPrimary);
        String сolorString = String.format("%X", textColor).substring(2);
        getSupportActionBar().setTitle(Html.fromHtml(String.format("<font color=\"#%s\"'>Trang chủ </font>", сolorString)));


        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        imageButton = (ImageButton)findViewById(R.id.btnHnag);
        Animation btnAni = new AlphaAnimation(1, 0);
        btnAni.setDuration(1100);
        btnAni.setRepeatCount(Animation.INFINITE);
        btnAni.setRepeatMode(Animation.REVERSE);
        imageButton.startAnimation(btnAni);
//        rotateAnimation();

    }

//    private void rotateAnimation() {
//        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
//        rotateAnimation.setDuration(2500);
//        rotateAnimation.setRepeatCount(Animation.INFINITE);
//        rotateAnimation.setRepeatMode(Animation.REVERSE);
//        rotateAnimation.setFillAfter(true);
//        imageButton.startAnimation(rotateAnimation);
//    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_diary:
                    startActivity(new Intent(MainActivity.this, DiaryActivity.class));
                    return true;
                case R.id.navigation_library:
                    startActivity(new Intent(MainActivity.this, LibraryActivity.class));
                    return true;
                case R.id.navigation_me:
                    startActivity(new Intent(MainActivity.this, Personal.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void clickToEat(View view) {
        startActivity(new Intent(MainActivity.this, SelectingMeal.class));
    }

    //click để hiện popup
//    public void clickToPopUp(View view) {
//        Intent intent = new Intent(this, SelectingMeal.class);
//        startActivity(intent);
//    }
}
