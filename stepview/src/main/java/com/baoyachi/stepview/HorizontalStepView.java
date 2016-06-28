package com.baoyachi.stepview;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * 日期：16/6/22 15:47
 * <p>
 * 描述：StepView
 */
public class HorizontalStepView extends LinearLayout implements HorizontalStepsViewIndicator.OnDrawIndicatorListener
{

    private RelativeLayout mTextContainer;
    private HorizontalStepsViewIndicator mStepsViewIndicator;
    private List<String> mTexts;
    private int mComplectingPosition;
    private int mUnComplectedTextColor = ContextCompat.getColor(getContext(), R.color.uncompleted_text_color);//定义默认未完成文字的颜色;
    private int mComplectedTextColor = ContextCompat.getColor(getContext(), android.R.color.white);//定义默认完成文字的颜色;

    public HorizontalStepView(Context context)
    {
        this(context, null);
    }

    public HorizontalStepView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public HorizontalStepView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.widget_horizontal_stepsview, this);
        mStepsViewIndicator = (HorizontalStepsViewIndicator) rootView.findViewById(R.id.steps_indicator);
        mStepsViewIndicator.setOnDrawListener(this);
        mTextContainer = (RelativeLayout) rootView.findViewById(R.id.rl_text_container);
        mTextContainer.removeAllViews();
    }

    /**
     * 设置显示的文字
     *
     * @param texts
     * @return
     */
    public HorizontalStepView setStepViewTexts(List<String> texts)
    {
        mTexts = texts;
        mStepsViewIndicator.setStepNum(mTexts.size());
        return this;
    }

    /**
     * 设置正在进行的position
     *
     * @param complectingPosition
     * @return
     */
    public HorizontalStepView setStepsViewIndicatorComplectingPosition(int complectingPosition)
    {
        mComplectingPosition = complectingPosition;
        mStepsViewIndicator.setComplectingPosition(complectingPosition);
        return this;
    }

    /**
     * 设置未完成文字的颜色
     *
     * @param unComplectedTextColor
     * @return
     */
    public HorizontalStepView setStepViewUnComplectedTextColor(int unComplectedTextColor)
    {
        mUnComplectedTextColor = unComplectedTextColor;
        return this;
    }

    /**
     * 设置完成文字的颜色
     *
     * @param complectedTextColor
     * @return
     */
    public HorizontalStepView setStepViewComplectedTextColor(int complectedTextColor)
    {
        this.mComplectedTextColor = complectedTextColor;
        return this;
    }

    /**
     * 设置StepsViewIndicator未完成线的颜色
     *
     * @param unCompletedLineColor
     * @return
     */
    public HorizontalStepView setStepsViewIndicatorUnCompletedLineColor(int unCompletedLineColor)
    {
        mStepsViewIndicator.setUnCompletedLineColor(unCompletedLineColor);
        return this;
    }

    /**
     * 设置StepsViewIndicator完成线的颜色
     *
     * @param completedLineColor
     * @return
     */
    public HorizontalStepView setStepsViewIndicatorCompletedLineColor(int completedLineColor)
    {
        mStepsViewIndicator.setCompletedLineColor(completedLineColor);
        return this;
    }

    /**
     * 设置StepsViewIndicator默认图片
     *
     * @param defaultIcon
     */
    public HorizontalStepView setStepsViewIndicatorDefaultIcon(Drawable defaultIcon)
    {
        mStepsViewIndicator.setDefaultIcon(defaultIcon);
        return this;
    }

    /**
     * 设置StepsViewIndicator已完成图片
     *
     * @param completeIcon
     */
    public HorizontalStepView setStepsViewIndicatorCompleteIcon(Drawable completeIcon)
    {
        mStepsViewIndicator.setCompleteIcon(completeIcon);
        return this;
    }

    /**
     * 设置StepsViewIndicator正在进行中的图片
     *
     * @param attentionIcon
     */
    public HorizontalStepView setStepsViewIndicatorAttentionIcon(Drawable attentionIcon)
    {
        mStepsViewIndicator.setAttentionIcon(attentionIcon);
        return this;
    }

    @Override
    public void ondrawIndicator()
    {
        List<Float> complectedXPosition = mStepsViewIndicator.getCircleCenterPointPositionList();
        if(mTexts != null)
        {
            for(int i = 0; i < mTexts.size(); i++)
            {
                TextView textView = new TextView(getContext());
                textView.setText(mTexts.get(i));
                textView.setX(complectedXPosition.get(i) - mStepsViewIndicator.getCircleRadius() - 10);//这里的-10是将文字进行调整居中，稍后再动态修改
                textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                if(i <= mComplectingPosition)
                {
                    textView.setTypeface(null, Typeface.BOLD);
                    textView.setTextColor(mComplectedTextColor);
                } else
                {
                    textView.setTextColor(mUnComplectedTextColor);
                }

                mTextContainer.addView(textView);
            }
        }
    }
}
