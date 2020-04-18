package lk.gov.arogya.activity;

import android.os.Bundle;
import android.widget.TextView;

import lk.gov.arogya.R;
import lk.gov.arogya.activity.parent.ToolbarActivity;
import lk.gov.arogya.utils.SharedPreferencesHelper;

public class ShowMyInfoActivity extends ToolbarActivity {

    private TextView nameTxtView;
    private TextView nicOrPassportTxtView;
    private TextView mobileNoTxtView;
    private TextView address1TxtView;
    private TextView address2TxtView;
    private TextView address3TxtView;
    private TextView dobTxtView;
    private TextView genderTxtView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_my_info);
        initToolbarWithBackButton();
        initInstances();
        setValuesToTextViews();
    }

    @Override
    public void initInstances() {
        nameTxtView = findViewById(R.id.txt_name);
        nicOrPassportTxtView = findViewById(R.id.txt_nic_or_passport);
        mobileNoTxtView = findViewById(R.id.txt_mobile_no);
        address1TxtView = findViewById(R.id.txt_address1);
        address2TxtView = findViewById(R.id.txt_address2);
        address3TxtView = findViewById(R.id.txt_address3);
        dobTxtView = findViewById(R.id.txt_dob);
        genderTxtView = findViewById(R.id.txt_gender);

    }

    private void setValuesToTextViews() {
        String fullName = SharedPreferencesHelper.getUserFullName(this);
        String nicOrPassport = SharedPreferencesHelper.getUserNicOrPassportNo(this);
        String mobileNo = SharedPreferencesHelper.getUserMobileNo(this);
//        USER_DS_DIVISION = SharedPreferencesHelper.getUserDsDivision(this);
//        USER_GS_DIVISION = SharedPreferencesHelper.getUserGsDivision(this);
        String address1 = SharedPreferencesHelper.getUserAddress1(this);
        String address2 = SharedPreferencesHelper.getUserAddress2(this);
        String address3 = SharedPreferencesHelper.getUserAddress3(this);
//        USER_ADDRESS4 = SharedPreferencesHelper.getUserAddress4(this);
        String dob = SharedPreferencesHelper.getUserDob(this);
        String gender = SharedPreferencesHelper.getUserGender(this);


        nameTxtView.setText(fullName);
        nicOrPassportTxtView.setText(nicOrPassport);
        mobileNoTxtView.setText(mobileNo);
        address1TxtView.setText(address1);
        address2TxtView.setText(address2);
        address3TxtView.setText(address3);
        dobTxtView.setText(dob);
        genderTxtView.setText(gender);
    }
}
