package com.kibtechnologies.captainshieid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.kibtechnologies.captainshieid.R;

/**
 * Created by Khushboo Jha on 5/17/21.
 */
public class DashboardOptionsAdapter extends RecyclerView.Adapter<DashboardOptionsAdapter.DashBoardViewHolder> {
    int[] images = {
            //R.mipmap.ic_textsms_white_24dp,
            R.mipmap.ic_contact_phone_white_24dp,
            R.mipmap.ic_fingerprint_white_24dp,
            R.mipmap.ic_security_white_24dp,
            R.mipmap.ic_library_books_white_24dp,
            R.mipmap.ic_settings_white_24dp,
            R.mipmap.ic_help_outline_white_24dp};
    private ItemClickListener mListener;

    public DashboardOptionsAdapter(ItemClickListener mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public DashBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_dashboard_options, parent, false);
        DashBoardViewHolder viewHolder = new DashBoardViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DashBoardViewHolder holder, int position) {
        holder.imageView.setBackgroundResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class DashBoardViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private CardView cardView;
        public DashBoardViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            cardView = itemView.findViewById(R.id.cardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(getAdapterPosition(), true);
                }
            });

        }
    }
}
