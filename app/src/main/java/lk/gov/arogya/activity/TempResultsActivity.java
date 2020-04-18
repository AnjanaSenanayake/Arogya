package lk.gov.arogya.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import lk.gov.arogya.R;

public class TempResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_results);


//        String finalResult = "USER_FULL_NAME: " + SharedPreferencesHelper.getUserFullName(this) + "\n" +
//                "USER_NIC_PASSPORT_NO: " + SharedPreferencesHelper.getUserNicOrPassportNo(this) + "\n" +
//                "USER_MOBILE_NO: " + SharedPreferencesHelper.getUserMobileNo(this) + "\n" +
//                "USER_PASSWORD: " + SharedPreferencesHelper.getUserPassword(this) + "\n" +
//                "USER_DS_DIVISION: " + SharedPreferencesHelper.getUserDsDivision(this) + "\n" +
//                "USER_GS_DIVISION: " + SharedPreferencesHelper.getUserGsDivision(this) + "\n" +
//                "USER_ADDRESS1: " + SharedPreferencesHelper.getUserAddress1(this) + "\n" +
//                "USER_ADDRESS2: " + SharedPreferencesHelper.getUserAddress2(this) + "\n" +
//                "USER_ADDRESS3: " + SharedPreferencesHelper.getUserAddress3(this) + "\n" +
//                "USER_ADDRESS4: " + SharedPreferencesHelper.getUserAddress4(this) + "\n" +
//                "USER_DOB: " + SharedPreferencesHelper.getUserDob(this) + "\n" +
//                "USER_GENDER: " + SharedPreferencesHelper.getUserGender(this) + "\n";


        TextView textView = findViewById(R.id.temp_txt);
        textView.setText("Will be added soon");
    }
}
