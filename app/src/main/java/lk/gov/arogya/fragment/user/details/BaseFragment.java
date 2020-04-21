package lk.gov.arogya.fragment.user.details;

import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import lk.gov.arogya.R;


public class BaseFragment extends Fragment {

    public BaseFragment() {
    }

    void initNextBtn(View view, final Fragment fragment) {
        Button nxtBtn = view.findViewById(R.id.btn_next);
        nxtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveValues();
                showFragment(fragment);
            }
        });
    }

    void initPrevBtn(View view, final Fragment fragment) {
        Button prevBtn = view.findViewById(R.id.btn_previous);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveValues();
                showFragment(fragment);
            }
        });
    }

    private void showFragment(Fragment fragment) {
        if (getActivity() != null) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
            transaction.replace(R.id.frag_placeholder, fragment);
//        transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    public void saveValues() {
    }

}
