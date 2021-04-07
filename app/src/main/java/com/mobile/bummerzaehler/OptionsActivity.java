package com.mobile.bummerzaehler;




import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.mobile.bummerzaehler.bummerl.Bummerl2PController;
import com.mobile.bummerzaehler.bummerl.Bummerl3PController;
import com.mobile.bummerzaehler.bummerl.Bummerl4PController;
import com.mobile.bummerzaehler.bummerl.BummerlController;
import com.mobile.bummerzaehler.helper.HelperClass;
import com.mobile.bummerzaehler.player.PlayerController;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RadioButton;

public class OptionsActivity extends ParentActivity {
	private SharedPreferences preferences;
	private CheckBox cbIsOverLockscreen, cbIsNotification;
	private RadioButton rbAfterGame, rbAfterRound;
	public static String OPTIONS_IS_OVER_LOCKSCREEN = "options_key_is_over_lockscreen";
	public static String OPTIONS_IS_NOTIFICATION = "options_key_is_notification";
	public static String OPTIONS_IS_AFTER_FULL_GAME = "options_key_is_after_full_game";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(ViewTyp.OPTIONS); 
        super.onCreate(savedInstanceState);
        
        cbIsOverLockscreen = (CheckBox) findViewById(R.id.cbIsOverLockscreen);
        cbIsNotification = (CheckBox) findViewById(R.id.cbIsNotification);
        rbAfterGame = (RadioButton) findViewById(R.id.rbAfterFullGame);
        rbAfterRound = (RadioButton) findViewById(R.id.rbAfterRound);
        
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		
		boolean isOverLockscreen = HelperClass.isBooleanPrefKeyActivated(preferences, OPTIONS_IS_OVER_LOCKSCREEN, true);
		cbIsOverLockscreen.setChecked(isOverLockscreen);
		
		boolean isNotification = HelperClass.isBooleanPrefKeyActivated(preferences, OPTIONS_IS_NOTIFICATION, true);
		cbIsNotification.setChecked(isNotification);
		
		boolean isGeberAfterFullGame = HelperClass.isBooleanPrefKeyActivated(preferences, OPTIONS_IS_AFTER_FULL_GAME, true);
		rbAfterGame.setChecked(isGeberAfterFullGame);
		rbAfterRound.setChecked(!isGeberAfterFullGame);
		
		cbIsOverLockscreen.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				updateIsOverLockScreen(cbIsOverLockscreen.isChecked());
			}
		});
		
		cbIsNotification.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				updateIsNotification(cbIsNotification.isChecked());
			}
		});
		OnClickListener ocl = new OnClickListener() {
			@Override
			public void onClick(View v) {
				updateGeberOrder(rbAfterGame.isChecked());
			}
		};
		rbAfterGame.setOnClickListener(ocl);
		rbAfterRound.setOnClickListener(ocl);
    }
    private void updateIsOverLockScreen(Boolean isChecked)
    {
    	Editor editor = preferences.edit();
        editor.putString(OPTIONS_IS_OVER_LOCKSCREEN, isChecked.toString());
        editor.commit();
    }
    private void updateIsNotification(Boolean isChecked)
    {
    	Editor editor = preferences.edit();
        editor.putString(OPTIONS_IS_NOTIFICATION, isChecked.toString());
        editor.commit();
    }
    private void updateGeberOrder(Boolean isAfterFullGame)
    {
    	Editor editor = preferences.edit();
        editor.putString(OPTIONS_IS_AFTER_FULL_GAME, isAfterFullGame.toString());
        editor.commit();
    }
    
    public void onReset2(View v)
    {
    	Bummerl2PController bc = new Bummerl2PController(this);
    	showYesNoReset(bc, "Bummerzähler fürs zwara Schnopsn wurde zurückgesetzt!");
    }
    public void onReset3(View v)
    {
    	Bummerl3PController bc = new Bummerl3PController(this);
    	showYesNoReset(bc, "Bummerzähler fürs dreier Schnopsn wurde zurückgesetzt!");
    }
    public void onReset4(View v)
    {
    	Bummerl4PController bc = new Bummerl4PController(this);
    	showYesNoReset(bc, "Bummerzähler fürs vierer Schnopsn wurde zurückgesetzt!");
    }
    private void showYesNoReset(final BummerlController bc,final String toast)
	{
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        switch (which){
		        case DialogInterface.BUTTON_POSITIVE:
		            bc.resetBummerls();
		        	HelperClass.showShortToast(toast, OptionsActivity.this);
		            break;

		        case DialogInterface.BUTTON_NEGATIVE:
		            //No button clicked
		            break;
		        }
		    }
		};
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Wollen Sie den Bummerlzähler wirklich zurücksetzen?").setPositiveButton("Sia", dialogClickListener)
		    .setNegativeButton("Na", dialogClickListener).show();
		
	}
	private static final int READ_REQUEST_CODE = 42;
	private static final int WRITE_REQUEST_CODE = 43;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
		if (resultCode != Activity.RESULT_OK || resultData == null) {
			return;
		}
		switch(requestCode) {
			case READ_REQUEST_CODE: {
				Uri uri = resultData.getData();
				readRaw(uri);
				break;
			}
			case WRITE_REQUEST_CODE: {
				Uri uri = resultData.getData();
				writeToSDFile(uri);
				break;
			}
		}
	}

	public void onImport(View v)
    {
    	// Source: http://android-doc.github.io/guide/topics/providers/document-provider.html
		Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("*/*");
		startActivityForResult(intent, READ_REQUEST_CODE);
    }
    public void onExport(View v)
    {
		Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_TITLE, "Bummerlzähler Backup.txt");
		startActivityForResult(intent, WRITE_REQUEST_CODE);
    }

  	private void writeToSDFile(Uri uri){

	   final SharedPreferences preferences;
			preferences = PreferenceManager.getDefaultSharedPreferences(this);
			String b2P = preferences.getString(Bummerl2PController.BUMMERL, "");
			String b3P = preferences.getString(Bummerl3PController.BUMMERL, "");
			String b4P = preferences.getString(Bummerl4PController.BUMMERL, "");
			String players = preferences.getString(PlayerController.PLAYERS, "");

      try {
		  ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "w");
		  FileOutputStream f = new FileOutputStream(parcelFileDescriptor.getFileDescriptor());
          PrintWriter pw = new PrintWriter(f);
          pw.println(players);
          pw.println(b2P);
          pw.println(b3P);
          pw.println(b4P);
          pw.flush();
          pw.close();
          f.close();
		  parcelFileDescriptor.close();
		  int plC = players.length() == 0 ? 0 : players.split(";").length;
		  int b2PC = b2P.length() == 0 ? 0 : b2P.split(";").length;
		  int b3PC = b3P.length() == 0 ? 0 : b3P.split(";").length;
		  int b4PC = b4P.length() == 0 ? 0 : b4P.split(";").length;
		  HelperClass.showLongToast("Es wurden "+plC+" Spieler, "+b2PC+" Zwara Spiele, "+b3PC+" Dreier Spiele, "+b4PC+" Vierer Spiele gesichert!", this);
      } catch (IOException e) {
          e.printStackTrace();
		  HelperClass.showLongToast("Beim Speichern ist folgender Fehler aufgetreten: " + e.getMessage(), this);
      }
  }

  /** Method to read in a text file placed in the res/raw directory of the application. The
    method reads in all lines of the file sequentially. */

  private void readRaw(Uri uri)  {

	  try {
		  InputStream inputStream = getContentResolver().openInputStream(uri);
		  BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
	      String line;
	      int i = 0;
			String players = ""; 
	      String b2P = "";
			String b3P = "";
			String b4P = "";
	      while ((line = br.readLine()) != null) {
	    	  switch(i)
	    	  {
	    	  case 0: players = line; break;
	    	  case 1: b2P = line; break;
	    	  case 2: b3P = line; break;
	    	  case 3: b4P = line; break;
	    	  }
	          i++;
	      }
	      br.close();
		  inputStream.close();
	      final String pl = players;

	      final String b2 = b2P;
	      final String b3 = b3P;
	      final String b4 = b4P;
	      

	      int plC = players.length() == 0 ? 0 : players.split(";").length;
	      int b2PC = b2P.length() == 0 ? 0 : b2P.split(";").length;
	      int b3PC = b3P.length() == 0 ? 0 : b3P.split(";").length;
	      int b4PC = b4P.length() == 0 ? 0 : b4P.split(";").length;
	      final String playerText = plC+" Spieler, "+b2PC+" Zwara Spiele, "+b3PC+" Dreier Spiele, "+b4PC+" Vierer Spiele";

	    	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			        switch (which){
			        case DialogInterface.BUTTON_POSITIVE:
			        	Editor editor = preferences.edit();
			  	      editor.putString(PlayerController.PLAYERS, pl);
			  	      editor.putString(Bummerl2PController.BUMMERL, b2);
			  	      editor.putString(Bummerl3PController.BUMMERL, b3);
			  	      editor.putString(Bummerl4PController.BUMMERL, b4);
			  	        editor.commit();
			  	        

			  	      HelperClass.showLongToast("Es wurden "+playerText+ " importiert!", OptionsActivity.this);
			            break;

			        case DialogInterface.BUTTON_NEGATIVE:
			            //No button clicked
			            break;
			        }
			    }
			};
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Wollen Sie wirklich "+playerText + " importieren?").setPositiveButton("Sia", dialogClickListener)
			    .setNegativeButton("Na", dialogClickListener).show();
	      
	      
	      
	  }
	  catch (IOException e) {
	      //You'll need to add proper error handling here
		  e.printStackTrace();
	  }
	  System.out.println("\n\nThat is all");
  }
}
