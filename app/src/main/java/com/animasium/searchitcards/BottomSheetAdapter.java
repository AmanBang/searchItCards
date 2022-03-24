package com.animasium.searchitcards;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.BottomSheetHolder> {

    LayoutInflater inflater;
    List<SeasonListPOJO> seasonList;

    public BottomSheetAdapter(Context inflater, List<SeasonListPOJO> seasonList) {
        this.inflater = LayoutInflater.from(inflater);
        this.seasonList = seasonList;
    }

    @NonNull
    @Override
    public BottomSheetAdapter.BottomSheetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_season_layout, parent, false);
        return new BottomSheetHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BottomSheetAdapter.BottomSheetHolder holder, int position) {
        holder.textView.setText(seasonList.get(position).getShowName());
    }

    @Override
    public int getItemCount() {
        return seasonList.size();
    }

    public class BottomSheetHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public BottomSheetHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.seasonHindiText);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (seasonList.get(getAdapterPosition()).getSeasons().contains("m3u8")){
                    Intent intent = new Intent(itemView.getContext(),VideoPlayerActivity.class);
                    intent.putExtra("movie_videoLink",seasonList.get(getAdapterPosition()).getSeasons());
                    itemView.getContext().startActivity(intent);
//
                    }else {
                        Intent intent = new Intent(itemView.getContext(),WebView.class);
                        intent.putExtra("watchID",seasonList.get(getAdapterPosition()).getSeasons());
                        itemView.getContext().startActivity(intent);
                    }

                }
            });
        }
    }
}
