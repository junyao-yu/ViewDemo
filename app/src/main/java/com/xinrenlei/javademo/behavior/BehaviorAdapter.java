package com.xinrenlei.javademo.behavior;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xinrenlei.javademo.R;

/**
 * Auth：yujunyao
 * Since: 2020/9/16 2:28 PM
 * Email：yujunyao@xinrenlei.net
 */

public class BehaviorAdapter extends RecyclerView.Adapter<BehaviorAdapter.BehaviorHolder> {

    @NonNull
    @Override
    public BehaviorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_move, null);
        return new BehaviorHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BehaviorHolder holder, int position) {
        holder.textView.setText("" + position);
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    class BehaviorHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public BehaviorHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.number);
        }
    }

}
