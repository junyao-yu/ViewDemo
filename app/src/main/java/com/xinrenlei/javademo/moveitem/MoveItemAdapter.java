package com.xinrenlei.javademo.moveitem;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

public class MoveItemAdapter extends RecyclerView.Adapter<MoveItemAdapter.MoveHolder> implements ItemTouchHelperAdapter {

    private List<String> list;
    private OnStartDragListener onStartDragListener;

    public MoveItemAdapter(List<String> list, OnStartDragListener onStartDragListener) {
        this.list = list;
        this.onStartDragListener = onStartDragListener;
    }

    @NonNull
    @Override
    public MoveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_move, null);
        return new MoveHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MoveHolder holder, int position) {
        holder.textView.setText("" + position);

        holder.drag.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    onStartDragListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        String tempStr = list.get(fromPosition);
        list.add(toPosition > fromPosition ? toPosition - 1 : toPosition, tempStr);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }



    static class MoveHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

        TextView textView;
        ImageView drag;
        FrameLayout frameLayout;

        public MoveHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.number);
            drag = itemView.findViewById(R.id.drag);
            frameLayout = itemView.findViewById(R.id.frameLayout);
        }

        @Override
        public void onItemSelected() {
            frameLayout.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.colorPrimary));
        }

        @Override
        public void onItemClear() {
            frameLayout.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.colorAccent));
        }
    }



}
