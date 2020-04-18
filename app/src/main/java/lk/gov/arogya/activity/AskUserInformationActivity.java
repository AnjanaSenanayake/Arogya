package lk.gov.arogya.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import lk.gov.arogya.R;
import lk.gov.arogya.activity.parent.ToolbarActivity;
import lk.gov.arogya.fragment.user.details.AskAddressFragment;
import lk.gov.arogya.fragment.user.details.AskGsAndDsFragment;
import lk.gov.arogya.fragment.user.details.AskPersonalDetailsFragment;

public class AskUserInformationActivity extends ToolbarActivity {

    private List<Fragment> userDetailsFragmentsList = new ArrayList<>();

    private AskGsAndDsFragment askGsAndDsFragment;
    private AskAddressFragment askAddressFragment;
    private AskPersonalDetailsFragment askPersonalDetailsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_user_information);

        initToolbarWithBackButton();
        initInstances();
        showFirstFragment();
    }


    @Override
    public void initInstances() {
        askGsAndDsFragment = new AskGsAndDsFragment();
        askAddressFragment = new AskAddressFragment();
        askPersonalDetailsFragment = new AskPersonalDetailsFragment();

        userDetailsFragmentsList.add(askGsAndDsFragment);
        userDetailsFragmentsList.add(askAddressFragment);
        userDetailsFragmentsList.add(askPersonalDetailsFragment);
    }


    private void showFirstFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.frag_placeholder, userDetailsFragmentsList.get(0));
//        transaction.addToBackStack(null);
        transaction.commit();
    }

}
