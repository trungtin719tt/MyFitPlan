package mobile.myfitplan.myfitplan;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;

import org.w3c.dom.Text;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link PersonalMenu.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link PersonalMenu#newInstance} factory method to
// * create an instance of this fragment.
// */
public class PersonalMenu extends Fragment {
    private LinearLayout mainLayout, foodDisplay, foodDisplay1;
    private TextView txtPlus, txtPlus1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personal_menu, container, false);

        RelativeLayout addFolder = rootView.findViewById(R.id.add_folder);
        addFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewFolder.class));
            }
        });

        ImageView addMeal1 = rootView.findViewById(R.id.add_meal1);
        addMeal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddingMeal.class));
            }
        });

        ImageView addMeal2 = rootView.findViewById(R.id.add_meal2);
        addMeal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddingMeal.class));
            }
        });

//        LinearLayout linearLayout = (LinearLayout) rootView.findViewById(R.id.popUp);
//        linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), NewFolder.class));
//            }
//        });
        LinearLayout linearLayoutMinimize = (LinearLayout) rootView.findViewById(R.id.minimize_layout);
        linearLayoutMinimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (foodDisplay.getVisibility() == View.VISIBLE) {
                    setLayoutInvisible(foodDisplay, txtPlus);
                } else {
                    setLayoutVisible(foodDisplay, txtPlus);
                }
            }
        });
        LinearLayout linearLayoutMinimize1 = (LinearLayout) rootView.findViewById(R.id.minimize_layout1);
        linearLayoutMinimize1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (foodDisplay1.getVisibility() == View.VISIBLE) {
                    setLayoutInvisible(foodDisplay1, txtPlus1);
                } else {
                    setLayoutVisible(foodDisplay1, txtPlus1);
                }
            }
        });
        foodDisplay = (LinearLayout)rootView.findViewById(R.id.foodDisplay);
        txtPlus = (TextView)rootView.findViewById(R.id.txtPlus);
        foodDisplay1 = (LinearLayout) rootView.findViewById(R.id.foodDisplay1);
        txtPlus1 = (TextView) rootView.findViewById(R.id.txtPlus1);
        return rootView;
    }
//    public void clickToMinimizeMaxmimize(LinearLayout LL) {
//        if (LL.getVisibility() == View.VISIBLE) {
//            setLayoutInvisible();
//        } else {
//            setLayoutVisible();
//        }
//    }

    public void clickToPopUp(View view) {
        Intent intent = new Intent(getActivity(), NewFolder.class);
        startActivity(intent);
    }

    public void clickToMinimizeMaxmimize1(View view) {
        if (foodDisplay1.getVisibility() == View.VISIBLE) {
            setLayoutInvisible1();
        } else {
            setLayoutVisible1();
        }


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

    public void setLayoutVisible1() {
        if (foodDisplay1.getVisibility() == View.GONE) {
            foodDisplay1.setVisibility(View.VISIBLE);
            txtPlus1.setVisibility(View.GONE);
        }
    }

    public void setLayoutInvisible1() {
        if (foodDisplay1.getVisibility() == View.VISIBLE) {
            foodDisplay1.setVisibility(View.GONE);
            txtPlus1.setVisibility(View.VISIBLE);
        }
    }
}
