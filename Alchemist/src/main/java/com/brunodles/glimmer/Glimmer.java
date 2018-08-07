package com.brunodles.glimmer;

/**
 * A wrapper to with with reflection with types that uses generics.
 */
public final class Glimmer {

    private Glimmer() {
    }

    /**
     * Returns a ClassGlimmer for targetClass.
     *
     * @param targetClass the class to lookup for generics.
     * @return A ClassGlimmer
     */
    public static ClassGlimmer forClass(Class targetClass) {
        return new ClassGlimmer(targetClass);
    }
}
