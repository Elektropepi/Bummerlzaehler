package com.mobile.bummerzaehler;



import com.mobile.bummerzaehler.bummerl.AllBummerls2P;
import com.mobile.bummerzaehler.bummerl.AllBummerls4P;
import com.mobile.bummerzaehler.bummerl.Bummerl2PController;
import com.mobile.bummerzaehler.bummerl.Bummerl4PController;
import com.mobile.bummerzaehler.helper.HelperClass;
import com.mobile.bummerzaehler.oldgame.OldGame4P;
import com.mobile.bummerzaehler.oldgame.OldGames4PController;
import com.mobile.bummerzaehler.player.Player;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class FourPlayerActivity extends ParentActivity {

	TransitionDrawable transitionP1,transitionP2;
    View gameOver;
	boolean isGameOver = false;
	int pointsP1 = 0;
	int pointsP2 = 0;
	int oldPoints = 0;
	int maxPoints;
	int currentWinner = -1;
	int activePlayer = -1;
	int marginDp;
	boolean isFourPlayers;
	String p1, p2, pn1, pn2, pn3, pn4, winText, shortP1,shortP2,shortP3,shortP4;
	TextView tvP1, tvP2, tvCurrentWinner, tvCurrentLoser, tvFullPointsP1,tvFullPointsP2, tvGameOver, tvDouble, 
	tvNormal, tvMultiplier, tvGameOverBummerl, tvGameOverRevert;
	Button bt1, bt2, bt3, bt5, bt6, bt9, bt10, bt12, bt18, bt24;
	LinearLayout llP2Btn, llP4Btn;
	ValueAnimator varl;
	View geber1, geber2, geber3, geber4, ll1, ll2;
	int geber = 1;
	int bummerlGeber = 1;
	ScrollView svMain;
	
	int multiPlier = 1;
	LinearLayout llBummerlP1, llBummerlP2;
	
	Bummerl4PController bController;
	OldGames4PController oController;
	TextView tvBummerlT1, tvSchneiderT1, tvBummerlT2, tvSchneiderT2;
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
		bController = new Bummerl4PController(this);
        updateBummerlTextViews();
        updateNotification();
		super.onResume();
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		uniqueNotificationId = HelperClass.getUniqueNotificationId();
    	System.out.println("tpa.oncreate");
        Intent intent = getIntent();
        if(intent.getStringExtra(TwoPlayerStartActivity.PLAYER_NAMES).isEmpty())
        {
            super.onCreate(savedInstanceState);
        	finish();
        	return;
        }
        String[] players = intent.getStringExtra(TwoPlayerStartActivity.PLAYER_NAMES).split(";");
        isFourPlayers = players.length == 4;
        setContentView(ViewTyp.FOUR_PLAYER); 
        super.onCreate(savedInstanceState);
        ivBummerl = findViewById(R.id.ivFourPlayerBummerlGameOver);
        ivSchneider = findViewById(R.id.ivFourPlayerSchneiderGameOver);
        
        TextView tv1 = (TextView) findViewById(R.id.tvFourPlayerP1);
        TextView tv2 = (TextView) findViewById(R.id.tvFourPlayerP2);
        TextView tv3 = (TextView) findViewById(R.id.tvFourPlayerP3);
        TextView tv4 = (TextView) findViewById(R.id.tvFourPlayerP4);
        tvFullPointsP1 = (TextView) findViewById(R.id.tvFourPlayerFullPointsP1);
        tvFullPointsP2 = (TextView) findViewById(R.id.tvFourPlayerFullPointsP2);
        tvP1 = (TextView) findViewById(R.id.tvFourPlayerPointsP1);
        tvP2 = (TextView) findViewById(R.id.tvFourPlayerPointsP2);
        svMain = (ScrollView) findViewById(R.id.svFourPlayerMain);
        
        bt1 = (Button) findViewById(R.id.btFourPlayer1);
        bt2 = (Button) findViewById(R.id.btFourPlayer2);
        bt3 = (Button) findViewById(R.id.btFourPlayer3);
        bt5 = (Button) findViewById(R.id.btFourPlayer5);
        bt6 = (Button) findViewById(R.id.btFourPlayer6);
        bt9 = (Button) findViewById(R.id.btFourPlayer9);
        bt10 = (Button) findViewById(R.id.btFourPlayer10);
        bt12 = (Button) findViewById(R.id.btFourPlayer12);
        bt18 = (Button) findViewById(R.id.btFourPlayer18);
        bt24 = (Button) findViewById(R.id.btFourPlayer24);
        
        tvDouble = (TextView) findViewById(R.id.tvFourPlayerDouble);
        tvNormal = (TextView) findViewById(R.id.tvFourPlayerNormal);
        tvMultiplier = (TextView) findViewById(R.id.tvFourPlayerMultiplier);
        tvGameOver = (TextView) findViewById(R.id.tvFourPlayerGameOverName);
        tvGameOverBummerl = (TextView) findViewById(R.id.tvFourPlayerGameOverBummerl);
        tvGameOverRevert = (TextView) findViewById(R.id.tvFourPlayerGameOverRevert);
        gameOver =  findViewById(R.id.llFourPlayerGameOver);
        llP4Btn = (LinearLayout) findViewById(R.id.llFPFourPlayerPoints);
        ll1 = findViewById(R.id.llFourPlayerP1);
        ll2 = findViewById(R.id.llFourPlayerP2);
        
        transitionP1 = (TransitionDrawable) ll1.getBackground();
        transitionP2 = (TransitionDrawable) ll2.getBackground();
               
        geber1 = findViewById(R.id.tvFourPlayerGeberP1);
        geber3 = findViewById(R.id.tvFourPlayerGeberP2);
        geber2 = findViewById(R.id.tvFourPlayerGeberP3);
        geber4 = findViewById(R.id.tvFourPlayerGeberP4);
        
        tvBummerlT1 = (TextView) findViewById(R.id.tvFourPlayerBummerlT1);
        tvSchneiderT1 = (TextView) findViewById(R.id.tvFourPlayerSchneiderT1);
        tvBummerlT2 = (TextView) findViewById(R.id.tvFourPlayerBummerlT2);
        tvSchneiderT2 = (TextView) findViewById(R.id.tvFourPlayerSchneiderT2);
        
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
        
        	llP4Btn.setVisibility(View.VISIBLE);
        	if(players[0].equals("---"))
        	{
        		pn1 = "Spieler 1";
        		shortP1 = "SP1";
        	}
        	else
        	{
        		pn1 = players[0];
        		shortP1 = pn1.substring(0,3);
        	}
        	if(players[1].equals("---"))
        	{
        		pn2 = "Spieler 2";
        		shortP2 = "SP2";
        	}
        	else
        	{
        		pn2 = players[1];
        		shortP2 = pn2.substring(0,3);
        	}
        	if(players[2].equals("---"))
        	{
        		pn3 = "Spieler 3";
        		shortP3 = "SP3";
        	}
        	else
        	{
        		pn3 = players[2];
        		shortP3 = pn3.substring(0,3);
        	}
        	if(players[3].equals("---"))
        	{
        		pn4 = "Spieler 4";
        		shortP4 = "SP4";
        	}
        	else
        	{
        		pn4 = players[3];
        		shortP4 = pn4.substring(0,3);
        	}
        	tv1.setText(pn1);
            tv2.setText(pn2);
            tv3.setText(pn3);
            tv4.setText(pn4);
            marginDp = 170;
            maxPoints = 24;
            p1 = pn1+" und "+pn2;
            p2 = pn3+" und "+pn4;
            winText = " haben gewonnen!";
            setTitle("Vierer");
        initAnimator();
        notificationBuilder = HelperClass.createNotificationManager(this, new Intent(this, FourPlayerActivity.class));
		oController = new OldGames4PController(this);
		Player[] t1 = {new Player(pn1), new Player(pn2)};
		Player[] t2 = {new Player(pn3), new Player(pn4)};
		OldGame4P og = oController.getOldGameByTeamCombination(t1,t2);
		if(og != null)
		{
			tvP1.setText(og.getPointsT1());
			tvP2.setText(og.getPointsT2());
			pointsP1 = getLastNumber(og.getPointsT1().split("\n"));
			pointsP2 = getLastNumber(og.getPointsT2().split("\n"));
			int oldGeber = 1;
			String geberName = og.getGeber();
			if(geberName.equals(pn1))
				oldGeber = 1;
			else if(geberName.equals(pn2))
				oldGeber = 2;
			else if(geberName.equals(pn3))
				oldGeber = 3;
			else if(geberName.equals(pn4))
				oldGeber = 4;
			geber = oldGeber;
			setGeberVisibility(oldGeber);

			int oldBummerlGeber = 1;
			String bummerlGeberName = og.getBummerlGeber();
			if(bummerlGeberName.equals(pn1))
				oldBummerlGeber = 1;
			else if(bummerlGeberName.equals(pn2))
				oldBummerlGeber = 2;
			else if(bummerlGeberName.equals(pn3))
				oldBummerlGeber = 3;
			else if(bummerlGeberName.equals(pn4))
				oldBummerlGeber = 4;
			bummerlGeber = oldBummerlGeber;

		}
        
    }
    private void updateNotification() {
    	
    	if(HelperClass.isBooleanPrefKeyActivated(PreferenceManager.getDefaultSharedPreferences(this), OptionsActivity.OPTIONS_IS_NOTIFICATION, true))
    		HelperClass.notifyUser(notificationBuilder, uniqueNotificationId, this, shortP1+", "+shortP2+" "+pointsP1+" - "+shortP3+", "+shortP4+ " "+pointsP2);
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
			if(pointsP1 >0 || pointsP2 > 0)
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

    
    private void onP1()
    {
    	System.out.println("tpa.onP1 click");
    	if(!isGameOver)
    	{
    	showPoints();
    	if(activePlayer != 1)
    	{
    	transitionP1.startTransition(500);
    	if(activePlayer > 0)
    		transitionP2.reverseTransition(500);
    	
    	activePlayer = 1;
    	}
    	else
    	{
    	closePoints();
    	}
    	}
    }
    
    private void onP2()
    {
    	if(!isGameOver)
    	{
    	showPoints();
    	if(activePlayer != 2)
    	{
    	transitionP2.startTransition(500);
    	if(activePlayer > 0)
    		transitionP1.reverseTransition(500);
    	
    	activePlayer = 2;
    	}
    	else
    	{
    	closePoints();
    	}
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
			geber4.setVisibility(View.INVISIBLE);
			break;
		case 2: 
			geber1.setVisibility(View.INVISIBLE);
			geber2.setVisibility(View.VISIBLE);
			geber3.setVisibility(View.INVISIBLE);
			geber4.setVisibility(View.INVISIBLE);
			break;
		case 3: 
			geber1.setVisibility(View.INVISIBLE);
			geber2.setVisibility(View.INVISIBLE);
			geber3.setVisibility(View.VISIBLE);
			geber4.setVisibility(View.INVISIBLE);
			break;
		case 4: 
			geber1.setVisibility(View.INVISIBLE);
			geber2.setVisibility(View.INVISIBLE);
			geber3.setVisibility(View.INVISIBLE);
			geber4.setVisibility(View.VISIBLE);
			break;
		}
    }
    
    private void updateBummerlTextViews()
    {
    	Player[] t1 = {new Player(pn1), new Player(pn2)};
    	Player[] t2 = {new Player(pn3), new Player(pn4)};
    	AllBummerls4P ab = bController.getAllBummerlsByTeamCombination(t1, t2);
    	int bummerlP1 = 0;
    	int schneiderP1 = 0;
    	int bummerlP2 = 0;
    	int schneiderP2 = 0;
    	if(ab != null)
    	{
    		if(ab.getT1()[0].getName().equals(pn1) || ab.getT1()[1].getName().equals(pn1))
    		{
    			bummerlP1 = ab.getBummerlT1();
    			schneiderP1 = ab.getSchneiderT1();
    			bummerlP2 = ab.getBummerlT2();
    			schneiderP2 = ab.getSchneiderT2();
    		}
    		else
    		{
    			bummerlP2 = ab.getBummerlT1();
    			schneiderP2 = ab.getSchneiderT1();
    			bummerlP1 = ab.getBummerlT2();
    			schneiderP1 = ab.getSchneiderT2();
    		}
    	}
    	tvBummerlT1.setText(bummerlP1+" * ");
    	tvSchneiderT1.setText(schneiderP1+" * ");
    	tvBummerlT2.setText(bummerlP2+" * ");
    	tvSchneiderT2.setText(schneiderP2+" * ");
    }
    
    
    Player[] tWinnerAfterGO,tLoserAfterGO;
    boolean isSchneiderAfterGO = false;
    private void updatePoints(int points)
    {
    	
    	if(!isGameOver)
    	{
     		   points *= multiPlier;
    		switch(geber)
    		{
    		case 1: 
    			geber = 2;
    			break;
    		case 2: 
    			geber = 3;
    			break;
    		case 3: 
    			geber = 4;
    			break;
    		case 4: 
    			geber = 1;
    			break;
    		}
        	setGeberVisibility(geber);
    		
    		oldPoints = points;
    	//Klick nach gewonnenem spiel irgendwo --> endscreen erscheint wieder
    	
    	int winnerPoints = 0;
    	if(activePlayer == 1)
    	{
    		
    		tvCurrentWinner = tvP1;
    		tvCurrentLoser = tvP2;
    		pointsP1 += points;
    		winnerPoints = pointsP1;
    	}
    	else if(activePlayer == 2)
    	{
    		
    		tvCurrentLoser = tvP1;
    		tvCurrentWinner = tvP2;
    		pointsP2 += points;
    		winnerPoints = pointsP2;
    	}
    	currentWinner = activePlayer;
    	String winner = tvCurrentWinner.getText().toString();
    	String loser = tvCurrentLoser.getText().toString();
    	if(winner.isEmpty() && loser.isEmpty())
    	{
    		tvCurrentWinner.setText(winnerPoints+"");
        	tvCurrentLoser.setText("-");
    	}
    	else
    	{
    	tvCurrentWinner.setText(winner + "\n" + winnerPoints);
    	tvCurrentLoser.setText(loser + "\n-");
    	}
    	if(winnerPoints >= maxPoints)
    	{
        	Player[] t1 = {new Player(pn1), new Player(pn2)};
        	Player[] t2 = {new Player(pn3), new Player(pn4)};
    		if(activePlayer == 1)
            {tvGameOver.setText(p1+ winText);
            tLoserAfterGO = t2;
            tWinnerAfterGO = t1;
            
            if(pointsP2 == 0)
            {
            	isSchneiderAfterGO = true;
                tvGameOverBummerl.setText(pn3+ " und "+pn4+" bekommen einen Schneider");
            }
            else 
            {
            	isSchneiderAfterGO = false;
            	tvGameOverBummerl.setText(pn3+ " und "+pn4+" bekommen ein Bummerl");
            }
            }
		else
			{tvGameOver.setText(p2+ winText);
            tLoserAfterGO = t1;
            tWinnerAfterGO = t2;
			if(pointsP1 == 0)
            {
				isSchneiderAfterGO = true;
                tvGameOverBummerl.setText(pn1+ " und "+pn2+" bekommen einen Schneider");
            }
            else 
            {
            	isSchneiderAfterGO = false;
            	tvGameOverBummerl.setText(pn1+ " und "+pn2+" bekommen ein Bummerl");
            }
			}
    		if(pointsP1 == 0 || pointsP2 == 0)
    		{
    			ivSchneider.setVisibility(View.VISIBLE);
    			ivBummerl.setVisibility(View.GONE);
    		}
    		else
    		{
    			ivSchneider.setVisibility(View.GONE);
    			ivBummerl.setVisibility(View.VISIBLE);
    		}
        	bController.updateBummerls(tLoserAfterGO, tWinnerAfterGO,isSchneiderAfterGO,true);
    		updateBummerlTextViews();
    	    gameOver.setVisibility(View.VISIBLE);
    	    closePoints();
    	    isGameOver = true;
    	}
    	updateFullPoints();
    	updateNotification();
    	scrollToBottom();
    	}
    }
    
    private void onRevertGameOver()
    {
    	gameOver.setVisibility(View.GONE);
    	bController.updateBummerls(tLoserAfterGO, tWinnerAfterGO,isSchneiderAfterGO,false);
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
   public boolean onOptionsItemSelectedTouchEvent(MotionEvent me)
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
    	System.out.println("tpa.onscreen");
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
    
    private void updateFullPoints()
    {
		tvFullPointsP1.setText(pointsP1+"");
		tvFullPointsP2.setText(pointsP2+"");
    }
    
    private void onRevert()
    {
    	switch(geber)
		{
		case 1: 
			geber = 4;
			break;
		case 2: 
			geber = 1;
			break;
		case 3: 
			geber = 2;
			break;
		case 4: 
			geber = 3;
			break;
		}
    	setGeberVisibility(geber);
    String p1 = tvP1.getText().toString();
    String p2 = tvP2.getText().toString();
    String[] p1Points = p1.split("\n");
    String[] p2Points = p2.split("\n");
    int p1L = p1Points.length;
    int p2L = p2Points.length;
    String p1Last = p1Points[p1L -1];
    String p2Last = p2Points[p2L -1];
    if(isInteger(p1Last))
    	{
    	p1Points[p1L-1] = "";
    	pointsP1 = getLastNumber(p1Points);
    	}
    else if(isInteger(p2Last))
    	{
    	p2Points[p2L-1] = "";
    	pointsP2 = getLastNumber(p2Points);
    	}
    
    p1 = "";
    p2 = "";
		if(p1Points.length > 1) {
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
			tvP1.setText(p1);
			tvP2.setText(p2);
		}
		else
		{
			tvP1.setText("");
			tvP2.setText("");
			tvP1.postInvalidate();
			tvP2.postInvalidate();

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
    	bt12.setText(12 * multiPlier + "");
    	bt18.setText(18 * multiPlier + "");
    	bt24.setText(24 * multiPlier + "");
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
    	activePlayer = -1;
    	tvP1.setText("");
    	tvP2.setText("");
    	boolean isGeberAfterFullGame = HelperClass.isBooleanPrefKeyActivated(PreferenceManager.getDefaultSharedPreferences(this),OptionsActivity.OPTIONS_IS_AFTER_FULL_GAME, true);
    	if(isGeberAfterFullGame)
    	{
    	switch(bummerlGeber)
    	{
    	case 1: bummerlGeber = 2; break;
    	case 2: bummerlGeber = 3; break;
    	case 3: bummerlGeber = 4; break;
    	case 4: bummerlGeber = 1; break;
    	}
    	geber = bummerlGeber;
    	}
    	setGeberVisibility(geber);
    	updateFullPoints();
    	updateNotification();
    }
    
    public void onRestart(View v)
    {
    	Intent nextIntent = new Intent(FourPlayerActivity.this, MainActivity.class);
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
    	closePoints();
    }
    public void onStatus(View v)
    {
    	closePoints();
    }
    public void onStatusOverview(View v)
    {
    	onStatus(v);
    	onScreen(v);
    }
    
    public void closePoints()
    {
    	if(activePlayer > 0)
    	{
    		switch(activePlayer)
    		{
    		    case 1 : { transitionP1.reverseTransition(500); break; }
    		    case 2 : { transitionP2.reverseTransition(500); break; }
    		}
    		varl.reverse();
    	}
    	activePlayer = -1;
    }
    
    private void initAnimator()
    {
    	float d = getResources().getDisplayMetrics().density;
    	final int newMargin = (int)(marginDp * d); // margin in pixels

    	final LinearLayout view = (LinearLayout) findViewById(R.id.llFourPlayerMain);
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
    	if(activePlayer < 0)
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
			case 1: geberPlayer = pn1; break;
			case 2: geberPlayer = pn2; break;
			case 3: geberPlayer = pn3; break;
			case 4: geberPlayer = pn4; break;
		}
		switch(bummerlGeber)
		{
			case 1: bummerlGeberPlayer = pn1; break;
			case 2: bummerlGeberPlayer = pn2; break;
			case 3: bummerlGeberPlayer = pn3; break;
			case 4: bummerlGeberPlayer = pn4; break;
		}
		Player[] t1 = {new Player(pn1), new Player(pn2)};
		Player[] t2 = {new Player(pn3), new Player(pn4)};
		oController.addOldGame(t1,t2,tvP1.getText()+"", tvP2.getText()+"", geberPlayer, bummerlGeberPlayer);
		HelperClass.showShortToast("Spiel wurde gespeichert!",this);
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

			AlertDialog.Builder builder = new AlertDialog.Builder(FourPlayerActivity.this);
			builder.setMessage("Wollen Sie das Spiel speichern?").setPositiveButton("Sia", dialogClickListener)
					.setNegativeButton("Na", dialogClickListener).setNeutralButton("Speichern und Beenden", dialogClickListener).show();

		}

		return super.onOptionsItemSelected(item);
	}
    
}
