package com.infowebmentsolution.ghosh.clickforflick.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.infowebmentsolution.ghosh.clickforflick.Fragment.HomeFragment;
import com.infowebmentsolution.ghosh.clickforflick.Model.AllCategoryList;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStateAdapter {


    ArrayList<AllCategoryList> allCategoryListArrayList ;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {

        super(fragmentActivity);
    }
    @NonNull @Override public Fragment createFragment(int position) {
        return HomeFragment.newInstance(position);
    }
    public void addCategory(ArrayList<AllCategoryList> allCategoryListArrayList) {

        this.allCategoryListArrayList =allCategoryListArrayList;
    }

    @Override public int getItemCount() {

        return 5;
    }
}