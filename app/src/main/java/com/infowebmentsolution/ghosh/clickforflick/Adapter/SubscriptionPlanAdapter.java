package com.infowebmentsolution.ghosh.clickforflick.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.infowebmentsolution.ghosh.clickforflick.Model.SubscriptionPlansList;
import com.infowebmentsolution.ghosh.clickforflick.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SubscriptionPlanAdapter extends RecyclerView.Adapter<SubscriptionPlanAdapter.SubscriptionHolder> {


    private ArrayList<SubscriptionPlansList> lists;
    private Context context;
    private ClickedOnItemListener clickedOnItemListener;

    public void SetData(ArrayList<SubscriptionPlansList> lists) {
        this.lists = lists;
    }
    public interface ClickedOnItemListener {
        void ClickedItem(SubscriptionPlansList list);
    }
    public SubscriptionPlanAdapter (ClickedOnItemListener clickedOnItemListener){
        this.clickedOnItemListener = clickedOnItemListener;
    }

    @NotNull
    @Override
    public SubscriptionHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new SubscriptionHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_subscription_plan,parent,false));
    }

    @Override
    public void onBindViewHolder(@NotNull SubscriptionHolder holder, int position) {

        holder.amount.setText(lists.get(position).getSubscriptionamount());
        holder.duration.setText(lists.get(position).getSubscriptionduration());
        holder.itemView.setOnClickListener(view -> {
            SubscriptionPlansList subscriptionPlansList = this.lists.get(position);
            holder.ll.setOnClickListener(v -> clickedOnItemListener.ClickedItem(subscriptionPlansList));
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class SubscriptionHolder extends RecyclerView.ViewHolder{

        LinearLayout ll;
        private TextView amount;
        private TextView duration;
        public SubscriptionHolder(@NotNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.subscription_plan_amount);
            duration=itemView.findViewById(R.id.subscription_plan_duration);
            ll=itemView.findViewById(R.id.subscription_plan_ll);
        }
    }
}
