package com.brunodles.glimmer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public final class TypeHelper {
    private TypeHelper() {
    }

    /**
     * Check if the type is assignable for class.
     *
     * <p>This method can look for parameterized type and normal types too.</p>
     *
     * @param wantedClass The class of the wanted type.
     * @param type        The type you don't know exactly if it is assignable for wantedClass
     * @return true if type is assignable for wantedClass
     */
    public static boolean isAssignable(Class wantedClass, Type type) {
        if (type instanceof ParameterizedType)
            return isAssignable(wantedClass, ((ParameterizedType) type).getRawType());
        if (type instanceof Class)
            try {
                //noinspection unchecked
                return wantedClass.isAssignableFrom((Class<?>) type);
            } catch (Exception ignore) {
                // ignore
            }
        return false;
    }
}
