package com.example.chat.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.chat.Utils.AppSharedPreferences;
import com.example.chat.Fragments.HomeFragment;
import com.example.chat.Adapters.HomeScreenViewpagerAdapter;
import com.example.chat.R;
import com.example.chat.Utils.UniversalImageLoderClass;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView btnMore;
    CircleImageView profileImage;
    FirebaseUser user;
    DatabaseReference ref;
    AppSharedPreferences appSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appSharedPreferences = new AppSharedPreferences(this);
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference();


        btnMore = findViewById(R.id.btn_more);
        profileImage = findViewById(R.id.profile_img);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.home_viewpager);

       // tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_home_24);
        UniversalImageLoderClass universalImageLoderClass = new UniversalImageLoderClass(this);
        ImageLoader.getInstance().init(universalImageLoderClass.getConfig());

        ref.child("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                String imgUrl = snapshot.child("Info").child("imgUrl").getValue(String.class);
                String name = snapshot.child("Info").child("name").getValue(String.class);
                UniversalImageLoderClass.setImage(imgUrl,profileImage,null);

                appSharedPreferences.setUsername(name);
                appSharedPreferences.setImgUrl(imgUrl);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        btnMore.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(MainActivity.this, btnMore);
            popup.inflate(R.menu.more);
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.profile) {
                    finish();
                }
                return false;
            });
            //displaying the popup
            popup.show();
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
            }
        });

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager pager) {
        HomeScreenViewpagerAdapter homeScreenViewpagerAdapter = new HomeScreenViewpagerAdapter(getSupportFragmentManager());
        homeScreenViewpagerAdapter.addFragment(new HomeFragment(),"Chat");
       // homeScreenViewpagerAdapter.addFragment(new StoryFragment(),"Story");
        pager.setAdapter(homeScreenViewpagerAdapter);
    }

    //-----------------------------------Change User Status (Online / Offline)--------------------------------//

    private void status(String status) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status",status);
        ref.child("Users").child(user.getUid()).child("Info").updateChildren(hashMap);
    }


    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }

}