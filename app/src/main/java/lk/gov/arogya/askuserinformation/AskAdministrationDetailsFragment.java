package lk.gov.arogya.askuserinformation;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import lk.gov.arogya.R;
import lk.gov.arogya.personalinformation.PersonalInformationActivity;
import lk.gov.arogya.models.User;
import lk.gov.arogya.support.ContentHolder;
import lk.gov.arogya.support.PreferenceUtil;
import lk.gov.arogya.api.RestAPI;

import static lk.gov.arogya.askuserinformation.AskUserInformationActivity.isNewUser;
import static lk.gov.arogya.askuserinformation.AskUserInformationActivity.newUser;

public class AskAdministrationDetailsFragment extends BaseFragment {

    private TextInputEditText edtDSDivision, edtGNDivision;
    private Button btnSubmit;
    private LinearLayout linearLayout;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ask_administration_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initInstances(view);
    }

    private void initInstances(View view) {
        linearLayout = view.findViewById(R.id.linear_layout_address_fragment);
        edtDSDivision = view.findViewById(R.id.edt_ds);
        edtGNDivision = view.findViewById(R.id.edt_gn);
        btnSubmit = view.findViewById(R.id.btn_submit);
        initPrevBtn(view, new AskContactDetailsFragment());

        edtDSDivision.setText(PreferenceUtil.getSharedPreferenceString(R.string.id_ds));
        edtGNDivision.setText(PreferenceUtil.getSharedPreferenceString(R.string.id_gn));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveValues();
                if (isNewUser) {
                    RestAPI.createUser(newUser, new RestAPI.OnSuccessListener<User, Throwable>() {
                        @Override
                        public void onSuccess(User response) {
                            Intent intent = new Intent(getActivity(), PersonalInformationActivity.class);
                            intent.putExtra("USER", response);
                            getActivity().startActivity(intent);
                        }

                        @Override
                        public void onFailure(Throwable err) {
                            showSnackBarMessage("Submit Failed");
                        }
                    });
                } else {
                    RestAPI.updateUser(newUser, new RestAPI.OnSuccessListener<Boolean, Throwable>() {
                        @Override
                        public void onSuccess(Boolean response) {
                            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.msg_submit_success), Toast.LENGTH_LONG).show();
                            getActivity().finish();
                            clearFormValues();
                            ContentHolder.setUser(null);
                            startActivity(new Intent(getActivity(), PersonalInformationActivity.class));
                        }

                        @Override
                        public void onFailure(Throwable err) {
                            showSnackBarMessage("Submit Failed");
                        }
                    });
                }
            }
        });
    }

    @Override
    public void saveValues() {
        newUser.setDSDivision(edtDSDivision.getText().toString());
        newUser.setGNDivision(edtGNDivision.getText().toString());

        PreferenceUtil.setSharedPreferenceString(R.string.id_ds, newUser.getDSDivision());
        PreferenceUtil.setSharedPreferenceString(R.string.id_gn, newUser.getGNDivision());
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

    private void showSnackBarMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(linearLayout, message, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }
}
