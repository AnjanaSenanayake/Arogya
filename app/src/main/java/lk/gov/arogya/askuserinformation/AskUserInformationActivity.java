package lk.gov.arogya.askuserinformation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import lk.gov.arogya.R;
import lk.gov.arogya.parent.ToolbarActivity;
import lk.gov.arogya.models.User;
import lk.gov.arogya.support.PreferenceUtil;

public class AskUserInformationActivity extends ToolbarActivity {

    private List<Fragment> userDetailsFragmentsList = new ArrayList<>();

    private AskPersonalDetailsFragment askPersonalDetailsFragment;
    private AskContactDetailsFragment contactDetailsFragment;
    private AskAddressFragment askAddressFragment;
    private AskAdministrationDetailsFragment askAdministrationDetailsFragment;

    public static User newUser = new User();
    public static boolean isNewUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_user_information);

        isNewUser = getIntent().getBooleanExtra("IS_NEW_USER", false);

        clearFormValues();
        initToolbarWithBackButton();
        initInstances();
        showFirstFragment();
    }

    @Override
    public void initInstances() {
        contactDetailsFragment = new AskContactDetailsFragment();
        askAddressFragment = new AskAddressFragment();
        askPersonalDetailsFragment = new AskPersonalDetailsFragment();
        askAdministrationDetailsFragment = new AskAdministrationDetailsFragment();
        userDetailsFragmentsList.add(askPersonalDetailsFragment);
        userDetailsFragmentsList.add(contactDetailsFragment);
        userDetailsFragmentsList.add(askAddressFragment);
        userDetailsFragmentsList.add(askAdministrationDetailsFragment);
    }

    private void showFirstFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_in_left);
        transaction.replace(R.id.frag_placeholder, userDetailsFragmentsList.get(0));
        transaction.commit();
    }

    private void clearFormValues() {
        PreferenceUtil.setSharedPreferenceString(R.string.id_full_name, "");
        PreferenceUtil.setSharedPreferenceString(R.string.id_dob, "");
        PreferenceUtil.setSharedPreferenceString(R.string.id_gender, "");
        PreferenceUtil.setSharedPreferenceString(R.string.id_marital_status, "");
        PreferenceUtil.setSharedPreferenceString(R.string.id_primary_contact, "");
        PreferenceUtil.setSharedPreferenceString(R.string.id_secondary_contact_1, "");
        PreferenceUtil.setSharedPreferenceString(R.string.id_secondary_contact_2, "");
        PreferenceUtil.setSharedPreferenceString(R.string.id_emergency_contact, "");
        PreferenceUtil.setSharedPreferenceString(R.string.id_emergency_contact_relation, "");
        PreferenceUtil.setSharedPreferenceString(R.string.id_address_line_1, "");
        PreferenceUtil.setSharedPreferenceString(R.string.id_address_line_2, "");
        PreferenceUtil.setSharedPreferenceString(R.string.id_address_line_3, "");
        PreferenceUtil.setSharedPreferenceString(R.string.id_address_line_4, "");
        PreferenceUtil.setSharedPreferenceString(R.string.id_ds, "");
        PreferenceUtil.setSharedPreferenceString(R.string.id_gn, "");
    }

    @Override
    protected void onStop() {
        clearFormValues();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        clearFormValues();
        super.onDestroy();
    }
}
