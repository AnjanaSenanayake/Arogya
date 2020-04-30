package lk.gov.arogya.curfewpass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import lk.gov.arogya.R;
import lk.gov.arogya.models.Constants.Approval;
import lk.gov.arogya.models.CurfewPassRequest;


public class CurfewPassesAdapter extends RecyclerView.Adapter<CurfewPassesAdapter.RecyclerViewHolder> {

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvStatus;
        TextView tvValidDate;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvValidDate = itemView.findViewById(R.id.tv_valid_date_from_to);
        }
    }

    private Context mContext;
    private ArrayList<CurfewPassRequest> curfewPassRequests;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private SimpleDateFormat sdfString = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    CurfewPassesAdapter(Context mContext, ArrayList<CurfewPassRequest> curfewPassRequests) {
        this.mContext = mContext;
        this.curfewPassRequests = curfewPassRequests;
    }

    void updateListData(ArrayList<CurfewPassRequest> curfewPassRequests) {
        this.curfewPassRequests = curfewPassRequests;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_requests_recycler, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return curfewPassRequests.size();
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final CurfewPassRequest curfewPassRequest = curfewPassRequests.get(position);

        holder.tvName.setText(curfewPassRequest.getRequestedFor());
        String status = curfewPassRequest.getStatus().getApproval();
        holder.tvStatus.setText(status);
        if (status.equals(Approval.APPROVED.getApproval())) {
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.lightGreen));
        } else if (status.equals(Approval.PENDING.getApproval())) {
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.lightOrange));
        } else {
            holder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.lightRed));
        }

        try {
            Date from = sdf.parse(curfewPassRequest.getValidDateFrom());
            Date to = sdf.parse(curfewPassRequest.getValidDateTo());
            String fromDate = sdfString.format(from);
            String toDate = sdfString.format(to);
            holder.tvValidDate.setText(String.format("From %s to %s", fromDate, toDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showRequestInformation(curfewPassRequest);
            }
        });
    }


    private void showRequestInformation(CurfewPassRequest curfewPassRequest) {
        AppCompatActivity activity = (AppCompatActivity) mContext;
        RequestPassFragment requestPassFragment = new RequestPassFragment(activity);
        requestPassFragment.setFormData(curfewPassRequest);
        requestPassFragment.show(activity.getSupportFragmentManager(), "requestPassFragment");
    }
}