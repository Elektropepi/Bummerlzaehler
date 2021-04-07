package com.mobile.bummerzaehler;



import com.mobile.bummerzaehler.bummerl.AllBummerls2P;
import com.mobile.bummerzaehler.bummerl.Bummerl2PController;
import com.mobile.bummerzaehler.helper.HelperClass;
import com.mobile.bummerzaehler.oldgame.OldGame2P;
import com.mobile.bummerzaehler.oldgame.OldGames2PController;
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
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class TwoPlayerActivity extends ParentActivity {

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
	String p1, p2, winText, shortP1, shortP2;
	TextView tvP1, tvP2, tvCurrentWinner, tvCurrentLoser, tvGameOver, tvGameOverBummerl, tvFullPointsP1, tvFullPointsP2,
	         tvGameOverRevert;
	LinearLayout llP2Btn;
	ValueAnimator varl;
	View geber1, geber2;
	int geber = 1;
	int bummerlGeber = 1;
	ScrollView svMain;
	
	Bummerl2PController bController;
	OldGames2PController oController;
	TextView tvBummerlP1, tvSchneiderP1, tvBummerlP2, tvSchneiderP2;
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
		bController = new Bummerl2PController(this);
        updateBummerlTextViews();
        updateNotification();
		super.onResume();
	}

	@Override
	protected void onPause()
	{

		super.onPause();
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
        setContentView(ViewTyp.TWO_PLAYER); 
        super.onCreate(savedInstanceState);
        ivBummerl = findViewById(R.id.ivTwoPlayerBummerlGameOver);
        ivSchneider = findViewById(R.id.ivTwoPlayerSchneiderGameOver);
        TextView tv1 = (TextView) findViewById(R.id.tvTwoPlayerP1);
        TextView tv2 = (TextView) findViewById(R.id.tvTwoPlayerP2);
        View ll1 = findViewById(R.id.llTwoPlayerP1);
        View ll2 = findViewById(R.id.llTwoPlayerP2);
        tvFullPointsP1 = (TextView) findViewById(R.id.tvTwoPlayerFullPointsP1);
        tvFullPointsP2 = (TextView) findViewById(R.id.tvTwoPlayerFullPointsP2);
        tvP1 = (TextView) findViewById(R.id.tvTwoPlayerPointsP1);
        tvP2 = (TextView) findViewById(R.id.tvTwoPlayerPointsP2);
        svMain = (ScrollView) findViewById(R.id.svTwoPlayerMain);
        tvGameOver = (TextView) findViewById(R.id.tvTwoPlayerGameOverName);
        tvGameOverBummerl = (TextView) findViewById(R.id.tvTwoPlayerGameOverBummerl);
        tvGameOverRevert = (TextView) findViewById(R.id.tvTwoPlayerGameOverRevert);
        gameOver =  findViewById(R.id.llTwoPlayerGameOver);
        llP2Btn = (LinearLayout) findViewById(R.id.llTwoPlayerPoints);
        transitionP1 = (TransitionDrawable) ll1.getBackground();
        transitionP2 = (TransitionDrawable) ll2.getBackground();
        geber1 = findViewById(R.id.tvTwoPlayerGeberP1);
        geber2 = findViewById(R.id.tvTwoPlayerGeberP2);
        geber1.setVisibility(View.VISIBLE);
        
        tvBummerlP1 = (TextView) findViewById(R.id.tvTwoPlayerBummerlP1);
        tvSchneiderP1 = (TextView) findViewById(R.id.tvTwoPlayerSchneiderP1);
        tvBummerlP2 = (TextView) findViewById(R.id.tvTwoPlayerBummerlP2);
        tvSchneiderP2 = (TextView) findViewById(R.id.tvTwoPlayerSchneiderP2);
        
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
        
        	llP2Btn.setVisibility(View.VISIBLE);
        	if(players[0].equals("---"))
        	{
        		p1 = "Spieler 1";
        		shortP1 = "SP1";
        	}
        	else
        	{
        		p1 = players[0];
        		shortP1 = HelperClass.getShortName(p1);
        	}
        	if(players[1].equals("---"))
        	{
        		p2 = "Spieler 2";
        		shortP2 = "SP2";
        	}
        	else
        	{
        		p2 = players[1];
        		shortP2 = HelperClass.getShortName(p2);
        	}
           tv1.setText(p1);
           tv2.setText(p2);
           marginDp = 75;
           maxPoints = 7;
           winText = " hat gewonnen!";
        
        initAnimator();
        notificationBuilder = HelperClass.createNotificationManager(this, new Intent(this, TwoPlayerActivity.class));
		updateNotification();

		oController = new OldGames2PController(this);
		OldGame2P og = oController.getOldGameByTeamCombination(new Player(p1), new Player(p2));
		if(og != null)
		{
				tvP1.setText(og.getPointsP1());
				tvP2.setText(og.getPointsP2());
				pointsP1 = getLastNumber(og.getPointsP1().split("\n"));
				pointsP2 = getLastNumber(og.getPointsP2().split("\n"));

			int oldGeber = 1;
			String geberName = og.getGeber();
			if(geberName.equals(p1))
				oldGeber = 1;
			else if(geberName.equals(p2))
				oldGeber = 2;
			geber = oldGeber;
			setGeberVisibility(oldGeber);

			int oldBummerlGeber = 1;
			String bummerlGeberName = og.getBummerlGeber();
			if(bummerlGeberName.equals(p1))
				oldBummerlGeber = 1;
			else if(bummerlGeberName.equals(p2))
				oldBummerlGeber = 2;
			bummerlGeber = oldBummerlGeber;

		}
		
    }
    private void updateNotification() {

    	if(HelperClass.isBooleanPrefKeyActivated(PreferenceManager.getDefaultSharedPreferences(this), OptionsActivity.OPTIONS_IS_NOTIFICATION, true))
    		HelperClass.notifyUser(notificationBuilder, uniqueNotificationId, this, shortP1+" "+pointsP1+" - "+shortP2+" "+pointsP2);
    	else
        	HelperClass.removeNotification(uniqueNotificationId, this);
    		
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
    @Override
    protected void onDestroy()
    {
    	HelperClass.removeNotification(uniqueNotificationId, this);
    	super.onDestroy();
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
    
    private void updateBummerlTextViews()
    {
    	AllBummerls2P ab = bController.getAllBummerlsByPlayerCombination(new Player(p1), new Player(p2));
    	int bummerlP1 = 0;
    	int schneiderP1 = 0;
    	int bummerlP2 = 0;
    	int schneiderP2 = 0;
    	if(ab != null)
    	{
    		if(ab.getP1().getName().equals(p1))
    		{
    			bummerlP1 = ab.getBummerlP1();
    			schneiderP1 = ab.getSchneiderP1();
    			bummerlP2 = ab.getBummerlP2();
    			schneiderP2 = ab.getSchneiderP2();
    		}
    		else
    		{
    			bummerlP2 = ab.getBummerlP1();
    			schneiderP2 = ab.getSchneiderP1();
    			bummerlP1 = ab.getBummerlP2();
    			schneiderP1 = ab.getSchneiderP2();
    		}
    	}
    	tvBummerlP1.setText(bummerlP1+" * ");
    	tvSchneiderP1.setText(schneiderP1+" * ");
    	tvBummerlP2.setText(bummerlP2+" * ");
    	tvSchneiderP2.setText(schneiderP2+" * ");
    }
    
    Player pWinnerAfterGO,pLoserAfterGO;
    boolean isSchneiderAfterGO = false;
    private void updatePoints(int points)
    {
    	
    	if(!isGameOver)
    	{
    		setNextGeber();
    		oldPoints = points;
    	//Klick nach gewonnenem spiel irgendwo --> endscreen erscheint wieder
    	
    	int winnerPoints = 0;
    	int loserPoints = 0;
    	if(activePlayer == 1)
    	{
    		tvCurrentWinner = tvP1;
    		tvCurrentLoser = tvP2;
    		pointsP1 += points;
    		winnerPoints = pointsP1;
    		loserPoints = pointsP2;
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
    		
    		
    		if(activePlayer == 1)
                {tvGameOver.setText(p1+ winText);
                pLoserAfterGO = new Player(p2);
                pWinnerAfterGO = new Player(p1);
                if(pointsP2 == 0)
                {
                	isSchneiderAfterGO = true;
                    tvGameOverBummerl.setText(p2+ " bekommt einen Schneider");
                }
                else 
                {
                	isSchneiderAfterGO = false;
                	tvGameOverBummerl.setText(p2+ " bekommt ein Bummerl");
                	
                }
                }
    		else
    			{tvGameOver.setText(p2+ winText);
                pLoserAfterGO = new Player(p1);
                pWinnerAfterGO = new Player(p2);
    			if(pointsP1 == 0)
                {
                	isSchneiderAfterGO = true;
                	tvGameOverBummerl.setText(p1+ " bekommt einen Schneider");
                }
                else 
                {
                	isSchneiderAfterGO = false;
                	tvGameOverBummerl.setText(p1+ " bekommt ein Bummerl");
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
        	bController.updateBummerls(pLoserAfterGO, pWinnerAfterGO,isSchneiderAfterGO, true);
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
    	bController.updateBummerls(pLoserAfterGO, pWinnerAfterGO,isSchneiderAfterGO,false);
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
		tvFullPointsP2.setText(pointsP2 + "");
    }
    
    private void onRevert()
    {
    	
    	setNextGeber();
    	
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
		if(p1Points.length > 1) {
			p1 = "";
			p2 = "";
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
	}
	updateFullPoints();
	updateNotification();
	scrollToBottom();
    }
    
    
    private void setGeberVisibility(int visibleGeber)
    {
    	switch(visibleGeber)
		{
		case 1: 
			geber1.setVisibility(View.VISIBLE);
			geber2.setVisibility(View.INVISIBLE);
			break;
		case 2: 
			geber1.setVisibility(View.INVISIBLE);
			geber2.setVisibility(View.VISIBLE);
			break;
		}
    }
    
    private void setNextGeber()
    {
    	switch(geber)
		{
		case 1: geber1.setVisibility(View.INVISIBLE); geber2.setVisibility(View.VISIBLE); geber = 2; break;
		case 2: geber2.setVisibility(View.INVISIBLE); geber1.setVisibility(View.VISIBLE); geber = 1; break;
		}
    }
    
    public void onRevanche(View v)
    {
    	gameOver.setVisibility(View.GONE);
        isGameOver = false;
    	pointsP1 = 0;
    	pointsP2 = 0;
    	activePlayer = -1;
    	
    	
    	boolean isGeberAfterFullGame = HelperClass.isBooleanPrefKeyActivated(PreferenceManager.getDefaultSharedPreferences(this),OptionsActivity.OPTIONS_IS_AFTER_FULL_GAME, true);
    	if(isGeberAfterFullGame)
    	{
    		switch(bummerlGeber)
        	{
        	case 1: bummerlGeber = 2; break;
        	case 2: bummerlGeber = 1; break;
        	}
        	geber = bummerlGeber;
    	}

    	setGeberVisibility(geber);
    	tvP1.setText("");
    	tvP2.setText("");
    	updateFullPoints();
    	updateNotification();
    }
    
    public void onRestart(View v)
    {
    	Intent nextIntent = new Intent(TwoPlayerActivity.this, MainActivity.class);
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

    	final LinearLayout view = (LinearLayout) findViewById(R.id.llTwoPlayerMain);
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
			case 1: geberPlayer = p1; break;
			case 2: geberPlayer = p2; break;
		}
		switch(bummerlGeber)
		{
			case 1: bummerlGeberPlayer = p1; break;
			case 2: bummerlGeberPlayer = p2; break;
		}
		oController.addOldGame(new Player(p1), new Player(p2), tvP1.getText() + "", tvP2.getText() + "", geberPlayer, bummerlGeberPlayer);
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

			AlertDialog.Builder builder = new AlertDialog.Builder(TwoPlayerActivity.this);
			builder.setMessage("Wollen Sie das Spiel speichern?").setPositiveButton("Sia", dialogClickListener)
					.setNegativeButton("Na", dialogClickListener).setNeutralButton("Speichern und Beenden", dialogClickListener).show();

		}

		return super.onOptionsItemSelected(item);
	}
}
