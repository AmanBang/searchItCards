package com.example.searchitcards.Movie.mAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.searchitcards.AnimeDeatails;
import com.example.searchitcards.R;
import com.example.searchitcards.ShowDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ShowHolder> {

    LayoutInflater SInflator;
    List<Show> showList;

    public ShowAdapter(Context SInflator, List<Show> showList) {
        this.SInflator = LayoutInflater.from(SInflator);
        this.showList = showList;
    }

    @NonNull
    @Override
    public ShowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = SInflator.inflate(R.layout.custom_show_card,parent,false);
        return new ShowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowHolder holder, int position) {
Picasso.get().load("https://image.tmdb.org/t/p/w500"+showList.get(position).getPoster_path()).into(holder.poster);
holder.Title.setText(showList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return showList.size();
    }

    public class ShowHolder extends RecyclerView.ViewHolder {
       TextView Title;
       ImageView poster;
       public ShowHolder(@NonNull View itemView) {
           super(itemView);

           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent i = new Intent(v.getContext(), ShowDetails.class);
                   i.putExtra("show_id",showList.get(getAdapterPosition()).getId());
                   v.getContext().startActivity(i);
               }
           });

           Title = itemView.findViewById(R.id.showName);
           poster = itemView.findViewById(R.id.showPoster);
       }
   }
}
