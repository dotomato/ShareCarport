package top.dotomato.sharecarport.Server;

import android.util.Log;

import rx.Observer;

/**
 * Created by chen on 2017/12/6.
 */


public abstract class MyAction1<T> implements Observer<T> {

    public T mVar;

    @Override
    public final void onCompleted() {

    }

    @Override
    public final void onError(Throwable e) {
        e.printStackTrace();
        Log.e("Server",e.toString());
    }

    @Override
    public final void onNext(T var){
        mVar = var;
        call();
    }

    public void call(){   }

}