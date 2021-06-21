package com.kibtechnologies.captainshieid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kibtechnologies.captainshieid.R;
import com.kibtechnologies.captainshieid.model.Operator;
import com.kibtechnologies.captainshieid.model.SliderItem;
import com.kibtechnologies.captainshieid.utils.PreferenceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khushboo Jha on 6/3/21.
 */
public class AllOperatorsAdapter extends RecyclerView.Adapter<AllOperatorsAdapter.AllOperatorsViewHolder> {
    private Context context;
    private List<Operator> operators = new ArrayList<>();
    private AdapterListener mListener;

    public AllOperatorsAdapter(Context context, AdapterListener mListener) {
        this.context = context;
        this.mListener = mListener;
    }

    public void addOperators(List<Operator> operators) {
        this.operators = operators;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AllOperatorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_operators_list, parent, false);
        return new AllOperatorsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllOperatorsViewHolder holder, int position) {
        Operator operator = operators.get(position);

            holder.operatorsName.setText(operator.getName());
        holder.operatorsCode.setText(operator.getCode());
        String operatorType = PreferenceUtils.getInstance(context).getString(PreferenceUtils.Key.TYPE_OPERATORS.name() , "");
        if (operatorType.equalsIgnoreCase("dth")){
            holder.tvImg.setVisibility(View.VISIBLE);
        }else {holder.tvImg.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return operators.size();
    }

    public class AllOperatorsViewHolder extends RecyclerView.ViewHolder {
        TextView operatorsName, operatorsCode;
        ImageView tvImg;
        public AllOperatorsViewHolder(@NonNull View itemView) {
            super(itemView);
            operatorsName = itemView.findViewById(R.id.oprts_name);
            operatorsCode = itemView.findViewById(R.id.oprts_code);
            tvImg = itemView.findViewById(R.id.tv);
            operatorsName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.setAdapterValue(operators.get(getAdapterPosition()).getName());
                    PreferenceUtils.getInstance(context).saveString(PreferenceUtils.Key.NUMBER_OPERATORS.name(), operators.get(getAdapterPosition()).getName());
                    PreferenceUtils.getInstance(context).saveString(PreferenceUtils.Key.CODE_OPERATORS.name(), operators.get(getAdapterPosition()).getCode());
                }
            });

        }
    }
}
