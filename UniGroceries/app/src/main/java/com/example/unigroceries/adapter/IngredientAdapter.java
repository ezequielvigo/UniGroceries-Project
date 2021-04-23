package com.example.unigroceries.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unigroceries.R;
import com.example.unigroceries.model.IngredientModel;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyViewHolder> {

    private Context context;
    private List<IngredientModel> ingredientModelList;

    //Our class constructor, initialises our Context and List<IngredientModel>
    public IngredientAdapter(Context context, List<IngredientModel> ingredientModelList){
        this.context = context;
        this.ingredientModelList = ingredientModelList;
    }

    @NonNull
    @Override
    public IngredientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Creates a view from an inflated version of our recycler_view layout file
        View view = LayoutInflater.from(context).inflate(R.layout.ingredient_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //Sets the text of the nameView field to that of our IngredientModel
        holder.nameView.setText(this.ingredientModelList.get(position).getName().toString());

        //Checks if the user clicks on this entry
        holder.nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If the user clicks on the entry, it crosses out the current IngredientModel
                holder.nameView.setPaintFlags(holder.nameView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG );
            }
        });
    }

    //Returns the number of elements in our list
    @Override
    public int getItemCount() {
        if(this.ingredientModelList!=null){
            return this.ingredientModelList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameView;

        //Constructor, sets our TextView object to the specific TextView using its ID
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = (TextView)itemView.findViewById(R.id.ingredientView);
        }
    }
}
