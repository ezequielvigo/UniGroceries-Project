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

    //Creating necessary objects
    public Context context;
    public List<GroceryListModel> groceryListModelList;

    //Constructor for our class
    public GroceryListAdapter(Context context, List<GroceryListModel> groceryListModels){
        this.context = context;
        this.groceryListModelList = groceryListModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Creates a view out of the inflated version of our recycler view layout
        View view = LayoutInflater.from(context).inflate(R.layout.grocery_list_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Sets the text of the viewholder to the grocery list date
        holder.listDate.setText(groceryListModelList.get(position).getDate().toString());

        //Checks if the title field is empty, if so sets it to a default title
        if(groceryListModelList.get(position).getTitle().isEmpty()){
            holder.listTitle.setText("Untitled grocery list");
        }else{
            holder.listTitle.setText(groceryListModelList.get(position).getTitle().toString());
        }
        holder.seeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Upon clicking an entry, redirects the user to the page to view that grocery list
                Intent intent = new Intent(context, SingleGroceryList.class);
                //Puts the current grocery list as a serializable extra into the intent
                intent.putExtra("GroceryList",(Serializable)groceryListModelList.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    //Returns the number of items in our list
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
            //Assigning our textView objects to the specific textViews using their ID
            listTitle = (TextView)itemView.findViewById(R.id.groceryListTitle);
            listDate = (TextView)itemView.findViewById(R.id.groceryListDate);
            seeMore = (TextView)itemView.findViewById(R.id.seeMore);
        }
    }
}
