package com.example.radhikayusuf.bakingapp.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.widget.RemoteViews;

import com.example.radhikayusuf.bakingapp.R;
import com.example.radhikayusuf.bakingapp.ui.detail.DetailActivity;
import com.example.radhikayusuf.bakingapp.ui.main_recipe.MainActivity;

/**
 * @author radhikayusuf.
 */

public class IngredientsWidgetProvider extends AppWidgetProvider {

    public static final String ACTION_DATA_UPDATED = "com.example.radhikayusuf.bakingapp.ACTION_DATA_UPDATED";

    @SuppressLint("PrivateResource")
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
//        if (QuoteSyncJob.ACTION_DATA_UPDATED.equals(intent.getAction())) {
//            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
//                    new ComponentName(context, getClass()));
//            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_ingredient_widget);
//        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void setRemoteAdapter(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(R.id.list_ingredient_widget,
                new Intent(context, IngredientsWidgetService.class));
    }

    @SuppressWarnings("deprecation")
    private void setRemoteAdapterV11(Context context, @NonNull final RemoteViews views) {
        views.setRemoteAdapter(0, R.id.list_ingredient_widget,
                new Intent(context, IngredientsWidgetService.class));
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.text_title_widget, pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            setRemoteAdapter(context, remoteViews);
        } else {
            setRemoteAdapterV11(context, remoteViews);
        }

        Intent clickIntentTemplate = new Intent(context, DetailActivity.class);

        PendingIntent pendingIntentTemplate = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            pendingIntentTemplate = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(clickIntentTemplate)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        if (pendingIntentTemplate != null) {
            remoteViews.setPendingIntentTemplate(R.id.list_ingredient_widget, pendingIntentTemplate);
            remoteViews.setEmptyView(R.id.list_ingredient_widget, R.id.widget_empty);
            remoteViews.setContentDescription(R.id.list_ingredient_widget, context.getString(R.string.widget_cd));
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }

    }


}
