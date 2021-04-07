package com.mobile.bummerzaehler;



import com.mobile.bummerzaehler.bummerl.AllBummerls3P;
import com.mobile.bummerzaehler.bummerl.Bummerl3PController;
import com.mobile.bummerzaehler.helper.HelperClass;
import com.mobile.bummerzaehler.oldgame.OldGame3P;
import com.mobile.bummerzaehler.oldgame.OldGames3PController;
import com.mobile.bummerzaehler.player.Player;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.core.app.NotificationCompat;

import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ThreePlayerActivity extends ParentActivity {

	TransitionDrawable transitionP1,transitionP2, transitionP3;
    View gameOver;
	boolean isGameOver = false;
	boolean[] activePlayers = {false, false, false};
	int pointsP1 = 0;
	int pointsP2 = 0;
	int pointsP3 = 0;
	int oldPoints = 0;
	ScrollView svMain;

	int maxPoints;
	int currentWinner = -1;
	int marginDp;
	boolean isFourPlayers;
	String pl1, pl2, pl3, shortP1, shortP2, shortP3;
	TextView tvP1, tvP2,tvP3, tvCurrentWinner, tvCurrentLoser, tvFullPointsP1, tvFullPointsP2, tvFullPointsP3, tvGameOver, 
	tvDouble, tvNormal, tvMultiplier, tvGameOverBummerl, tvGameOverRevert;
	Button bt1, bt2, bt3, bt5, bt6, bt9, bt10, bt12, bt18, bt24;
	LinearLayout llP2Btn, llP4Btn;
	ValueAnimator varl;
	View geber1, geber2, geber3, ll1, ll2, ll3;
	int geber = 1;
	int bummerlGeber = 1;

	int multiPlier = 1;
	Bummerl3PController bController;
	OldGames3PController oController;
	TextView tvBummerlP1, tvSchneiderP1, tvBummerlP2, tvSchneiderP2, tvBummerlP3, tvSchneiderP3;
	private NotificationCompat.Builder notificationBuilder;
	private int uniqueNotificationId;
	private View ivBummerl, ivSchneider;
	@Override
	protected void onResume()
	{
		boolean isOverLockscreen = HelperClass.isBooleanPrefKeyActivated(PreferenceManager.getDefaultSharedPreferences(this),OptionsActivity.OPTIONS_IS_OVER_LOCKSCREEN, true);
    	
		if(isOverLockscreen)
		{
    	   getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		}
		else
		{
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
	                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
	                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
	                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		}
		bController = new Bummerl3PController(this);
        updateBummerlTextViews();
        updateNotification();
		super.onResume();
	}
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		uniqueNotificationId = HelperClass.getUniqueNotificationId();  	
        Intent intent = getIntent();
        if(intent.getStringExtra(ThreePlayerStartActivity.PLAYER_NAMES).isEmpty())
        {
            super.onCreate(savedInstanceState);
        	finish();
        	return;
        }
        String[] players = intent.getStringExtra(ThreePlayerStartActivity.PLAYER_NAMES).split(";");
        isFourPlayers = players.length == 4;
        setContentView(ViewTyp.THREE_PLAYER); 
        super.onCreate(savedInstanceState);
        ivBummerl = findViewById(R.id.ivThreePlayerBummerlGameOver);
        ivSchneider = findViewById(R.id.ivThreePlayerSchneiderGameOver);
        TextView tv1 = (TextView) findViewById(R.id.tvThreePlayerP1);
        TextView tv2 = (TextView) findViewById(R.id.tvThreePlayerP2);
        TextView tv3 = (TextView) findViewById(R.id.tvThreePlayerP3);
        tvP1 = (TextView) findViewById(R.id.tvThreePlayerPointsP1);
        tvP2 = (TextView) findViewById(R.id.tvThreePlayerPointsP2);
        tvP3 = (TextView) findViewById(R.id.tvThreePlayerPointsP3);
        tvFullPointsP1 = (TextView) findViewById(R.id.tvThreePlayerFullPointsP1);
        tvFullPointsP2 = (TextView) findViewById(R.id.tvThreePlayerFullPointsP2);
        tvFullPointsP3 = (TextView) findViewById(R.id.tvThreePlayerFullPointsP3);
        svMain = (ScrollView) findViewById(R.id.svThreePlayerMain);
        
        bt1 = (Button) findViewById(R.id.btThreePlayer1);
        bt2 = (Button) findViewById(R.id.btThreePlayer2);
        bt3 = (Button) findViewById(R.id.btThreePlayer3);
        bt5 = (Button) findViewById(R.id.btThreePlayer5);
        bt6 = (Button) findViewById(R.id.btThreePlayer6);
        bt9 = (Button) findViewById(R.id.btThreePlayer9);
        bt10 = (Button) findViewById(R.id.btThreePlayer10);
        bt12 = (Button) findViewById(R.id.btThreePlayer12);
        bt18 = (Button) findViewById(R.id.btThreePlayer18);
        bt24 = (Button) findViewById(R.id.btThreePlayer24);
        
        tvDouble = (TextView) findViewById(R.id.tvThreePlayerDouble);
        tvNormal = (TextView) findViewById(R.id.tvThreePlayerNormal);
        tvMultiplier = (TextView) findViewById(R.id.tvThreePlayerMultiplier);
        tvGameOver = (TextView) findViewById(R.id.tvThreePlayerGameOverName);
        tvGameOverBummerl = (TextView) findViewById(R.id.tvThreePlayerGameOverBummerl);
        tvGameOverRevert = (TextView) findViewById(R.id.tvThreePlayerGameOverRevert);
        gameOver =  findViewById(R.id.llThreePlayerGameOver);
        
        ll1 = findViewById(R.id.llThreePlayerP1);
        ll2 = findViewById(R.id.llThreePlayerP2);
        ll3 = findViewById(R.id.llThreePlayerP3);
        
        transitionP1 = (TransitionDrawable) ll1.getBackground();
        transitionP2 = (TransitionDrawable) ll2.getBackground();
        transitionP3 = (TransitionDrawable) ll3.getBackground();
               
        geber1 = findViewById(R.id.tvThreePlayerGeberP1);
        geber2 = findViewById(R.id.tvThreePlayerGeberP2);
        geber3 = findViewById(R.id.tvThreePlayerGeberP3);
        

        
        tvBummerlP1 = (TextView) findViewById(R.id.tvThreePlayerBummerlP1);
        tvSchneiderP1 = (TextView) findViewById(R.id.tvThreePlayerSchneiderP1);
        tvBummerlP2 = (TextView) findViewById(R.id.tvThreePlayerBummerlP2);
        tvSchneiderP2 = (TextView) findViewById(R.id.tvThreePlayerSchneiderP2);
        tvBummerlP3 = (TextView) findViewById(R.id.tvThreePlayerBummerlP3);
        tvSchneiderP3 = (TextView) findViewById(R.id.tvThreePlayerSchneiderP3);
        
        geber1.setVisibility(View.VISIBLE);
        
        tvGameOverRevert.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onRevertGameOver();
			}
        });
        ll1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onP1();
			}
        });
        ll2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onP2();
			}
        });
        ll3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onP3();
			}
        });
        tvDouble.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onDouble();
			}
        });
        tvNormal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onNormal();
			}
        });
        if(players[0].equals("---"))
    	{
    		pl1 = "Spieler 1";
    		shortP1 = "SP1";
    	}
    	else
    	{
    		pl1 = players[0];
    		shortP1 = pl1.substring(0,3);
    	}
    	if(players[1].equals("---"))
    	{
    		pl2 = "Spieler 2";
    		shortP2 = "SP2";
    	}
    	else
    	{
    		pl2 = players[1];
    		shortP2 = pl2.substring(0,3);
    	}
    	if(players[2].equals("---"))
    	{
    		pl3 = "Spieler 3";
    		shortP3 = "SP3";
    	}
    	else
    	{
    		pl3 = players[2];
    		shortP3 = pl3.substring(0,3);
    	}
           tv1.setText(pl1);
           tv2.setText(pl2);
           tv3.setText(pl3);
           marginDp = 170;
           maxPoints = 24;
        initAnimator();
        notificationBuilder = HelperClass.createNotificationManager(this, new Intent(this, ThreePlayerActivity.class));

		oController = new OldGames3PController(this);
		OldGame3P og = oController.getOldGameByTeamCombination(new Player(pl1), new Player(pl2), new Player(pl3));
		if(og != null)
		{
			tvP1.setText(og.getPointsP1());
			tvP2.setText(og.getPointsP2());
			tvP3.setText(og.getPointsP3());
			pointsP1 = getLastNumber(og.getPointsP1().split("\n"));
			pointsP2 = getLastNumber(og.getPointsP2().split("\n"));
			pointsP3 = getLastNumber(og.getPointsP3().split("\n"));
			int oldGeber = 1;
			String geberName = og.getGeber();
			if(geberName.equals(pl1))
				oldGeber = 1;
			else if(geberName.equals(pl2))
				oldGeber = 2;
			else if(geberName.equals(pl3))
				oldGeber = 3;
			geber = oldGeber;
			setGeberVisibility(oldGeber);

			int oldBummerlGeber = 1;
			String bummerlGeberName = og.getBummerlGeber();
			if(bummerlGeberName.equals(pl1))
				oldBummerlGeber = 1;
			else if(bummerlGeberName.equals(pl2))
				oldBummerlGeber = 2;
			else if(bummerlGeberName.equals(pl3))
				oldBummerlGeber = 3;
			bummerlGeber = oldBummerlGeber;

		}
    }
 private void updateNotification() {
    	
    	if(HelperClass.isBooleanPrefKeyActivated(PreferenceManager.getDefaultSharedPreferences(this), OptionsActivity.OPTIONS_IS_NOTIFICATION, true))
    		HelperClass.notifyUser(notificationBuilder, uniqueNotificationId, this, shortP1+" "+pointsP1+" - "+shortP2+" "+pointsP2+" - "+shortP3+" "+pointsP3);
    	else
        	HelperClass.removeNotification(uniqueNotificationId, this);
    		
	}
 @Override
 protected void onDestroy()
 {
 	HelperClass.removeNotification(uniqueNotificationId, this);
 	super.onDestroy();
 }
 
    @Override
    public void onBackPressed() {
    	if(!isGameOver)
		{
			if(pointsP1 >0 || pointsP2 > 0|| pointsP3 > 0)
				onRevert();
			else
				HelperClass.showYesNoCloseGame(this);
		}	
    	else
    	{
    		if(gameOver.getVisibility() == View.VISIBLE)
     			gameOver.setVisibility(View.GONE);
     		else
     		   finish();
    	}
    }

    private int activePlayerCount()
    {
    	int c = 0;
    	for(boolean isActive : activePlayers)
    	{
    		if(isActive)
    			c++;
    	}
    	return c;
    }
    
    private void changeActivePlayer(TransitionDrawable trMain,
    		TransitionDrawable trCounter1,
    		TransitionDrawable trCounter2,
    		int posMain,
    		int posCounter1,
    		int posCounter2)
    {
    	int actPlayers =activePlayerCount(); 
    	if(actPlayers == 0)
    	{
    		trMain.startTransition(500);
        	activePlayers[posMain] = true;
    	}
    	else if(actPlayers == 1)
    	{
    		if(activePlayers[posMain])
    			{
    			trMain.reverseTransition(500);
    			activePlayers[posMain] = false;
    			if(activePlayerCount() == 0)
    				closePoints(true);
    			}
    		else
    		{
    			trMain.startTransition(500);
    			activePlayers[posMain] = true;
    		}
    	}
    	else if(actPlayers == 2)
    	{
    		if(activePlayers[posMain])
			{
    			trMain.reverseTransition(500);
			activePlayers[posMain] = false;
			}
    		else
    		{
    			trCounter1.reverseTransition(500);
    			trCounter2.reverseTransition(500);
    			trMain.startTransition(500);
    			activePlayers[posMain] = true;
    			activePlayers[posCounter1] = false;
    			activePlayers[posCounter2] = false;
    		}
    	}
    }
    
    private void onP1()
    {
    	if(!isGameOver)
    	{
    	showPoints();
    	changeActivePlayer(transitionP1, transitionP2, transitionP3, 0, 1, 2);
    	}
    }
    
    private void onP2()
    {
    	if(!isGameOver)
    	{
    		showPoints();
        	changeActivePlayer(transitionP2, transitionP1, transitionP3, 1, 0, 2);
    	}
    }
    
    private void onP3()
    {
    	if(!isGameOver)
    	{
    		showPoints();
        	changeActivePlayer(transitionP3, transitionP2, transitionP1, 2, 1, 0);
    	}
    }
    
    private boolean isInteger(String text)
    {
    	return text.matches("^-?[0-9]+(\\.[0-9]+)?$");
    }
    
    private void setGeberVisibility(int visibleGeber)
    {
    	switch(visibleGeber)
		{
		case 1: 
			geber1.setVisibility(View.VISIBLE);
			geber2.setVisibility(View.INVISIBLE);
			geber3.setVisibility(View.INVISIBLE);
			break;
		case 2: 
			geber1.setVisibility(View.INVISIBLE);
			geber2.setVisibility(View.VISIBLE);
			geber3.setVisibility(View.INVISIBLE);
			break;
		case 3: 
			geber1.setVisibility(View.INVISIBLE);
			geber2.setVisibility(View.INVISIBLE);
			geber3.setVisibility(View.VISIBLE);
			break;
		}
    }
    private void updateBummerlTextViews()
    {
    	AllBummerls3P ab = bController.getAllBummerlsByPlayerCombination(new Player(pl1), new Player(pl2), new Player(pl3));
		int p1Schneider = 0;
		int p1Bummerl = 0;
		int p2Schneider = 0;
		int p2Bummerl = 0;
		int p3Schneider = 0;
		int p3Bummerl = 0;
    	if(ab != null)
    	{
    		if(ab.getP1().getName().equals(pl1)  
		    		&& ab.getP2().getName().equals(pl2)
		    		&& ab.getP3().getName().equals(pl3))
		    	{
		    		p1Bummerl = ab.getBummerlP1();
		    		p2Bummerl = ab.getBummerlP2();
		    		p3Bummerl = ab.getBummerlP3();
		    		p1Schneider = ab.getSchneiderP1();
		    		p2Schneider = ab.getSchneiderP2();
		    		p3Schneider = ab.getSchneiderP3();
		    	}
		    	else if(ab.getP1().getName().equals(pl1)  
			    		&& ab.getP3().getName().equals(pl2)
			    		&& ab.getP2().getName().equals(pl3))
		    	{
		    		p1Bummerl = ab.getBummerlP1();
		    		p2Bummerl = ab.getBummerlP3();
		    		p3Bummerl = ab.getBummerlP2();
		    		p1Schneider = ab.getSchneiderP1();
		    		p2Schneider = ab.getSchneiderP3();
		    		p3Schneider = ab.getSchneiderP2();
		    	}
		    	else if(ab.getP2().getName().equals(pl1)  
			    		&& ab.getP1().getName().equals(pl2)
			    		&& ab.getP3().getName().equals(pl3))
		    	{
		    		p1Bummerl = ab.getBummerlP2();
		    		p2Bummerl = ab.getBummerlP1();
		    		p3Bummerl = ab.getBummerlP3();
		    		p1Schneider = ab.getSchneiderP2();
		    		p2Schneider = ab.getSchneiderP1();
		    		p3Schneider = ab.getSchneiderP3();
		    	}
		    	else if(ab.getP2().getName().equals(pl1)  
			    		&& ab.getP3().getName().equals(pl2)
			    		&& ab.getP1().getName().equals(pl3))
		    	{
		    		p1Bummerl = ab.getBummerlP2();
		    		p2Bummerl = ab.getBummerlP3();
		    		p3Bummerl = ab.getBummerlP1();
		    		p1Schneider = ab.getSchneiderP2();
		    		p2Schneider = ab.getSchneiderP3();
		    		p3Schneider = ab.getSchneiderP1();
		    	}
		    	else if(ab.getP3().getName().equals(pl1)  
			    		&& ab.getP1().getName().equals(pl2)
			    		&& ab.getP2().getName().equals(pl3))
		    	{
		    		p1Bummerl = ab.getBummerlP3();
		    		p2Bummerl = ab.getBummerlP1();
		    		p3Bummerl = ab.getBummerlP2();
		    		p1Schneider = ab.getSchneiderP3();
		    		p2Schneider = ab.getSchneiderP1();
		    		p3Schneider = ab.getSchneiderP2();
		    	}
		    	else if(ab.getP3().getName().equals(pl1)  
			    		&& ab.getP2().getName().equals(pl2)
			    		&& ab.getP1().getName().equals(pl3))
		    	{
		    		p1Bummerl = ab.getBummerlP3();
		    		p2Bummerl = ab.getBummerlP2();
		    		p3Bummerl = ab.getBummerlP1();
		    		p1Schneider = ab.getSchneiderP3();
		    		p2Schneider = ab.getSchneiderP2();
		    		p3Schneider = ab.getSchneiderP1();
		    	}
    	}
    	tvBummerlP1.setText(p1Bummerl+" * ");
    	tvSchneiderP1.setText(p1Schneider+" * ");
    	tvBummerlP2.setText(p2Bummerl+" * ");
    	tvSchneiderP2.setText(p2Schneider+" * ");
    	tvBummerlP3.setText(p3Bummerl+" * ");
    	tvSchneiderP3.setText(p3Schneider+" * ");
    }
    
    Player player1,player2,player3;
    boolean isP1Loser;
	boolean isP2Loser;
	boolean isP3Loser;
	boolean isP1Schneider;
	boolean isP2Schneider;
	boolean isP3Schneider;
	boolean isP1Bummerl;
	boolean isP2Bummerl;
	boolean isP3Bummerl;
    private void updatePoints(int points)
    {
    	
    	if(!isGameOver)
    	{
    		   points *= multiPlier;
    		
    		setNextGeber();
        	setGeberVisibility(geber);
    		
    		oldPoints = points;
    		boolean[] winners = {false, false, false};
    	//Klick nach gewonnenem spiel irgendwo --> endscreen erscheint wieder
    	

    		String p1 = tvP1.getText().toString();
    		String p2 = tvP2.getText().toString();
    		String p3 = tvP3.getText().toString();
    		if(activePlayers[0])
        	{
        		pointsP1 += points;
        		if(p1.isEmpty())
        			tvP1.setText(pointsP1+"");
        		else
        			tvP1.setText(p1 + "\n" + pointsP1);
        		
        		if(pointsP1 >= maxPoints)
        			winners[0] = true;
        	}
        	else
        	{
        		if(p1.isEmpty())
        			tvP1.setText("-");
        		else
        			tvP1.setText(p1 + "\n-");
        	}
    		
    		if(activePlayers[1])
        	{
        		pointsP2 += points;
        		if(p2.isEmpty())
        			tvP2.setText(pointsP2+"");
        		else
        			tvP2.setText(p2 + "\n" + pointsP2);
        		
        		if(pointsP2 >= maxPoints)
        			winners[1] = true;
        	}
        	else
        	{
        		if(p2.isEmpty())
        			tvP2.setText("-");
        		else
        			tvP2.setText(p2 + "\n-");
        	}
    		
    		if(activePlayers[2])
        	{
        		pointsP3 += points;
        		if(p3.isEmpty())
        			tvP3.setText(pointsP3+"");
        		else
        			tvP3.setText(p3 + "\n" + pointsP3);
        		
        		if(pointsP3 >= maxPoints)
        			winners[2] = true;
        	}
        	else
        	{
        		if(p3.isEmpty())
        			tvP3.setText("-");
        		else
        			tvP3.setText(p3 + "\n-");
        	}
    		updateFullPoints();
    		updateNotification();
        	scrollToBottom();
    	
    		String winText = "";
    		
    		int c = 0;
        	for(boolean isWinner : winners)
        	{
        		if(isWinner)
        			c++;
        	}
        	
        	
        	
        	if(c == 0)
        		return;

    		 isP1Loser = !winners[0];
    		 isP2Loser = !winners[1];
    		 isP3Loser = !winners[2];
    		 isP1Schneider = pointsP1 == 0;
    		 isP2Schneider = pointsP2 == 0;
    		 isP3Schneider = pointsP3 == 0;
    		 isP1Bummerl = isP1Loser && pointsP1 != 0;
    		 isP2Bummerl = isP2Loser && pointsP2 != 0;
    		 isP3Bummerl = isP3Loser && pointsP3 != 0;
    		 player1 = new Player(pl1);
    		 player2 = new Player(pl2);
    		 player3 = new Player(pl3);
    		
    	    bController.updateBummerls(player1, player2, player3, isP1Loser, isP2Loser, isP3Loser, isP1Schneider, isP2Schneider, isP3Schneider, true);
    	    boolean isSchneider = isP1Schneider || isP2Schneider || isP3Schneider;
    	    boolean isBummerl =  isP1Bummerl || isP2Bummerl || isP3Bummerl;
    	    if(isSchneider)
    	    	ivSchneider.setVisibility(View.VISIBLE);
    	    else
    	    	ivSchneider.setVisibility(View.GONE);
    	    
    	    if(isBummerl)
    	    	ivBummerl.setVisibility(View.VISIBLE);
    	    else
    	    	ivBummerl.setVisibility(View.GONE);
    	    
    		updateBummerlTextViews();
        	if(c == 1)
        	{
        		String winner = "";
        		String loser = "";
        		if(winners[0])
        			{
        			winner = pl1;
        			if(isP2Schneider)
        				loser += pl2 + " bekommt einen Schneider \n";
        			else
        				loser += pl2 + " bekommt ein Bummerl \n";
        			if(isP3Schneider)
        				loser += pl3 + " bekommt einen Schneider";
        			else
        				loser += pl3 + " bekommt ein Bummerl";
        			}
        		else if(winners[1])
        		{
        			winner = pl2;
        			if(isP1Schneider)
        				loser += pl1 + " bekommt einen Schneider \n";
        			else
        				loser += pl1 + " bekommt ein Bummerl \n";
        			if(isP3Schneider)
        				loser += pl3 + " bekommt einen Schneider";
        			else
        				loser += pl3 + " bekommt ein Bummerl";
        			}
        		else if(winners[2])
        		{
        			winner = pl3;
        			if(isP1Schneider)
        				loser += pl1 + " bekommt einen Schneider \n";
        			else
        				loser += pl1 + " bekommt ein Bummerl \n";
        			if(isP2Schneider)
        				loser += pl2 + " bekommt einen Schneider";
        			else
        				loser += pl2 + " bekommt ein Bummerl";
        			}
        		tvGameOverBummerl.setText(loser);
        		tvGameOver.setText(winner + " hat gewonnen!");
        	    gameOver.setVisibility(View.VISIBLE);
        	    closePoints(true);
        	    isGameOver = true;
        	}
        	else if(c == 2)
        	{
        		String winner = "";
        		if(winners[0])
        			{
        			winner += pl1 + " und ";
        			if(winners[1])
            			winner += pl2;
            		else if(winners[2])
            			winner += pl3;
        			}
        		else if(winners[1])
        			{
        			winner += pl2 + " und ";
        			if(winners[2])
            			winner += pl3;
        			}
        		
        		
        		String loser = "";
        		if(!winners[0])
        			if(isP1Schneider)
        				loser += pl1 + " bekommt einen Schneider \n";
        			else
        				loser += pl1 + " bekommt ein Bummerl \n";
        		else if(!winners[1])
        			if(isP2Schneider)
        				loser += pl2 + " bekommt einen Schneider \n";
        			else
        				loser += pl2 + " bekommt ein Bummerl \n";
        		else if(!winners[2])
        			if(isP3Schneider)
        				loser += pl3 + " bekommt einen Schneider \n";
        			else
        				loser += pl3 + " bekommt ein Bummerl \n";
        		
        		tvGameOver.setText(winner + " haben gewonnen!"); 
        		tvGameOverBummerl.setText(loser);
        	    gameOver.setVisibility(View.VISIBLE);
        	    closePoints(true);
        	    isGameOver = true;
        	}
    	}
    }
    
    private void onRevertGameOver()
    {
    	gameOver.setVisibility(View.GONE);
    	bController.updateBummerls(player1, player2, player3, isP1Loser, isP2Loser, isP3Loser, isP1Schneider, isP2Schneider, isP3Schneider, false);
    	onRevert();
    	isGameOver = false;
    	updateBummerlTextViews();
    }
    
    private void scrollToBottom()
    {
    	svMain.post(new Runnable() {

			@Override
			public void run() {
	    		svMain.fullScroll(ScrollView.FOCUS_DOWN);
				
			}
			
		});
    }
   /* @Override
   public boolean onTouchEvent(MotionEvent me)
   {
    	if(isGameOver)
    	{
    		if(gameOver.getVisibility() == View.VISIBLE)
    			gameOver.setVisibility(View.GONE);
    		else
    			gameOver.setVisibility(View.VISIBLE);
    	}
	   return super.onTouchEvent(me);
   }*/
    
    public void onScreen(View v)
    {
     	if(isGameOver)
     	{
     		if(gameOver.getVisibility() == View.VISIBLE)
     			gameOver.setVisibility(View.GONE);
     		else
     			gameOver.setVisibility(View.VISIBLE);
     	}
    }
    
    private int getLastNumber(String[] arr)
    {
    	for(int i= arr.length -1; i >= 0; i--)
    	{
    		if(isInteger(arr[i]))
    			return Integer.parseInt(arr[i]);
    	}
    	return 0;
    }
    
    private void revertNextGeber()
    {
    	switch(geber)
		{
		case 1: 
			geber = 3;
			break;
		case 2: 
			geber = 1;
			break;
		case 3: 
			geber = 2;
			break;
		}
    }
    private void setNextGeber()
    {
    	switch(geber)
		{
		case 1: 
			geber = 2;
			break;
		case 2: 
			geber = 3;
			break;
		case 3: 
			geber = 1;
			break;
		}
    }
    
    private void updateFullPoints()
    {
		tvFullPointsP1.setText(pointsP1+"");
		tvFullPointsP2.setText(pointsP2+"");
		tvFullPointsP3.setText(pointsP3+"");
    }
    
    private void onRevert()
    {
    	revertNextGeber();
    	setGeberVisibility(geber);
    	
    String p1 = tvP1.getText().toString();
    String p2 = tvP2.getText().toString();
    String p3 = tvP3.getText().toString();
    String[] p1Points = p1.split("\n");
    String[] p2Points = p2.split("\n");
    String[] p3Points = p3.split("\n");
    
    
    int p1L = p1Points.length;
    int p2L = p2Points.length;
    int p3L = p3Points.length;
    
    	p1Points[p1L-1] = "";
    	pointsP1 = getLastNumber(p1Points);
    	
    	p2Points[p2L-1] = "";
    	pointsP2 = getLastNumber(p2Points);
    	
    	p3Points[p3L-1] = "";
    	pointsP3 = getLastNumber(p3Points);

		if(p1Points.length > 1) {
			p1 = "";
			p2 = "";
			p3 = "";
			for (int i = 0; i < p1Points.length - 1; i++) {
				if (i == p1Points.length - 2)
					p1 += p1Points[i];
				else
					p1 += p1Points[i] + "\n";
			}
			for (int i = 0; i < p2Points.length - 1; i++) {
				if (i == p2Points.length - 2)
					p2 += p2Points[i];
				else
					p2 += p2Points[i] + "\n";
			}
			for (int i = 0; i < p3Points.length - 1; i++) {
				if (i == p3Points.length - 2)
					p3 += p3Points[i];
				else
					p3 += p3Points[i] + "\n";
			}
			tvP1.setText(p1);
			tvP2.setText(p2);
			tvP3.setText(p3);
		}
	else
	{
		tvP1.setText("");
		tvP2.setText("");
		tvP3.setText("");
		tvP1.postInvalidate();
	}
	updateFullPoints();
	updateNotification();
	scrollToBottom();
    }
    
    private void updateButtonValue()
    {
    	tvMultiplier.setText(multiPlier+"x");
    	bt1.setText(1*multiPlier+"");
    	bt2.setText(2*multiPlier+"");
    	bt3.setText(3*multiPlier+"");
    	bt5.setText(5*multiPlier+"");
    	bt6.setText(6*multiPlier+"");
    	bt9.setText(9*multiPlier+"");
    	bt10.setText(10*multiPlier+"");
    	bt12.setText(12*multiPlier+"");
    	bt18.setText(18*multiPlier+"");
    	bt24.setText(24*multiPlier+"");
    }
    private void onDouble()
    {
    	if(multiPlier > 16)
		return;
    	multiPlier*=2;
    	
    	updateButtonValue();
    	tvDouble.setText("Zruckgspritzt!!");
    	if(multiPlier >= 4)
    	tvDouble.setAlpha(1.0f);
    	tvNormal.setAlpha(0.4f);
    }
    
    private void onNormal()
    {
    	multiPlier=1;
    	updateButtonValue();
		tvDouble.setText("Gspritzt is!");
    	tvDouble.setAlpha(0.4f);
    	tvNormal.setAlpha(1f);
    }
    
    public void onRevanche(View v)
    {
    	gameOver.setVisibility(View.GONE);
        isGameOver = false;
    	pointsP1 = 0;
    	pointsP2 = 0;
    	pointsP3 = 0;
    	boolean[] pl = {false, false, false};
    	activePlayers = pl;
    	tvP1.setText("");
    	tvP2.setText("");
    	tvP3.setText("");
    	boolean isGeberAfterFullGame = HelperClass.isBooleanPrefKeyActivated(PreferenceManager.getDefaultSharedPreferences(this),OptionsActivity.OPTIONS_IS_AFTER_FULL_GAME, true);
    	if(isGeberAfterFullGame)
    	{
    	switch(bummerlGeber)
    	{
    	case 1: bummerlGeber = 2; break;
    	case 2: bummerlGeber = 3; break;
    	case 3: bummerlGeber = 1; break;
    	}
    	geber = bummerlGeber;
    	}
    	setGeberVisibility(geber);
    	updateFullPoints();
    	updateNotification();
    }
    
    public void onRestart(View v)
    {
    	Intent nextIntent = new Intent(ThreePlayerActivity.this, MainActivity.class);
		nextIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(nextIntent);
		overridePendingTransition(R.anim.right_to_left,
				R.anim.noanimation);
    }
    
    public void onPoint1(View v)
    {
    	updatePoints(1);
    }
    public void onPoint2(View v)
    {
    	updatePoints(2);
    }
    public void onPoint3(View v)
    {
    	updatePoints(3);
    }
    public void onPoint5(View v)
    {
    	updatePoints(5);
    }
    public void onPoint6(View v)
    {
    	updatePoints(6);
    }
    public void onPoint9(View v)
    {
    	updatePoints(9);
    }
    public void onPoint10(View v)
    {
    	updatePoints(10);
    }
    public void onPoint12(View v)
    {
    	updatePoints(12);
    }
    public void onPoint18(View v)
    {
    	updatePoints(18);
    }
    public void onPoint24(View v)
    {
    	updatePoints(24);
    }

    public void onVs(View v)
    {
    	closePoints(false);
    }
    public void onStatus(View v)
    {
    	closePoints(false);
    }
    public void onStatusOverview(View v)
    {
    	onStatus(v);
    	onScreen(v);
    }
    
    public void closePoints(boolean isReverseVarl)
    {
    	if(activePlayerCount() > 0)
    	{
    		if(activePlayers[0])
    			transitionP1.reverseTransition(500);
    		if(activePlayers[1])
    			transitionP2.reverseTransition(500);
    		if(activePlayers[2])
    			transitionP3.reverseTransition(500);

        	if(!isReverseVarl)
        		varl.reverse();
    	}
    	if(isReverseVarl)
    		varl.reverse();
    	boolean[] pl = {false, false, false};
    	activePlayers = pl;
    }
    
    private void initAnimator()
    {
    	float d = getResources().getDisplayMetrics().density;
    	final int newMargin = (int)(marginDp * d); // margin in pixels

    	final LinearLayout view = (LinearLayout) findViewById(R.id.llThreePlayerMain);
    	varl = ValueAnimator.ofInt(newMargin);
    	
    	varl.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();

				params.setMargins(0, (Integer) animation.getAnimatedValue(), 0, 0);
				view.setLayoutParams(params);
			}
		});
    	varl.setDuration(500);
    }
    
    private void showPoints()
    {
    	if(activePlayerCount() < 1)
    	{
    	varl.start();
    	}
    }
	private void saveGame()
	{
		String geberPlayer = "";
		String bummerlGeberPlayer = "";

		switch(geber)
		{
			case 1: geberPlayer = pl1; break;
			case 2: geberPlayer = pl2; break;
			case 3: geberPlayer = pl3; break;
		}
		switch(bummerlGeber)
		{
			case 1: bummerlGeberPlayer = pl1; break;
			case 2: bummerlGeberPlayer = pl2; break;
			case 3: bummerlGeberPlayer = pl3; break;
		}
		oController.addOldGame(new Player(pl1), new Player(pl2),new Player(pl3), tvP1.getText() + "", tvP2.getText() + "",tvP3.getText()+"", geberPlayer, bummerlGeberPlayer);
		HelperClass.showShortToast("Spiel wurde gespeichert!", this);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		if (item.getItemId() == R.id.action_save_game) {
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which){
						case DialogInterface.BUTTON_POSITIVE:
							saveGame();
							break;

						case DialogInterface.BUTTON_NEGATIVE:
							//No button clicked
							break;
						case DialogInterface.BUTTON_NEUTRAL:
							saveGame();
							finish();
							break;
					}
				}
			};

			AlertDialog.Builder builder = new AlertDialog.Builder(ThreePlayerActivity.this);
			builder.setMessage("Wollen Sie das Spiel speichern?").setPositiveButton("Sia", dialogClickListener)
					.setNegativeButton("Na", dialogClickListener).setNeutralButton("Speichern und Beenden", dialogClickListener).show();

		}

		return super.onOptionsItemSelected(item);
	}
}
