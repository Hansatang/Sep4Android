package com.example.sep4android;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.icu.util.Calendar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TimePicker;
import android.widget.Toolbar;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
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
public class MainActivityInstrumentedTest {

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
    onView(withId(R.id.deviceNameText)).check(matches(isDisplayed()));
    onView(withId(R.id.roomNameText)).check(matches(isDisplayed()));
  }

  @Test
  public void test_Add_New_Room(){
    onView(withId(R.id.fabCreateRoom)).perform(click());

    onView(withId(R.id.deviceNameText)).perform(typeText("TestDevice"));
    onView(withId(R.id.roomNameText)).perform(typeText("TestRoom"));
    onView(withId(R.id.CreateRoom)).perform(click());
  }

  @Test
  public void test_Change_Room_Name(){
    onView(withId(R.id.room_container)).perform(swipeRight());
    onView(withId(R.id.nameText)).perform(typeText("TestRoom1"));
    onView(withId(R.id.changeNameButton));
  }

  @Test
  public void test_Navigation_Bar_Visible(){
    ViewInteraction appCompatImageButton = onView(
            allOf(withContentDescription("Open navigation drawer"),
                    childAtPosition(allOf(withId(R.id.topAppBar),
                            childAtPosition(withId(R.id.app_bar_main), 2)), 2),
                    isDisplayed()));
  }

  @Test
  public void test_Navigation_To_Measurements() {
    ViewInteraction appCompatImageButton = onView(
            allOf(withContentDescription("Open navigation drawer"),
                    childAtPosition(allOf(withId(R.id.topAppBar),
                            childAtPosition(withId(R.id.app_bar_main), 2)), 2)));
    appCompatImageButton.perform(click());
    onView(withId(R.id.Archive)).perform(click());

    onView(withId(R.id.sp)).check(matches(isDisplayed()));
    onView(withId(R.id.measurement_rv)).check(matches(isDisplayed()));
  }

  @Test
  public void test_Recycle_View_In_Measurements(){
    ViewInteraction appCompatImageButton = onView(
            allOf(withContentDescription("Open navigation drawer"),
                    childAtPosition(allOf(withId(R.id.topAppBar),
                            childAtPosition(withId(R.id.app_bar_main), 2)), 2)));
    appCompatImageButton.perform(click());
    onView(withId(R.id.Archive)).perform(click());

    ViewInteraction recyclerView = onView(allOf(withId(R.id.measurement_rv),
            childAtPosition(withId(R.id.measurement_linearLayout), 0)));
    recyclerView.perform(actionOnItemAtPosition(0, click()));
  }

  @Test
  public void test_Navigation_To_Statistics() {
    ViewInteraction appCompatImageButton = onView(
            allOf(withContentDescription("Open navigation drawer"),
                    childAtPosition(allOf(withId(R.id.topAppBar),
                            childAtPosition(withId(R.id.app_bar_main), 2)), 2)));
    appCompatImageButton.perform(click());
    onView(withId(R.id.Statistics)).perform(click());

    onView(withId(R.id.sp)).check(matches(isDisplayed()));
    onView(withId(R.id.tempBarchart)).check(matches(isDisplayed()));
    onView(withId(R.id.humidityBarchart)).check(matches(isDisplayed()));
    onView(withId(R.id.co2Barchart)).check(matches(isDisplayed()));
  }

  @Test
  public void test_Navigation_To_TempThreshold() {
    ViewInteraction appCompatImageButton = onView(
            allOf(withContentDescription("Open navigation drawer"),
                    childAtPosition(allOf(withId(R.id.topAppBar),
                            childAtPosition(withId(R.id.app_bar_main), 2)), 2)));
    appCompatImageButton.perform(click());
    onView(withId(R.id.TemperatureThreshold)).perform(click());

    onView(withId(R.id.sp_temperature)).check(matches(isDisplayed()));
    onView(withId(R.id.fab_add_new_threshold_temperature)).check(matches(isDisplayed()));
  }

  @Test
  public void test_Add_TemThreshold(){
    ViewInteraction appCompatImageButton = onView(
            allOf(withContentDescription("Open navigation drawer"),
                    childAtPosition(allOf(withId(R.id.topAppBar),
                            childAtPosition(withId(R.id.app_bar_main), 2)), 2)));
    appCompatImageButton.perform(click());
    onView(withId(R.id.TemperatureThreshold)).perform(click());

    onView(withId(R.id.fab_add_new_threshold_temperature)).perform(click());

    onView(withId(R.id.select_start_time)).perform(click());
    Calendar calendar = Calendar.getInstance();
    onView(isAssignableFrom(TimePicker.class)).perform(
            PickerActions.setTime(
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE) + 2
            )
    );
    onView(withText("OK")).perform(click());

    onView(withId(R.id.select_end_time)).perform(click());
    Calendar calendar1 = Calendar.getInstance();
    onView(isAssignableFrom(TimePicker.class)).perform(
            PickerActions.setTime(
                    calendar1.get(Calendar.HOUR_OF_DAY),
                    calendar1.get(Calendar.MINUTE) + 4
            )
    );
    onView(withText("OK")).perform(click());

    onView(withId(R.id.select_start_value)).perform(swipeUp());
    onView(withId(R.id.select_end_value)).perform(swipeDown());
    onView(withId(R.id.add_button)).perform(click());

    onView(withId(R.id.temperature_threshold_rv)).check(matches(isDisplayed()));
  }

  @Test
  public void test_Navigation_To_HumThreshold() {
    ViewInteraction appCompatImageButton = onView(
            allOf(withContentDescription("Open navigation drawer"),
                    childAtPosition(allOf(withId(R.id.topAppBar),
                            childAtPosition(withId(R.id.app_bar_main), 2)), 2)));
    appCompatImageButton.perform(click());
    onView(withId(R.id.HumidityThreshold)).perform(click());

    onView(withId(R.id.sp_humidity)).check(matches(isDisplayed()));
    onView(withId(R.id.fab_add_new_threshold_humidity)).check(matches(isDisplayed()));
  }

  @Test
  public void test_Add_HumThreshold(){
    ViewInteraction appCompatImageButton = onView(
            allOf(withContentDescription("Open navigation drawer"),
                    childAtPosition(allOf(withId(R.id.topAppBar),
                            childAtPosition(withId(R.id.app_bar_main), 2)), 2)));
    appCompatImageButton.perform(click());
    onView(withId(R.id.HumidityThreshold)).perform(click());

    onView(withId(R.id.fab_add_new_threshold_humidity)).perform(click());

    onView(withId(R.id.select_start_time)).perform(click());
    Calendar calendar = Calendar.getInstance();
    onView(isAssignableFrom(TimePicker.class)).perform(
            PickerActions.setTime(
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE) + 2
            )
    );
    onView(withText("OK")).perform(click());

    onView(withId(R.id.select_end_time)).perform(click());
    Calendar calendar1 = Calendar.getInstance();
    onView(isAssignableFrom(TimePicker.class)).perform(
            PickerActions.setTime(
                    calendar1.get(Calendar.HOUR_OF_DAY),
                    calendar1.get(Calendar.MINUTE) + 4
            )
    );
    onView(withText("OK")).perform(click());

    onView(withId(R.id.select_start_value)).perform(swipeUp());
    onView(withId(R.id.select_end_value)).perform(swipeDown());
    onView(withId(R.id.add_button)).perform(click());

    onView(withId(R.id.humidity_threshold_rv)).check(matches(isDisplayed()));
  }

  @Test
  public void test_Navigation_To_Settings() {
    openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
    onView(withText("Settings")).perform(click());

    onView(withId(R.id.oldPass)).check(matches(isDisplayed()));
    onView(withId(R.id.newPass)).check(matches(isDisplayed()));
    onView(withId(R.id.GoneGoogle)).check(matches(isDisplayed()));
    onView(withId(R.id.repeatNewPass)).check(matches(isDisplayed()));
    onView(withId(R.id.savePassButton)).check(matches(isDisplayed()));
    onView(withId(R.id.deleteAccountButton)).check(matches(isDisplayed()));
    onView(withId(R.id.changeThemeButton)).check(matches(isDisplayed()));
  }

  @Test
  public void test_Setting_Change_Theme(){
    openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
    onView(withText("Settings")).perform(click());

    ViewInteraction materialButton2 = onView(
            allOf(withId(R.id.changeThemeButton), withText("Change"),
                    childAtPosition(
                            allOf(withId(R.id.constraintLayout),
                                    childAtPosition(
                                            withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                            0)),
                            2),
                    isDisplayed()));
    materialButton2.perform(click());
    onView(withId(R.id.oldPass)).check(matches(isDisplayed()));
    onView(withId(R.id.newPass)).check(matches(isDisplayed()));
    onView(withId(R.id.GoneGoogle)).check(matches(isDisplayed()));
    onView(withId(R.id.repeatNewPass)).check(matches(isDisplayed()));
    onView(withId(R.id.savePassButton)).check(matches(isDisplayed()));
    onView(withId(R.id.deleteAccountButton)).check(matches(isDisplayed()));
    onView(withId(R.id.changeThemeButton)).check(matches(isDisplayed()));

    materialButton2.perform(click());
  }

  @Test
  public void test_Setting_Change_Password(){
    openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
    onView(withText("Settings")).perform(click());

    onView((withId(R.id.repeatNewPass))).perform(typeText("newPassTest1"));
    onView((withId(R.id.newPass))).perform(typeText("newPassTest1"));
    onView((withId(R.id.oldPass))).perform(typeText("oldPassTest1"));
    Espresso.pressBack();

    onView(withId(R.id.savePassButton)).perform(click());
  }

  @Test
  public void test_Delete_Room(){
    onView(withId(R.id.room_container)).perform(swipeRight());
    onView(withId(R.id.deleteButton));
  }

  @Test
  public void test_Setting_Delete_Account(){
    openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
    onView(withText("Settings")).perform(click());

    onView(withId(R.id.deleteAccountButton)).perform(click());
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