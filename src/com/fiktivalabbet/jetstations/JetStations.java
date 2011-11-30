package com.fiktivalabbet.jetstations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class JetStations extends MapActivity {
    
	private MapView mp;
	private MapController mc;
	private Drawable mDrawable;
	private List<Overlay> mOverlays;
	private JetOverlay mJetStations;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mp = (MapView) findViewById(R.id.mapview);
        
        mc = mp.getController();
        mc.setCenter(new GeoPoint((int)(62.754726*1E6), (int)(16.391602*1E6)));
        
        mp.setBuiltInZoomControls(true);
        
        mOverlays = mp.getOverlays();
        mDrawable = this.getResources().getDrawable(R.drawable.jet_pin);
        mJetStations = new JetOverlay(mDrawable, this);
        
        try
		{
			addOverlays();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //Temp
    }

	private void addOverlays() throws IOException
	{
		InputStream is = this.getResources().openRawResource(R.raw.stations);
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "iso8859-15"));
		String line;
		String[] values;
		GeoPoint p;
		OverlayItem oi;
		while((line = br.readLine()) != null)
		{
			values = line.split(",");
			p = new GeoPoint((int)(Double.parseDouble(values[1])*1E6), (int)(Double.parseDouble(values[0])*1E6));
			oi = new OverlayItem(p, values[2].replace("\"", ""), values[3].replace("\"", ""));
			mJetStations.addOverlay(oi);
		}
		mOverlays.add(mJetStations);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		super.onCreateOptionsMenu(menu);
		MenuInflater mi = getMenuInflater();
		mi.inflate(R.menu.toggle_map, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId())
		{
		case R.id.map:
			mp.setSatellite(false);
			return true;
		case R.id.satillite:
			mp.setSatellite(true);
			return true;
		}
		return false;
	}
	
	@Override
	protected boolean isRouteDisplayed()
	{
		// TODO Auto-generated method stub
		return false;
	}
}