package com.pb.app.bookmarketingE;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationView;
import com.pb.app.bookmarketingE.data.database.DatabaseSQL;
import com.pb.app.bookmarketingE.data.favorite.FavoriteEntity;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private Toolbar toolbar;
    private static ImageButton favoriteButton;
    private static ImageButton shareButton;
    private static ImageButton backButton;
    private static String currentFragment;
    private static Boolean isFavorite;
    private static Boolean isDark;
    private DrawerLayout drawer;
    private static MainActivity activity;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        FavoriteEntity.getInstance(getResources().getStringArray(R.array.content_items));
        DatabaseSQL.getInstance().writeUserDB(getApplicationContext());
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        isFavorite = false;
        isDark = false;
        drawer = findViewById(R.id.drawer_layout);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_content, R.id.nav_favorite, R.id.nav_settings)
                .setOpenableLayout(drawer)
                .build();
        NavigationView navigationView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        favoriteButton = findViewById(R.id.button_favorite);
        shareButton = findViewById(R.id.button_share);
        backButton = findViewById(R.id.button_back);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFavorite = !isFavorite;
                if (isFavorite) {
                    DatabaseSQL.getInstance().readUserDb(getApplicationContext(), currentFragment, FavoriteEntity.getInstance().convertStringToFavorite(currentFragment));
                } else {
                    DatabaseSQL.getInstance().deleteUserDb(getApplicationContext(), FavoriteEntity.getInstance().getIndexFavorite(currentFragment));
                }
                OnFavorite(isFavorite);
            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                String s = currentFragment +
                        "find out from here: link to the app on Google play";
                sendIntent.putExtra(Intent.EXTRA_TEXT, s);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share"));
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onBackPressed();
            }
        });
        activity = this;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        MainActivity.getFavoriteButton().setVisibility(View.INVISIBLE);
        MainActivity.getShareButton().setVisibility(View.INVISIBLE);
        MainActivity.getBackButton().setVisibility(View.INVISIBLE);
        MainActivity.getActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MainActivity.getActivity().getSupportActionBar().setDisplayShowTitleEnabled(true);
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public static ImageButton getFavoriteButton() {
        return favoriteButton;
    }

    public static ImageButton getShareButton() {
        return shareButton;
    }

    public static ImageButton getBackButton() {
        return backButton;
    }

    public static void OnFavorite(Boolean bool) {
        isFavorite = bool;
        if (bool) {
            favoriteButton.setBackgroundResource(R.drawable.ic_chekced_true_favorite);
        } else {
            favoriteButton.setBackgroundResource(R.drawable.ic_chekced_false_favorite);
        }
    }

    public static void setCurrentFragment(String s) {
        currentFragment = s;
    }

    public void onSharedItemSelected(MenuItem item) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        String s = "Digital-marketing: Link to App on Google Play";
        sendIntent.putExtra(Intent.EXTRA_TEXT, s);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share"));
    }

    public static boolean isDarkMode() {
        return isDark;
    }

    public static void onDarkMode(boolean bool) {
        isDark = bool;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public static MainActivity getActivity() {
        return activity;
    }
}
