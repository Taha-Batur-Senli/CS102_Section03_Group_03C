package android.example.fireapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMenu extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;
    Button logOff, myAccount, allRestaurants, favRestaurants, myReservations, help;

    public FragmentMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMenu newInstance(String param1, String param2) {
        FragmentMenu fragment = new FragmentMenu();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_fragment_menu, container, false);
        logOff = view.findViewById(R.id.log_off_button);
        logOff.setOnClickListener(this);
        myReservations = view.findViewById(R.id.reservation_button);
        myReservations.setOnClickListener(this);
        myAccount = view.findViewById(R.id.my_account_button);
        myAccount.setOnClickListener(this);
        allRestaurants = view.findViewById(R.id.show_all_button);
        allRestaurants.setOnClickListener(this);
        favRestaurants = view.findViewById(R.id.favourite_restaurants);
        favRestaurants.setOnClickListener(this);
        help = view.findViewById(R.id.help_call_us_button);
        help.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.log_off_button:
                FirebaseAuth.getInstance().signOut();
                startActivity( new Intent(getActivity(), LogInActivity.class));
                break;
            case R.id.my_account_button:
                startActivity( new Intent(getActivity(), CustomerMyAccountActivity.class));
                break;
            case R.id.show_all_button:
                startActivity( new Intent(getActivity(), AllRestaurantsDisplay.class));
                break;
            case R.id.favourite_restaurants:
                startActivity(new Intent(getActivity(), MyFavActivities.class));
                break;
            case R.id.reservation_button:
                startActivity(new Intent(getActivity(), MyReservations.class));
                break;
            case R.id.help_call_us_button:
                startActivity(new Intent(getActivity(), CustHelpActivity.class));
                break;

        }
    }
}
