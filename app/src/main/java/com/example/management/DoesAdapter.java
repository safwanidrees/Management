package com.example.management;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DoesAdapter extends RecyclerView.Adapter<DoesAdapter.MyViewHolder> {

    Context context;
    ArrayList<Detail>details;
    private Context mContext;
    private OnItemClickListener mListener;
    public interface  OnItemClickListener{
        void onItemClick(int positon);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;

    }


    public DoesAdapter(Context context, ArrayList<Detail> details) {
        this.context = context;
        this.details = details;


    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_does, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;
//        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.items_does,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        holder.name.setText(details.get(position).getName());
        holder.address.setText(details.get(position).getAddress());
        holder.email.setText(details.get(position).getEmail());

        holder.phone.setText(details.get(position).getPhone());

        holder.order.setText(details.get(position).getOrderspinner());

//
//holder.parentLayout.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//        Intent i= new Intent (mContext,NewTask.class);
//            mContext.startActivity(i);
//    }
//});

    }

    @Override
    public int getItemCount() {
        return details.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,address,email,phone,order;
        RecyclerView parentLayout;
OnItemClickListener onItemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=(TextView)itemView.findViewById(R.id.pname);
            address=(TextView)itemView.findViewById(R.id.paddress);

            email=(TextView)itemView.findViewById(R.id.pemail);
            phone=(TextView)itemView.findViewById(R.id.pphone);
            order=(TextView)itemView.findViewById(R.id.porder);


itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(mListener!=null){
            int Position=getAdapterPosition();
            if(Position!=RecyclerView.NO_POSITION){
                mListener.onItemClick(Position);
            }
        }
    }
});

        }


    }
}

