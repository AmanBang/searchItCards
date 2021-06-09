package com.animasium.searchitcards.Favoutites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.animasium.searchitcards.Favoutites.Fragments.AnimeFFragment;
import com.animasium.searchitcards.Favoutites.Fragments.MoviesFragment;
import com.animasium.searchitcards.Favoutites.Fragments.TVShowsFragment;

public class TabAdapter extends FragmentPagerAdapter {
    public TabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TVShowsFragment();

            case 1:
                return new MoviesFragment();
            case 2:
                return new AnimeFFragment();

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
                return "TVShows";
            case 1:
                return "Movies";
            case 2:
                return "Anime";
            default:
                return null;
        }
    }
}
