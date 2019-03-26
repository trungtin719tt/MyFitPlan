package mobile.myfitplan.myfitplan;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalInfo extends Fragment {


    public PersonalInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_personal_info, container, false);
//        TextView textView = rootView.findViewById(R.id.change_info_text_button);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), ChangInfo.class));
//            }
//        });
//        TextView changeGoal = rootView.findViewById(R.id.change_goal_text_button);
//        changeGoal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), ChangeGoal.class));
//            }
//        });
        TextView changePassword = rootView.findViewById(R.id.change_password_text_button);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangePassword.class));
            }
        });
//        TextView target_record = rootView.findViewById(R.id.target_record);
//        target_record.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), TargetRecord.class));
//            }
//        });

        RequestParams param = new RequestParams();
        String username = ((MyApplication)getActivity().getApplication()).username;
        HttpUtils httpUtils = new HttpUtils();
        String authorization = ((MyApplication)getActivity().getApplication()).token_type + " " +  ((MyApplication)getActivity().getApplication()).access_token;
        httpUtils.client.addHeader("Authorization", authorization);
        httpUtils.get("api/AccUsers", param, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject res = new JSONObject(response.toString());
                    int Purpose = (int)res.get("Purpose");
                    if (Purpose == 0){
                        ((TextView)getActivity().findViewById(R.id.purpose)).setText("Mục tiêu: Giữ dáng");
                    } else if (Purpose == 1){
                        ((TextView)getActivity().findViewById(R.id.purpose)).setText("Mục tiêu: Tăng cân");
                    } else{
                        ((TextView)getActivity().findViewById(R.id.purpose)).setText("Mục tiêu: Giảm cân");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
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

        RequestParams rp = new RequestParams();
        HttpUtils http = new HttpUtils();
        http.client.addHeader("Authorization", authorization);
        http.get("api/UserProgresses", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject res = new JSONObject(response.toString());
                    String weight = res.getString("Weight");
                    String height = res.getString("Height");
                    String calo = res.getString("GoalCalories");
                    String fat = res.getString("GoalFat");
                    String carb = res.getString("GoalCarbs");
                    String protein = res.getString("GoalProtein");
                    ((TextView)getActivity().findViewById(R.id.weight)).setText("Cân nặng: " + weight);
                    ((TextView)getActivity().findViewById(R.id.height)).setText("Chiều cao: " + height);
                    ((TextView)getActivity().findViewById(R.id.calories)).setText("Calories: " + calo);
                    ((TextView)getActivity().findViewById(R.id.calories_details))
                            .setText("Carbs "+ carb +" / Fat "+ fat +" / Protein "+ protein +"");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
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
        return rootView;
    }

}
