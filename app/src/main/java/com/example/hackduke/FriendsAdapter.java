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

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {

    ArrayList<String> names;
    ArrayList<Long> carbons;
    ArrayList<Integer> images;
    ArrayList<String> meals;
    ArrayList<String> emails;
    Context context;

    public FriendsAdapter(Context ct, ArrayList<String> n, ArrayList<Long> c, ArrayList<Integer> img, ArrayList<String> m) {
        context = ct;
        names = n;
        carbons = c;
        images = img;
        meals = m;
    }

    public FriendsAdapter(Context ct, ArrayList<String> n, ArrayList<Long> c, ArrayList<Integer> img, ArrayList<String> m, ArrayList<String> e) {
        context = ct;
        names = n;
        carbons = c;
        images = img;
        meals = m;
        emails = e;
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
        if(position < names.size() ) {
            holder.name.setText(names.get(position));
            holder.carbonScore.setText(String.valueOf(carbons.get(position)));
            holder.pfp.setImageResource(images.get(position));
            if(meals.get(position) == null) {
                holder.meal.setText(meals.get(position));
            }
        }
    }

    public void onBindViewHolder(@NonNull AddFriendsViewHolder holder, int position) {
        if(position < names.size() ) {
            holder.name.setText(names.get(position));
            holder.carbonScore.setText(String.valueOf(carbons.get(position)));
            holder.pfp.setImageResource(images.get(position));
            if(meals.get(position) == null) {
                holder.meal.setText(meals.get(position));
            }
            holder.email = emails.get(position);
        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class FriendsViewHolder extends RecyclerView.ViewHolder{

        TextView name, carbonScore, meal;
        ImageView pfp;

        public FriendsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            carbonScore = itemView.findViewById(R.id.carbonScore);
            meal = itemView.findViewById(R.id.meal);
            pfp = itemView.findViewById(R.id.pfp);
        }
    }

    public class AddFriendsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, carbonScore, meal;
        ImageView pfp;
        String email;

        public AddFriendsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name2);
            carbonScore = itemView.findViewById(R.id.carbonScore2);
            meal = itemView.findViewById(R.id.meal2);
            pfp = itemView.findViewById(R.id.pfp2);
            itemView.setOnClickListener(this);
            itemView.findViewById(R.id.button3).setOnClickListener(this);
            itemView.findViewById(R.id.button4).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            UploadData up = new UploadData();
            switch (v.getId()) {
                case R.id.button3:
                    up.AcceptorDenyFriendRequest(email, "0", true);
                    break;
                case R.id.button4:
                    up.AcceptorDenyFriendRequest(email, "0", false);
                    break;
                default :
                    return;
            }
        }
    }
}
