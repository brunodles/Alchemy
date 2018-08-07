package com.brunodles.glimmer;

import java.lang.reflect.Type;

/**
 * A Wrapper for Reflection with Generics.
 *
 * <p>This class provides ways to work with generics.</p>
 */
public class ClassGlimmer extends BaseGlimmer<Class> {

    private TypeGlimmer[] genericInterfaces = null;

    ClassGlimmer(Class type) {
        super(type);
    }

    private static TypeGlimmer[] buildGenericInterfaces(final Class targetClass) {
        Type[] genericInterfaces = targetClass.getGenericInterfaces();
        TypeGlimmer[] result = new TypeGlimmer[genericInterfaces.length];
        for (int i = 0; i < genericInterfaces.length; i++)
            result[i] = new TypeGlimmer(genericInterfaces[i]);
        return result;
    }

    /**
     * Get a TypeGlimmer for all generic interfaces.
     *
     * <p>With this method you can iterate over all generic interfaces using a TypeGlimmer.</p>
     *
     * @return an array of TypeGlimmer
     */
    public TypeGlimmer[] getGenericInterfaces() {
        if (genericInterfaces == null)
            genericInterfaces = buildGenericInterfaces(super.type);
        return genericInterfaces;
    }

    /**
     * Return a TypeGlimmer for targetInterface.
     *
     * <p>This method will check for generic annotations that is a instance of targetInterface. Returns null if this
     * class does not implements the interface.
     *
     * @param targetInterface interface to look for
     * @return a TypeGlimmer
     */
    public TypeGlimmer getTypeForGenericInterface(Class targetInterface) {
        for (TypeGlimmer typeGlimmer : getGenericInterfaces()) {
            if (typeGlimmer.isAssignableAs(targetInterface))
                return typeGlimmer;
        }
        return null;
    }
}
