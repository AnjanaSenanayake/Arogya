package lk.gov.arogya.activity.parent;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import lk.gov.arogya.R;

public abstract class FormValidationActivity extends AppCompatActivity {

    public View coordinatorView;
    private ProgressBar progressBar;
    private boolean isProgressLoading = false;

    protected abstract void initInstances();

    protected abstract void disableOrEnableButtons(boolean disable);

    protected abstract void clearAllErrorMessages();

    protected abstract boolean checkForEmptyEditBoxAndShowAlert();

    @Override
    protected void onStart() {
        super.onStart();
        progressBar = findViewById(R.id.loading_spinner);
        coordinatorView = findViewById(R.id.my_coordinator_layout);
    }

    @Override
    public void onBackPressed() {
        if (isProgressLoading) {
            showSnackBar("Please wait, Your process is loading");
        } else {
            super.onBackPressed();
        }
    }

    protected void disableAllButtons() {
        disableOrEnableButtons(false);
    }

    protected void enableAllButtons() {
        disableOrEnableButtons(true);
    }

    public void showProgressBar() {
        isProgressLoading = true;
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        isProgressLoading = false;
        progressBar.setVisibility(View.GONE);
    }

    public boolean isEditTextEmpty(EditText editText) {
        return TextUtils.isEmpty(editText.getText());
    }

    public void showSnackBar(String message) {
        Snackbar.make(coordinatorView, message, Snackbar.LENGTH_SHORT).show();
    }

}
