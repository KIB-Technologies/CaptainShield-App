package com.kibtechnologies.captainshieid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kibtechnologies.captainshieid.R;
import com.kibtechnologies.captainshieid.model.Operator;
import com.kibtechnologies.captainshieid.model.Plancategory;
import com.kibtechnologies.captainshieid.model.Plandetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khushboo Jha on 6/3/21.
 */
public class RechargePlansAdapater extends RecyclerView.Adapter<RechargePlansAdapater.RechargePlanViewHolder> {
    private Context context;
    private List<Plandetail> plandetailList = new ArrayList<>();
    private List<Plancategory> plancategories = new ArrayList<>();
    private AdapterListener mListener;

    public RechargePlansAdapater(Context context) {
        this.context = context;
    }

    public void addPlanDetails(List<Plandetail> plandetailList) {
        this.plandetailList = plandetailList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RechargePlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_recharge_plan_list, parent, false);
        return new RechargePlanViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RechargePlanViewHolder holder, int position) {
        Plandetail plandetail = plandetailList.get(position);
       // Plancategory plancategory = plancategories.get(position);
        holder.paise.setText("Rs" + plandetail.getAmount());
        holder.category.setText(plandetail.getCategory());
        holder.validity.setText(plandetail.getValidity());
        holder.description.setText(plandetail.getDescription());
//        holder.rechargefor.setText(plandetail.getOperator_name());
        holder.mrp.setText(plandetail.getAmount());

    }

    @Override
    public int getItemCount() {
        return plandetailList.size();
    }

    public class RechargePlanViewHolder extends RecyclerView.ViewHolder {
        private  TextView paise, category, mrp, validity, description, rechargefor;
        public RechargePlanViewHolder(@NonNull View itemView) {
            super(itemView);
            paise = itemView.findViewById(R.id.paise);
            category = itemView.findViewById(R.id.category);
            mrp = itemView.findViewById(R.id.mrp);
            validity = itemView.findViewById(R.id.validity);
            description = itemView.findViewById(R.id.description);
//            rechargefor = itemView.findViewById(R.id.rechargefor);

        }
    }
}
