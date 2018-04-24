package com.android.employeemanagement.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.employeemanagement.R;
import com.android.employeemanagement.model.data.Employee;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by suraj on 14/4/18.
 */

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.RecyclerViewHolder> {

    Context context;
    List<Employee> employeeArrayList;
    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        ImageView thumbnailImage;
        public RecyclerViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.title_text_view);
            thumbnailImage = itemView.findViewById(R.id.thumbnail_image_view);
        }
    }


    public EmployeeListAdapter(Context context,List<Employee> employeeArrayList){
        this.context = context;
        this.employeeArrayList = employeeArrayList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View singleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.employe_single_item,parent,false);
        return new RecyclerViewHolder(singleView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.titleTextView.setText(employeeArrayList.get(position).employee_name);

        Picasso.with(context).load(employeeArrayList.get(position).profile_url).into(holder.thumbnailImage);
    }

    @Override
    public int getItemCount() {
        return employeeArrayList.size();
    }
}
