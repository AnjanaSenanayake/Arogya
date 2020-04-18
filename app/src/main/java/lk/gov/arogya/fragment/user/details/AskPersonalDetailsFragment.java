package lk.gov.arogya.fragment.user.details;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import lk.gov.arogya.R;
import lk.gov.arogya.activity.MainActivity;
import lk.gov.arogya.utils.SharedPreferencesHelper;

public class AskPersonalDetailsFragment extends BaseFragment {

    private RadioGroup radioGroup;
    private Button submitBtn;
    private TextInputEditText dobEditTxt;
    private Button dobTxtView;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initInstances(view);
    }

    private void initInstances(View view) {
        dobEditTxt = view.findViewById(R.id.dob);
        dobTxtView = view.findViewById(R.id.dob_txtView);
        radioGroup = view.findViewById(R.id.gender_radio_group);
        submitBtn = view.findViewById(R.id.btn_submit);

        initSavedValuesToTxtView();
        initPrevBtn(view, new AskAddressFragment());
        initSubmitBtn();
        initDob();
    }

    private void initDob() {
        dobEditTxt.setEnabled(false);
        dobTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMaterialDatePicker();
            }
        });
    }

    private void showMaterialDatePicker() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        final MaterialDatePicker<Long> picker = builder.build();
        picker.show(getActivity().getSupportFragmentManager(), picker.toString());

        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                SharedPreferencesHelper.saveUserDob(getActivity(), picker.getHeaderText());
                dobEditTxt.setText(picker.getHeaderText());

            }
        });
    }

    private void initSubmitBtn() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveValues();
                openTempActivityForResults();
            }
        });
    }

    private void openTempActivityForResults() {
        if (getActivity() != null) {
            Intent mainIntent = new Intent(getActivity(), MainActivity.class);
            getActivity().startActivity(mainIntent);
        }
    }

    private void initSavedValuesToTxtView() {
        dobEditTxt.setText(SharedPreferencesHelper.getUserDob(getActivity()));
    }


    @Override
    public void saveValues() {
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = radioGroup.findViewById(radioButtonID);
        String selectedText = radioButton.getText().toString();
        SharedPreferencesHelper.saveUserGender(getActivity(), selectedText);
    }

}
