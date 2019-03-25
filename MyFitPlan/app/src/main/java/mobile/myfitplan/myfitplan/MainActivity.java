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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ProgressBar calories, fat, carb, protein;
    private TextView caloText, fatText, carbText, proteinText;
    Animation rotateAnimation;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calories = findViewById(R.id.progress_calo);
        fat = findViewById(R.id.progress_fat);
        carb = findViewById(R.id.progress_carb);
        protein = findViewById(R.id.progress_protein);

        caloText = findViewById(R.id.txt_progress_calo);
        fatText = findViewById(R.id.txt_progress_fat);
        proteinText = findViewById(R.id.txt_progress_protein);
        carbText = findViewById(R.id.txt_progress_carb);

        RequestParams param = new RequestParams();
        String username = ((MyApplication) getApplication()).username;
        param.add("email", username);
        HttpUtils httpUtils = new HttpUtils();
        String authorization = ((MyApplication)getApplication()).token_type + " " +  ((MyApplication)getApplication()).access_token;
        httpUtils.client.addHeader("Authorization", authorization);
        httpUtils.get("api/AccUsers", param, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject res = new JSONObject(response.toString());
                    int ID = (int)res.get("ID");
                    String Email = res.get("Email").toString();
                    String DateOfBirth = res.get("DateOfBirth").toString();
                    String Name = res.get("Name").toString();
                    int Purpose = (int)res.get("Purpose");
                    String Gender = res.get("Gender").toString();
                    int TrainingLevel = (int)res.get("TrainingLevel");
//                                AccUser accUser = new AccUser(ID, Email, DateOfBirth, Name, Purpose, Gender, TrainingLevel);
                    ((MyApplication) getApplication()).accUser = new AccUser(ID, Email, DateOfBirth, Name, Purpose, Gender, TrainingLevel);


                    RequestParams rp = new RequestParams();
                    int accUserID = ((MyApplication)getApplication()).accUser.ID;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String currentDateandTime = sdf.format(new Date());
//                    Date currentTime = Calendar.getInstance().getTime();
                    rp.add("accUserID", String.valueOf(accUserID));
                    rp.add("date", currentDateandTime);
                    HttpUtils http = new HttpUtils();
                    String authorization = ((MyApplication)getApplication()).token_type + " " +  ((MyApplication)getApplication()).access_token;
                    http.client.addHeader("Accept", "application/json");
                    http.client.addHeader("Authorization", authorization);
                    http.get("api/DailyProgressses", rp, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                JSONObject serverResp = new JSONObject(response.toString());
                                double goalProtein = (double)serverResp.getDouble("GoalProtein");
                                double absorbedProtein = (double)serverResp.getDouble("AbsorbedProtein");
                                double goalCalories = (double)serverResp.getDouble("GoalCalories");
                                double absorbedCalories = (double)serverResp.getDouble("AbsorbedCalories");
                                double goalFat = (double)serverResp.getDouble("GoalFat");
                                double absorbedFat = (double)serverResp.getDouble("AbsorbedFat");
                                double goalCarb = (double)serverResp.getDouble("GoalCarbs");
                                double absorbedCarb = (double)serverResp.getDouble("AbsorbedCarbs");

                                calories.setMax((int)goalCalories);
                                calories.setProgress((int)absorbedCalories);
                                fat.setMax((int)goalFat);
                                fat.setProgress((int)absorbedFat);
                                carb.setMax((int)goalCarb);
                                carb.setProgress((int)absorbedCarb);
                                protein.setMax((int)goalProtein);
                                protein.setProgress((int)absorbedProtein);

                                caloText.setText(String.format("%.0f", absorbedCalories) +"/"+String.format("%.0f", goalCalories));
                                fatText.setText(String.format("%.0f", absorbedFat)+"/"+String.format("%.0f", goalFat));
                                proteinText.setText(String.format("%.0f", absorbedProtein)+"/"+String.format("%.0f", goalProtein));
                                carbText.setText(String.format("%.0f", absorbedCarb)+"/"+String.format("%.0f", goalCarb));

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

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                            super.onFailure(statusCode, headers, throwable, errorResponse);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            super.onFailure(statusCode, headers, responseString, throwable);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                startActivity(new Intent(MainActivity.this, FillingGoal.class));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
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
