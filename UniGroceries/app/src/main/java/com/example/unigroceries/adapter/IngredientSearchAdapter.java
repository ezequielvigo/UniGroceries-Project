package com.example.unigroceries.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unigroceries.R;
import com.example.unigroceries.model.IngredientModel;

import java.util.List;

public class IngredientSearchAdapter extends RecyclerView.Adapter<IngredientSearchAdapter.MyViewHolder> {

    private Context context;
    private List<IngredientModel> ingredientModelList;

    //Our class constructor, sets the Context and List<IngredientModel>
    public IngredientSearchAdapter(Context context, List<IngredientModel> ingredientModelList){
        this.context = context;
        this.ingredientModelList = ingredientModelList;
    }

    @NonNull
    @Override
    public IngredientSearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Creates a view from an inflated version of our recycler_view_layout
        View view = LayoutInflater.from(context).inflate(R.layout.ingredient_search_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    //Called upon binding to the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Sets the nameView contents to the name of our IngredientModel
        holder.nameView.setText(this.ingredientModelList.get(position).getName().toString());

        //Checks if user presses the delete button
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Item is removed and Adapter is notify of data change so it can update
                ingredientModelList.remove(ingredientModelList.get(position));
                notifyDataSetChanged();
            }
        });
    }

    //Returns the number of items in our list
    @Override
    public int getItemCount() {
        if(this.ingredientModelList!=null){
            return this.ingredientModelList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameView;
        Button deleteButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //Setting our TextView objects to the appropriate TextViews using their IDs
            nameView = (TextView)itemView.findViewById(R.id.ingredientView);
            deleteButton = (Button)itemView.findViewById(R.id.deleteIngredient);
        }
    }
}
