package lk.gov.arogya.askuserinformation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;

import lk.gov.arogya.R;
import lk.gov.arogya.support.PreferenceUtil;

import static lk.gov.arogya.askuserinformation.AskUserInformationActivity.newUser;

public class AskAddressFragment extends BaseFragment {
    private TextInputEditText addressLine1, addressLine2, addressLine3, addressLine4;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ask_address, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initInstances(view);
    }

    private void initInstances(View view) {
        addressLine1 = view.findViewById(R.id.address_1);
        addressLine2 = view.findViewById(R.id.address_2);
        addressLine3 = view.findViewById(R.id.address_3);
        addressLine4 = view.findViewById(R.id.address_4);
        initNextBtn(view, new AskAdministrationDetailsFragment());
        initPrevBtn(view, new AskContactDetailsFragment());

        addressLine1.setText(PreferenceUtil.getSharedPreferenceString(R.string.id_address_line_1));
        addressLine2.setText(PreferenceUtil.getSharedPreferenceString(R.string.id_address_line_2));
        addressLine3.setText(PreferenceUtil.getSharedPreferenceString(R.string.id_address_line_3));
        addressLine4.setText(PreferenceUtil.getSharedPreferenceString(R.string.id_address_line_4));
    }

    @Override
    public void saveValues() {
        newUser.setAddressLine1(addressLine1.getText().toString());
        newUser.setAddressLine2(addressLine2.getText().toString());
        newUser.setAddressLine3(addressLine3.getText().toString());
        newUser.setAddressLine4(addressLine4.getText().toString());

        PreferenceUtil.setSharedPreferenceString(R.string.id_address_line_1, newUser.getAddressLine1());
        PreferenceUtil.setSharedPreferenceString(R.string.id_address_line_2, newUser.getAddressLine2());
        PreferenceUtil.setSharedPreferenceString(R.string.id_address_line_3, newUser.getAddressLine3());
        PreferenceUtil.setSharedPreferenceString(R.string.id_address_line_4, newUser.getAddressLine4());
    }
}

