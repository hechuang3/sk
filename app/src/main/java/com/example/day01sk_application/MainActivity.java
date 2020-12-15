package com.example.day01sk_application;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private Button btn;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn = (Button) findViewById(R.id.btn);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);

        btn.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                execute();
                break;
            case R.id.btn2:

                enqueue();
                break;
            case R.id.btn3:

                postExecute();
                break;
            case R.id.btn4:

                postEnqueue();
                break;
            case R.id.btn5:

                postString();
                break;
        }
    }

    private void postString() {
        MediaType type = MediaType.parse("text/x-markdown; charset=utf-8");
        RequestBody body = RequestBody.create(type, "没人瞌睡");
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(body)
                .build();
        new OkHttpClient()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG, "onFailure: "+e.toString());
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d(TAG, "onResponse: "+response.body().string());
                    }
                });
    }

    private void postEnqueue() {
        FormBody body = new FormBody.Builder()
                .add("username", "xts")
                .add("password", "123456")
                .add("repassword", "123456")
                .build();
        Request request = new Request.Builder()
                .url("https://www.wanandroid.com/user/register")
                .post(body)
                .build();
        new OkHttpClient()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.d(TAG,"onFailure:"+e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.d(TAG, "onResponse: " + response.body().string());
                    }
                });
    }

    private void postExecute() {
    //post同步方式
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody builder = new FormBody.Builder()
                        .add("username","xts")
                        .add("password","123456")
                        .add("repassword","123456")
                        .build();
                Request request = new Request.Builder()
                        .post(builder)
                        .url("https://www.wanandroid.com/user/register")
                        .build();
                try {
                    Response execute = new OkHttpClient()
                            .newCall(request)
                            .execute();
                    Log.d(TAG,"run:"+execute.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void enqueue() {
        //client.newcall().enqueue(异步)/execute(同步);
//       final Request request=new Request.Builder()
//                .url("https://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/3")
//                .build();
        Request build = new Request.Builder()
                .url("https://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/3")
                .build();
        new OkHttpClient.Builder()
            .build()
            .newCall(build)
            .enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(TAG, "onFailure: " + e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.d(TAG, "onResponse: " + response.body().string());
                }
            });
        Log.d(TAG,"enqueue:滴答");
    }

    private void execute() {

        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .get()
                .url("https://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/3")
                .build();
        final Call call = client.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response execute = call.execute();
                    Log.d(TAG,"run:"+execute.body().string());
                    Log.d(TAG,"run:滴滴");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}