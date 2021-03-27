package com.example.searchitcards.Favoutites.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.searchitcards.R;
import com.example.searchitcards.RecommendedMovies;
import com.example.searchitcards.TVshows.ShowDetails;
import com.example.searchitcards.TVshows.showAdapter.ShowRAdapter;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TVShowsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TVShowsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //====================================================================================================================================================================//

    List<RecommendedMovies> showRecivedList;
    ShowRAdapter showRAdapter;
    RecyclerView pendingRecycleView;





    //==============================================================                         ==========================================================================================//

    public void Recommend(String Urly) {

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, Urly, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);


                        Log.i("hjlj",showRecivedList.toString());

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(objectRequest);
    }

    //================================================================================================================================================================================//

    public TVShowsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TVShowsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TVShowsFragment newInstance(String param1, String param2) {
        TVShowsFragment fragment = new TVShowsFragment();
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
//============================================================================================================================================================================================================//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_t_v_shows, container, false);

        pendingRecycleView = view.findViewById(R.id.FV_pending_recycleView);
        showRecivedList = new ArrayList<>();

        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Show");

      parseQuery.whereMatches("type","Pending");

//        try {
//            parseQuery.get("showID");

       parseQuery.findInBackground(new FindCallback<ParseObject>() {
           @Override
           public void done(List<ParseObject> objects, ParseException e) {
               for (ParseObject parseObject : objects){
                   Log.i("recivedObjects", parseObject.get("showID") +"");

                   RecommendedMovies showReciver = new RecommendedMovies();
                   showReciver.setId(parseObject.get("showID")+"");
                   showReciver.setPoster_path(parseObject.get("posterPath")+"");
                   showReciver.setTitle(parseObject.get("showName")+"");

                   showRecivedList.add(showReciver);
                   pendingRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                   showRAdapter = new ShowRAdapter(getContext(), showRecivedList);
                   pendingRecycleView.setAdapter(showRAdapter);
               }
           }
       });


        return view;
    }
}