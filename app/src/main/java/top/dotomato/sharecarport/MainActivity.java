package top.dotomato.sharecarport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import top.dotomato.sharecarport.DataModel.GetTimeResult;
import top.dotomato.sharecarport.DataModel.MyCode;
import top.dotomato.sharecarport.Server.MyAction1;
import top.dotomato.sharecarport.Server.Server;

public class MainActivity extends AppCompatActivity {


    final static String TAG = "MainActivity";

    final static int SCAN_CODE = 0;

    @BindView(R.id.backImageView)
    public ImageView mBackImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Glide.with(this).load(R.drawable.back).into(mBackImageView);
    }


    @OnClick(R.id.scanButton)
    public void scanButtonClick(){
//        测试用
//        final MyCode myCode = new MyCode();
//        myCode.id = "111";
//        Server.getApi().get_time(myCode)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new MyAction1<GetTimeResult>() {
//                    @Override
//                    public void call() {
//                        if (mVar.result.equals("success")){
//                            Intent i = new Intent(MainActivity.this, UsingActivity.class);
//                            i.putExtra("id", myCode.id);
//                            i.putExtra("time", mVar.time);
//                            MainActivity.this.startActivity(i);
//                        }
//                    }
//                });


        Intent i = new Intent(this, ScanActivity.class);
        this.startActivityForResult(i, SCAN_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == SCAN_CODE){
            if (resultCode == RESULT_OK){
                String result = data.getStringExtra("code");
                Gson gson = new Gson();
                final MyCode myCode = gson.fromJson(result, MyCode.class);
                if (!myCode.type.equals("car"))
                    return;
                Server.getApi().get_time(myCode)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MyAction1<GetTimeResult>() {
                            @Override
                            public void call() {
                                if (mVar.result.equals("success")){
                                    Intent i = new Intent(MainActivity.this, UsingActivity.class);
                                    i.putExtra("id", myCode.id);
                                    i.putExtra("time", mVar.time);
                                    MainActivity.this.startActivity(i);
                                }
                            }
                        });
            }
        }
    }
}
