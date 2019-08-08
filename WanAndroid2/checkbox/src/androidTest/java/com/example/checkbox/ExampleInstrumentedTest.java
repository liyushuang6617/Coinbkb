package com.example.checkbox;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.checkbox.base.BaseResponse;
import com.example.checkbox.base.HttpManage;
import com.example.checkbox.base.ListData;
import com.example.checkbox.base.MyService;
import com.example.checkbox.base.RxUtils;

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

        assertEquals("com.example.checkbox", appContext.getPackageName());
    }

    @Test
    public void getHttp() {
        HttpManage.getHttpUtils().getApiService(MyService.class).get("wxarticle/chapters/json")
                .compose(RxUtils.<BaseResponse<List<ListData>>>observableTransformer())
                .compose(RxUtils.<List<ListData>>changeResult())
                .subscribe(new Consumer<List<ListData>>() {
                    @Override
                    public void accept(List<ListData> listData) throws Exception {
                        Log.e("lys2111", "accept: " + listData.toString());
                    }
                });
    }
}
