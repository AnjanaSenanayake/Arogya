package lk.gov.arogya.nearbypeopletracker;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import lk.gov.arogya.R;
import lk.gov.arogya.models.EpidemicAlert;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactReportFragment extends BottomSheetDialogFragment implements OnMapReadyCallback {

    static ContactReportFragment newInstance(Context context, EpidemicAlert epidemicAlert) {
        return new ContactReportFragment(context, epidemicAlert);
    }

    private ContactReportFragment(Context context, EpidemicAlert epidemicAlert) {
        this.mContext = context;
        this.mEpidemicAlert = epidemicAlert;
    }

    private TextView tvEpidemicName;
    private TextView tvContactedDate;
    private LatLng coordinates;
    private Context mContext;
    private EpidemicAlert mEpidemicAlert;

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.setMinZoomPreference(16);

        CircleOptions options = new CircleOptions()
                .radius(20)
                .strokeWidth(3)
                .center(coordinates);

        googleMap.addMarker(new MarkerOptions().position(coordinates).title("Contacted Location"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
        googleMap.addCircle(options);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_report, container, false);

        tvEpidemicName = view.findViewById(R.id.tv_epidemic_name);
        tvContactedDate = view.findViewById(R.id.tv_contacted_date);

        tvEpidemicName.setText(mEpidemicAlert.getEpidemic());
        tvContactedDate.setText(mEpidemicAlert.getContactDate());

        coordinates = new LatLng(Double.valueOf(mEpidemicAlert.getLatitude()),
                Double.valueOf(mEpidemicAlert.getLongitude()));

        // Gets the MapView from the XML layout and creates it
        MapView mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        return view;
    }
}
