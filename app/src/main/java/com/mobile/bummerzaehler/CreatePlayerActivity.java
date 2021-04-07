package com.mobile.bummerzaehler;

import com.mobile.bummerzaehler.helper.HelperClass;
import com.mobile.bummerzaehler.player.Player;
import com.mobile.bummerzaehler.player.PlayerController;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreatePlayerActivity extends Activity {

	EditText etName;
	public static int RETURN_NEW_PLAYER = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_player);
        etName = (EditText)findViewById(R.id.etCreatePlayerName);
        
        etName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    createPlayer(null);
                    return true;
                }
                return false;
            }

        });
    }
    
    public void createPlayer(View v) {
    	
    	String name = etName.getText().toString();
        PlayerController pController = new PlayerController(this);
    	if(name.contains(";") ||name.contains(","))
    	{
    		HelperClass.showShortToast(getResources().getString(R.string.invalid_character), this);
    		return;
    	}
    	else if(name.isEmpty() || name == ""){
    		HelperClass.showShortToast(getResources().getString(R.string.empty_text), this);
    		return;
    	}
    	else if(pController.contains(name))
    	{
    		HelperClass.showShortToast(getResources().getString(R.string.player_exists), this);
    		return;
    	}
        pController.addPlayer(new Player(name));
        
        Intent data = new Intent();
        //---set the data to pass back---
        data.setData(Uri.parse(name));
          setResult(RESULT_OK, data);
        //---close the activity---
        finish();
    }
}
