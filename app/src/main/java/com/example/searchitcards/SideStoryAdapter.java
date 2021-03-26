package com.example.searchitcards;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.searchitcards.Anime.AnimeDeatails;

import java.util.List;

public class SideStoryAdapter extends RecyclerView.Adapter<SideStoryAdapter.SSViewHolder> {
    LayoutInflater SInflator;
    List<SStory> SSList;

    public SideStoryAdapter(Context SInflator, List<SStory> SSList) {
        this.SInflator = LayoutInflater.from(SInflator);
        this.SSList = SSList;
    }

    @NonNull
    @Override
    public SSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("ResourceType") View view = SInflator.inflate(R.layout.custom_sidestory_card,parent,false);
        return new SSViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SSViewHolder holder, int position) {
        holder.title.setText(SSList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return SSList.size();
    }


    public class SSViewHolder extends RecyclerView.ViewHolder{

        TextView title;

    public SSViewHolder(@NonNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), AnimeDeatails.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("title_Id_Pass", String.valueOf(SSList.get(getAdapterPosition()).getMal_id()));
                v.getContext().startActivity(i);
            }
        });
        title = itemView.findViewById(R.id.anime_sideStory_title);
    }
}
}
