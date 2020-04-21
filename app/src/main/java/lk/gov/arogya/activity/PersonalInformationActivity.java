package lk.gov.arogya.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import lk.gov.arogya.R;
import lk.gov.arogya.activity.parent.ToolbarActivity;
import lk.gov.arogya.models.Constants;
import lk.gov.arogya.models.User;
import lk.gov.arogya.support.ContentHolder;
import lk.gov.arogya.support.PreferenceUtil;
import lk.gov.arogya.support.RestAPI;

public class PersonalInformationActivity extends ToolbarActivity {

    private User currentUser;

    private TextView tvName;
    private TextView tvNICorPassport;
    private TextView tvPrimaryContact, tvSecondaryContact1, tvSecondaryContact2, tvEmergencyContact, tvEmergencyContactRelation;
    private TextView tvAddressLine1, tvAddressLine2, tvAddressLine3, tvAddressLine4;
    private TextView tvDSDivision, tvGNDivision;
    private TextView tvDOB;
    private TextView tvGender;
    private TextView tvMaritalStatus;
    private MaterialButton btnEdit;
    private RelativeLayout layoutVerification;
    private LinearLayout layoutRoot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        initToolbarWithBackButton();
        initInstances();

        currentUser = (User) getIntent().getSerializableExtra("USER");
        loadUserData();
    }

    @Override
    public void initInstances() {
        layoutVerification = findViewById(R.id.layout_verification);
        layoutRoot = findViewById(R.id.layout_personal_information);
        tvName = findViewById(R.id.tv_name);
        tvNICorPassport = findViewById(R.id.tv_nic_or_passport);
        tvPrimaryContact = findViewById(R.id.tv_primary_contact);
        tvSecondaryContact1 = findViewById(R.id.tv_secondary_contact1);
        tvSecondaryContact2 = findViewById(R.id.tv_secondary_contact2);
        tvEmergencyContact = findViewById(R.id.tv_emergency_contact);
        tvEmergencyContactRelation = findViewById(R.id.tv_emergency_contact_relation);
        tvAddressLine1 = findViewById(R.id.tv_address1);
        tvAddressLine2 = findViewById(R.id.tv_address2);
        tvAddressLine3 = findViewById(R.id.tv_address3);
        tvAddressLine4 = findViewById(R.id.tv_address4);
        tvDSDivision = findViewById(R.id.tv_ds);
        tvGNDivision = findViewById(R.id.tv_gn);
        tvDOB = findViewById(R.id.tv_dob);
        tvGender = findViewById(R.id.tv_gender);
        tvMaritalStatus = findViewById(R.id.tv_marital_status);
        btnEdit = findViewById(R.id.btn_edit);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFormValues();
                Intent intent = new Intent(PersonalInformationActivity.this, AskUserInformationActivity.class);
                intent.putExtra("USER", currentUser);
                startActivity(intent);
            }
        });
    }

    private void populateDataToForm() {
        if (currentUser.isVerified())
            layoutVerification.setVisibility(View.GONE);
        else
            layoutVerification.setVisibility(View.VISIBLE);
        tvName.setText(currentUser.getFullName());
        tvNICorPassport.setText(currentUser.getNicpp());
        tvPrimaryContact.setText(currentUser.getPrimaryContact());
        if (currentUser.getSecondaryContact1() != null)
            tvSecondaryContact1.setText(currentUser.getSecondaryContact1());
        if (currentUser.getSecondaryContact2() != null)
            tvSecondaryContact2.setText(currentUser.getSecondaryContact2());
        if (currentUser.getEmergencyContact() != null)
            tvEmergencyContact.setText(currentUser.getEmergencyContact());
        if (currentUser.getEmergencyContactRelation() != null)
            tvEmergencyContactRelation.setText(currentUser.getEmergencyContactRelation());
        if (currentUser.getAddressLine1() != null)
            tvAddressLine1.setText(currentUser.getAddressLine1());
        if (currentUser.getAddressLine2() != null)
            tvAddressLine2.setText(currentUser.getAddressLine2());
        if (currentUser.getAddressLine3() != null)
            tvAddressLine3.setText(currentUser.getAddressLine3());
        if (currentUser.getAddressLine4() != null)
            tvAddressLine4.setText(currentUser.getAddressLine4());
        if (currentUser.getDSDivision() != null)
            tvDSDivision.setText(currentUser.getDSDivision());
        if (currentUser.getGNDivision() != null)
            tvGNDivision.setText(currentUser.getGNDivision());
        if (currentUser.getDob() != null)
            tvDOB.setText(currentUser.getDob());
        if (currentUser.getGender() != null) {
            Log.e(PersonalInformationActivity.class.getSimpleName(), currentUser.getGender());
            tvGender.setText(Constants.Gender.valueOf(currentUser.getGender()).getGender());
        }
        if (currentUser.getMaritalStatus() != null) {
            tvMaritalStatus.setText(Constants.MaritalStatus.valueOf(currentUser.getMaritalStatus()).getStatus());
            Log.e(PersonalInformationActivity.class.getSimpleName(), currentUser.getGender());
        }
    }

    private void loadUserData() {
        if (currentUser == null) {
            currentUser = new User();
            currentUser.setUid(ContentHolder.getUID());
        }
        RestAPI.getUserByUID(currentUser.getUid(), new RestAPI.OnSuccessListener<User, Throwable>() {
            @Override
            public void onSuccess(User response) {
                currentUser = response;
                populateDataToForm();
            }

            @Override
            public void onFailure(Throwable err) {

            }
        });
    }

    private void setFormValues() {
        PreferenceUtil.setSharedPreferenceString(R.string.id_full_name, currentUser.getFullName());
        PreferenceUtil.setSharedPreferenceString(R.string.id_dob, currentUser.getDob());
        PreferenceUtil.setSharedPreferenceString(R.string.id_gender, currentUser.getGender());
        PreferenceUtil.setSharedPreferenceString(R.string.id_marital_status, currentUser.getMaritalStatus());
        PreferenceUtil.setSharedPreferenceString(R.string.id_primary_contact, currentUser.getPrimaryContact());
        PreferenceUtil.setSharedPreferenceString(R.string.id_secondary_contact_1, currentUser.getSecondaryContact1());
        PreferenceUtil.setSharedPreferenceString(R.string.id_secondary_contact_2, currentUser.getSecondaryContact2());
        PreferenceUtil.setSharedPreferenceString(R.string.id_emergency_contact, currentUser.getEmergencyContact());
        PreferenceUtil.setSharedPreferenceString(R.string.id_emergency_contact_relation, currentUser.getEmergencyContactRelation());
        PreferenceUtil.setSharedPreferenceString(R.string.id_address_line_1, currentUser.getAddressLine1());
        PreferenceUtil.setSharedPreferenceString(R.string.id_address_line_2, currentUser.getAddressLine2());
        PreferenceUtil.setSharedPreferenceString(R.string.id_address_line_3, currentUser.getAddressLine3());
        PreferenceUtil.setSharedPreferenceString(R.string.id_address_line_4, currentUser.getAddressLine4());
        PreferenceUtil.setSharedPreferenceString(R.string.id_ds, currentUser.getDSDivision());
        PreferenceUtil.setSharedPreferenceString(R.string.id_gn, currentUser.getGNDivision());
    }
}
