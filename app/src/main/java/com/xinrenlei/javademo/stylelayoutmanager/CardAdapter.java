package com.xinrenlei.javademo.stylelayoutmanager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xinrenlei.javademo.R;

import java.util.List;

/**
 * Auth：yujunyao
 * Since: 2020/9/15 9:33 AM
 * Email：yujunyao@xinrenlei.net
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder> {

    public List<CardBean> list;

    public CardAdapter(List<CardBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = View.inflate(parent.getContext(), R.layout.item_card, null);
        return new CardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        holder.textView.setText("" + position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class CardHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public CardHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.number);
        }
    }

}
