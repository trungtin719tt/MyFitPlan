package mobile.myfitplan.myfitplan;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link PersonalMenu.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link PersonalMenu#newInstance} factory method to
// * create an instance of this fragment.
// */
public class PersonalMenu extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personal_menu, container, false);

        //add food
        RequestParams rp = new RequestParams();
//        rp.add("accUserID", String.valueOf(((MyApplication)getActivity().getApplication()).accUser.ID));
//        rp.add("getType", "1");
        HttpUtils http = new HttpUtils();
        String authorization = ((MyApplication) getActivity().getApplication()).token_type + " " +  ((MyApplication)getActivity().getApplication()).access_token;
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
                        LayoutInflater vi = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                        ViewGroup insertPoint = (ViewGroup) getActivity().findViewById(R.id.category_content);
                        insertPoint.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                        LayoutInflater lv = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View btnView = lv.inflate(R.layout.add_food_button_layout, null);
                        LinearLayout ll = btnView.findViewById(R.id.add_meal_layout);
                        ll.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(getActivity(), LibraryActivity.class));
                            }
                        });
                        foodDisplay.addView(btnView, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                        //add food
                        for (int j = 0; j < foods.length(); j++){
                            try{
                                JSONObject foodObj = foods.getJSONObject(j);

                                String ID = foodObj.get("ID").toString();
                                String Name = foodObj.get("NameVN").toString().equals("null") ? foodObj.get("NameENG").toString() : foodObj.get("NameVN").toString();
                                String Protein = foodObj.get("Protein").toString().equals("null") ? "0" : foodObj.get("Protein").toString();
                                String Fat = foodObj.get("Fat").toString().equals("null") ? "0" : foodObj.get("Fat").toString();
                                String Carbs = foodObj.get("Carbs").toString().equals("null") ? "0" : foodObj.get("Carbs").toString();
                                String Calories = foodObj.get("Calories").toString().equals("null") ? "0" : foodObj.get("Calories").toString();
                                String Unit = foodObj.get("Unit").toString().equals("null") ? "" : foodObj.get("Unit").toString();
                                String FollowedBy = foodObj.get("FollowedBy").toString().equals("null") ? "0" : foodObj.get("FollowedBy").toString();


                                LayoutInflater li = (LayoutInflater) getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                Toast.makeText(getActivity().getApplicationContext(),"Kiểm tra lại kết nối mạng",Toast.LENGTH_LONG).show();
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

        RelativeLayout addFolder = rootView.findViewById(R.id.add_folder);
        addFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewFolder.class));
            }
        });

//        ImageView addMeal1 = rootView.findViewById(R.id.add_meal1);
//        addMeal1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), LibraryActivity.class));
//            }
//        });
//
//        ImageView addMeal2 = rootView.findViewById(R.id.add_meal2);
//        addMeal2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), LibraryActivity.class));
//            }
//        });
//
//        ImageView addMeal3 = rootView.findViewById(R.id.add_meal3);
//        addMeal3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), LibraryActivity.class));
//            }
//        });

//        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.popUp);
//        linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), NewFolder.class));
//            }
//        });
//        LinearLayout linearLayoutMinimize = (LinearLayout) rootView.findViewById(R.id.minimize_layout);
//        linearLayoutMinimize.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (foodDisplay.getVisibility() == View.VISIBLE) {
//                    setLayoutInvisible(foodDisplay, txtPlus);
//                } else {
//                    setLayoutVisible(foodDisplay, txtPlus);
//                }
//            }
//        });
//        LinearLayout linearLayoutMinimize1 = (LinearLayout) rootView.findViewById(R.id.minimize_layout1);
//        linearLayoutMinimize1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (foodDisplay1.getVisibility() == View.VISIBLE) {
//                    setLayoutInvisible(foodDisplay1, txtPlus1);
//                } else {
//                    setLayoutVisible(foodDisplay1, txtPlus1);
//                }
//            }
//        });
//        LinearLayout linearLayoutMinimize2 = (LinearLayout) rootView.findViewById(R.id.minimize_layout2);
//        linearLayoutMinimize2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (foodDisplay2.getVisibility() == View.VISIBLE) {
//                    setLayoutInvisible(foodDisplay2, txtPlus2);
//                } else {
//                    setLayoutVisible(foodDisplay2, txtPlus2);
//                }
//            }
//        });
//        foodDisplay = (LinearLayout)rootView.findViewById(R.id.foodDisplay);
//        txtPlus = (TextView)rootView.findViewById(R.id.txtPlus);
//        foodDisplay1 = (LinearLayout) rootView.findViewById(R.id.foodDisplay1);
//        txtPlus1 = (TextView) rootView.findViewById(R.id.txtPlus1);
//        foodDisplay2 = (LinearLayout) rootView.findViewById(R.id.foodDisplay2);
//        txtPlus2 = (TextView) rootView.findViewById(R.id.txtPlus2);
        return rootView;
    }
//    public void clickToMinimizeMaxmimize(LinearLayout LL) {
//        if (LL.getVisibility() == View.VISIBLE) {
//            setLayoutInvisible();
//        } else {
//            setLayoutVisible();
//        }
//    }

//    public void clickToPopUp(View view) {
//        Intent intent = new Intent(getActivity(), NewFolder.class);
//        startActivity(intent);
//    }
//
//    public void clickToMinimizeMaxmimize1(View view) {
//        if (foodDisplay1.getVisibility() == View.VISIBLE) {
//            setLayoutInvisible1();
//        } else {
//            setLayoutVisible1();
//        }
//
//
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
