package com.sengmei.meililian.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sengmei.kline.R;
import com.sengmei.meililian.Adapter.AddressAdapter;
import com.sengmei.meililian.BaseActivity;
import com.sengmei.meililian.UserBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.sengmei.kline.DataRequest.getStringFromAssert;

public class AddressActivity extends BaseActivity {
    private List<String> list=new ArrayList<>();
    private AddressAdapter addressAdapter;
    private ListView listview;
    @Override
    public void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.addressactivity);
        listview=(ListView)findViewById(R.id.listview);
      String s=getStringFromAssert(AddressActivity.this, "city.json");
          if (list.size()>0){
              list.clear();
          }
        try {
            JSONObject objectt = new JSONObject(s);
            String citylist = objectt.getString("citylist");
            JSONArray array = new JSONArray(citylist);
            for (int i = 0; i < array.length(); i++) {
                JSONObject objec0 = array.getJSONObject(i);
                String name_cn = objec0.getString("name_cn");
                list.add(name_cn);
            }
            addressAdapter=new AddressAdapter(AddressActivity.this,list);
            listview.setAdapter(addressAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

      listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

          }
      });
    }

    @Override
    public void initViews() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserBean.Address=list.get(position);
                finish();
            }
        });
    }

    @Override
    public void ReinitViews() {

    }

    @Override
    public void initData() {

    }

    public void back(View v) {
        finish();
    }
}
