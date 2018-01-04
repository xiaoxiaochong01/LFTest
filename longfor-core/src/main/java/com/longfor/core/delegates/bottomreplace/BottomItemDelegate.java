package com.longfor.core.delegates.bottomreplace;

import android.content.Context;
import android.widget.Toast;

import com.longfor.core.delegates.LongForDelegate;

/**
 * Created by zhanghaitao1 on 2017/12/22.
 */

public abstract class BottomItemDelegate extends LongForDelegate {
    //连续点击两次退出程序时间设置
    private static final long WATE_TIME = 2000L;
    private long touch_time = 0;
    private BackHandledInterface mBackHandledInterface;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(getActivity() instanceof BackHandledInterface)) {
            throw new ClassCastException(
                    "Hosting Activity must implement BackHandledInterface");
        } else {
            this.mBackHandledInterface = (BackHandledInterface) getActivity();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            mBackHandledInterface.setSelectedFragment(this);
        }
    }

    public boolean onBackPressed() {
        if(System.currentTimeMillis() - touch_time < WATE_TIME){
            _mActivity.finish();
            if(touch_time!=0){
                touch_time = 0;
            }
            return true;
        }else{
            touch_time = System.currentTimeMillis();
            Toast.makeText(_mActivity, "双击退出", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
