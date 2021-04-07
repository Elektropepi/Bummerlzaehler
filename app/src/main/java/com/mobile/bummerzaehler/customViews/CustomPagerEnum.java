package com.mobile.bummerzaehler.customViews;

import com.mobile.bummerzaehler.R;

public enum CustomPagerEnum {

	
    
    TWO_PLAYER(R.string.two_players, R.layout.history_two_player),
    THREE_PLAYER(R.string.three_players, R.layout.history_three_player),
    FOUR_PLAYER(R.string.four_players, R.layout.history_four_player);

    private int mTitleResId;
    private int mLayoutResId;

    CustomPagerEnum(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}