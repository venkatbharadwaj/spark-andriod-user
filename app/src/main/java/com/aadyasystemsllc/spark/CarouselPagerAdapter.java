package com.aadyasystemsllc.spark;

import android.util.Log;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class CarouselPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {

    public final static float BIG_SCALE = 0.9f;
    public final static float SMALL_SCALE = 0.5f;
    public final static float DIFF_SCALE = BIG_SCALE - SMALL_SCALE;
    private HomeActivity context;
    private FragmentManager fragmentManager;
    private float scale;

    private ArrayList<ParkingDetails> parkingDetailsArrayList;

    public CarouselPagerAdapter(HomeActivity context, FragmentManager fm) {
        super(fm);
        this.fragmentManager = fm;
        this.context = context;
    }

    public CarouselPagerAdapter(HomeActivity context, FragmentManager fragmentManager, ArrayList<ParkingDetails> parkingDetailsArrayList) {
        super(fragmentManager);
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.parkingDetailsArrayList = parkingDetailsArrayList;
    }

    @Override
    public Fragment getItem(int position) {
        // make the first pager bigger than others
        try {
            if (position == HomeActivity.FIRST_PAGE)
                scale = BIG_SCALE;
            else
                scale = SMALL_SCALE;

            position = position % HomeActivity.count;


            if (position == HomeActivity.FIRST_PAGE)
                scale = BIG_SCALE;
            else
                scale = SMALL_SCALE;

            position = position % HomeActivity.count;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ItemFragment.newInstance(context, position, scale, parkingDetailsArrayList);


    }

    @Override
    public int getCount() {
       /* int count = 0;
        try {
            count = HomeActivity.count * HomeActivity.LOOPS;
            //  count = AutomaticFragment.count * AutomaticFragment.LOOPS;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return count*/;
        return parkingDetailsArrayList.size();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        try {
            if (positionOffset >= 0f && positionOffset <= 1f) {
                CarouselLinearLayout cur = getRootView(position);
                CarouselLinearLayout next = getRootView(position + 1);

                cur.setScaleBoth(BIG_SCALE - DIFF_SCALE * positionOffset);
                next.setScaleBoth(SMALL_SCALE + DIFF_SCALE * positionOffset);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @SuppressWarnings("ConstantConditions")
    private CarouselLinearLayout getRootView(int position) {
        Log.d("position", "" + position);
        return (CarouselLinearLayout) fragmentManager.findFragmentByTag(this.getFragmentTag(position))
                .getView().findViewById(R.id.root_container);
    }

    private String getFragmentTag(int position) {
        return "android:switcher:" + HomeActivity.pager.getId() + ":" + position;
    }

}
