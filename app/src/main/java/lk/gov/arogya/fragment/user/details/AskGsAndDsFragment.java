package lk.gov.arogya.fragment.user.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;

import lk.gov.arogya.R;
import lk.gov.arogya.utils.SharedPreferencesHelper;

public class AskGsAndDsFragment extends BaseFragment {

    private TextInputEditText gs;
    private TextInputEditText ds;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ask_gs_ds, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initInstances(view);
        initSavedValuesToTxtView();
    }

    private void initInstances(View view) {
        gs = view.findViewById(R.id.gs_division);
        ds = view.findViewById(R.id.div_sec);
        initNextBtn(view, new AskAddressFragment());
    }

    private void initSavedValuesToTxtView() {
        gs.setText(SharedPreferencesHelper.getUserGsDivision(getActivity()));
        ds.setText(SharedPreferencesHelper.getUserDsDivision(getActivity()));

    }

    @Override
    public void saveValues() {
        SharedPreferencesHelper.saveUserGsDivision(getActivity(), gs.getText().toString());
        SharedPreferencesHelper.saveUserDsDivision(getActivity(), ds.getText().toString());
    }

}

