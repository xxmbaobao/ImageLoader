package com.fcar.viewpager;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ViewPagerManager extends PagerAdapter
{
  private List<View> views;

  public ViewPagerManager(List<View> paramList)
  {
    this.views = paramList;
  }

  public void destroyItem(View paramView, int paramInt, Object paramObject)
  {
    ViewPager localViewPager = (ViewPager)paramView;
    View localView = (View)this.views.get(paramInt);
    localViewPager.removeView(localView);
  }

  public void finishUpdate(View paramView)
  {
  }

  public int getCount()
  {
    if (this.views != null);
    for (int i = this.views.size(); ; i = 0)
      return i;
  }

  public Object instantiateItem(View paramView, int paramInt)
  {
    ViewPager localViewPager = (ViewPager)paramView;
    View localView = (View)this.views.get(paramInt);
    localViewPager.addView(localView, 0);
    return this.views.get(paramInt);
  }

  public boolean isViewFromObject(View paramView, Object paramObject)
  {
    if (paramView == paramObject);
    for (int i = 1; ; i = 0)
      return true;
  }
}