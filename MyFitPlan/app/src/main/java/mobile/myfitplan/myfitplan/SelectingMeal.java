package mobile.myfitplan.myfitplan;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class SelectingMeal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecting_meal);

        //add food
        RequestParams rp = new RequestParams();
//        rp.add("accUserID", String.valueOf(((MyApplication)getApplication()).accUser.ID));
//        rp.add("getType", "1");
        HttpUtils http = new HttpUtils();
        String authorization = ((MyApplication)getApplication()).token_type + " " +  ((MyApplication)getApplication()).access_token;
        http.client.addHeader("Accept", "application/json");
        http.client.addHeader("Authorization", authorization);
        http.get("api/PersonalCategories", rp, new JsonHttpResponseHandler() {
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
                        String name = obj.get("Name").toString();
                        JSONArray foods = obj.getJSONArray("Foods");
                        //add category view
                        LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View v = vi.inflate(R.layout.personal_category, null);
                        // fill in any details dynamically here
                        TextView foodName =  v.findViewById(R.id.food_name);
                        foodName.setText(name);
                        LinearLayout linearLayoutMinimize = (LinearLayout) v.findViewById(R.id.minimize_layout);
                        final LinearLayout foodDisplay = (LinearLayout) v.findViewById(R.id.foodDisplay);
                        final TextView txtPlus = v.findViewById(R.id.txtPlus);

                        linearLayoutMinimize.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(foodDisplay.getVisibility() == View.VISIBLE){
                                    setLayoutInvisible(foodDisplay, txtPlus);
                                } else{
                                    setLayoutVisible(foodDisplay, txtPlus);
                                }
                            }
                        });
                        // insert into main view
                        ViewGroup insertPoint = (ViewGroup)findViewById(R.id.category_content);
                        insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

//                        LayoutInflater lv = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                        View btnView = lv.inflate(R.layout.add_food_button_layout, null);
//                        LinearLayout ll = btnView.findViewById(R.id.add_meal_layout);
//                        ll.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                startActivity(new Intent(SelectingMeal.this, LibraryActivity.class));
//                            }
//                        });
//                        foodDisplay.addView(btnView, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                        //add food
                        for (int j = 0; j < foods.length(); j++){
                            try{
                                JSONObject foodObj = foods.getJSONObject(j);

                                String ID = foodObj.get("ID").toString();
                                String Name = foodObj.get("NameVN").toString().equals("null") ? foodObj.get("NameENG").toString() : obj.get("NameVN").toString();
                                String Protein = foodObj.get("Protein").toString().equals("null") ? "0" : foodObj.get("Protein").toString();
                                String Fat = foodObj.get("Fat").toString().equals("null") ? "0" : foodObj.get("Fat").toString();
                                String Carbs = foodObj.get("Carbs").toString().equals("null") ? "0" : foodObj.get("Carbs").toString();
                                String Calories = foodObj.get("Calories").toString().equals("null") ? "0" : foodObj.get("Calories").toString();
                                String Unit = foodObj.get("Unit").toString().equals("null") ? "" : foodObj.get("Unit").toString();
                                String FollowedBy = foodObj.get("FollowedBy").toString().equals("null") ? "0" : foodObj.get("FollowedBy").toString();


                                LayoutInflater li = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View view = li.inflate(R.layout.food_info_2, null);
                                // fill in any details dynamically here
                                TextView foodID =  view.findViewById(R.id.food_id);
                                foodID.setText(ID);
                                TextView foodname =  view.findViewById(R.id.txt_food_name);
                                foodname.setText(Name);
                                TextView nutrition_info =  view.findViewById(R.id.txt_nutrition);
                                nutrition_info.setText("("+ Fat + " fats, "+ Carbs +" carbs, " + Protein + " proteins)");
                                TextView txt_library_calories = view.findViewById(R.id.txt_calories);
                                txt_library_calories.setText(Calories + " cal");
                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(SelectingMeal.this, Pop.class);
                                        intent.putExtra("FoodID", ((TextView)v.findViewById(R.id.food_id)).getText());
                                        startActivity(intent);
                                    }
                                });
                                // insert into main view
                                foodDisplay.addView(view, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                            }catch (Exception ex){

                            }
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

//        //khai báo
//        foodDisplay = (LinearLayout)findViewById(R.id.foodDisplay);
//        txtPlus = (TextView)findViewById(R.id.txtPlus);
//        foodDisplay1 = (LinearLayout) findViewById(R.id.foodDisplay1);
//        txtPlus1 = (TextView) findViewById(R.id.txtPlus1);
//        foodDisplay2 = (LinearLayout) findViewById(R.id.foodDisplay2);
//        txtPlus2 = (TextView) findViewById(R.id.txtPlus2);
//
//        //layout popup
//        LinearLayout popUp = (LinearLayout) findViewById(R.id.popUp);
//        popUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SelectingMeal.this, Pop.class));
//            }
//        });
//
//        //layout popup 2
//        LinearLayout popUp1 = (LinearLayout) findViewById(R.id.popUp1);
//        popUp1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SelectingMeal.this, Pop.class));
//            }
//        });
//
//        //layout popup 3
//        LinearLayout popUp2 = (LinearLayout) findViewById(R.id.popUp2);
//        popUp2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SelectingMeal.this, Pop.class));
//            }
//        });
//
//        //layout popup 4
//        LinearLayout popUp3 = (LinearLayout) findViewById(R.id.popUp3);
//        popUp3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SelectingMeal.this, Pop.class));
//            }
//        });
//
//        //layout popup 5
//        LinearLayout popUp4 = (LinearLayout) findViewById(R.id.popUp4);
//        popUp4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SelectingMeal.this, Pop.class));
//            }
//        });
//        //layout popup 6
//        LinearLayout popUp5 = (LinearLayout) findViewById(R.id.popUp5);
//        popUp5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SelectingMeal.this, Pop.class));
//            }
//        });
//
//        //layout mini 1
//        LinearLayout mmL = (LinearLayout) findViewById(R.id.minimize_layout);
//
//        mmL.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (foodDisplay.getVisibility() == View.VISIBLE) {
//                    setLayoutInvisible(foodDisplay, txtPlus);
//                } else {
//                    setLayoutVisible(foodDisplay, txtPlus);
//                }
//            }
//        });
//
//        //layout mini 2
//        LinearLayout mmL1 = (LinearLayout) findViewById(R.id.minimize_layout1);
//
//        mmL1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (foodDisplay1.getVisibility() == View.VISIBLE) {
//                    setLayoutInvisible(foodDisplay1, txtPlus1);
//                } else {
//                    setLayoutVisible(foodDisplay1, txtPlus1);
//                }
//            }
//        });
//        //layout mini 3
//        LinearLayout mmL2 = (LinearLayout) findViewById(R.id.minimize_layout2);
//
//        mmL2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (foodDisplay2.getVisibility() == View.VISIBLE) {
//                    setLayoutInvisible(foodDisplay2, txtPlus2);
//                } else {
//                    setLayoutVisible(foodDisplay2, txtPlus2);
//                }
//            }
//        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //set title and color for toolbar
        int textColor = getResources().getColor(R.color.textColorOnPrimary);
        String сolorString = String.format("%X", textColor).substring(2);
        getSupportActionBar().setTitle(Html.fromHtml(String.format("<font color=\"#%s\"'>Chọn món ăn </font>", сolorString)));

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

//    public void clickToBack(View view) {
//        startActivity(new Intent(this, DiaryActivity.class));
//    }

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
