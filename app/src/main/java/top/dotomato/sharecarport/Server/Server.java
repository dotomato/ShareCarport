package top.dotomato.sharecarport.Server;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;
import top.dotomato.sharecarport.DataModel.GetTimeResult;
import top.dotomato.sharecarport.DataModel.MyCode;
import top.dotomato.sharecarport.DataModel.MyCodeResult;

/**
 * Created by chen on 2017/12/6.
 */


public class Server {


    final static private String TAG = "Server";

    final static private String BASEURL = "http://dotomato.top:5001"; //这里要填上电脑服务器的地址
    final static private String VERSION = "/api/v0.01";

    public interface ServerInterface {

        @Headers({"Content-Type: application/json", "Accept: application/json"})
        @POST(VERSION + "/get_time")
        Observable<GetTimeResult> get_time(@Body MyCode var);

        @Headers({"Content-Type: application/json", "Accept: application/json"})
        @POST(VERSION + "/exit_car")
        Observable<MyCodeResult> exit_car(@Body MyCode var);

    }


    private static ServerInterface mServer=null;

    public static ServerInterface getApi(){
        if (mServer==null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            mServer = retrofit.create(ServerInterface.class);
        }
        return mServer;
    }
}
