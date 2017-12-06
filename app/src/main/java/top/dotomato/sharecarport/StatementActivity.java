package top.dotomato.sharecarport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import top.dotomato.sharecarport.DataModel.MyCode;
import top.dotomato.sharecarport.DataModel.MyCodeResult;
import top.dotomato.sharecarport.Server.MyAction1;
import top.dotomato.sharecarport.Server.Server;

public class StatementActivity extends AppCompatActivity {

    @BindView(R.id.timeView)
    public TextView mTimeView;

    @BindView(R.id.moneyView)
    public TextView mMoneyView;

    @BindView(R.id.restView)
    public TextView mRestView;

    @BindView(R.id.stateButton)
    public Button mStateButton;

    private int mTimeCount;
    private String id;
    private float mMoney;
    private float mRest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);
        ButterKnife.bind(this);
        Intent i = getIntent();
        mTimeCount = i.getIntExtra("TimeCount", 0);
        id = i.getStringExtra("id");
        int t1 = mTimeCount % 60;
        int t2 = (mTimeCount / 60) % 60;
        int t3 = (mTimeCount / 3600) % 60;
        mTimeView.setText(String.format(Locale.CHINA, "%02d:%02d:%02d", t3, t2, t1));

        int t4 = mTimeCount/60/30; //多个个30分钟
        mMoney = (t4+1)*0.5f;
        mMoneyView.setText(String.format(Locale.CHINA, "%1.2f元", mMoney));

        mRest = AccountManager.getIns(this).getRest();
        mRestView.setText(String.format(Locale.CHINA, "%1.2f元", mRest));
    }


    @OnClick(R.id.stateButton)
    public void stateButtonClick(){
        AccountManager.getIns(this).setRest(mRest - mMoney);
        mStateButton.setText("请稍等......");

        MyCode myCode = new MyCode();
        myCode.id = id;
        myCode.type = "car";
        Server.getApi().exit_car(myCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyAction1<MyCodeResult>() {
                    @Override
                    public void call() {
                        Log.d("aaaa", mVar.result);
                        if (mVar.result.equals("success")) {
                            mStateButton.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mStateButton.setText("付款成功");
                                    mStateButton.setEnabled(false);
                                }
                            }, 1000);

                            mStateButton.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    StatementActivity.this.finish();
                                }
                            }, 2000);
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
