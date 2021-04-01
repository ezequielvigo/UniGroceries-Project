package com.example.unigroceries.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.unigroceries.R;
import com.example.unigroceries.SingleRecipe;
import com.example.unigroceries.model.RecipeModel;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.MyViewHolder> {
    private Context context;
    private List<RecipeModel> recipeList;

    public  RecipeAdapter(Context context, List<RecipeModel> recipeList){
        this.recipeList = recipeList;
        this.context = context;
    }

    public void setRecipeList(List<RecipeModel> recipeList) {
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.titleView.setText(this.recipeList.get(position).getTitle().toString());
        Picasso.get()
                .load(recipeList.get(position).getImageUrl())
                .resize(240,150)
                .centerCrop()
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleRecipe.class);
                intent.putExtra("id", recipeList.get(position).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        if(this.recipeList != null){
            return this.recipeList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView titleView;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titleView = (TextView)itemView.findViewById(R.id.recyclerTitleView);
            imageView = (ImageView)itemView.findViewById(R.id.recyclerImageView);

        }
    }
}
