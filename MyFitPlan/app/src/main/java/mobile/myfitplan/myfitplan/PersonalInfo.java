package mobile.myfitplan.myfitplan;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


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
        TextView textView = rootView.findViewById(R.id.change_info_text_button);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangInfo.class));
            }
        });
        TextView changeGoal = rootView.findViewById(R.id.change_goal_text_button);
        changeGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangeGoal.class));
            }
        });
        TextView changePassword = rootView.findViewById(R.id.change_password_text_button);
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangePassword.class));
            }
        });
        TextView target_record = rootView.findViewById(R.id.target_record);
        target_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TargetRecord.class));
            }
        });
        return rootView;
    }

}
