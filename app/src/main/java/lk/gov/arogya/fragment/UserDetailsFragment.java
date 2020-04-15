package lk.gov.arogya.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class UserDetailsFragment extends Fragment {
    private static final String ARG_FRAGMENT_LAYOUT = "arg_frag_lay";
    private int fragLayout;

    public UserDetailsFragment() {
    }

    public static UserDetailsFragment newInstance(int fragLayout) {
        UserDetailsFragment fragment = new UserDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_FRAGMENT_LAYOUT, fragLayout);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fragLayout = getArguments().getInt(ARG_FRAGMENT_LAYOUT);
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(fragLayout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initInstances();
    }

    private void initInstances() {

    }
}
