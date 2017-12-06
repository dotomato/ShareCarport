package top.dotomato.sharecarport;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Random;

/**
 * Created by chen on 17-11-5.
 * Copyright *
 */

class AccountManager {
    private static AccountManager ins = null;
    private SharedPreferences mSp;

    static AccountManager getIns(Context context){
        if (ins == null)
            ins = new AccountManager(context);
        return ins;
    }

    private AccountManager(Context context) {
        mSp = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        if (mSp.getString("UID", "None").equals("None")) {
            SharedPreferences.Editor editor = mSp.edit();
            String uid = String.valueOf(new Random().nextInt(100000));
            editor.putString("UID", uid);
            editor.putFloat("rest", 10);
            editor.apply();
        }
    }

    public String getUID(){
        return mSp.getString("UID","None");
    }

    public float getRest(){
        return mSp.getFloat("rest",100);
    }

    public void setRest(float rest){
        SharedPreferences.Editor editor = mSp.edit();
        editor.putFloat("rest", rest);
        editor.apply();
    }
}
