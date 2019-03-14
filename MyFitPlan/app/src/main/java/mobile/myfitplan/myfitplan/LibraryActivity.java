package mobile.myfitplan.myfitplan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class LibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //set title and color for toolbar
        int textColor = getResources().getColor(R.color.textColorOnPrimary);
        String сolorString = String.format("%X", textColor).substring(2);
        getSupportActionBar().setTitle(Html.fromHtml(String.format("<font color=\"#%s\"'>Thư viện món ăn </font>", сolorString)));

        //pop up plus 1
        Button popUp1 = (Button) findViewById(R.id.btnAdd1);
        popUp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LibraryActivity.this, PopUpLibraryPlus.class));
            }
        });
        //pop up plus 2
        Button popUp2= (Button) findViewById(R.id.btnAdd2);
        popUp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LibraryActivity.this, PopUpLibraryPlus.class));
            }
        });
        //pop up plus 3
        Button popUp3 = (Button) findViewById(R.id.btnAdd3);
        popUp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LibraryActivity.this, PopUpLibraryPlus.class));
            }
        });
        //pop up plus 4
        Button popUp4 = (Button) findViewById(R.id.btnAdd4);
        popUp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LibraryActivity.this, PopUpLibraryPlus.class));
            }
        });
        //pop up plus 5
        Button popUp5 = (Button) findViewById(R.id.btnAdd5);
        popUp5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LibraryActivity.this, PopUpLibraryPlus.class));
            }
        });
        //pop up plus 6
        Button popUp6 = (Button) findViewById(R.id.btnAdd6);
        popUp6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LibraryActivity.this, PopUpLibraryPlus.class));
            }
        });

        //pop up food 1
        Button pop1 = (Button) findViewById(R.id.btnAddFood1);
        pop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LibraryActivity.this, Pop.class));
            }
        });

        //pop up food 2
        Button pop2 = (Button) findViewById(R.id.btnAddFood2);
        pop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LibraryActivity.this, Pop.class));
            }
        });
        //pop up food 3
        Button pop3 = (Button) findViewById(R.id.btnAddFood3);
        pop3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LibraryActivity.this, Pop.class));
            }
        });
        //pop up food 4
        Button pop4 = (Button) findViewById(R.id.btnAddFood4);
        pop4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LibraryActivity.this, Pop.class));
            }
        });
        //pop up food 5
        Button pop5 = (Button) findViewById(R.id.btnAddFood5);
        pop5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LibraryActivity.this, Pop.class));
            }
        });
        //pop up food 6
        Button pop6 = (Button) findViewById(R.id.btnAddFood6);
        pop6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LibraryActivity.this, Pop.class));
            }
        });


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

}
