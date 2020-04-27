package lk.gov.arogya.nearbypeopletracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import lk.gov.arogya.R;
import lk.gov.arogya.models.EpidemicAlert;


public class AlertsListAdapter extends RecyclerView.Adapter<AlertsListAdapter.RecyclerViewHolder> {

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView epidemic;
        TextView contactedDate;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            epidemic = itemView.findViewById(R.id.tv_epidemic);
            contactedDate = itemView.findViewById(R.id.tv_contacted_date);
        }
    }

    private Context mContext;
    private ArrayList<EpidemicAlert> mEpidemicAlerts;

    public AlertsListAdapter(Context mContext, ArrayList<EpidemicAlert> epidemicAlerts) {
        this.mContext = mContext;
        this.mEpidemicAlerts = epidemicAlerts;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_alerts_recycler, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mEpidemicAlerts.size();
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final EpidemicAlert epidemicAlert = mEpidemicAlerts.get(position);

        holder.epidemic.setText(epidemicAlert.getEpidemic());
        holder.contactedDate.setText(epidemicAlert.getContactDate());

        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertInformation(epidemicAlert);
            }
        });
    }


    private void showAlertInformation(EpidemicAlert epidemicAlert) {
        AppCompatActivity activity = (AppCompatActivity) mContext;
        ContactReportFragment addPhotoBottomDialogFragment =
                ContactReportFragment.newInstance(mContext, epidemicAlert);
        addPhotoBottomDialogFragment.show(activity.getSupportFragmentManager(), "");
    }
}