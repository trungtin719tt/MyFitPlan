package mobile.myfitplan.myfitplan;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DiaryActivity extends AppCompatActivity implements View.OnClickListener{
    private DrawerLayout drawerLayout;
    private TextView txt_date;
    private ImageView btn_diary_after;
    private ImageView btn_diary_previous;
    private TextView txt_food, txt_left;
    private TextView txt_food_item, txt_calories, txt_calories_title;
    private Date today, selectedDate;
    private String currentDate, select;
    private RelativeLayout layout_food_item;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

        today = Calendar.getInstance().getTime();

        selectedDate = today;


        //get date
        txt_date = findViewById(R.id.txt_date);
        btn_diary_after = findViewById(R.id.btn_diary_after);
        btn_diary_previous = findViewById(R.id.btn_diary_previous);

        txt_date.setOnClickListener(this);
        btn_diary_previous.setOnClickListener(this);
        btn_diary_after.setOnClickListener(this);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        currentDate = day + "/" + (month + 1) + "/" + year;

        select = currentDate; //start date



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

        txt_food = findViewById(R.id.txt_food);
        txt_left = findViewById(R.id.txt_left);
        txt_food_item = findViewById(R.id.txt_food_item);
        txt_calories = findViewById(R.id.txt_calories);
        txt_calories_title = findViewById(R.id.txt_calories_title);
        layout_food_item = findViewById(R.id.layout_food_item);

//        LinearLayout my_root = (LinearLayout)findViewById(R.id.my_root);
//        LinearLayout LL1 = new LinearLayout(this);
//        LinearLayout LL2 = new LinearLayout(this);
//        LinearLayout LL3 = new LinearLayout(this);
//        TextView view1 = new TextView(this);
//        TextView view2 = new TextView(this);
//        TextView view3 = new TextView(this);
//        view1.setText("Cơm tấm");
//        view2.setText("21 cal..");
//        view3.setText("500");
//
//        LL1.setOrientation(LinearLayout.HORIZONTAL);
//        LL2.setOrientation(LinearLayout.VERTICAL);
//        LL2.addView(view1);
//        LL2.addView(view2);
//        LL3.addView(view3);
//
//        LL1.addView(LL2);
//        LL1.addView(LL3);
//        my_root.addView(LL1);

        if (v == txt_date) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            txt_date.setText(dayOfMonth + "/" + (month + 1) +"/" +year);
                            selectedDate = new Date(year, month, dayOfMonth);
                        }
                    }, year, month, day);
            datePickerDialog.show();


        }

        //previous day
        if (v == btn_diary_previous) {
            cal.setTime(selectedDate);
            cal.add(Calendar.DATE, -1);
            selectedDate = cal.getTime();

//            txt_food.setText("3000");
//            txt_left.setText("250");
//            txt_calories.setText("3000");
//            txt_food_item.setText("Phở đặc biệt");
//            LinearLayout my_root = (LinearLayout)findViewById(R.id.my_root);
//            LinearLayout LL1 = new LinearLayout(this);
//            LinearLayout LL2 = new LinearLayout(this);
//            LinearLayout LL3 = new LinearLayout(this);
//            TextView view1 = new TextView(this);
//            TextView view2 = new TextView(this);
//            TextView view3 = new TextView(this);
//            view1.setText("Cơm tấm");
//            view2.setText("21 cal..");
//            view3.setText("500");
//
//            LL1.setOrientation(LinearLayout.HORIZONTAL);
//            LL2.setOrientation(LinearLayout.VERTICAL);
//            LL2.addView(view1);
//            LL2.addView(view2);
//            LL3.addView(view3);
//
//            LL1.addView(LL2);
//            LL1.addView(LL3);
//            my_root.addView(LL1);
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
                    layout_food_item.setVisibility(View.VISIBLE);
                    txt_date.setText("Hôm nay");
                    txt_food_item.setText("Bún bò Huế");
                    txt_calories.setText("600");
                    txt_calories_title.setText("600");
                    txt_food.setText("600");
                    txt_left.setText("2650");
                }
                if(calSelected.get(Calendar.DAY_OF_MONTH) == calToday.get(Calendar.DAY_OF_MONTH) + 1){
                    txt_date.setText("Ngày mai");
                    txt_calories_title.setText("0");
                    txt_food.setText("0");
                    txt_left.setText("3250");
                    layout_food_item.setVisibility(View.GONE);
                }
                if(calSelected.get(Calendar.DAY_OF_MONTH) == calToday.get(Calendar.DAY_OF_MONTH) - 1){
                    layout_food_item.setVisibility(View.VISIBLE);
                    txt_date.setText("Hôm qua");
                    txt_food_item.setText("Phở đặc biệt");
                    txt_calories.setText("3000");
                    txt_calories_title.setText("3000");
                    txt_food.setText("3000");
                    txt_left.setText("250");
                }
            }
        }

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
