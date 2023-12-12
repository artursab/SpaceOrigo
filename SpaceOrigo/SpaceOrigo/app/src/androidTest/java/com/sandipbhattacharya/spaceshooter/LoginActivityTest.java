package com.sandipbhattacharya.spaceshooter;

import static org.junit.Assert.*;


import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void testLoginProcess() {
        // Input login credentials into the login form
        Espresso.onView(ViewMatchers.withId(R.id.editTextUsername)).perform(ViewActions.typeText("TestUser"));
        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword)).perform(ViewActions.typeText("password"));

        // Click the login button
        Espresso.onView(ViewMatchers.withId(R.id.buttonLogin)).perform(ViewActions.click());

        // Add assertions to check if login is successful

        // You might want to check if the user is redirected to a different activity or view
    }
}
