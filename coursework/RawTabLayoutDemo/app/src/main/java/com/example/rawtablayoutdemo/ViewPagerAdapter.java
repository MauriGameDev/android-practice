package com.example.rawtablayoutdemo;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull MainActivity activity) {
        super(activity);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new Frag1();
            case 1: return new Frag2();
            case 2: return new Frag3();
            default: throw new IllegalArgumentException(
                    "Invalid page position: " + position);
        }
    }
    @Override
    public int getItemCount() {
        return 3;
    }
}