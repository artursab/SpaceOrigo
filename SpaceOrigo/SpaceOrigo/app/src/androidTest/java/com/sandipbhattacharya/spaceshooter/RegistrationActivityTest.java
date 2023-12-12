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
public class RegistrationActivityTest {

    @Rule
    public ActivityTestRule<RegistrationActivity> activityRule = new ActivityTestRule<>(RegistrationActivity.class);

    @Test
    public void testRegistrationProcess() {
        // Input some data into the registration form
        Espresso.onView(ViewMatchers.withId(R.id.editTextUsername)).perform(ViewActions.typeText("TestUser"));
        Espresso.onView(ViewMatchers.withId(R.id.editTextEmail)).perform(ViewActions.typeText("test@example.com"));
        Espresso.onView(ViewMatchers.withId(R.id.editTextPassword)).perform(ViewActions.typeText("password"));

        // Click the registration button
        Espresso.onView(ViewMatchers.withId(R.id.buttonRegister)).perform(ViewActions.click());

        // Add assertions to check if registration is successful



        // You might want to check if the user is redirected to a different activity or view
    }

}









