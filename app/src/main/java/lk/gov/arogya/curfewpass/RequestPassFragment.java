package lk.gov.arogya.curfewpass;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.github.nkzawa.emitter.Emitter.Listener;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import lk.gov.arogya.R;
import lk.gov.arogya.api.RestAPI;
import lk.gov.arogya.api.RestAPI.OnSuccessListener;
import lk.gov.arogya.models.Constants.Approval;
import lk.gov.arogya.models.CurfewPassRequest;
import lk.gov.arogya.models.User;
import lk.gov.arogya.support.ContentHolder;

public class RequestPassFragment extends BottomSheetDialogFragment {

    private Context context;
    private Spinner spinnerRequestedFor;
    private TextInputEditText edtReason, edtWhereTo, edtDateFrom, edtDateTo;
    private Button btnRequest, btnCancelRequest;
    private TextView tvStatus, tvApprovedBy, tvApprovedOn;
    private View verificationView;
    private ProgressBar mProgressBar;
    private RelativeLayout mLayout;
    private Calendar calendar = Calendar.getInstance();
    private boolean isValidFromDate = true;
    private Socket socket;

    private CurfewPassRequest mCurfewPassRequest;

    public RequestPassFragment(Context context) {
        this.context = context;
    }

    void setFormData(CurfewPassRequest curfewPassRequest) {
        mCurfewPassRequest = curfewPassRequest;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_request_pass, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            socket = IO.socket("https://arogyawebapp.herokuapp.com/");
            socket.on("verifyUser", new Listener() {
                @Override
                public void call(final Object... args) {
                    verificationView.setVisibility(View.GONE);
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        initInstances(view);
        populateRequestedForSpinner();
        if (mCurfewPassRequest != null) {
            populateForms(mCurfewPassRequest);
        } else {
            mCurfewPassRequest = new CurfewPassRequest();
        }
    }

    private void initInstances(View view) {
        mLayout = view.findViewById(R.id.layout_request_pass);
        mProgressBar = view.findViewById(R.id.loading_spinner);
        verificationView = view.findViewById(R.id.view_verification);
        spinnerRequestedFor = view.findViewById(R.id.spinner_requested_for);
        edtReason = view.findViewById(R.id.edt_reason);
        edtWhereTo = view.findViewById(R.id.edt_where_to);
        edtDateFrom = view.findViewById(R.id.edt_valid_from);
        edtDateTo = view.findViewById(R.id.edt_valid_until);
        tvStatus = view.findViewById(R.id.tv_verification_status);
        tvApprovedBy = view.findViewById(R.id.tv_approved_by);
        tvApprovedOn = view.findViewById(R.id.tv_approved_on);
        btnRequest = view.findViewById(R.id.btn_request);
        btnCancelRequest = view.findViewById(R.id.btn_cancel);

        final TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(final TimePicker view, final int hourOfDay, final int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                updateDateInputs(calendar.getTime());
            }
        };
        final DatePickerDialog.OnDateSetListener datelistener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.clear();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                new TimePickerDialog(getActivity(), timeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), true).show();
            }

        };
        edtDateFrom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), datelistener, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                isValidFromDate = true;
            }
        });

        edtDateTo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), datelistener, calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                isValidFromDate = false;
            }
        });

        btnRequest.setOnClickListener(view1 -> {
            showProgressBar();
            if (TextUtils.isEmpty(spinnerRequestedFor.getSelectedItem().toString())) {
                showSnackBarMessage("Requested For is required");
            } else if (TextUtils.isEmpty(edtReason.getText().toString())) {
                edtReason.setError("Reason is required");
            } else if (TextUtils.isEmpty(edtWhereTo.getText().toString())) {
                edtWhereTo.setError("Where To is required");
            } else if (TextUtils.isEmpty(edtDateFrom.getText().toString())) {
                edtDateFrom.setError("Valid date from is required");
            } else if (TextUtils.isEmpty(edtDateTo.getText().toString())) {
                edtDateTo.setError("Valid date to is required");
            } else {
                mCurfewPassRequest.setRequestedFor(spinnerRequestedFor.getSelectedItem().toString());
                mCurfewPassRequest.setReasonOfRequest(Objects.requireNonNull(edtReason.getText()).toString());
                mCurfewPassRequest.setWhereTo(Objects.requireNonNull(edtWhereTo.getText()).toString());
                mCurfewPassRequest.setValidDateFrom(Objects.requireNonNull(edtDateFrom.getText()).toString());
                mCurfewPassRequest.setValidDateTo(Objects.requireNonNull(edtDateTo.getText()).toString());

                RestAPI.requestCurfewPass(mCurfewPassRequest, new OnSuccessListener<Boolean, Throwable>() {
                    @Override
                    public void onSuccess(final Boolean response) {
                        Toast.makeText(getActivity(), "Request is Success, Wait for Approval", Toast.LENGTH_LONG)
                                .show();
                        hideProgressBar();
                        dismiss();
                        getActivity().finish();
                        startActivity(new Intent(context, CurfewPassActivity.class));
                    }

                    @Override
                    public void onFailure(final Throwable err) {
                        showSnackBarMessage("Request failed");
                    }
                });
            }
        });

        btnCancelRequest
                .setOnClickListener(view12 -> RestAPI.cancelRequestedCurfewPass(mCurfewPassRequest.getRequestID(),
                        new OnSuccessListener<Boolean, Throwable>() {
                            @Override
                            public void onSuccess(final Boolean response) {
                                Toast.makeText(getActivity(), "Request is Cancelled", Toast.LENGTH_LONG).show();
                                hideProgressBar();
                                getActivity().getFragmentManager().popBackStack();
                                getActivity().finish();
                                startActivity(new Intent(getActivity(), CurfewPassActivity.class));
                            }

                            @Override
                            public void onFailure(final Throwable err) {
                                hideProgressBar();
                                showSnackBarMessage("Canceling request failed");
                            }
                        }));
    }

    private void updateDateInputs(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
        String sourceString = sdf.format(date);
        if (isValidFromDate) {
            edtDateFrom.setText(Html.fromHtml(sourceString));
        } else {
            edtDateTo.setText(Html.fromHtml(sourceString));
        }
    }

    private void populateRequestedForSpinner() {
        showProgressBar();
        RestAPI.getUserByUID(ContentHolder.getUID(), new OnSuccessListener<User, Throwable>() {
            @Override
            public void onSuccess(final User response) {
                ContentHolder.setUser(response);
                RestAPI.getAllChildUsers(ContentHolder.getUID(), new OnSuccessListener<ArrayList<User>, Throwable>() {
                    @Override
                    public void onSuccess(final ArrayList<User> response) {
                        response.add(ContentHolder.getUser());
                        ArrayList<String> userNames = new ArrayList<>();

                        for (User user : response) {
                            userNames.add(user.getFullName());
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.item_drop_down_list,
                                R.id.tv_item_value, userNames);
                        spinnerRequestedFor.setAdapter(adapter);

                        if (mCurfewPassRequest != null) {
                            int selectionPositionDS = adapter
                                    .getPosition(mCurfewPassRequest.getRequestedFor());
                            spinnerRequestedFor.setSelection(selectionPositionDS);
                        }

                        hideProgressBar();
                    }

                    @Override
                    public void onFailure(final Throwable err) {
                        hideProgressBar();
                        setComponentsEnable(false);
                        dismiss();
                    }
                });
            }

            @Override
            public void onFailure(final Throwable err) {
                hideProgressBar();
                setComponentsEnable(false);
                dismiss();
            }
        });

    }

    private void populateForms(CurfewPassRequest curfewPassRequest) {
        if (curfewPassRequest.getStatus().getApproval().equals(Approval.PENDING.getApproval())) {
            verificationView.setBackgroundColor(ContextCompat.getColor(context, R.color.lightOrange));
            btnCancelRequest.setVisibility(View.VISIBLE);
        } else if (curfewPassRequest.getStatus().getApproval().equals(Approval.APPROVED.getApproval())) {
            verificationView.setBackgroundColor(ContextCompat.getColor(context, R.color.lightGreen));
            btnCancelRequest.setVisibility(View.GONE);
        } else {
            verificationView.setBackgroundColor(ContextCompat.getColor(context, R.color.lightRed));
            btnCancelRequest.setVisibility(View.GONE);
        }
        btnRequest.setVisibility(View.GONE);
        tvStatus.setText(curfewPassRequest.getStatus().getApproval());

        populateRequestedForSpinner();
        edtReason.setText(curfewPassRequest.getReasonOfRequest());
        edtWhereTo.setText(curfewPassRequest.getWhereTo());
        edtDateFrom.setText(curfewPassRequest.getValidDateFrom());
        edtDateTo.setText(curfewPassRequest.getValidDateTo());

        tvApprovedBy.setText(curfewPassRequest.getInspectedBy());
        tvApprovedOn.setText(curfewPassRequest.getInspectedOn());
        setComponentsEnable(false);
    }

    private void showSnackBarMessage(String message) {
        Snackbar snackbar = Snackbar
                .make(mLayout, message, Snackbar.LENGTH_LONG)
                .setAction("RETRY", view -> {
                });
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    private void setComponentsEnable(boolean value) {
        edtReason.setEnabled(value);
        edtWhereTo.setEnabled(value);
        edtDateFrom.setEnabled(value);
        edtDateTo.setEnabled(value);
        tvApprovedBy.setEnabled(value);
        tvApprovedOn.setEnabled(value);
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}
