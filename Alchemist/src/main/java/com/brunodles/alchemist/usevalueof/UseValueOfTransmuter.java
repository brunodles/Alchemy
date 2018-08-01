package com.brunodles.alchemist.usevalueof;

import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.Transmuter;
import com.brunodles.alchemist.transformers.TransformerFor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@TransformerFor(UseValueOf.class)
public class UseValueOfTransmuter<OUTPUT> implements Transmuter<AnnotationInvocation<UseValueOf, List<String>>,
        List<OUTPUT>> {

    private static final String METHOD_NAME = "valueOf";

    @Override
    public List<OUTPUT> transform(AnnotationInvocation<UseValueOf, List<String>> value) {
        Class<OUTPUT> resultClass = (Class<OUTPUT>) value.getMethodRealReturnType();
        Method method = methodFor(resultClass);
        if (method == null)
            throw new RuntimeException("Return type does not contain \"valueOf\" method.");
        ArrayList<OUTPUT> result = new ArrayList<>();
        for (String s : value.result)
            result.add(invokeMethod(method, s));
        return result;
    }

    private OUTPUT invokeMethod(@NotNull Method method, @NotNull String value) {
        try {
            return (OUTPUT) method.invoke(null, value);
        } catch (Exception ignored) {
            // ignored
        }
        return null;
    }

    @Nullable
    private Method methodFor(@NotNull Class<OUTPUT> resultClass) {
        try {
            return resultClass.getDeclaredMethod(METHOD_NAME, String.class);
        } catch (NoSuchMethodException ignored) {
            // ignored
        }
        try {
            return resultClass.getMethod(METHOD_NAME, String.class);
        } catch (NoSuchMethodException ignored) {
            // ignored
        }
        return null;
    }
}
