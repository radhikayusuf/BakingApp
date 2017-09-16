package com.example.radhikayusuf.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.TextView;

import com.example.radhikayusuf.bakingapp.ui.main_recipe.MainActivity;
import com.example.radhikayusuf.bakingapp.util.TestUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.core.IsNot.not;

/**
 * @author radhikayusuf.
 */

@RunWith(AndroidJUnit4.class)
public class IntentTest {

    private String text = "";

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void stubAllExternalIntents() {
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }


    @Test
    public void testClickRecyclerItem(){
        text = TestUtils.getText(TestUtils.withRecyclerView(R.id.recycler_main)
                .atPositionOnView(0, R.id.title));

        onView(TestUtils.withRecyclerView(R.id.recycler_main).atPosition(0))
                .perform(click());

        onView(allOf(instanceOf(TextView.class), withParent(withId(R.id.toolbar))))
                .check(matches(withText(text)));
    }

}
