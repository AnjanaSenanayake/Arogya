package lk.gov.arogya.curfewpass;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import lk.gov.arogya.R;
import lk.gov.arogya.api.RestAPI;
import lk.gov.arogya.api.RestAPI.OnSuccessListener;
import lk.gov.arogya.models.CurfewPassRequest;
import lk.gov.arogya.support.ContentHolder;

public class CurfewPassActivity extends AppCompatActivity {

    private TextView tvNoRequests;
    private RecyclerView recyclerRequestedPasses;
    private FloatingActionButton fabRequestPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curfew_pass);

        tvNoRequests = findViewById(R.id.tv_no_curfew_passes);
        recyclerRequestedPasses = findViewById(R.id.recycler_curfew_passes);
        fabRequestPass = findViewById(R.id.fab_request_curfew_pass);

        recyclerRequestedPasses.setLayoutManager(new LinearLayoutManager(this));

        fabRequestPass.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                RequestPassFragment requestPassFragment = new RequestPassFragment(CurfewPassActivity.this);
                requestPassFragment.show(getSupportFragmentManager(), "requestPassFragment");
            }
        });

        loadRequestedPasses();
    }

    private void loadRequestedPasses() {
        RestAPI.getAllCurfewPasses(ContentHolder.getUID(),
                new OnSuccessListener<ArrayList<CurfewPassRequest>, Throwable>() {
                    @Override
                    public void onSuccess(final ArrayList<CurfewPassRequest> response) {
                        CurfewPassesAdapter curfewPassesAdapter = new CurfewPassesAdapter(CurfewPassActivity.this,
                                response);
                        recyclerRequestedPasses.setAdapter(curfewPassesAdapter);
                        tvNoRequests.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(final Throwable err) {
                        tvNoRequests.setVisibility(View.VISIBLE);
                    }
                });
    }
}
