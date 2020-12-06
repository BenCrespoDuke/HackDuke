package com.example.hackduke;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {

    ArrayList<String> names;
    ArrayList<Double> carbons;
    int images[];
    Context context;

    public FriendsAdapter(Context ct, ArrayList<String> n, ArrayList<Double> c, int img[]) {
        context = ct;
        names = n;
        carbons = c;
        images = img;
    }
    @NonNull
    @Override
    public FriendsAdapter.FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.friends_row,parent,false);
        return new FriendsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsViewHolder holder, int position) {
        holder.name.setText(names.get(position));
        holder.carbonScore.setText(String.valueOf(carbons.get(position)));
        holder.pfp.setImageResource(images[position]);

    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class FriendsViewHolder extends RecyclerView.ViewHolder{

        TextView name, carbonScore;
        ImageView pfp;
        ConstraintLayout mainLayout;

        public FriendsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            carbonScore = itemView.findViewById(R.id.carbonScore);
            pfp = itemView.findViewById(R.id.pfp);
        }
    }
}