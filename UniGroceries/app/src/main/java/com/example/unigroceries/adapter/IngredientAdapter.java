package com.example.unigroceries.adapter;

import android.content.Context;
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

    public IngredientAdapter(Context context, List<IngredientModel> ingredientModelList){
        this.context = context;
        this.ingredientModelList = ingredientModelList;
    }

    @NonNull
    @Override
    public IngredientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ingredient_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameView.setText(this.ingredientModelList.get(position).getName().toString());
    }

    @Override
    public int getItemCount() {
        if(this.ingredientModelList!=null){
            return this.ingredientModelList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = (TextView)itemView.findViewById(R.id.ingredientView);
        }
    }
}
