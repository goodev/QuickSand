package com.blundell.quicksand;

import android.content.Context;
import android.transition.Transition;

import com.blundell.quicksand.viscosity.Viscosity;
import com.novoda.notils.exception.DeveloperError;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Quicksand allows the monitoring and manipulation of any Android animations.
 * This allows you to speed up animations the user has seen many times, thus allowing you to surprise
 * and delight your users to start with and then get straight to business after they are used to your UX.
 */
public class Quicksand {

    private static TransitionTracker transitionManipulator;

    // This is the public facing API, act accordingly

    /**
     * Initialises the Quicksand library.
     * Only needs to be called once.
     * You will most likely want to call this in onCreate of your (@link android.app.Application}.
     * Using this method means you cannot have different animation degradation speeds per key
     *
     * @param context any context, we will take the application context from this to avoid activity leaks
     */
    public static void create(Context context) {
        create(context, Collections.<String, Viscosity>emptyMap());
    }

    /**
     * Initialises the Quicksand library adding a Map of viscosities which allow you to change transition duration over time.
     * Only needs to be called once.
     * You will most likely want to call this in onCreate of your (@link android.app.Application}.
     *
     * @param context     any context, we will take the application context from this to avoid activity leaks
     * @param viscosities a map which contains key values to allow you to change transition duration over time.
     */
    public static void create(Context context, Map<String, Viscosity> viscosities) {
        Context applicationContext = context.getApplicationContext();
        TransitionCounter counter = TransitionCounter.newInstance(applicationContext);
        DurationCalculator durationCalculator = new DurationCalculator();
        Map<String, Viscosity> viscosityMap = new HashMap<>();
        viscosityMap.putAll(viscosities);
        transitionManipulator = new TransitionTracker(counter, durationCalculator, viscosityMap);

    }

    /**
     * Quicksand will manipulate your transition duration based on the {@link com.blundell.quicksand.viscosity.Viscosity} for the key.
     * Animations can be grouped by the same {@param key} so that the duration is not incremented multiple times for one group.
     * If you do not group by key, each transition will be treated separate and so will have an independent transition duration.
     *
     * @param key        a unique key to group a set of transition
     * @param transition the transition to be manipulated
     */
    public static void trap(String key, Transition transition) {
        checkLibraryInstantiation();
        transitionManipulator.manipulate(key, transition);
    }

    /**
     * Allows you to reset a transition trap, meaning the transition will next run at the original speed
     *
     * @param key the key of the transition you wish to reset
     */
    public static void resetTrap(String key) {
        checkLibraryInstantiation();
        transitionManipulator.resetTransition(key);
    }

    private static void checkLibraryInstantiation() {
        if (transitionManipulator == null) {
            throw new DeveloperError("Please call create(Context) first to initialise this library."); // TODO use arrow logger
        }
    }
}
