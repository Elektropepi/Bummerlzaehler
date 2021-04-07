package com.mobile.bummerzaehler.customViews;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

//http://stackoverflow.com/questions/2617266/how-to-adjust-text-font-size-to-fit-textview
//Create a subclass of TextView
public class FontFitTextView extends TextView
{
	// Attributes
	private Paint testPaint;

	public FontFitTextView(Context context)
	{
		// Dont' forget to call the Constructor of the
		// SuperClass
		super(context);
		initialise();
	}

	public FontFitTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		initialise();
	}

	private void initialise()
	{
		testPaint = new Paint();
		testPaint.set(this.getPaint());
		// max size defaults to the initially specified text size unless it is
		// too small
	}

	/*
	 * Re size the font so the specified text fits in the text box assuming the
	 * text box is the specified width. What we are trying here is to measure if
	 * our calculated size does fit, and if not, we reduce the max or min value
	 * and try it again. This is done as long as the difference is bigger than
	 * our predifined threshold
	 */
	private void refitText(String text, int textWidth)
	{
		if (textWidth <= 0)
			return;
		// target width is the space available for text
		// (width - paddings)
		int targetWidth = textWidth - this.getPaddingLeft()
				- this.getPaddingRight();
		float hi = 60;
		float lo = 2;
		final float threshold = 0.05f; // How close we have to be

		testPaint.set(this.getPaint());

		// As long as the difference between max size is bigger
		// than our threshold
		while ((hi - lo) > threshold)
		{
			float size = (hi + lo) / 2;
			testPaint.setTextSize(size);
			if (testPaint.measureText(text) >= targetWidth)
				hi = size; // too big
			else
				lo = size; // too small
		}
		// Use lo so that we undershoot rather than overshoot
		this.setTextSize(TypedValue.COMPLEX_UNIT_PX, lo);
	}

	// When the text is measured we refit the text
	@Override
	protected void onMeasure(int widthMeasureSpec,
			int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
		int height = getMeasuredHeight();
		refitText(this.getText().toString(), parentWidth);
		this.setMeasuredDimension(parentWidth, height);
	}

	// When the text changes, we too refit the text
	@Override
	protected void onTextChanged(final CharSequence text,
			final int start, final int before, final int after)
	{
		refitText(text.toString(), this.getWidth());
	}

	// When the size changed and the width is different than the
	// old width, we too refit the text
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		if (w != oldw)
		{
			refitText(this.getText().toString(), w);
		}
	}

}
