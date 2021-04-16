package com.example.unigroceries.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unigroceries.R;
import com.example.unigroceries.SingleGroceryList;
import com.example.unigroceries.model.GroceryListModel;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.List;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.MyViewHolder> {

    public Context context;
    public List<GroceryListModel> groceryListModelList;

    public GroceryListAdapter(Context context, List<GroceryListModel> groceryListModels){
        this.context = context;
        this.groceryListModelList = groceryListModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grocery_list_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.listDate.setText(groceryListModelList.get(position).getDate().toString());
        if(groceryListModelList.get(position).getTitle().isEmpty()){
            holder.listTitle.setText("Untitled grocery list");
        }else{
            holder.listTitle.setText(groceryListModelList.get(position).getTitle().toString());
        }
        holder.seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleGroceryList.class);
                intent.putExtra("GroceryList",(Serializable)groceryListModelList.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(groceryListModelList!=null){
            return groceryListModelList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView listTitle, listDate, seeMore;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            listTitle = (TextView)itemView.findViewById(R.id.groceryListTitle);
            listDate = (TextView)itemView.findViewById(R.id.groceryListDate);
            seeMore = (TextView)itemView.findViewById(R.id.seeMore);
        }
    }
}
