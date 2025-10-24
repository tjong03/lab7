package com.example.androiduitesting;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static org.hamcrest.Matchers.anything;

/**
 * Espresso tests for activity switching, displayed city name, and back navigation.
 * These tests add a city through the UI instead of relying on preloaded data.
 */
@RunWith(AndroidJUnit4.class)
public class ShowActivityTest {

    private static final String TEST_CITY = "Edmonton";

    /**
     * Helper: add a city via the UI (click add, type text, confirm).
     */
    private void addCityThroughUI(String cityName) {
        // Ensure MainActivity is launched by caller
        // Click the ADD CITY button to reveal the name entry field
        onView(withId(R.id.button_add)).perform(click());

        // Type the city name into the EditText and close keyboard
        onView(withId(R.id.editText_name)).perform(typeText(cityName), closeSoftKeyboard());

        // Click CONFIRM to add the city to the ListView
        onView(withId(R.id.button_confirm)).perform(click());
    }

    /**
     * Test 1: Clicking a newly-added city opens ShowActivity (city_name view displayed).
     */
    @Test
    public void testActivitySwitch_onListItemClick_opensShowActivity() {
        ActivityScenario.launch(MainActivity.class);

        // Add a city via UI
        addCityThroughUI(TEST_CITY);

        // Click the first list item (position 0)
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());

        // Check that ShowActivity's city_name view is displayed
        onView(withId(R.id.city_name)).check(matches(isDisplayed()));
    }

    /**
     * Test 2: The displayed city name matches the clicked item after adding it.
     */
    @Test
    public void testCityNameConsistency_afterClick_displaysCorrectName() {
        ActivityScenario.launch(MainActivity.class);

        // Add a city and click it
        addCityThroughUI(TEST_CITY);
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());

        // Assert the text shown equals the test city name
        onView(withId(R.id.city_name)).check(matches(withText(TEST_CITY)));
    }

    /**
     * Test 3: Back button returns to MainActivity after adding and clicking a city.
     */
    @Test
    public void testBackButton_returnsToMainActivity() {
        ActivityScenario.launch(MainActivity.class);

        // Add a city and click it to open ShowActivity
        addCityThroughUI(TEST_CITY);
        onData(anything()).inAdapterView(withId(R.id.city_list)).atPosition(0).perform(click());

        // Click the back button in ShowActivity
        onView(withId(R.id.back_button)).perform(click());

        // After finishing ShowActivity, MainActivity's ListView should be visible again
        onView(withId(R.id.city_list)).check(matches(isDisplayed()));
    }
}
