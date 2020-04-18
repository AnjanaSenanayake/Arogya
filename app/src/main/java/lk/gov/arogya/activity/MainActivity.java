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

        MainComponent mainComponent1 = new MainComponent("My Info", R.drawable.user_info);
        MainComponent mainComponent2 = new MainComponent("Disease Trends", R.drawable.icon_bus_map);
        MainComponent mainComponent3 = new MainComponent("Disease Information", R.drawable.icon_bus_map);
        MainComponent mainComponent4 = new MainComponent("Emergency Contacts and Services", R.drawable.icon_bus_map);
        MainComponent mainComponent5 = new MainComponent("My Health Tracker", R.drawable.icon_bus_map);
        MainComponent mainComponent6 = new MainComponent("test1", R.drawable.icon_bus_map);
        MainComponent mainComponent7 = new MainComponent("test", R.drawable.icon_bus_map);
        MainComponent mainComponent8 = new MainComponent("test", R.drawable.icon_bus_map);
        MainComponent mainComponent9 = new MainComponent("test", R.drawable.icon_bus_map);
        MainComponent mainComponent10 = new MainComponent("test", R.drawable.icon_bus_map);
        MainComponent mainComponent11 = new MainComponent("test", R.drawable.icon_bus_map);
        MainComponent mainComponent12 = new MainComponent("test", R.drawable.icon_bus_map);


        mainComponent1.setCls(ShowMyInfoActivity.class);
        mainComponent2.setCls(TempResultsActivity.class);
        mainComponent3.setCls(TempResultsActivity.class);
        mainComponent4.setCls(TempResultsActivity.class);
        mainComponent5.setCls(TempResultsActivity.class);
        mainComponent6.setCls(TempResultsActivity.class);
        mainComponent7.setCls(TempResultsActivity.class);
        mainComponent8.setCls(TempResultsActivity.class);
        mainComponent9.setCls(TempResultsActivity.class);
        mainComponent10.setCls(TempResultsActivity.class);
        mainComponent11.setCls(TempResultsActivity.class);
        mainComponent12.setCls(TempResultsActivity.class);


        mainComponents.add(mainComponent1);
        mainComponents.add(mainComponent2);
        mainComponents.add(mainComponent3);
        mainComponents.add(mainComponent4);
        mainComponents.add(mainComponent5);
        mainComponents.add(mainComponent6);
        mainComponents.add(mainComponent7);
        mainComponents.add(mainComponent8);
        mainComponents.add(mainComponent9);
        mainComponents.add(mainComponent10);
        mainComponents.add(mainComponent11);
        mainComponents.add(mainComponent12);


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
