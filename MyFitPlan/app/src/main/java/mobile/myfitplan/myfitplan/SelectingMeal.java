package mobile.myfitplan.myfitplan;

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
    public LinearLayout foodDisplay, foodDisplay1, foodDisplay2;
    public TextView txtPlus, txtPlus1, txtPlus2;
    private TextView txt_library_calories1, txt_library_calories2, txt_library_calories3, txt_library_calories4, txt_library_calories5, txt_library_calories6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecting_meal);

        //khai báo
        foodDisplay = (LinearLayout)findViewById(R.id.foodDisplay);
        txtPlus = (TextView)findViewById(R.id.txtPlus);
        foodDisplay1 = (LinearLayout) findViewById(R.id.foodDisplay1);
        txtPlus1 = (TextView) findViewById(R.id.txtPlus1);
        foodDisplay2 = (LinearLayout) findViewById(R.id.foodDisplay2);
        txtPlus2 = (TextView) findViewById(R.id.txtPlus2);

        //layout popup
        LinearLayout popUp = (LinearLayout) findViewById(R.id.popUp);
        popUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectingMeal.this, Pop.class);
                txt_library_calories1 = findViewById(R.id.txt_library_calories1);
                intent.putExtra("KCAL", txt_library_calories1.getText().toString());
                startActivity(intent);
            }
        });

        //layout popup 2
        LinearLayout popUp1 = (LinearLayout) findViewById(R.id.popUp1);
        popUp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectingMeal.this, Pop.class);
                txt_library_calories2 = findViewById(R.id.txt_library_calories2);
                intent.putExtra("KCAL", txt_library_calories2.getText().toString());
                startActivity(intent);
            }
        });

        //layout popup 3
        LinearLayout popUp2 = (LinearLayout) findViewById(R.id.popUp2);
        popUp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectingMeal.this, Pop.class);
                txt_library_calories3 = findViewById(R.id.txt_library_calories3);
                intent.putExtra("KCAL", txt_library_calories3.getText().toString());
                startActivity(intent);
            }
        });

        //layout popup 4
        LinearLayout popUp3 = (LinearLayout) findViewById(R.id.popUp3);
        popUp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectingMeal.this, Pop.class);
                txt_library_calories4 = findViewById(R.id.txt_library_calories4);
                intent.putExtra("KCAL", txt_library_calories4.getText().toString());
                startActivity(intent);
            }
        });

        //layout popup 5
        LinearLayout popUp4 = (LinearLayout) findViewById(R.id.popUp4);
        popUp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectingMeal.this, Pop.class);
                txt_library_calories5 = findViewById(R.id.txt_library_calories5);
                intent.putExtra("KCAL", txt_library_calories5.getText().toString());
                startActivity(intent);
            }
        });
        //layout popup 6
        LinearLayout popUp5 = (LinearLayout) findViewById(R.id.popUp5);
        popUp5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectingMeal.this, Pop.class);
                txt_library_calories6 = findViewById(R.id.txt_library_calories6);
                intent.putExtra("KCAL", txt_library_calories6.getText().toString());
                startActivity(intent);
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
        //layout mini 3
        LinearLayout mmL2 = (LinearLayout) findViewById(R.id.minimize_layout2);

        mmL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (foodDisplay2.getVisibility() == View.VISIBLE) {
                    setLayoutInvisible(foodDisplay2, txtPlus2);
                } else {
                    setLayoutVisible(foodDisplay2, txtPlus2);
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

    public void clickToMoveToLibrary(View view) {
        startActivity(new Intent(this, LibraryActivity.class));
    }

}
