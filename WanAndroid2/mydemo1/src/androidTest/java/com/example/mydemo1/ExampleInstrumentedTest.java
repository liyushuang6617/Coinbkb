package com.example.mydemo1;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.mydemo1.api.MyService;
import com.example.mydemo1.base.BaseResponse;
import com.example.mydemo1.bean.ListData;
import com.example.mydemo1.http.HttpManager;
import com.example.mydemo1.utils.RxUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.reactivex.functions.Consumer;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.mydemo1", appContext.getPackageName());
    }

    @Test
    public void http() {
        HttpManager.getInstance().getApiService(MyService.class).get("wxarticle/chapters/json")
                .compose(RxUtils.<BaseResponse<List<ListData>>>rxScheduleThread())
                .compose(RxUtils.<List<ListData>>changeResult())
                .subscribe(new Consumer<List<ListData>>() {
                    @Override
                    public void accept(List<ListData> listData) throws Exception {
                        Log.e("lys", "accept: " + listData.toString());
                    }
                });
    }
}
