package lk.gov.arogya.askuserinformation;


import static lk.gov.arogya.askuserinformation.AskUserInformationActivity.isNewUser;
import static lk.gov.arogya.askuserinformation.AskUserInformationActivity.newUser;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import lk.gov.arogya.R;
import lk.gov.arogya.api.RestAPI;
import lk.gov.arogya.api.RestAPI.OnSuccessListener;
import lk.gov.arogya.models.User;
import lk.gov.arogya.personalinformation.PersonalInformationActivity;
import lk.gov.arogya.support.ContentHolder;
import lk.gov.arogya.support.PreferenceUtil;

public class AskAdministrationDetailsFragment extends BaseFragment {

    private Spinner spinnerDistrict, spinnerDSDivision, spinnerGNDivision;
    private LinearLayout linearLayout;
    private ProgressBar mProgressBar;
    private ArrayList<String> districtList = new ArrayList<>();
    private boolean isLoading = false;

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
        spinnerDistrict = view.findViewById(R.id.spinner_district);
        spinnerDSDivision = view.findViewById(R.id.spinner_ds);
        spinnerGNDivision = view.findViewById(R.id.spinner_gn);
        mProgressBar = view.findViewById(R.id.loading_spinner);

        final Button btnSubmit = view.findViewById(R.id.btn_submit);

        initPrevBtn(view, new AskContactDetailsFragment());

        populateDistrictsSpinner();

        spinnerDistrict.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> adapterView, final View view, final int i, final long l) {
                String district = adapterView.getItemAtPosition(i).toString();
                if (!district.isEmpty()) {
                    populateDSSpinner(district);
                }
            }

            @Override
            public void onNothingSelected(final AdapterView<?> adapterView) {

            }
        });

        spinnerDSDivision.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> adapterView, final View view, final int i, final long l) {
                String dsName = adapterView.getItemAtPosition(i).toString();
                if (!dsName.isEmpty()) {
                    populateGNSpinner(adapterView.getItemAtPosition(i).toString());
                    PreferenceUtil
                            .setSharedPreferenceString(R.string.id_ds, adapterView.getItemAtPosition(i).toString());
                }
            }

            @Override
            public void onNothingSelected(final AdapterView<?> adapterView) {

            }
        });

        spinnerGNDivision.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView<?> adapterView, final View view, final int i, final long l) {
                PreferenceUtil.setSharedPreferenceString(R.string.id_gn, adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(final AdapterView<?> adapterView) {

            }
        });

        populateDSSpinner(PreferenceUtil.getSharedPreferenceString(R.string.id_district));
        populateGNSpinner(PreferenceUtil.getSharedPreferenceString(R.string.id_ds));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveValues();
                if (validateForm()) {
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
                                Toast.makeText(getActivity(),
                                        getActivity().getResources().getString(R.string.msg_submit_success),
                                        Toast.LENGTH_LONG).show();
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
            }
        });
    }

    private void populateDistrictsSpinner() {
        districtList.add("");
        districtList.add("කොළඹ/கொழும்பு/Colombo");
        districtList.add("ගම්පහ/கம்\\u00D\\u00D\\u00Eப\\u00D\\u00Dஹ/Gampaha");
        districtList.add("කළුතර/களுத்து\\u00Dறை/Kalutara");
        districtList.add("මහනුවර/கண்டி/Kandy");
        districtList.add("මාතලේ/மாத்த\\u00D\\u00D\\u00Dளை/Matale");
        districtList.add("නුවරඑළිය/நுவ\\u00Eரெளியா/NuwaraEliya");
        districtList.add("ගාල්ල/காலி/Galle");
        districtList.add("මාතර/மாத்தறை/Matara");
        districtList.add("හම්බන්තොට/ஹம்பாந்தோட்\\u00Dடை/Hambanthota");
        districtList.add("යාපනය/யாழ்பாணம்/Jaffna");
        districtList.add("මන්නාරම/மன்னார்/Mannar");
        districtList.add("වව්නියාව/வவுனியா/Vavuniya");
        districtList.add("මුලතිව්/முள்ளைத்தீவு/Mullaitiv");
        districtList.add("කිලිනොච්චි/கிளி\\u00E\\u00Eநொச்சி/Kilinochchi");
        districtList.add("මඩකලපුව/மட்டக்களப்பு/Batticalo");
        districtList.add("අම්පාර/அம்பா\\u00D\\u00Dறை/Ampara");
        districtList.add("ත්\\u00Dරිකුණාමලය/திரு\\u00Dகோண\\u00Dமலை/Trincomalee");
        districtList.add("කුරුණෑගල/குருணாக\\u00Dலை/Kurunegela");
        districtList.add("පුත්තලම/புத்தளம்/Puttalama");
        districtList.add("අනුරාධපුරය/அநுராதபுரம்/Anuradhapura");
        districtList.add("පොළොන්නරුව/பொலண்ணரு\\u00Dவை/Polonnaruwa");
        districtList.add("බදුල්ල/பது\\u00Dளை/Badulla");
        districtList.add("මොනරාගල/மொணராக\\u00Dலை/Moneragala");
        districtList.add("රත්නපුර/இரத்திணபுரி/Ratnapura");
        districtList.add("කෑගල්ල/\\u00D\\u00E\\u00D\\u00D\\u00Dகேகா\\u00Dலை/Kegalle");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.item_drop_down_list,
                R.id.tv_item_value,
                districtList);
        spinnerDistrict.setAdapter(adapter);
    }

    private void populateDSSpinner(String district) {
        showProgressBar();
        RestAPI.getDSByDistrict(district, new OnSuccessListener<ArrayList<String>, Throwable>() {
            @Override
            public void onSuccess(final ArrayList<String> response) {
                response.add("");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.item_drop_down_list,
                        R.id.tv_item_value,
                        response);
                spinnerDSDivision.setAdapter(adapter);

                int selectionPositionDS = adapter
                        .getPosition(PreferenceUtil.getSharedPreferenceString(R.string.id_ds));
                spinnerDSDivision.setSelection(selectionPositionDS);
                hideProgressBar();
            }

            @Override
            public void onFailure(final Throwable err) {
                hideProgressBar();
            }
        });
    }

    private void populateGNSpinner(String DSID) {
        showProgressBar();
        RestAPI.getGNByDivision(DSID, new OnSuccessListener<ArrayList<String>, Throwable>() {
            @Override
            public void onSuccess(final ArrayList<String> response) {
                response.add("");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.item_drop_down_list,
                        R.id.tv_item_value,
                        response);
                adapter.setDropDownViewResource(R.layout.item_drop_down_list);
                spinnerGNDivision.setAdapter(adapter);

                int selectionPositionGN = adapter
                        .getPosition(PreferenceUtil.getSharedPreferenceString(R.string.id_gn));
                spinnerGNDivision.setSelection(selectionPositionGN, true);
                hideProgressBar();
            }

            @Override
            public void onFailure(final Throwable err) {
                hideProgressBar();
            }
        });
    }

    @Override
    public void saveValues() {
        newUser.setDSDivision(spinnerDSDivision.getSelectedItem().toString());
        newUser.setGNDivision(spinnerGNDivision.getSelectedItem().toString());

        PreferenceUtil.setSharedPreferenceString(R.string.id_gn, newUser.getGNDivision());
    }

    private boolean validateForm() {
        boolean value = false;
        if (newUser.getFullName().isEmpty() || newUser.getDob().isEmpty() || newUser.getGender().isEmpty() || newUser
                .getMaritalStatus().isEmpty()) {
            showSnackBarMessage("Personal details are incomplete");
        } else if (newUser.getPrimaryContact().isEmpty() || newUser.getEmergencyContact().isEmpty() || newUser
                .getEmergencyContactRelation().isEmpty()) {
            showSnackBarMessage("Contact details are incomplete");
        } else if (newUser.getAddressLine1().isEmpty() || newUser.getAddressLine2().isEmpty() || newUser
                .getAddressLine3().isEmpty()) {
            showSnackBarMessage("Address details are incomplete");
        } else if (newUser.getDSDivision().isEmpty() || newUser.getGNDivision().isEmpty()) {
            showSnackBarMessage("Division details are incomplete");
        } else {
            value = true;
        }
        return value;
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

    private void showProgressBar() {
        isLoading = true;
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        isLoading = false;
        mProgressBar.setVisibility(View.GONE);
    }
}
