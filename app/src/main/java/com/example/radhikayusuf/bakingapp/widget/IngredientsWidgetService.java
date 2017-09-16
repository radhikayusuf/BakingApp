package com.example.radhikayusuf.bakingapp.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * @author radhikayusuf.
 */

public class IngredientsWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsViewFactory(this.getApplicationContext(), intent);
    }
}
