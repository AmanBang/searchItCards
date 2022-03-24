package com.animasium.searchitcards.Movie.mAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Slide;

import com.animasium.searchitcards.Movie.DeatailsMovie;
import com.animasium.searchitcards.R;
import com.animasium.searchitcards.RecommendedMovies;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FM_adapter extends RecyclerView.Adapter<FM_adapter.FM_ViewHolder> {

    LayoutInflater MInflator;
    List<RecommendedMovies> Slist;

    public FM_adapter(Context MInflator,List<RecommendedMovies> slist){
        this.MInflator = LayoutInflater.from(MInflator);
        Slist = slist;

    }


    @NonNull
    @Override
    public FM_adapter.FM_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("ResourceType") View view = MInflator.inflate(R.layout.custom_anime_recommendation,parent,false);
        return new FM_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FM_adapter.FM_ViewHolder holder, int position) {
        Picasso.get().load("https://image.tmdb.org/t/p/w500"+Slist.get(position).getPoster_path()).into(holder.animePoster);
        holder.animeTitle.setText(Slist.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return Slist.size();
    }


    public class FM_ViewHolder extends RecyclerView.ViewHolder {

        TextView animeTitle;
        ImageView animePoster;
        public FM_ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), DeatailsMovie.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("pass_id", String.valueOf(Slist.get(getAdapterPosition()).getId()));
                    v.getContext().startActivity(i);

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showBottomSheet(Slist.get(getAdapterPosition()).getId());
                    return true;
                }
            });

//

            animeTitle = itemView.findViewById(R.id.anime_r_title);
            animePoster = itemView.findViewById(R.id.anime_r_poster);
        }

        private void showBottomSheet(String id) {


            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(itemView.getContext());
            bottomSheetDialog.setContentView(R.layout.fav_bottom_sheet);

            LinearLayout Remove = bottomSheetDialog.findViewById(R.id.remove_option);
            LinearLayout Watched = bottomSheetDialog.findViewById(R.id.watched_option);
            LinearLayout Pending = bottomSheetDialog.findViewById(R.id.pending_option);

            Remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();

                    ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Movies");
                    parseQuery.whereEqualTo("showID",id);
                    parseQuery.getFirstInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            try {
                                object.delete();
                                object.saveInBackground();
                                bottomSheetDialog.dismiss();
                                bottomSheetDialog.setDismissWithAnimation(true);
                                Slist.remove(getAdapterPosition());
                                FM_adapter.this.notifyDataSetChanged();
                                Toast.makeText(itemView.getContext(), "Operation success", Toast.LENGTH_SHORT).show();
                            } catch (ParseException parseException) {
                                parseException.printStackTrace();
                            }
                        }
                    });


                }
            });

            bottomSheetDialog.show();


            bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                }
            });
        }

    }
}
