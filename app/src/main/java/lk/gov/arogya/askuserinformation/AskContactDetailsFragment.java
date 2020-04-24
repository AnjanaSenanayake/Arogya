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

public class AskContactDetailsFragment extends BaseFragment {

    private TextInputEditText primaryContact;
    private TextInputEditText secondaryContact1, secondaryContact2;
    private TextInputEditText emergencyContact, emergencyContactRelation;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initInstances(view);
    }

    private void initInstances(View view) {
        primaryContact = view.findViewById(R.id.edt_primary_contact);
        secondaryContact1 = view.findViewById(R.id.edt_secondary_contact1);
        secondaryContact2 = view.findViewById(R.id.edt_secondary_contact2);
        emergencyContact = view.findViewById(R.id.edt_emergency_contact);
        emergencyContactRelation = view.findViewById(R.id.edt_emergency_contact_relation);
        initPrevBtn(view, new AskPersonalDetailsFragment());
        initNextBtn(view, new AskAddressFragment());

        primaryContact.setText(PreferenceUtil.getSharedPreferenceString(R.string.id_primary_contact));
        secondaryContact1.setText(PreferenceUtil.getSharedPreferenceString(R.string.id_secondary_contact_1));
        secondaryContact2.setText(PreferenceUtil.getSharedPreferenceString(R.string.id_secondary_contact_2));
        emergencyContact.setText(PreferenceUtil.getSharedPreferenceString(R.string.id_emergency_contact));
        emergencyContactRelation.setText(PreferenceUtil.getSharedPreferenceString(R.string.id_emergency_contact_relation));
    }

    @Override
    public void saveValues() {
        newUser.setPrimaryContact(primaryContact.getText().toString());
        newUser.setSecondaryContact1(secondaryContact1.getText().toString());
        newUser.setSecondaryContact2(secondaryContact2.getText().toString());
        newUser.setEmergencyContact(emergencyContact.getText().toString());
        newUser.setEmergencyContactRelation(emergencyContactRelation.getText().toString());

        PreferenceUtil.setSharedPreferenceString(R.string.id_primary_contact, newUser.getPrimaryContact());
        PreferenceUtil.setSharedPreferenceString(R.string.id_secondary_contact_1, newUser.getSecondaryContact1());
        PreferenceUtil.setSharedPreferenceString(R.string.id_secondary_contact_2, newUser.getSecondaryContact2());
        PreferenceUtil.setSharedPreferenceString(R.string.id_emergency_contact, newUser.getEmergencyContact());
        PreferenceUtil.setSharedPreferenceString(R.string.id_emergency_contact_relation, newUser.getEmergencyContactRelation());
    }
}

