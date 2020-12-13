package com.example.hackduke;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    ArrayList<String> names;
    ArrayList<Long> carbons;
    ArrayList<Integer> images;
    ArrayList<String> meals;
    Context context;

    public MealAdapter(Context ct, ArrayList<String> n, ArrayList<Long> c, ArrayList<Integer> img, ArrayList<String> m) {
        context = ct;
        names = n;
        carbons = c;
        images = img;
        meals = m;
    }

    @NonNull
    @Override
    public MealAdapter.MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.friends_row,parent,false);
        return new MealViewHolder(view);
    }

    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        if(position < names.size() ) {
            holder.name.setText(names.get(position));
            holder.carbonScore.setText(String.valueOf(carbons.get(position)));
            holder.pfp.setImageResource(images.get(position));
            if(meals.get(position) == null) {
                holder.meal.setText(meals.get(position));
            }
        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class MealViewHolder extends RecyclerView.ViewHolder{

        TextView name, carbonScore, meal;
        ImageView pfp;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            carbonScore = itemView.findViewById(R.id.carbonScore);
            meal = itemView.findViewById(R.id.meal);
            pfp = itemView.findViewById(R.id.pfp);
        }
    }
}
