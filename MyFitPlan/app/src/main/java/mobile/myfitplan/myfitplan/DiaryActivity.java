package mobile.myfitplan.myfitplan;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class DiaryActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout drawerLayout;
    private TextView goalCalories, absorbedCalories, remainCalories, breakfastCalories, lunchCalories, dinnerCalories, txt_date;
    Date selectedDate, breakfast, lunch, dinner;
    double breakfastCalo = 0, lunchCalo = 0, dinnerCalo = 0;
    private ImageView btn_diary_before;
    private ImageView btn_diary_after;
    private Date today;
    private String currentDate, select;
    private LinearLayout dinnerContent, lunchContent, breakfastContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        dinnerContent = findViewById(R.id.dinner_content);
        lunchContent = findViewById(R.id.lunch_content);
        breakfastContent = findViewById(R.id.breakfast_content);

                //fixing
        today = Calendar.getInstance().getTime();
        selectedDate = today;

        //get date
        txt_date = findViewById(R.id.txt_date);
        btn_diary_after = findViewById(R.id.btn_diary_after);
        btn_diary_before = findViewById(R.id.btn_diary_before);

        btn_diary_before.setOnClickListener(this);
        btn_diary_after.setOnClickListener(this);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        currentDate = day + "/" + (month + 1) + "/" + year;

        select = currentDate; //start date

//        selectedDate = new Date();


        goalCalories = findViewById(R.id.txt_calories_goal);
        absorbedCalories = findViewById(R.id.txt_calories_absorbed);
        remainCalories = findViewById(R.id.txt_calories_remain);
        breakfastCalories = findViewById(R.id.breakfast_calories);
        lunchCalories = findViewById(R.id.lunch_calories);
        dinnerCalories = findViewById(R.id.dinner_calories);

        breakfastCalories.setText("0");
        lunchCalories.setText("0");
        dinnerCalories.setText("0");






        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24px);

        //set title and color for toolbar
        int textColor = getResources().getColor(R.color.textColorOnPrimary);
        String сolorString = String.format("%X", textColor).substring(2);
        getSupportActionBar().setTitle(Html.fromHtml(String.format("<font color=\"#%s\"'>Nhật ký </font>", сolorString)));

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_diary);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
//        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int id = menuItem.getItemId();

                        if (id == R.id.nav_logout) {
                            logout();
                        }

                        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                        drawer.closeDrawer(GravityCompat.START);
                        return true;
                    }
                });
        initData();
    }

    private void initData(){

        dinnerContent.removeAllViews();
        lunchContent.removeAllViews();
        breakfastContent.removeAllViews();
        lunchCalo = 0;
        lunchCalories.setText(String.valueOf(lunchCalo));
        breakfastCalo = 0;
        breakfastCalories.setText(String.valueOf(breakfastCalo));
        dinnerCalo = 0;
        dinnerCalories.setText(String.valueOf(dinnerCalo));

        final Calendar calendar = Calendar.getInstance();
        int day          = Integer.parseInt(DateFormat.format("dd", selectedDate).toString()); // 20
        int monthNumber  = Integer.parseInt(DateFormat.format("MM", selectedDate).toString()); // 06
        int year         = Integer.parseInt(DateFormat.format("yyyy", selectedDate).toString());  // 2013
        calendar.set(year, monthNumber - 1, day, 0, 0);

        calendar.add(Calendar.HOUR, 11);
        breakfast = calendar.getTime();
        calendar.add(Calendar.HOUR, 6);
        lunch = calendar.getTime();


//        calendar.setTime(new Date(year, monthNumber, day));
//        calendar.set(year, monthNumber - 1, day, 0, 0);
//        calendar.add(Calendar.HOUR, 10);
//        breakfast = calendar.getTime();
//        calendar.add(Calendar.HOUR, 2);
//        lunch = calendar.getTime();



        RequestParams rp = new RequestParams();
        int accUserID = ((MyApplication)getApplication()).accUser.ID;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(selectedDate);
//                    Date currentTime = Calendar.getInstance().getTime();
//        rp.add("accUserID", String.valueOf(accUserID));
        rp.add("date", dateString);
        HttpUtils http = new HttpUtils();
        String authorization = ((MyApplication)getApplication()).token_type + " " +  ((MyApplication)getApplication()).access_token;
        http.client.addHeader("Accept", "application/json");
        http.client.addHeader("Authorization", authorization);
        http.get("api/DailyProgressses", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    int goalCaloriesInfo = (int)serverResp.get("GoalCalories");
                    int absorbedCaloriesInfo = (int)serverResp.get("AbsorbedCalories");
                    goalCalories.setText(String.valueOf(goalCaloriesInfo));
                    absorbedCalories.setText(String.valueOf(absorbedCaloriesInfo));
                    remainCalories.setText(String.valueOf(goalCaloriesInfo - absorbedCaloriesInfo));
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

        //add food
        RequestParams param = new RequestParams();
//        param.add("accUserID", String.valueOf(accUserID));
        param.add("date", dateString);
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.client.addHeader("Accept", "application/json");
        httpUtils.client.addHeader("Authorization", authorization);
        httpUtils.get("api/Diaries", rp, new JsonHttpResponseHandler() {
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
                for (int i = 0; i < response.length(); i++){
                    try{
                        JSONObject diaryObj = response.getJSONObject(i);
                        JSONObject obj = diaryObj.getJSONObject("Food");
                        int Quantity = diaryObj.getInt("Quantity");
                        String Time = diaryObj.getString("Time");
                        Date time = new Date();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        try {
                            time = format.parse(Time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        String ID = obj.get("ID").toString();
                        String Name = obj.get("NameVN").toString().equals("null") ? obj.get("NameENG").toString() : obj.get("NameVN").toString();
                        String Protein = obj.get("Protein").toString().equals("null") ? "0" : obj.get("Protein").toString();
                        String Fat = obj.get("Fat").toString().equals("null") ? "0" : obj.get("Fat").toString();
                        String Carbs = obj.get("Carbs").toString().equals("null") ? "0" : obj.get("Carbs").toString();
                        String Calories = obj.get("Calories").toString().equals("null") ? "0" : obj.get("Calories").toString();
                        String Unit = obj.get("Unit").toString().equals("null") ? "" : obj.get("Unit").toString();
                        String FollowedBy = obj.get("FollowedBy").toString().equals("null") ? "0" : obj.get("FollowedBy").toString();

                        double calories;
                        try{
                            calories = Double.parseDouble(Calories);
                        }catch (Exception ex){
                            calories = 0;
                        }

                        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View v = vi.inflate(R.layout.food_info_3, null);
                        // fill in any details dynamically here
                        TextView foodID =  v.findViewById(R.id.food_id);
                        foodID.setText(ID);
                        TextView name =  v.findViewById(R.id.txt_food_name);
                        name.setText(Name);
                        TextView nutrition_info =  v.findViewById(R.id.txt_nutrition);
                        nutrition_info.setText("("+ Fat + " fats, "+ Carbs +" carbs, " + Protein + " proteins)");
                        TextView txt_library_calories = v.findViewById(R.id.txt_calories);
                        txt_library_calories.setText(Calories);
                        TextView quantity_sum_calories = v.findViewById(R.id.quantity_sum_calories);
                        quantity_sum_calories.setText("Số lượng: "+ Quantity +" -> Tổng Calories: " + Quantity*calories);

                        if (time.getTime() <= breakfast.getTime()){
                            breakfastCalo += calories*Quantity;
                            breakfastCalories.setText(String.valueOf(breakfastCalo));
                            // insert into main view
                            ViewGroup insertPoint = (ViewGroup) findViewById(R.id.breakfast_content);
                            insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                        }else if (time.getTime() <= lunch.getTime()){
                            lunchCalo += calories*Quantity;
                            lunchCalories.setText(String.valueOf(lunchCalo));

                            // insert into main view
                            ViewGroup insertPoint = (ViewGroup) findViewById(R.id.lunch_content);
                            insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                        }else{
                            dinnerCalo += calories*Quantity;
                            dinnerCalories.setText(String.valueOf(dinnerCalo));

                            // insert into main view
                            ViewGroup insertPoint = (ViewGroup) findViewById(R.id.dinner_content);
                            insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                        }


                    }catch (Exception ex){

                    }
                }
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
    }

    public void logout(){
        MyApplication myApplication = (MyApplication)getApplication();
        myApplication.access_token = null;
        myApplication.token_type = null;
        myApplication.username = null;
        myApplication.accUser = new AccUser();
        startActivity(new Intent(DiaryActivity.this, LoginPage.class));
    }

    public void clickToEat(View view) {
        startActivity(new Intent(DiaryActivity.this, SelectingMeal.class));
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(DiaryActivity.this, MainActivity.class));
                    return true;
                case R.id.navigation_diary:

                    return true;
                case R.id.navigation_library:
                    startActivity(new Intent(DiaryActivity.this, LibraryActivity.class));
                    return true;
                case R.id.navigation_me:
                    startActivity(new Intent(DiaryActivity.this, Personal.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_personal, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        final Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");

        today = Calendar.getInstance().getTime();

        try {
            cal.setTime(sdf.parse(select));
        } catch (ParseException p) {
            p.printStackTrace();
        }

        //previous day
        if (v == btn_diary_before) {
            cal.setTime(selectedDate);
            cal.add(Calendar.DATE, -1);
            selectedDate = cal.getTime();
        }

        //next day
        if (v == btn_diary_after) {
            cal.setTime(selectedDate);
            cal.add(Calendar.DATE, 1);
            selectedDate = cal.getTime();

            select = sdf.format(selectedDate);
        }

        txt_date.setText(sdf.format(selectedDate));

        Calendar calSelected = Calendar.getInstance();
        calSelected.setTime(selectedDate);
        Calendar calToday = Calendar.getInstance();
        calToday.setTime(today);
        if (calSelected.get(Calendar.YEAR) == calToday.get(Calendar.YEAR)){
            if(calSelected.get(Calendar.MONTH) == calToday.get(Calendar.MONTH)){
                if(calSelected.get(Calendar.DAY_OF_MONTH) == calToday.get(Calendar.DAY_OF_MONTH)){
                    txt_date.setText("Hôm nay");
                }
                if(calSelected.get(Calendar.DAY_OF_MONTH) == calToday.get(Calendar.DAY_OF_MONTH) + 1){
                    txt_date.setText("Ngày mai");
                }
                if(calSelected.get(Calendar.DAY_OF_MONTH) == calToday.get(Calendar.DAY_OF_MONTH) - 1){
                    txt_date.setText("Hôm qua");

                }
            }
        }
        initData();

    }
}
