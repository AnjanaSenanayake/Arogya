package lk.gov.arogya.otherusers;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import lk.gov.arogya.R;
import lk.gov.arogya.personalinformation.PersonalInformationActivity;
import lk.gov.arogya.models.User;


public class UserRegisterAdapter extends RecyclerView.Adapter<UserRegisterAdapter.RecyclerViewHolder> {

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tvNICPP;
        TextView tvName;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvNICPP = itemView.findViewById(R.id.tv_nicpp);
        }
    }

    private Context mContext;
    private ArrayList<User> usersList;

    public UserRegisterAdapter(Context mContext, ArrayList<User> users) {
        this.mContext = mContext;
        this.usersList = users;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_register_user_recycler, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final User user = usersList.get(position);

        holder.tvName.setText(user.getFullName());
        holder.tvNICPP.setText(user.getNicpp());

        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showPersonalInformation(user);
            }
        });
    }


    private void showPersonalInformation(User user) {
        Intent intent = new Intent(mContext, PersonalInformationActivity.class);
        intent.putExtra("USER", user);
        mContext.startActivity(intent);
    }
}