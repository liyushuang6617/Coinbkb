package com.example.mydemo1.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import com.example.mydemo1.activity.ArticleDetailActivity;
import com.example.mydemo1.app.MyApp;
import com.example.mydemo1.constant.Constants;

import java.util.Random;

public class HomePagerUtils {
    public static void startArticleDetailActivity(Context context, int articleId, String articleTitle,
                                                  String articleLink, boolean isCollected,
                                                  boolean isShowCollectIcon, int articleItemPosition,
                                                  String eventBusTag) {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(Constants.ARTICLE_ID, articleId);
        intent.putExtra(Constants.ARTICLE_TITLE, articleTitle);
        intent.putExtra("link", articleLink);
        intent.putExtra(Constants.IS_COLLECTED, isCollected);
        intent.putExtra(Constants.IS_SHOW_COLLECT_ICON, isShowCollectIcon);
        intent.putExtra(Constants.ARTICLE_ITEM_POSITION, articleItemPosition);
        intent.putExtra(Constants.EVENT_BUS_TAG, eventBusTag);
        context.startActivity(intent);
    }

    public static int getRandomColor() {
        Random random = new Random();
        //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        int red;
        int green;
        int blue;
        if (false) {
//            150-255
            red = random.nextInt(105) + 150;
            green = random.nextInt(105) + 150;
            blue = random.nextInt(105) + 150;
        } else {
            red = random.nextInt(190);
            green = random.nextInt(190);
            blue = random.nextInt(190);
        }
        //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
        return Color.rgb(red, green, blue);
    }
}
