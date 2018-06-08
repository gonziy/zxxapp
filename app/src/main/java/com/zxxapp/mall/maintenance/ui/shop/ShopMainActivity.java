package com.zxxapp.mall.maintenance.ui.shop;

import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.zxxapp.mall.maintenance.R;
import com.zxxapp.mall.maintenance.databinding.ActivityShopMainBinding;
import com.zxxapp.mall.maintenance.helper.account.AccountHelper;

import java.util.ArrayList;
import java.util.List;

public class ShopMainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener,
        KindFragment.OnFragmentInteractionListener,
        MineFragment.OnFragmentInteractionListener,
        OrderFragment.OnFragmentInteractionListener{

    private ActivityShopMainBinding binding;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_service:
                    return true;
                case R.id.navigation_order:
                    return true;
                case R.id.navigation_mine:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shop_main);

        initNavigationBar(binding.navigation);
        initViewPager(binding.viewPager);
    }

    private void initNavigationBar(BottomNavigationBar navigationBar) {
        navigationBar.setTabSelectedListener(this);
        navigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        navigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        navigationBar.setBarBackgroundColor(R.color.bottomNavigationBarBackgroundColor);

        navigationBar.addItem(new BottomNavigationItem(R.drawable.ic_shop_service_normal, "服务").setActiveColorResource(R.color.colorTabTextCheck))
                .addItem(new BottomNavigationItem(R.drawable.ic_shop_order_normal, "订单").setActiveColorResource(R.color.colorTabTextCheck))
                .addItem(new BottomNavigationItem(R.drawable.ic_shop_mine_normal, "我的").setActiveColorResource(R.color.colorTabTextCheck))
                .setFirstSelectedPosition(0)
                .initialise(); //所有的设置需在调用该方法前完成

        //badgeItem = new BadgeItem().setBackgroundColor(Color.RED).setText("99");
    }

    private void initViewPager(ViewPager viewPager) {
        final List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(KindFragment.newInstance());
        fragmentList.add(OrderFragment.newInstance());
        fragmentList.add(MineFragment.newInstance());

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
    }

    @Override
    public void onTabSelected(int position) {
        if(binding.viewPager.getCurrentItem()!=position){
            binding.viewPager.setCurrentItem(position);
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
