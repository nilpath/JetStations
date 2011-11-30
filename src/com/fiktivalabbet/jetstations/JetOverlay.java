package com.fiktivalabbet.jetstations;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class JetOverlay extends ItemizedOverlay<OverlayItem>
{
	private ArrayList<OverlayItem> mStations = new ArrayList<OverlayItem>();
	private Context mCtx;
	
	public JetOverlay(Drawable defaultMarker)
	{
		super(boundCenterBottom(defaultMarker));
		// TODO Auto-generated constructor stub
	}
	
	public JetOverlay(Drawable defaultMarker, Context ctx)
	{
		super(boundCenterBottom(defaultMarker));
		mCtx = ctx;
	}

	public void addOverlay(OverlayItem station)
	{
		mStations.add(station);
		populate();
	}
	
	@Override
	protected boolean onTap(int index)
	{
		OverlayItem i = mStations.get(index);
		AlertDialog.Builder d = new AlertDialog.Builder(mCtx);
		d.setTitle(i.getTitle());
		d.setMessage(i.getSnippet());
		d.show();
		return true;
	}
	
	@Override
	protected OverlayItem createItem(int i)
	{
		return mStations.get(i);
	}

	@Override
	public int size()
	{
		return mStations.size();
	}

}
