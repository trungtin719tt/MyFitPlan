package mobile.myfitplan.myfitplan;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class SelectingMeal extends AppCompatActivity {
    private LinearLayout foodDisplay, foodDisplay1;
    private TextView txtPlus, txtPlus1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecting_meal);

        foodDisplay = (LinearLayout)findViewById(R.id.foodDisplay);
        txtPlus = (TextView)findViewById(R.id.txtPlus);
        foodDisplay1 = (LinearLayout) findViewById(R.id.foodDisplay1);
        txtPlus1 = (TextView) findViewById(R.id.txtPlus1);

        //layout popup
        LinearLayout popUp = (LinearLayout) findViewById(R.id.popUp);
        popUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectingMeal.this, Pop.class));
            }
        });

        //layout popup 2
        LinearLayout popUp1 = (LinearLayout) findViewById(R.id.popUp1);
        popUp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectingMeal.this, Pop.class));
            }
        });

        //layout popup 3
        LinearLayout popUp2 = (LinearLayout) findViewById(R.id.popUp2);
        popUp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectingMeal.this, Pop.class));
            }
        });

        //layout popup 4
        LinearLayout popUp3 = (LinearLayout) findViewById(R.id.popUp3);
        popUp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectingMeal.this, Pop.class));
            }
        });

        //layout mini 1
        LinearLayout mmL = (LinearLayout) findViewById(R.id.minimize_layout);

        mmL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (foodDisplay.getVisibility() == View.VISIBLE) {
                    setLayoutInvisible(foodDisplay, txtPlus);
                } else {
                    setLayoutVisible(foodDisplay, txtPlus);
                }
            }
        });

        //layout mini 2
        LinearLayout mmL1 = (LinearLayout) findViewById(R.id.minimize_layout1);

        mmL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (foodDisplay1.getVisibility() == View.VISIBLE) {
                    setLayoutInvisible(foodDisplay1, txtPlus1);
                } else {
                    setLayoutVisible(foodDisplay1, txtPlus1);
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //set title and color for toolbar
        int textColor = getResources().getColor(R.color.textColorOnPrimary);
        String сolorString = String.format("%X", textColor).substring(2);
        getSupportActionBar().setTitle(Html.fromHtml(String.format("<font color=\"#%s\"'>Chọn món ăn </font>", сolorString)));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    public void clickToMinimizeMaxmimize(View view) {
//        if (foodDisplay.getVisibility() == View.VISIBLE) {
//            setLayoutInvisible();
//        } else {
//            setLayoutVisible();
//        }
//    }


    public void setLayoutInvisible(LinearLayout LL, TextView TV) {
        if (LL.getVisibility() == View.VISIBLE) {
            LL.setVisibility(View.GONE);
            TV.setVisibility(View.VISIBLE);
        }
    }

    public void setLayoutVisible(LinearLayout LL, TextView TV) {
        if (LL.getVisibility() == View.GONE) {
            LL.setVisibility(View.VISIBLE);
            TV.setVisibility(View.GONE);
        }
    }

    public void clickToGoToLibrary(View view) {
        startActivity(new Intent(SelectingMeal.this, LibraryActivity.class));
    }

//    public void setLayoutVisible1() {
//        if (foodDisplay1.getVisibility() == View.GONE) {
//            foodDisplay1.setVisibility(View.VISIBLE);
//            txtPlus1.setVisibility(View.GONE);
//        }
//    }
//
//    public void setLayoutInvisible1() {
//        if (foodDisplay1.getVisibility() == View.VISIBLE) {
//            foodDisplay1.setVisibility(View.GONE);
//            txtPlus1.setVisibility(View.VISIBLE);
//        }
//    }

}