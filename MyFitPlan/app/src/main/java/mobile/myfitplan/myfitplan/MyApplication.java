package mobile.myfitplan.myfitplan;

import android.app.Application;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class MyApplication extends Application {

    public Map<Integer,String> NutritionMap = new HashMap<Integer,String>();
    public String access_token;
    public String token_type;
    public  AccUser accUser;
    public String username;

//    URL url;
//    HttpsURLConnection myConnection;

    public  MyApplication(){
        accUser = new AccUser();
        NutritionMap.put(1, "Protein");
        NutritionMap.put(2, "Fat");
        NutritionMap.put(3, "Carbs");
        NutritionMap.put(4, "Calories");


//
//        try{
//            // Create URL
//            url = new URL(Url_API);
//
//            // Create connection
//            myConnection = (HttpsURLConnection) url.openConnection();
//        }catch (Exception ex){

//        }

    }
}