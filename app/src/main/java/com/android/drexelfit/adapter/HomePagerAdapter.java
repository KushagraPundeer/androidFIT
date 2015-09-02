package com.android.drexelfit.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.drexelfit.FragmentGoals;
import com.android.drexelfit.FragmentRewards;

public class HomePagerAdapter extends FragmentPagerAdapter
{
	private FragmentGoals mFragmentGoals;
	private FragmentRewards mFragmentRewards;
	
	public HomePagerAdapter(FragmentManager fm, FragmentGoals fg, FragmentRewards fr) 
	{
		super(fm);
		
		mFragmentGoals = fg;
		mFragmentRewards = fr;
	}

	@Override
	public Fragment getItem(int position) 
	{
		if (position == 0)
    	{
    		return mFragmentGoals;
    	}
    	else if (position == 1)
    	{
    		return mFragmentRewards;
    	}
    	else
    	{
    		return null;
    	}
	}

	@Override
	public int getCount() 
	{
		return 2;
	}
	
	@Override
	public CharSequence getPageTitle(int position)
	{
		return position == 0 ? "Goals" : "Rewards";
	}
}
