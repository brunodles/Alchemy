package com.brunodles.jsoupparser.usevalueof;

import com.brunodles.jsoupparser.AnnotationInvocation;
import com.brunodles.jsoupparser.Transformer;
import com.brunodles.jsoupparser.TransformerFor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@TransformerFor(UseValueOf.class)
public class UseValueOfTransformer<OUTPUT> implements Transformer<AnnotationInvocation<UseValueOf, List<String>>, List<OUTPUT>> {

    private static final String METHOD_NAME = "valueOf";

    @Override
    public List<OUTPUT> transform(AnnotationInvocation<UseValueOf, List<String>> value) {
        Class<OUTPUT> resultClass = (Class<OUTPUT>) value.getMethodRawReturnType();
        Method method = methodFor(resultClass);
        if (method == null)
            return null;
        ArrayList<OUTPUT> result = new ArrayList<>();
        for (String s : value.result) {
            result.add(invokeMethod(method, s));
        }
        return result;
    }

    private OUTPUT invokeMethod(@NotNull Method method, @NotNull String value) {
        try {
            return (OUTPUT) method.invoke(null, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    private Method methodFor(@NotNull Class<OUTPUT> resultClass) {
        try {
            return resultClass.getDeclaredMethod(METHOD_NAME, String.class);
        } catch (NoSuchMethodException ignored) {
        }
        try {
            return resultClass.getMethod(METHOD_NAME, String.class);
        } catch (NoSuchMethodException ignored) {
        }
        return null;
    }
}
