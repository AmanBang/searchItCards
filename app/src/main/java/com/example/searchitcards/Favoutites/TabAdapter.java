package com.example.searchitcards.Favoutites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.searchitcards.Favoutites.Fragments.AnimeFFragment;
import com.example.searchitcards.Favoutites.Fragments.MoviesFragment;
import com.example.searchitcards.Favoutites.Fragments.TVShowsFragment;

public class TabAdapter extends FragmentPagerAdapter {
    public TabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
               AnimeFFragment animeFFragment = new AnimeFFragment();
                return animeFFragment;
            case 1:
                return new MoviesFragment();
            case 2:
                return new TVShowsFragment();

            default:
                return null;
        }


    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Anime";
            case 1:
                return "Movies";
            case 2:
                return "TVShows";
            default:
                return null;
        }
    }
}
