package lk.gov.arogya.askuserinformation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import lk.gov.arogya.R;
import lk.gov.arogya.models.Constants;
import lk.gov.arogya.support.PreferenceUtil;

import static lk.gov.arogya.askuserinformation.AskUserInformationActivity.newUser;

public class AskPersonalDetailsFragment extends BaseFragment {

    private TextInputEditText edtName, edtDOB;
    private RadioButton radioMale, radioFemale, radioNonBinary, radioMarried, radioNotMarried;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_details, container, false);

        edtName = view.findViewById(R.id.edt_full_name);
        edtDOB = view.findViewById(R.id.edt_dob);
        radioMale = view.findViewById(R.id.radio_male);
        radioFemale = view.findViewById(R.id.radio_female);
        radioNonBinary = view.findViewById(R.id.radio_non_binary);
        radioMarried = view.findViewById(R.id.radio_married);
        radioNotMarried = view.findViewById(R.id.radio_not_married);

        initNextBtn(view, new AskContactDetailsFragment());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        edtName.setText(PreferenceUtil.getSharedPreferenceString(R.string.id_full_name));
        edtDOB.setText(PreferenceUtil.getSharedPreferenceString(R.string.id_dob));

        String gender = PreferenceUtil.getSharedPreferenceString(R.string.id_gender);
        String maritalStatus = PreferenceUtil.getSharedPreferenceString(R.string.id_marital_status);

        if (gender.equals(Constants.Gender.MALE.name()))
            radioMale.setChecked(true);
        else if (gender.equals(Constants.Gender.FEMALE.name()))
            radioFemale.setChecked(true);
        else
            radioNonBinary.setChecked(true);

        if (maritalStatus.equals(Constants.MaritalStatus.MARRIED.name()))
            radioMarried.setChecked(true);
        else
            radioNotMarried.setChecked(true);


        edtDOB.setEnabled(false);
        edtDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMaterialDatePicker();
            }
        });
        edtDOB.setEnabled(true);
    }

    private void showMaterialDatePicker() {
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        final MaterialDatePicker<Long> picker = builder.build();
        picker.show(getActivity().getSupportFragmentManager(), picker.toString());

        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                edtDOB.setText(picker.getHeaderText());
            }
        });
    }

    @Override
    public void saveValues() {
        newUser.setFullName(edtName.getText().toString());
        newUser.setDob(edtDOB.getText().toString());

        Constants.Gender gender;
        Constants.MaritalStatus maritalStatus;

        if (radioMale.isChecked())
            gender = Constants.Gender.MALE;
        else if (radioFemale.isChecked())
            gender = Constants.Gender.FEMALE;
        else
            gender = Constants.Gender.NON_BINARY;

        newUser.setGender(gender.name());

        if (radioMarried.isChecked())
            maritalStatus = Constants.MaritalStatus.MARRIED;
        else
            maritalStatus = Constants.MaritalStatus.NOT_MARRIED;

        newUser.setMaritalStatus(maritalStatus.name());


        PreferenceUtil.setSharedPreferenceString(R.string.id_full_name, newUser.getFullName());
        PreferenceUtil.setSharedPreferenceString(R.string.id_dob, newUser.getDob());
        PreferenceUtil.setSharedPreferenceString(R.string.id_gender, newUser.getGender());
        PreferenceUtil.setSharedPreferenceString(R.string.id_marital_status, newUser.getMaritalStatus());
    }

}
