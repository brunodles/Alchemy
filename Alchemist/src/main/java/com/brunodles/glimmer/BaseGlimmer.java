package com.brunodles.glimmer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import static com.brunodles.glimmer.TypeHelper.isAssignable;

class BaseGlimmer<TypeT extends Type> {

    final TypeT type;

    BaseGlimmer(TypeT type) {
        this.type = type;
    }

    /**
     * Cast current type and return it as ParameterizedTypeGlimmer.
     *
     * <p>This method may throw {@link ClassCastException} if type is not a {@link ParameterizedType}.</p>
     *
     * @return this type as a ParameterizedTypeGlimmer
     */
    public ParameterizedTypeGlimmer asParameterizedType() {
        return new ParameterizedTypeGlimmer((ParameterizedType) type);
    }

    public boolean isCollection() {
        return isAssignable(Collection.class, type);
    }

    public boolean isParameterizedType() {
        return type instanceof ParameterizedType;
    }

    @Override
    public String toString() {
        return type.toString();
    }

    public boolean isAssignableAs(Class targetInterface) {
        return isAssignable(targetInterface, type);
    }

    public Class asClass() {
        return (Class) type;
    }
}
