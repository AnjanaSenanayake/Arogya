package lk.gov.arogya.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import lk.gov.arogya.R;
import lk.gov.arogya.fragment.UserDetailsFragment;

public class AskUserInformationActivity extends AppCompatActivity {

    private List<Integer> detailsLayout = new ArrayList<>();
    private int detailsLayoutNo = 0;
    private int totalLayouts;
    private Button nextLayoutBtn;
    private Button previousLayoutBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_user_information);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        initInstances();
        showFirstFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initInstances() {
        nextLayoutBtn = findViewById(R.id.btn_next);
        previousLayoutBtn = findViewById(R.id.btn_previous);

        detailsLayout.add(R.layout.fragment_ask_gs_ds);
        detailsLayout.add(R.layout.fragment_ask_address);
        detailsLayout.add(R.layout.fragment_gender);
        totalLayouts = detailsLayout.size() - 1;

    }

    public void onNxtBtnClicked(View view) {
        int fCode = getNextFragmentNo();
        manageNxtPrvButtons(fCode);
        showFragment(fCode);
    }

    public void onPreviousBtnClicked(View view) {
        int fCode = getPreviousFragmentNo();
        manageNxtPrvButtons(fCode);
        showFragment(fCode);
    }

    private void showFirstFragment() {
        hidePreviousBtn();
        showFragment(0);
    }

    private void showSubmitBtn() {
    }

    private void hideNextBtn() {
        nextLayoutBtn.setVisibility(View.GONE);
    }

    private void hidePreviousBtn() {
        previousLayoutBtn.setVisibility(View.GONE);
    }

    private void showNextBtn() {
        nextLayoutBtn.setVisibility(View.VISIBLE);
    }

    private void showPreviousBtn() {
        previousLayoutBtn.setVisibility(View.VISIBLE);
    }


    private void showFragment(int layoutId) {
        Fragment newFragment = UserDetailsFragment.newInstance(detailsLayout.get(layoutId));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.frag_placeholder, newFragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }

    private int getNextFragmentNo() {
        return ++detailsLayoutNo;
    }

    private int getPreviousFragmentNo() {
        return --detailsLayoutNo;
    }

    private void manageNxtPrvButtons(int layoutNo) {
        if (layoutNo == 0) {
            hidePreviousBtn();
        } else if (layoutNo == totalLayouts) {
            hideNextBtn();
            showSubmitBtn();
        } else {
            showPreviousBtn();
            showNextBtn();
        }
    }
}
