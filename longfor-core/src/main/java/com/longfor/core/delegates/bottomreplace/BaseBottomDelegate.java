package com.longfor.core.delegates.bottomreplace;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.joanzapata.iconify.widget.IconTextView;
import com.longfor.core.R;
import com.longfor.core.R2;
import com.longfor.core.delegates.LongForDelegate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * Created by zhanghaitao1 on 2017/12/22.
 */

public abstract class BaseBottomDelegate extends LongForDelegate{
    private final ArrayList<BottomTabBean> TAB_BEANS = new ArrayList<>();
    private final ArrayList<BottomItemDelegate> ITEM_DELEGATES = new ArrayList<>();
    private final LinkedHashMap<BottomTabBean,BottomItemDelegate> ITEMS = new LinkedHashMap<>();

    private int mCurrentDelegate = 0;
    private int mIndexDelegate = 0;
//    private int mClickedColor = Color.RED;

    @BindView(R2.id.bottom_bar)
    RadioGroup mBottomBar = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_bottom_replace;
    }

    public abstract LinkedHashMap<BottomTabBean,BottomItemDelegate> setItems(ItemBuilder builder);
    public abstract int setIndexDelegate();

    public abstract int setIndexBottomBackground(); // 设置底部背景

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndexDelegate = setIndexDelegate();

        final ItemBuilder builder = ItemBuilder.builder();
        final LinkedHashMap<BottomTabBean,BottomItemDelegate> items = setItems(builder);
        ITEMS.putAll(items);
        for(Map.Entry<BottomTabBean,BottomItemDelegate> item:ITEMS.entrySet()){
            final BottomTabBean key = item.getKey();
            final BottomItemDelegate value = item.getValue();
            TAB_BEANS.add(key);
            ITEM_DELEGATES.add(value);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        if(setIndexBottomBackground()>0) {
            mBottomBar.setBackgroundResource(setIndexBottomBackground());
        }
        final int size = ITEMS.size();
        for (int i = 0; i < size; i++) {
            AppCompatRadioButton radioButton = new AppCompatRadioButton(getContext());
            radioButton.setId(i);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(0, RadioGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1;
//            params.gravity = Gravity.CENTER;
//            params.leftMargin = 100;
            radioButton.setLayoutParams(params);
            radioButton.setButtonDrawable(null);
            if(i == mIndexDelegate) {
                radioButton.setChecked(true);
            }
            else {
                radioButton.setChecked(false);
            }
            final BottomTabBean bean = TAB_BEANS.get(i);
            Drawable drwable = ContextCompat.getDrawable(getContext(), bean.getIcon());
            radioButton.setCompoundDrawablesWithIntrinsicBounds(null,drwable,null,null);
//
            mBottomBar.addView(radioButton);
        }
        final ISupportFragment[] delegateArray  = ITEM_DELEGATES.toArray(new ISupportFragment[size]);
        getSupportDelegate().loadMultipleRootFragment(R.id.bottom_bar_delegate_container,mIndexDelegate,delegateArray);

        mBottomBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int tag = i;
                getSupportDelegate().showHideFragment(ITEM_DELEGATES.get(tag),ITEM_DELEGATES.get(mCurrentDelegate));
                mCurrentDelegate = tag;
            }
        });
    }

//    private void resetColor(){
//        final int count = mBottomBar.getChildCount();
//        for (int i = 0; i < count; i++) {
//            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
//            final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
//            itemIcon.setTextColor(Color.GRAY);
//            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
//            itemTitle.setTextColor(Color.GRAY);
//        }
//    }

//    @Override
//    public void onClick(View view) {
//        final int tag = (int) view.getTag();
//        resetColor();
//        final RelativeLayout item = (RelativeLayout) view;
//        final IconTextView itemIcon = (IconTextView) item.getChildAt(0);
//        itemIcon.setTextColor(mClickedColor);
//        final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
//        itemTitle.setTextColor(mClickedColor);
//        getSupportDelegate().showHideFragment(ITEM_DELEGATES.get(tag),ITEM_DELEGATES.get(mCurrentDelegate));
//        mCurrentDelegate = tag;
//    }

}
