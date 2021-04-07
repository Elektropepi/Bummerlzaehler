package com.mobile.bummerzaehler;




import com.mobile.bummerzaehler.helper.HelperClass;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ParentActivity {

	@Override
	protected void onResume() {
        HelperClass.removeAllNotifications(this);
		super.onResume();
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(ViewTyp.MAIN); 
        super.onCreate(savedInstanceState);
    }
    
    public void twoPlayersStart(View v)
    {
    	Intent nextIntent = null;
		nextIntent = new Intent(MainActivity.this, TwoPlayerStartActivity.class);
		nextIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(nextIntent);
		overridePendingTransition(R.anim.right_to_left,
				R.anim.noanimation);
    }
    public void threePlayersStart(View v)
    {
    	Intent nextIntent = null;
		nextIntent = new Intent(MainActivity.this, ThreePlayerStartActivity.class);
		nextIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(nextIntent);
		overridePendingTransition(R.anim.right_to_left,
				R.anim.noanimation);
    }
    public void fourPlayersStart(View v)
    {
    	Intent nextIntent = null;
		nextIntent = new Intent(MainActivity.this, FourPlayerStartActivity.class);
		nextIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(nextIntent);
		overridePendingTransition(R.anim.right_to_left,
				R.anim.noanimation);
    }
}
