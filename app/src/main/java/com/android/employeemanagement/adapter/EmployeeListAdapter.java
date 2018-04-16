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
 * Created by Ankit on 16/4/18.
 * Recycler List view for displaying data after fetching from server
 * 
 */

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.RecyclerViewHolder> {

    private Context context;
   private List<Employee> employeeArrayList;
    private onEmployeeClickListener onItemClick;
    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView, designationTextView;
        ImageView thumbnailImage;
        
         RecyclerViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.title_text_view);
            thumbnailImage = itemView.findViewById(R.id.thumbnail_image_view);
            designationTextView = itemView.findViewById(R.id.designation_text_view);
        }
    }


    public EmployeeListAdapter(Context context,List<Employee> employeeArrayList){
        this.context = context;
        this.employeeArrayList = employeeArrayList;
        this.onItemClick = (onEmployeeClickListener) context;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View singleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.employe_single_item,parent,false);
        return new RecyclerViewHolder(singleView);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        holder.titleTextView.setText(employeeArrayList.get(position).employee_name);
        holder.designationTextView.setText(employeeArrayList.get(position).employee_designation);
        Picasso.with(context).load(employeeArrayList.get(position).profile_url).into(holder.thumbnailImage);
        
        //Item click listener on employe list, it will load the Detail view for same
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               onItemClick.onEmployeeItemClick(employeeArrayList.get(holder.getAdapterPosition()));

           }
       }); 
    }

    @Override
    public int getItemCount() {
        return employeeArrayList.size();
    }
    
    
    //Interface for implementing the click listener on list view
    public interface onEmployeeClickListener {
        void onEmployeeItemClick(Employee employee);
    }
}
