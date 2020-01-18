package com.test.hyxc.page.island;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import com.test.hyxc.R;
import com.test.hyxc.ccb.base.BaseActivity;
import com.test.hyxc.page.island.fragment.ClassifyFragment;
public class IslandSelectCategoryActivity extends BaseActivity {
    private FrameLayout fl;
    private Fragment cateSelectFragment =null;
    @Override
    public int getContentViewResource() {
        return R.layout.imap_island_select_category;
    }
    @Override
    protected void initView() {
        fl=(FrameLayout)findViewById(R.id.fl);
        if (cateSelectFragment == null) {
            cateSelectFragment = new ClassifyFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fl, cateSelectFragment).commit();
        } else {
            getSupportFragmentManager().beginTransaction().show(cateSelectFragment).commit();
        }
    }
    @Override
    protected void initData() {
    }
    @Override
    protected void initList() {
    }
}
