package lk.gov.arogya.nearbypeopletracker;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import lk.gov.arogya.R;
import lk.gov.arogya.api.RestAPI;
import lk.gov.arogya.api.RestAPI.OnSuccessListener;
import lk.gov.arogya.models.EpidemicAlert;
import lk.gov.arogya.support.ContentHolder;
import lk.gov.arogya.support.FileUtils;
import lk.gov.arogya.support.ParserUtils;
import lk.gov.arogya.support.PermissionResultCallback;
import lk.gov.arogya.support.PermissionUtils;
import lk.gov.arogya.support.PreferenceUtil;

public class DiseaseContactTraceActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback,
        PermissionResultCallback {

    private String[] healthStatuses;
    private SwitchCompat switchContactTracer;
    private NumberPicker picker;
    private Button btnAlert;
    private TextView tvNoAlerts;
    private LinearLayout layoutRoot;
    private RecyclerView alertsRecycler;

    private PermissionUtils permissionUtils;
    private ArrayList<String> permissions = new ArrayList<>();
    private ArrayList<EpidemicAlert> epidemicAlerts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_trace);
        layoutRoot = findViewById(R.id.linear_contact_tracer);
        switchContactTracer = findViewById(R.id.switch_contact_tracer);
        picker = findViewById(R.id.picker_health_status);
        tvNoAlerts = findViewById(R.id.tv_no_alerts);
        alertsRecycler = findViewById(R.id.recycler_alerts);
        btnAlert = findViewById(R.id.btn_alert);
        picker.setMinValue(0);

        // Setup the permissions
        permissionUtils = new PermissionUtils(DiseaseContactTraceActivity.this);
        permissions.add(Manifest.permission.BLUETOOTH);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);

        setComponentEnable(false);
        loadExistingAlerts();
        loadData();
    }

    private void loadData() {
        RestAPI.getAllEpidemics(new OnSuccessListener<HashMap<Integer, String>, Throwable>() {
            @Override
            public void onSuccess(final HashMap<Integer, String> response) {
                healthStatuses = new String[response.size()];
                healthStatuses[0] = "None";
                for (int i = 1; i < response.size(); i++) {
                    healthStatuses[i] = response.get(i);
                }
                if (healthStatuses.length != 0) {
                    switchContactTracer.setChecked(PreferenceUtil.getSharedPreferenceBool(R.string.id_contact_trace));

                    String currentHealthStatus = PreferenceUtil
                            .getSharedPreferenceString(R.string.id_current_health_status);

                    if (currentHealthStatus.equals(healthStatuses[picker.getValue()])) {
                        btnAlert.setEnabled(false);
                    } else {
                        btnAlert.setEnabled(true);
                    }

                    setComponentEnable(true);
                    picker.setMaxValue(healthStatuses.length - 1);
                    picker.setDisplayedValues(healthStatuses);
                    setSwitchListener();
                    setPickerListener();
                    setBtnAlertListener();
                    permissionUtils.check_permission(permissions,
                            "This functionality needs few permissions", 1);
                } else {
                    setComponentEnable(false);
                }
            }

            @Override
            public void onFailure(final Throwable err) {
                setComponentEnable(false);
            }
        });
    }

    private void loadExistingAlerts() {
        String filePath = getApplicationContext().getFilesDir().toString() + "/contacts/";
        String alertsString = FileUtils.readFromStorage(filePath, "alerts.txt");
        String contactsString = FileUtils.readFromStorage(filePath, "contacts.txt");

        if (alertsString != null && contactsString != null && !alertsString.isEmpty() && !contactsString.isEmpty()) {
            epidemicAlerts = ParserUtils.parseToEpidemicAlertList(alertsString, contactsString);
            if (!epidemicAlerts.isEmpty()) {
                tvNoAlerts.setVisibility(View.GONE);
                AlertsListAdapter adapter = new AlertsListAdapter(this, epidemicAlerts);
                alertsRecycler.setLayoutManager(new LinearLayoutManager(this));
                alertsRecycler.setAdapter(adapter);
            } else {
                tvNoAlerts.setVisibility(View.VISIBLE);
            }
        }
    }

    private void createEpidemicAlert(String UID, int infectionID) {
        String currentDateTime = new SimpleDateFormat("yyyy-MM-d hh:mm:ss").format(Calendar.getInstance().getTime());
        RestAPI.createEpidemicAlert(UID, infectionID, currentDateTime, new OnSuccessListener<Boolean, Throwable>() {
            @Override
            public void onSuccess(final Boolean response) {
                Toast.makeText(DiseaseContactTraceActivity.this, "Alert Success", Toast.LENGTH_LONG).show();
                NotificationService
                        .sendNotification(DiseaseContactTraceActivity.this, FileUtils.getHash(ContentHolder.getUID()),
                                healthStatuses[picker.getValue()]);
                PreferenceUtil
                        .setSharedPreferenceString(R.string.id_current_health_status, healthStatuses[infectionID]);
            }

            @Override
            public void onFailure(final Throwable err) {
                showSnackBarMessage("Alert Failed");
            }
        });
    }

    private void setComponentEnable(boolean value) {
        switchContactTracer.setEnabled(value);
        picker.setEnabled(value);
        btnAlert.setEnabled(value);
    }

    private void setSwitchListener() {
        switchContactTracer.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton compoundButton, final boolean b) {
                if (b) {
                    startService(new Intent(DiseaseContactTraceActivity.this, NearbyTrackingService.class));
                    startService(new Intent(DiseaseContactTraceActivity.this, MessagingService.class));
                } else {
                    stopService(new Intent(DiseaseContactTraceActivity.this, NearbyTrackingService.class));
                    stopService(new Intent(DiseaseContactTraceActivity.this, MessagingService.class));
                }
                PreferenceUtil.setSharedPreferenceBool(R.string.id_contact_trace, b);
            }
        });
    }

    private void setPickerListener() {
        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                String oldStatus = healthStatuses[oldVal];
                String newStatus = healthStatuses[newVal];
                String currentStatus = PreferenceUtil.getSharedPreferenceString(R.string.id_current_health_status);
                if (currentStatus.equals(newStatus)) {
                    btnAlert.setEnabled(false);
                } else {
                    btnAlert.setEnabled(true);
                }
            }
        });
    }

    private void setBtnAlertListener() {
        btnAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEpidemicAlert(ContentHolder.getUID(), picker.getValue());
            }
        });
    }

    private void showSnackBarMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(layoutRoot, message, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        createEpidemicAlert(ContentHolder.getUID(), picker.getValue());
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        // redirects to utils
        permissionUtils.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void NeverAskAgain(int request_code) {
        Log.i("PERMISSION", "NEVER ASK AGAIN");
        permissionUtils.check_permission(permissions,
                "This functionality needs few permissions", 1);
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {
        Log.i("PERMISSION PARTIALLY", "GRANTED");
        permissionUtils.check_permission(permissions,
                "This functionality needs few permissions", 1);
    }

    @Override
    public void PermissionDenied(int request_code) {
        Log.i("PERMISSION", "DENIED");
        permissionUtils.check_permission(permissions,
                "This functionality needs few permissions", 1);
    }

    // Callback functions
    @Override
    public void PermissionGranted(int request_code) {
        Log.i("PERMISSION", "GRANTED");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, NearbyTrackingService.class));
        stopService(new Intent(this, MessagingService.class));
    }
}
