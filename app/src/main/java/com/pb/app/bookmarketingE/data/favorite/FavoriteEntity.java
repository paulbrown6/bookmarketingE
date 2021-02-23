package com.pb.app.bookmarketingE.data.favorite;

import androidx.fragment.app.Fragment;

import com.pb.app.bookmarketingE.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavoriteEntity {

    private static FavoriteEntity instance;
    private List<String> favorites;
    private ArrayList<Fragment> favoritesFragments;
    private ArrayList<Fragment> allFragments;
    private ArrayList<String> allItems;
    private ArrayList<String> favoritesID;
    private static String[] s;

    private FavoriteEntity(String[] strings){
        s = strings;
        favorites = new ArrayList<>();
        favoritesFragments = new ArrayList<>();
        favoritesID = new ArrayList<>();
        allFragments = new ArrayList<>();
        allFragments.add(new Fragment(R.layout.frame_1));
        allFragments.add(new Fragment(R.layout.frame_2));
        allFragments.add(new Fragment(R.layout.frame_3));
        allFragments.add(new Fragment(R.layout.frame_4));
        allFragments.add(new Fragment(R.layout.frame_5));
        allFragments.add(new Fragment(R.layout.frame_6));
        allFragments.add(new Fragment(R.layout.frame_7));
        allItems = new ArrayList<>();
        allItems.addAll(Arrays.asList(strings));
    }

    public static FavoriteEntity getInstance(){
        if(instance == null) instance = new FavoriteEntity(s);
        return instance;
    }

    public static void getInstance(String[] strings){
        if(instance == null) instance = new FavoriteEntity(strings);
    }

    public void addFavorite(String token, String id){
        favorites.add(token);
        favoritesID.add(id);
        favoritesFragments.add(allFragments.get(Integer.parseInt(convertStringToFavorite(token))));
    }

    public void removeFavorite(String s){
        favorites.remove(s);
    }

    public boolean isFavorite(String s){
        for (String string: favorites) {
            if (s.equals(string))
                return true;
        }
        return false;
    }

    public List<String> getFavorites(){
        return favorites;
    }

    public ArrayList<Fragment> getFavoritesFragments() {
        return favoritesFragments;
    }

    public ArrayList<Fragment> getAllFragments() {
        return allFragments;
    }

    public String convertStringToFavorite(String string){
        for (int i = 0; i < allItems.size(); i++){
            if (allItems.get(i).equals(string)){
                return String.valueOf(i);
            }
        }
        return String.valueOf(0);
    }

    public String convertFavoriteToString(String string){
        for (int i = 0; i < allItems.size(); i++){
            if (i == Integer.parseInt(String.valueOf(string.toCharArray()[0]))){
                return allItems.get(i);
            }
        }
        return String.valueOf(0);
    }


    public void clearFavorite(){
        favorites = new ArrayList<>();
        favoritesFragments = new ArrayList<>();
        favoritesID = new ArrayList<>();
    };

    public String getIndexFavorite(String token){
        for (int i = 0; i < favorites.size(); i++){
            if (favorites.get(i).equals(token)){
                return String.valueOf(favoritesID.get(i));
            }
        }
        return String.valueOf(0);
    }
}
