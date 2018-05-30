package com.zxxapp.mall.maintenance.view.paymentradio;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zxxapp.mall.maintenance.R;


public class PayRadioButton extends Button implements Checkable {

	private Context context;
	private ImageView payLogo;
	private TextView payDesc;
	private ImageView payChecked;
	
	private Drawable mLogoDrawable;
	
	private boolean mChecked;	////状态是否选中
    private int mButtonResource;
    private boolean mBroadcasting;
    private Drawable mButtonDrawable;
    private OnCheckedChangeListener mOnCheckedChangeListener;	////选中状态改变监听
    private OnCheckedChangeListener mOnCheckedChangeWidgetListener;
    
    private static final int[] CHECKED_STATE_SET = {
        android.R.attr.state_checked
    };

	public PayRadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PayRidioButton);
		
		Drawable d = array.getDrawable(R.styleable.PayRidioButton_radio);
		if (d != null) {
			setButtonDrawable(d);
		}
		
		boolean checked = array.getBoolean(R.styleable.PayRidioButton_checked, false);
		setChecked(checked);
		
		String dString = array.getString(R.styleable.PayRidioButton_desc);
		setText(dString);
		
		Drawable logo = array.getDrawable(R.styleable.PayRidioButton_logo1);
		mLogoDrawable = logo;
		setCompoundDrawables(mLogoDrawable, null, null, null);
		
		array.recycle();
		
		
	}
	
	@Override
	public boolean isChecked() {
		// TODO Auto-generated method stub
		return mChecked;
	}

	@Override
	public void setChecked(boolean checked) {
		// TODO Auto-generated method stub		//执行到这，因为第一个是没有选中的，所以没知道这里，但是第二个，默认是选中的，执行了它
		//当执行了点击事件
		if (mChecked != checked) {
            mChecked = checked;
            refreshDrawableState();

            // Avoid infinite recursions if setChecked() is called from a listener
            if (mBroadcasting) {
                return;
            }
            mBroadcasting = true;
            if (mOnCheckedChangeListener != null) {
            	System.out.println(">>>25");		//点击了也没有执行
                mOnCheckedChangeListener.onCheckedChanged(this, mChecked);
            }
            if (mOnCheckedChangeWidgetListener != null) {
            	System.out.println(">>>27");		//点击后执行了
                mOnCheckedChangeWidgetListener.onCheckedChanged(this, mChecked);
            }
            mBroadcasting = false;		//这里都是最后执行的、  
        }
	}

	@Override
	public void toggle() {
		// TODO Auto-generated method stub
		if (!isChecked()) {
			setChecked(!mChecked);
		}
	}
	
	@Override
	public boolean performClick() {
		// TODO Auto-generated method stub
		/*
         * XXX: These are tiny, need some surrounding 'expanded touch area',
         * which will need to be implemented in Button if we only override
         * performClick()
         */

        /* When clicked, toggle the state */
        toggle();
		return super.performClick();
	}
	
	/**
     * Register a callback to be invoked when the checked state of this button
     * changes.
     *
     * @param listener the callback to call on checked state change
     */
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }

    /**
     * Register a callback to be invoked when the checked state of this button
     * changes. This callback is used for internal purpose only.
     *
     * @param listener the callback to call on checked state change
     * @hide
     */
    void setOnCheckedChangeWidgetListener(OnCheckedChangeListener listener) {
        mOnCheckedChangeWidgetListener = listener;
    }
    
    /**
     * Interface definition for a callback to be invoked when the checked state
     * of a compound button changed.
     */
    public static interface OnCheckedChangeListener {
        /**
         * Called when the checked state of a compound button has changed.
         *
         * @param buttonView The compound button view whose state has changed.
         * @param isChecked  The new checked state of buttonView.
         */
        void onCheckedChanged(PayRadioButton buttonView, boolean isChecked);
    }
    
    /**
     * Set the background to a given Drawable, identified by its resource id.
     *
     * @param resid the resource id of the drawable to use as the background 
     */
    public void setButtonDrawable(int resid) {
        if (resid != 0 && resid == mButtonResource) {
            return;
        }

        mButtonResource = resid;

        Drawable d = null;
        if (mButtonResource != 0) {
            d = getResources().getDrawable(mButtonResource);
        }
        setButtonDrawable(d);
    }
    
    /**
     * Set the background to a given Drawable
     *
     * @param d The Drawable to use as the background
     */
    public void setButtonDrawable(Drawable d) {
        if (d != null) {
            if (mButtonDrawable != null) {
                mButtonDrawable.setCallback(null);
                System.out.println(">>>11");
                unscheduleDrawable(mButtonDrawable);
                System.out.println(">>>12");
            }
            d.setCallback(this);
            d.setState(getDrawableState());
            d.setVisible(getVisibility() == VISIBLE, false);
            mButtonDrawable = d;
            mButtonDrawable.setState(null);
            setMinHeight(mButtonDrawable.getIntrinsicHeight());
        }
        refreshDrawableState();
    }

    @Override
    protected void onDraw(Canvas canvas) {
    	// TODO Auto-generated method stub
    	super.onDraw(canvas);
    	
    	final Drawable buttonDrawable = mButtonDrawable;
        if (buttonDrawable != null) {
            final int verticalGravity = getGravity() & Gravity.VERTICAL_GRAVITY_MASK;
            final int height = buttonDrawable.getIntrinsicHeight();
            final int width = buttonDrawable.getIntrinsicWidth();

            int y = 0;
            int x = 0;

            switch (verticalGravity) {
                case Gravity.BOTTOM:
                    y = getHeight() - height;
                    break;
                case Gravity.CENTER_VERTICAL:
                    y = (getHeight() - height) / 2;
                    break;
            }
            
            x = getWidth() - width;		//得到其与左边的间距

//            buttonDrawable.setBounds(0, y, buttonDrawable.getIntrinsicWidth(), y + height);
            buttonDrawable.setBounds(x, y, getWidth(), y + height);
            buttonDrawable.draw(canvas);
        }
    }
    
    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
           mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        
        if (mButtonDrawable != null) {
            int[] myDrawableState = getDrawableState();
            
            // Set the state of the Drawable
            mButtonDrawable.setState(myDrawableState);
            
            invalidate();
        }
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == mButtonDrawable;
    }
    
    static class SavedState extends BaseSavedState {
        boolean checked;

        /**
         * Constructor called from {@link CompoundButton#onSaveInstanceState()}
         */
        SavedState(Parcelable superState) {
            super(superState);
        }
        
        /**
         * Constructor called from {@link #CREATOR}
         */
        private SavedState(Parcel in) {
            super(in);
            checked = (Boolean)in.readValue(null);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeValue(checked);
        }

        @Override
        public String toString() {
            return "CompoundButton.SavedState{"
                    + Integer.toHexString(System.identityHashCode(this))
                    + " checked=" + checked + "}";
        }

        public static final Creator<SavedState> CREATOR
                = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    @Override
    public Parcelable onSaveInstanceState() {
        // Force our ancestor class to save its state
        setFreezesText(true);
        Parcelable superState = super.onSaveInstanceState();

        SavedState ss = new SavedState(superState);

        ss.checked = isChecked();
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
  
        super.onRestoreInstanceState(ss.getSuperState());
        setChecked(ss.checked);
        requestLayout();
    }
    
}
