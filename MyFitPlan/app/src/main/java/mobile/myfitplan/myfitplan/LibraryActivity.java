package mobile.myfitplan.myfitplan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class LibraryActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private String keyword;
    private Button loadMore;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);


        drawerLayout = findViewById(R.id.drawer_layout);

        ImageView searchButton = findViewById(R.id.search_button);
        searchButton.bringToFront();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search();
            }
        });

        EditText searchBox = findViewById(R.id.edtAccount);
        searchBox.setImeActionLabel("Done", KeyEvent.KEYCODE_ENTER);
        searchBox.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    Search();
                    return true;
                }
                return false;
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24px);
        //set title and color for toolbar
        int textColor = getResources().getColor(R.color.textColorOnPrimary);
        String сolorString = String.format("%X", textColor).substring(2);
        getSupportActionBar().setTitle(Html.fromHtml(String.format("<font color=\"#%s\"'>Thư viện món ăn </font>", сolorString)));

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_library);

        keyword = "";

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
        InitData();



    }

    public void InitData(){
        page = 1;
        LinearLayout foodInfo = findViewById(R.id.food_content);
        foodInfo.removeAllViews();

        Button addFood = new Button(this);
        addFood.setText("Tải thêm");
        addFood.setBackground(getResources().getDrawable(R.drawable.custom_button));
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.gravity = Gravity.CENTER;
        ll.topMargin = 20;
        addFood.setLayoutParams(ll);
        addFood.setPadding(10, 0, 10, 0);
        addFood.setTextColor(Color.parseColor("#FFFFFF"));

        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMore();
            }
        });
        foodInfo.addView(addFood);
        loadMore = addFood;
        //add food
        RequestParams rp = new RequestParams();
        rp.add("keyword", keyword);
        HttpUtils http = new HttpUtils();
        String authorization = ((MyApplication)getApplication()).token_type + " " +  ((MyApplication)getApplication()).access_token;
        http.client.addHeader("Accept", "application/json");
        http.client.addHeader("Authorization", authorization);
        http.get("api/Foods", rp, new JsonHttpResponseHandler() {
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
                        JSONObject obj = response.getJSONObject(i);
                        String ID = obj.get("ID").toString();
                        String Name = obj.get("NameVN").toString().equals("null") ? obj.get("NameENG").toString() : obj.get("NameVN").toString();
                        String Protein = obj.get("Protein").toString().equals("null") ? "0" : obj.get("Protein").toString();
                        String Fat = obj.get("Fat").toString().equals("null") ? "0" : obj.get("Fat").toString();
                        String Carbs = obj.get("Carbs").toString().equals("null") ? "0" : obj.get("Carbs").toString();
                        String Calories = obj.get("Calories").toString().equals("null") ? "0" : obj.get("Calories").toString();
                        String Unit = obj.get("Unit").toString().equals("null") ? "" : obj.get("Unit").toString();
                        String FollowedBy = obj.get("FollowedBy").toString().equals("null") ? "0" : obj.get("FollowedBy").toString();


                        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View v = vi.inflate(R.layout.food_info, null);
                        // fill in any details dynamically here
                        TextView foodID =  v.findViewById(R.id.food_id);
                        foodID.setText(ID);
                        TextView name =  v.findViewById(R.id.txt_library_name);
                        name.setText(Name);
                        TextView nutrition_info =  v.findViewById(R.id.nutrition_info);
                        nutrition_info.setText("("+ Fat + " fats, "+ Carbs +" carbs, " + Protein + " proteins)");
                        TextView txt_library_count =  v.findViewById(R.id.txt_library_count);
                        txt_library_count.setText(FollowedBy);
                        TextView txt_library_calories = v.findViewById(R.id.txt_library_calories);
                        txt_library_calories.setText(Calories);
                        ImageButton addBtn = v.findViewById(R.id.btnAdd);

                        addBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(LibraryActivity.this, PopUpLibraryPlus.class);
                                intent.putExtra("FoodID", ((TextView)((ViewGroup)v.getParent().getParent().getParent()).findViewById(R.id.food_id)).getText());
                                startActivity(intent);
                            }
                        });
                        ImageButton btnHnag = v.findViewById(R.id.btnHnag);
                        btnHnag.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(LibraryActivity.this, Pop.class);
                                intent.putExtra("FoodID", ((TextView)((ViewGroup)v.getParent().getParent().getParent()).findViewById(R.id.food_id)).getText());
                                startActivity(intent);
                            }
                        });
                        // insert into main view
                        ViewGroup insertPoint = (ViewGroup) findViewById(R.id.food_content);

                        insertPoint.addView(v, insertPoint.indexOfChild(loadMore), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


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

    public void loadMore(){
        page++;
        //add food
        RequestParams rp = new RequestParams();
        rp.add("keyword", keyword);
        rp.add("page", String.valueOf(page));
        HttpUtils http = new HttpUtils();
        String authorization = ((MyApplication)getApplication()).token_type + " " +  ((MyApplication)getApplication()).access_token;
        http.client.addHeader("Accept", "application/json");
        http.client.addHeader("Authorization", authorization);
        http.get("api/Foods", rp, new JsonHttpResponseHandler() {
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
                        JSONObject obj = response.getJSONObject(i);
                        String ID = obj.get("ID").toString();
                        String Name = obj.get("NameVN").toString().equals("null") ? obj.get("NameENG").toString() : obj.get("NameVN").toString();
                        String Protein = obj.get("Protein").toString().equals("null") ? "0" : obj.get("Protein").toString();
                        String Fat = obj.get("Fat").toString().equals("null") ? "0" : obj.get("Fat").toString();
                        String Carbs = obj.get("Carbs").toString().equals("null") ? "0" : obj.get("Carbs").toString();
                        String Calories = obj.get("Calories").toString().equals("null") ? "0" : obj.get("Calories").toString();
                        String Unit = obj.get("Unit").toString().equals("null") ? "" : obj.get("Unit").toString();
                        String FollowedBy = obj.get("FollowedBy").toString().equals("null") ? "0" : obj.get("FollowedBy").toString();


                        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View v = vi.inflate(R.layout.food_info, null);
                        // fill in any details dynamically here
                        TextView foodID =  v.findViewById(R.id.food_id);
                        foodID.setText(ID);
                        TextView name =  v.findViewById(R.id.txt_library_name);
                        name.setText(Name);
                        TextView nutrition_info =  v.findViewById(R.id.nutrition_info);
                        nutrition_info.setText("("+ Fat + " fats, "+ Carbs +" carbs, " + Protein + " proteins)");
                        TextView txt_library_count =  v.findViewById(R.id.txt_library_count);
                        txt_library_count.setText(FollowedBy);
                        TextView txt_library_calories = v.findViewById(R.id.txt_library_calories);
                        txt_library_calories.setText(Calories);
                        ImageButton addBtn = v.findViewById(R.id.btnAdd);
                        addBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(LibraryActivity.this, AddingMeal.class);
                                intent.putExtra("FoodID", ((TextView)((ViewGroup)v.getParent().getParent().getParent()).findViewById(R.id.food_id)).getText());
                                startActivity(intent);
                            }
                        });
                        ImageButton btnHnag = v.findViewById(R.id.btnHnag);
                        btnHnag.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(LibraryActivity.this, Pop.class);
                                intent.putExtra("FoodID", ((TextView)((ViewGroup)v.getParent().getParent().getParent()).findViewById(R.id.food_id)).getText());
                                startActivity(intent);
                            }
                        });
                        // insert into main view
                        ViewGroup insertPoint = (ViewGroup) findViewById(R.id.food_content);

                        insertPoint.addView(v, insertPoint.indexOfChild(loadMore), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));


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



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(LibraryActivity.this, MainActivity.class));
                    return true;
                case R.id.navigation_diary:
                    startActivity(new Intent(LibraryActivity.this, DiaryActivity.class));
                    return true;
                case R.id.navigation_library:

                    return true;
                case R.id.navigation_me:
                    startActivity(new Intent(LibraryActivity.this, Personal.class));
                    return true;
            }
            return false;
        }
    };

    public void goToAddMeal(View view) {
        startActivity(new Intent(this, AddingMeal.class));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void Search() {
        keyword = ((EditText)findViewById(R.id.edtAccount)).getText().toString();
        InitData();
    }

    public void logout(){
        MyApplication myApplication = (MyApplication)getApplication();
        myApplication.access_token = null;
        myApplication.token_type = null;
        myApplication.username = null;
        myApplication.accUser = new AccUser();
        startActivity(new Intent(LibraryActivity.this, LoginPage.class));
    }







//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

}
