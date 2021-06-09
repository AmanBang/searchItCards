package com.animasium.searchitcards.Movie.mAdapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.animasium.searchitcards.TVshows.Show;
import com.animasium.searchitcards.R;
import com.animasium.searchitcards.TVshows.ShowDetails;
import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShowAdapter extends RecyclerView.Adapter<ShowAdapter.ShowHolder> {

    LayoutInflater SInflator;
    List<Show> showList;
    Dialog dialog;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button cancle;
    Button Add;
    String switchText = "Pending";

    public ShowAdapter(Context SInflator, List<Show> showList) {
        this.SInflator = LayoutInflater.from(SInflator);
        this.showList = showList;
        dialog = new Dialog(SInflator);


        dialog.setContentView(R.layout.custom_favourites_dialogbox);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            @SuppressLint("UseCompatLoadingForDrawables") Drawable drawable = SInflator.getResources().getDrawable(R.drawable.background);
            dialog.getWindow().setBackgroundDrawable(drawable);
        }

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
        radioGroup = dialog.findViewById(R.id.radioGroup);


        cancle = dialog.findViewById(R.id.dialog_cancelBtn);
        Add = dialog.findViewById(R.id.dialog_addBtn);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.RB_pending:
                        switchText = "";
                        switchText = "Pending";
                        break;
                    case R.id.RB_ongoing:
                        switchText = "";
                        switchText = "Ongoing";
                        break;
                    case R.id.RB_watched:
                        switchText = "";
                        switchText = "Watched";
                        break;
                }
            }
        });


    }

    @NonNull
    @Override
    public ShowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = SInflator.inflate(R.layout.custom_movies_card, parent, false);
        return new ShowHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ShowHolder holder, int position) {
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + showList.get(position).getPoster_path()).into(holder.poster);
        holder.Title.setText(showList.get(position).getName());


        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseQuery<ParseUser> parseQuery = new ParseQuery<ParseUser>("Show");

                parseQuery.whereMatches("showID", showList.get(position).getId());
                parseQuery.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> objects, ParseException e) {
//                        Log.i("objestparesd", objects + "");
//                        Log.i("objestparesd:", switchText);

                        if (objects.isEmpty()) {
                            ParseObject tvShowFav = new ParseObject("Show");
                            tvShowFav.put("showID", showList.get(position).getId());
                            tvShowFav.put("type", switchText);
                            tvShowFav.setACL(new ParseACL(ParseUser.getCurrentUser()));
                            tvShowFav.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        FancyToast.makeText(v.getContext(), "ADDED to Favourites", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();

                                    } else {
                                        FancyToast.makeText(v.getContext(), "Could Not Add to Favourites:" + e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.INFO, true).show();
                                    }
                                }
                            });
                        } else {
                            FancyToast.makeText(v.getContext(), "Already add in your favourite", FancyToast.LENGTH_SHORT, FancyToast.INFO, true).show();

                        }
                        dialog.dismiss();
                    }
                });

            }

        });

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
                    i.putExtra("show_id", showList.get(getAdapterPosition()).getId());
                    v.getContext().startActivity(i);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    dialog.show();
                    return true;
                }
            });

            Title = itemView.findViewById(R.id.cardMovieTitle);
            poster = itemView.findViewById(R.id.moviePoster);
        }
    }


}
