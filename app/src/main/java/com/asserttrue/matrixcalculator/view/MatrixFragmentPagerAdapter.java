package com.asserttrue.matrixcalculator.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.asserttrue.matrixcalculator.view.computationsTab.ComputationsFragment;

import com.asserttrue.matrixcalculator.view.storedMatricesTab.StoredMatricesFragment;

public class MatrixFragmentPagerAdapter extends FragmentPagerAdapter {
    public MatrixFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return ComputationsFragment.getInstance();
            case 1: return StoredMatricesFragment.getInstance();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "Calculator";
            case 1: return "Matrices";
            default: return null;
        }
    }
}
