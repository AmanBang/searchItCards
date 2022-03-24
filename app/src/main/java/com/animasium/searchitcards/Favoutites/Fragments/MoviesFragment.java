package com.animasium.searchitcards.Favoutites.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.animasium.searchitcards.Movie.mAdapter.FM_adapter;
import com.animasium.searchitcards.R;
import com.animasium.searchitcards.RecommendedMovies;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //========================================================================================================================================================//

    List<RecommendedMovies> showRecivedList;
    List<RecommendedMovies> showWatched;
    //    ShowRAdapter showRAdapter;
    FM_adapter fm_adapter;
    RecyclerView pendingRecycleView;
    RecyclerView watchedRecycleView;
    ImageView emptyview;
    ProgressBar progressBar;

    public MoviesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Analitics.
     */
    // TODO: Rename and change types and number of parameters
    public static MoviesFragment newInstance(String param1, String param2) {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        pendingRecycleView = view.findViewById(R.id.MF_pending_recycleView);
        emptyview = view.findViewById(R.id.movie_empty);
        progressBar = view.findViewById(R.id.movie_progressbar);
//        watchedRecycleView = view.findViewById(R.id.MF_watched_recycleView);
        showRecivedList = new ArrayList<>();
        showWatched = new ArrayList<>();

progressBar.setVisibility(View.VISIBLE);
        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Movies");

//        parseQuery.whereMatches("type","Pending");
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (objects.size() > 0) {
                    for (ParseObject parseObject : objects) {
                        Log.i("recivedObjects", parseObject.get("showID") + "");

                        RecommendedMovies showReciver = new RecommendedMovies();
                        showReciver.setId(parseObject.get("showID") + "");
                        showReciver.setPoster_path(parseObject.get("posterPath") + "");
                        showReciver.setTitle(parseObject.get("showName") + "");

                        showRecivedList.add(showReciver);
                        try {
                            progressBar.setVisibility(View.GONE);
                            LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false);
                            pendingRecycleView.setLayoutManager(linearLayoutManager);
//                    pendingRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                            fm_adapter = new FM_adapter(getContext(), showRecivedList);
                            fm_adapter.notifyDataSetChanged();
                            pendingRecycleView.setAdapter(fm_adapter);
                        } catch (Exception z) {
                            z.printStackTrace();
                        }
                    }
                }else {
                    emptyview.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }

            }
        });

//        parseQuery.whereMatches("type","Watched");
//        parseQuery.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                for (ParseObject parseObject : objects){
//                    Log.i("recivedObjects", parseObject.get("showID") +"");
//
//                    RecommendedMovies showReciver = new RecommendedMovies();
//                    showReciver.setId(parseObject.get("showID")+"");
//                    showReciver.setPoster_path(parseObject.get("posterPath")+"");
//                    showReciver.setTitle(parseObject.get("showName")+"");
//
//                    showWatched.add(showReciver);
//                    watchedRecycleView.setLayoutManager(new GridLayoutManager(getContext(),3));
////                    pendingRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//                    fm_adapter = new FM_adapter(getContext(), showWatched);
//                    watchedRecycleView.setAdapter(fm_adapter);
//                }
//            }
//        });

        return view;
    }
}