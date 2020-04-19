package lk.gov.arogya.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import lk.gov.arogya.R;
import lk.gov.arogya.activity.parent.ToolbarActivity;
import lk.gov.arogya.adapter.MainActivityRecyclerViewAdapter;
import lk.gov.arogya.models.MainComponent;
import lk.gov.arogya.utils.GridSpacingItemDecoration;

public class MainActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        initToolbarWithoutBackButton();
//        initCollapsingToolbar();
        initRecyclerView();
    }

    private void initRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);

        List<MainComponent> mainComponents = new ArrayList<>();

        MainComponent personalInformation = new MainComponent(getResources().getString(R.string.personal_information), R.drawable.ic_personal_information);
        MainComponent diseaseTends = new MainComponent(getResources().getString(R.string.disease_trends), R.drawable.ic_trends);
        MainComponent diseaseInformation = new MainComponent("Disease Information", R.drawable.ic_diseases);
        MainComponent emergencyContacts = new MainComponent("Emergency Services", R.drawable.ic_question);
        MainComponent myHealthTracker = new MainComponent("My Health Tracker", R.drawable.ic_question);
        MainComponent mainComponent6 = new MainComponent("test1", R.drawable.ic_question);
        MainComponent mainComponent7 = new MainComponent("test", R.drawable.ic_question);
        MainComponent mainComponent8 = new MainComponent("test", R.drawable.ic_question);


        personalInformation.setCls(ShowMyInfoActivity.class);
        diseaseTends.setCls(TempResultsActivity.class);
        diseaseInformation.setCls(TempResultsActivity.class);
        emergencyContacts.setCls(TempResultsActivity.class);
        myHealthTracker.setCls(TempResultsActivity.class);
        mainComponent6.setCls(TempResultsActivity.class);
        mainComponent7.setCls(TempResultsActivity.class);
        mainComponent8.setCls(TempResultsActivity.class);


        mainComponents.add(personalInformation);
        mainComponents.add(diseaseTends);
        mainComponents.add(diseaseInformation);
        mainComponents.add(emergencyContacts);
        mainComponents.add(myHealthTracker);
        mainComponents.add(mainComponent6);
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
