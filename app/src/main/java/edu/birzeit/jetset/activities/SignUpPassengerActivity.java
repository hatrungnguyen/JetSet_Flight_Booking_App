package edu.birzeit.jetset.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

import edu.birzeit.jetset.Fragments.PassengerFirstFragment;
import edu.birzeit.jetset.Fragments.PassengerSecondFragment;
import edu.birzeit.jetset.R;
import edu.birzeit.jetset.tasks.ZoomOutPageTransformer;

public class SignUpPassengerActivity extends AppCompatActivity {
    private static final int NUM_PAGES = 2;
    private ViewPager2 viewPager2;
    private FragmentStateAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup_passenger);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.sign_up_passenger), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewPager2 = findViewById(R.id.viewPager);
        DotsIndicator dotsIndicator = findViewById(R.id.worm_dots_indicator);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), getLifecycle());

        viewPager2.setAdapter(pagerAdapter);
        viewPager2.setPageTransformer(new ZoomOutPageTransformer());
        dotsIndicator.setViewPager2(viewPager2);

//        TabLayout tabLayout = new TabLayout(this);
//        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2,
//                (tab, position) -> {
//                    // Customize the tabs if needed
//                });

    }

    @Override
    public void onBackPressed() {
        if (viewPager2.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm, Lifecycle lifecycle) {
            super(fm, lifecycle);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new PassengerFirstFragment();
                case 1:
                    return new PassengerSecondFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return NUM_PAGES;
        }
    }
}
