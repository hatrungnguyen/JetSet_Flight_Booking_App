package edu.birzeit.jetset.utils;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VerticalPagerAdapter2 extends RecyclerView.Adapter<VerticalPagerAdapter2.ViewHolder> {
    private final List<View> pageViews;

    public VerticalPagerAdapter2(List<View> pageViews) {
        this.pageViews = pageViews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Here, we just use the provided View instead of inflating a new one
        return new ViewHolder(pageViews.get(viewType));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Nothing specific to bind, just return the view
    }

    @Override
    public int getItemCount() {
        return pageViews.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
