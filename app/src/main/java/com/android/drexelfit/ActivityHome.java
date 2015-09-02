package com.android.drexelfit;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.android.drexelfit.FragmentGoals.OnLoadCompleteListener;
import com.android.drexelfit.adapter.HomePagerAdapter;
import com.android.drexelfit.db.User;

public class ActivityHome extends ActionBarActivity 
{
	private HomePagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    
    private FragmentGoals mFragmentGoals;
    private FragmentRewards mFragmentRewards;
    
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.addTab(
                actionBar.newTab()
                        .setText("Goals")
                        .setTabListener(mTabListener));
		actionBar.addTab(
                actionBar.newTab()
                        .setText("Rewards")
                        .setTabListener(mTabListener));
		
		mFragmentGoals = new FragmentGoals();
		mFragmentGoals.setOnLoadCompleteListener(mOnGoalsLoadListener);
		
		mFragmentRewards = new FragmentRewards();
		
		mPagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), mFragmentGoals, mFragmentRewards);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(mOnPageChange);
	}
	
	@SuppressWarnings("deprecation")
	ActionBar.TabListener mTabListener = new ActionBar.TabListener() 
	{
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) 
        {
        	if (mViewPager != null) {
        		mViewPager.setCurrentItem(tab.getPosition());
        	}
        }

        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        }

        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        }
    };
    
    public OnLoadCompleteListener mOnGoalsLoadListener = new OnLoadCompleteListener()
    {
		@Override
		public void onLoaded(User user) 
		{
			mFragmentRewards.loadData(user);
		}
    };
    
	ViewPager.SimpleOnPageChangeListener mOnPageChange = new ViewPager.SimpleOnPageChangeListener()
	{
        @SuppressWarnings("deprecation")
		@Override
        public void onPageSelected(int position) 
        {
        	getSupportActionBar().setSelectedNavigationItem(position);
        	
        	mFragmentGoals.refreshLayout();
        }
    };
}
