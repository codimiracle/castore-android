package cn.com.sise.ca.castore.adapters;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.com.sise.ca.castore.R;

public class PowerpointAdapter extends RecyclerView.Adapter<PowerpointAdapter.PowerpointViewHolder> {
    List<Bitmap> powerpointImages;

    public PowerpointAdapter() {
        powerpointImages = new ArrayList<>();
    }

    public void addPowerpoint(Bitmap bitmap) {
        powerpointImages.add(bitmap);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PowerpointViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.powerpoint_item, parent, false);
        return new PowerpointViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PowerpointViewHolder holder, int position) {
        holder.setPowerpointImage(powerpointImages.get(position));
    }

    @Override
    public int getItemCount() {
        return powerpointImages.size();
    }

    public class PowerpointViewHolder extends RecyclerView.ViewHolder {
        private ImageView powerpoint;

        public PowerpointViewHolder(View itemView) {
            super(itemView);
            powerpoint = itemView.findViewById(R.id.powerpoint_image);
        }

        public void setPowerpointImage(Bitmap powerpointImage) {
            powerpoint.setImageBitmap(powerpointImage);
        }
    }
}
