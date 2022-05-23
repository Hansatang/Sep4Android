package com.example.sep4android;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
  @Rule
  public ActivityTestRule<MainActivity> mainTestRule =
      new ActivityTestRule<>(MainActivity.class);

  @Before
  public void setUp() throws Exception {
    final MainActivity activity = mainTestRule.getActivity();
  }

  @Test
  public void useAppContext() {
    Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
    assertEquals("com.example.sep4android", appContext.getPackageName());
  }

  @Test
  public void test_Move_To_Create_Room_visible() {
    onView(withId(R.id.fabCreateRoom)).check(matches(isDisplayed()));
    onView(withId(R.id.fabCreateRoom)).perform(click());
    onView(withId(R.id.CreateRoom)).check(matches(isDisplayed()));
  }

  @Test
  public void test_Navigation_To_Measurements() {
    ViewInteraction appCompatImageButton = onView(
        allOf(withContentDescription("Open navigation drawer"),
            childAtPosition(allOf(withId(R.id.topAppBar),
                childAtPosition(withId(R.id.app_bar_main), 2)), 2),
            isDisplayed()));
    appCompatImageButton.perform(click());
    onView(withId(R.id.Archive)).perform(click());


    ViewInteraction recyclerView = onView(allOf(withId(R.id.measurement_rv),
        childAtPosition(withId(R.id.measurement_linearLayout), 0)));
    recyclerView.perform(actionOnItemAtPosition(0, click()));
  }


  private static Matcher<View> childAtPosition(
      final Matcher<View> parentMatcher, final int position) {

    return new TypeSafeMatcher<View>() {
      @Override
      public void describeTo(Description description) {
        description.appendText("Child at position " + position + " in parent ");
        parentMatcher.describeTo(description);
      }

      @Override
      public boolean matchesSafely(View view) {
        ViewParent parent = view.getParent();
        return parent instanceof ViewGroup && parentMatcher.matches(parent)
            && view.equals(((ViewGroup) parent).getChildAt(position));
      }
    };
  }
}