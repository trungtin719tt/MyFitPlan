package mobile.myfitplan.myfitplan;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LibraryActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
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

        //pop up plus 1
        Button popUp1 = (Button) findViewById(R.id.btnAdd1);
        popUp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LibraryActivity.this, Pop.class));
            }
        });
        //pop up plus 2
        Button popUp2= (Button) findViewById(R.id.btnAdd2);
        popUp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LibraryActivity.this, Pop.class));
            }
        });
        //pop up plus 3
        Button popUp3 = (Button) findViewById(R.id.btnAdd3);
        popUp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LibraryActivity.this, Pop.class));
            }
        });
        //pop up plus 4
        Button popUp4 = (Button) findViewById(R.id.btnAdd4);
        popUp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LibraryActivity.this, Pop.class));
            }
        });
        //pop up plus 5
        Button popUp5 = (Button) findViewById(R.id.btnAdd5);
        popUp5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LibraryActivity.this, Pop.class));
            }
        });
        //pop up plus 6
        Button popUp6 = (Button) findViewById(R.id.btnAdd6);
        popUp6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LibraryActivity.this, Pop.class));
            }
        });

//        //pop up food 1
//        Button pop1 = (Button) findViewById(R.id.btnAddFood1);
//        pop1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LibraryActivity.this, Pop.class));
//            }
//        });
//
//        //pop up food 2
//        Button pop2 = (Button) findViewById(R.id.btnAddFood2);
//        pop2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LibraryActivity.this, Pop.class));
//            }
//        });
//        //pop up food 3
//        Button pop3 = (Button) findViewById(R.id.btnAddFood3);
//        pop3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LibraryActivity.this, Pop.class));
//            }
//        });
//        //pop up food 4
//        Button pop4 = (Button) findViewById(R.id.btnAddFood4);
//        pop4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LibraryActivity.this, Pop.class));
//            }
//        });
//        //pop up food 5
//        Button pop5 = (Button) findViewById(R.id.btnAddFood5);
//        pop5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LibraryActivity.this, Pop.class));
//            }
//        });
//        //pop up food 6
//        Button pop6 = (Button) findViewById(R.id.btnAddFood6);
//        pop6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LibraryActivity.this, Pop.class));
//            }
//        });
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
        TextView textView = new TextView(this);
        textView.setText("Không có kết quả phù hợp!");
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        LinearLayout content = findViewById(R.id.food_content);

        Button addFood = new Button(this);
        addFood.setText("Thêm món mới");
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
                startActivity(new Intent(LibraryActivity.this, AddingMeal.class));
            }
        });

        content.removeAllViews();
        content.addView(textView);
        content.addView(addFood);
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
