package lk.gov.arogya.fragment.user.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;

import lk.gov.arogya.R;
import lk.gov.arogya.utils.SharedPreferencesHelper;

public class AskAddressFragment extends BaseFragment {
    private TextInputEditText addressLine1;
    private TextInputEditText addressLine2;
    private TextInputEditText addressLine3;
    private TextInputEditText addressLine4;


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
        initSavedValuesToTxtView();
        initNextBtn(view, new AskPersonalDetailsFragment());
        initPrevBtn(view, new AskGsAndDsFragment());
    }

    private void initSavedValuesToTxtView() {
        addressLine1.setText(SharedPreferencesHelper.getUserAddress1(getActivity()));
        addressLine2.setText(SharedPreferencesHelper.getUserAddress2(getActivity()));
        addressLine3.setText(SharedPreferencesHelper.getUserAddress3(getActivity()));
        addressLine4.setText(SharedPreferencesHelper.getUserAddress4(getActivity()));
    }

    @Override
    public void saveValues() {
        SharedPreferencesHelper.saveUserAddress1(getActivity(), addressLine1.getText().toString());
        SharedPreferencesHelper.saveUserAddress2(getActivity(), addressLine2.getText().toString());
        SharedPreferencesHelper.saveUserAddress3(getActivity(), addressLine3.getText().toString());
        SharedPreferencesHelper.saveUserAddress4(getActivity(), addressLine4.getText().toString());
    }
}

