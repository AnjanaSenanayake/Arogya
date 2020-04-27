package lk.gov.arogya.main;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.util.ArrayList;
import java.util.List;
import lk.gov.arogya.R;
import lk.gov.arogya.TempResultsActivity;
import lk.gov.arogya.nearbypeopletracker.DiseaseContactTraceActivity;
import lk.gov.arogya.otherusers.UserRegisterActivity;
import lk.gov.arogya.parent.ToolbarActivity;
import lk.gov.arogya.personalinformation.PersonalInformationActivity;
import lk.gov.arogya.support.GridSpacingItemDecoration;

public class MainActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initToolbarWithoutBackButton();
//        initCollapsingToolbar();
        initFirebaseServices();
        FirebaseApp.initializeApp(this);
        initRecyclerView();
    }

    private void initFirebaseServices() {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId(getResources().getString(R.string.firebase_app_id)) // Required for Analytics.
                .setApiKey(getResources().getString(R.string.firebase_api_key)) // Required for Auth.
                .build();
        FirebaseApp.initializeApp(this, options, getResources().getString(R.string.app_name));
    }
    private void initRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);

        List<MainComponent> mainComponents = new ArrayList<>();

        MainComponent personalInformation = new MainComponent(getResources().getString(R.string.personal_information), R.drawable.ic_personal_information);
        MainComponent diseaseTends = new MainComponent(getResources().getString(R.string.disease_trends), R.drawable.ic_trends);
        MainComponent diseaseInformation = new MainComponent("Disease Information", R.drawable.ic_diseases);
        MainComponent nearbyPeopleTracker = new MainComponent("Nearby People Tracker",
                R.drawable.ic_virus_transmission_300dp);
        MainComponent myHealthTracker = new MainComponent("My Health Tracker", R.drawable.ic_question);
        MainComponent otherUsers = new MainComponent("Other Users", R.drawable.ic_others_200dp);
        MainComponent mainComponent7 = new MainComponent("test", R.drawable.ic_question);
        MainComponent mainComponent8 = new MainComponent("test", R.drawable.ic_question);

        personalInformation.setCls(PersonalInformationActivity.class);
        diseaseTends.setCls(TempResultsActivity.class);
        diseaseInformation.setCls(TempResultsActivity.class);
        nearbyPeopleTracker.setCls(DiseaseContactTraceActivity.class);
        myHealthTracker.setCls(TempResultsActivity.class);
        otherUsers.setCls(UserRegisterActivity.class);
        mainComponent7.setCls(TempResultsActivity.class);
        mainComponent8.setCls(TempResultsActivity.class);

        mainComponents.add(personalInformation);
        mainComponents.add(diseaseTends);
        mainComponents.add(diseaseInformation);
        mainComponents.add(nearbyPeopleTracker);
        mainComponents.add(myHealthTracker);
        mainComponents.add(otherUsers);
        mainComponents.add(mainComponent7);
        mainComponents.add(mainComponent8);

        MainActivityRecyclerViewAdapter adapter = new MainActivityRecyclerViewAdapter(mainComponents);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initInstances() {

    }


    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
