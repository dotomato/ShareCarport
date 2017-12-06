package top.dotomato.sharecarport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class UsingActivity extends AppCompatActivity {

    @BindView(R.id.timeView)
    public TextView mTimeView;

    private int mTimeCount;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using);

        ButterKnife.bind(this);
        mTimeView.post(mTimer);
        mTimerEnable = true;

        Intent i = getIntent();
        id = i.getStringExtra("id");
        long time = i.getLongExtra("time", -1);
        mTimeCount = (int) time;
    }


    private boolean mTimerEnable;
    private Runnable mTimer = new Runnable() {
        @Override
        public void run() {
            int t1 = mTimeCount % 60;
            int t2 = (mTimeCount / 60) % 60;
            int t3 = (mTimeCount / 3600) % 60;
            mTimeView.setText(String.format(Locale.CHINA, "%02d:%02d:%02d", t3, t2, t1));
            mTimeCount += 1;
            if (mTimerEnable) {
                mTimeView.postDelayed(this, 1000);
            } else {

            }
        }
    };

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @OnClick(R.id.payButton)
    public void onPayButtonClick(){
        mTimerEnable = false;
        Intent i = new Intent(UsingActivity.this, StatementActivity.class);
        i.putExtra("TimeCount", mTimeCount);
        i.putExtra("id", id);
        UsingActivity.this.startActivity(i);
        UsingActivity.this.finish();


    }
}
