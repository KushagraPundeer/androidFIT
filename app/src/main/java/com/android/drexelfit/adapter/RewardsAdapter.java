package com.android.drexelfit.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.drexelfit.R;
import com.android.drexelfit.db.Reward;

public class RewardsAdapter extends BaseAdapter
{
	private ArrayList<Reward> mRewards;
	private LayoutInflater mInflator;
	
	public RewardsAdapter (ArrayList<Reward> rewards, Context context)
	{
		mRewards = rewards;
		mInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() 
	{
		return mRewards.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return mRewards.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		Reward reward = mRewards.get(position);
		
		convertView = mInflator.inflate(R.layout.list_item_rewards, parent, false);
		((TextView) convertView.findViewById(R.id.txt_title)).setText(reward.getName());
		((TextView) convertView.findViewById(R.id.txt_calories)).setText("calories cost: " + reward.getCost());
		((TextView) convertView.findViewById(R.id.txt_meal_type)).setText("offered during: " + reward.getMeal());
		((TextView) convertView.findViewById(R.id.txt_day)).setText("redeemable on: " + reward.getDay());
		((TextView) convertView.findViewById(R.id.txt_redeemed)).setVisibility(reward.getWasRedeemed() ? View.VISIBLE : View.GONE);
		return convertView;
	}
}
