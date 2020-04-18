package lk.gov.arogya.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import lk.gov.arogya.R;
import lk.gov.arogya.models.MainComponent;

public class MainActivityRecyclerViewAdapter extends RecyclerView.Adapter<MainActivityRecyclerViewAdapter.MyViewHolder> {

    private List<MainComponent> mainComponents;


    public MainActivityRecyclerViewAdapter(List<MainComponent> mainComponents) {
        this.mainComponents = mainComponents;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_main_activity, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MainComponent mainComponent = mainComponents.get(position);
        holder.title.setText(mainComponent.getName());
        holder.thumbnail.setImageResource(mainComponent.getThumbnail());
    }

    @Override
    public int getItemCount() {
        return mainComponents.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView thumbnail;

        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            thumbnail = view.findViewById(R.id.thumbnail);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int mPosition = getLayoutPosition();
            MainComponent mainComponent = mainComponents.get(mPosition);

            Intent mainIntent = new Intent(v.getContext(), mainComponent.getCls());
            v.getContext().startActivity(mainIntent);
        }
    }
}
