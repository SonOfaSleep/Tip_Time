package com.example.tiptime

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.assertion.ViewAssertions.matches
import org.hamcrest.Matchers.containsString
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sonofasleep.tiptime.MainActivity
import com.sonofasleep.tiptime.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculatorTests {
    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)

    private val costView: ViewInteraction = onView(withId(R.id.cost_of_service_edit_text))
    private val personsNumView: ViewInteraction = onView(withId(R.id.number_of_persons_edit_text))
    private val calcButtonView: ViewInteraction = onView(withId(R.id.calculate_button))
    private val tipResultView: ViewInteraction = onView(withId(R.id.tip_result))

    @Test
    fun calculate_10_percent_tip() {
        costView.perform(typeText("50.00"))

        personsNumView
            .perform(typeText("1"))
            .perform(ViewActions.closeSoftKeyboard())

        calcButtonView.perform(click())

        tipResultView.check(matches(withText(containsString("$5.00"))))
    }

    @Test
    fun calculate_15_percent_tip() {
        costView.perform(typeText("100.00"))

        personsNumView
            .perform(typeText("1"))
            .perform(ViewActions.closeSoftKeyboard())

        onView(withId(R.id.option_fifteen_percent)).perform(click())

        calcButtonView.perform(click())

        tipResultView.check(matches(withText(containsString("$15.00"))))
    }

    @Test
    fun calculate_7_percent_tip() {
        costView.perform(typeText("100.00"))

        personsNumView
            .perform(typeText("1"))
            .perform(ViewActions.closeSoftKeyboard())

        onView(withId(R.id.option_seven_percent)).perform(click())

        onView(withId(R.id.round_up_switch)).perform(click())

        calcButtonView.perform(click())

        tipResultView.check(matches(withText(containsString("$7.00"))))
    }
}