package com.mobile.bummerzaehler;




import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.mobile.bummerzaehler.bummerl.Bummerl2PController;
import com.mobile.bummerzaehler.bummerl.Bummerl3PController;
import com.mobile.bummerzaehler.bummerl.Bummerl4PController;
import com.mobile.bummerzaehler.bummerl.BummerlController;
import com.mobile.bummerzaehler.helper.HelperClass;
import com.mobile.bummerzaehler.player.Player;
import com.mobile.bummerzaehler.player.PlayerController;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
    public void onImport(View v)
    {
    	readRaw();	
    }
    public void onExport(View v)
    {
    checkExternalMedia();
    writeToSDFile();
    }
    
    private void checkExternalMedia(){
        boolean mExternalStorageAvailable = false;
      boolean mExternalStorageWriteable = false;
      String state = Environment.getExternalStorageState();

      if (Environment.MEDIA_MOUNTED.equals(state)) {
          // Can read and write the media
          mExternalStorageAvailable = mExternalStorageWriteable = true;
      } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
          // Can only read the media
          mExternalStorageAvailable = true;
          mExternalStorageWriteable = false;
      } else {
          // Can't read or write
          mExternalStorageAvailable = mExternalStorageWriteable = false;
      }   
      System.out.println("options.check:\nExternal Media: readable="
              +mExternalStorageAvailable+" writable="+mExternalStorageWriteable);
  }

  /** Method to write ascii text characters to file on SD card. Note that you must add a 
     WRITE_EXTERNAL_STORAGE permission to the manifest file or this method will throw
     a FileNotFound Exception because you won't have write permission. */

    
    private File getBackupDir()
    {
    	File root = android.os.Environment.getExternalStorageDirectory(); 
		String path = root.getAbsolutePath() + "/Bummerlzaehler";
      System.out.println("options.getBackupDir: External file system root: "+root);

      // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder
      return new File (path);
    }
    private static String BACKUP_NAME = "bummerlzaehler.txt";
  private void writeToSDFile(){

	   final SharedPreferences preferences;
			preferences = PreferenceManager.getDefaultSharedPreferences(this);
			String b2P = preferences.getString(Bummerl2PController.BUMMERL, "");
			String b3P = preferences.getString(Bummerl3PController.BUMMERL, "");
			String b4P = preferences.getString(Bummerl4PController.BUMMERL, "");
			String players = preferences.getString(PlayerController.PLAYERS, "");
     

      // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder
      File dir = getBackupDir();
      dir.mkdirs();

      // initiate media scan and put the new things into the path array to
      // make the scanner aware of the location and the files you want to see
     // MediaScannerConnection.scanFile(this, new String[] {path + "bummerlzaehler.txt"}, null, null);
         MediaScannerConnection.scanFile(this, new String[] { dir.getAbsolutePath() }, null, null);
         sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(dir)));
      File file = new File(dir, BACKUP_NAME);

      try {
          FileOutputStream f = new FileOutputStream(file);
          PrintWriter pw = new PrintWriter(f);
          pw.println(players);
          pw.println(b2P);
          pw.println(b3P);
          pw.println(b4P);
          pw.flush();
          pw.close();
          f.close();

          // initiate media scan and put the new things into the path array to
          // make the scanner aware of the location and the files you want to see
         // MediaScannerConnection.scanFile(this, new String[] {path + "bummerlzaehler.txt"}, null, null);
             MediaScannerConnection.scanFile(this, new String[] { file.getAbsolutePath() }, null, null);
             


      } catch (FileNotFoundException e) {
          e.printStackTrace();
          Log.i("Options.write","******* File not found. Did you" +
                  " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
      } catch (IOException e) {
          e.printStackTrace();
      }   
      int plC = players.length() == 0 ? 0 : players.split(";").length;
      int b2PC = b2P.length() == 0 ? 0 : b2P.split(";").length;
      int b3PC = b3P.length() == 0 ? 0 : b3P.split(";").length;
      int b4PC = b4P.length() == 0 ? 0 : b4P.split(";").length;
      HelperClass.showLongToast("Es wurden "+plC+" Spieler, "+b2PC+" Zwara Spiele, "+b3PC+" Dreier Spiele, "+b4PC+" Vierer Spiele in der Datei "+file+" gesichert!", this);
  }

  /** Method to read in a text file placed in the res/raw directory of the application. The
    method reads in all lines of the file sequentially. */

  private void readRaw(){
	  
	  System.out.println("\nData read from res/raw/textfile.txt:");
	  File dir = getBackupDir();
	//Get the text file
	  File file = new File(dir,BACKUP_NAME);

	  if(file.exists())
	  {

	  try {
	      BufferedReader br = new BufferedReader(new FileReader(file));
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
	  }
	  }
	  else
	  {
		  HelperClass.showLongToast("Die Sicherungsdatei "+file.getAbsolutePath() + " ist nicht vorhanden!\nWurde noch keine Sicherung erstellt?", this);
	  }
      System.out.println("\n\nThat is all");
  }
}
