package com.brunodles.glimmer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ParameterizedTypeGlimmer extends BaseGlimmer<ParameterizedType> {

    ParameterizedTypeGlimmer(ParameterizedType type) {
        super(type);
    }

    /**
     * Get generics type arguments for parameterized type.
     *
     * <p>This method will provide a TypeGlimmer for all typeArguments</p>
     *
     * @return An array of TypeGlimmer built from type.getActualTypeArguments.
     */
    public TypeGlimmer[] arguments() {
        Type[] typeArguments = type.getActualTypeArguments();
        TypeGlimmer[] typeGlimmers = new TypeGlimmer[typeArguments.length];
        for (int i = 0; i < typeArguments.length; i++)
            typeGlimmers[i] = new TypeGlimmer(typeArguments[i]);
        return typeGlimmers;
    }
}
