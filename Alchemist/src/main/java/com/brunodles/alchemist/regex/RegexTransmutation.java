package com.brunodles.alchemist.regex;

import com.brunodles.alchemist.AnnotationInvocation;
import com.brunodles.alchemist.AnnotationTransmutation;
import com.brunodles.utils.LRUCache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTransmutation implements AnnotationTransmutation<Regex, List<String>, List<String>> {

    private static Map<String, Pattern> REGEX_MAP = new LRUCache<>(15);

    @Override
    public List<String> transform(AnnotationInvocation<Regex, List<String>> value) {
        String regex = value.annotation.value();
        Pattern pattern;
        if (REGEX_MAP.containsKey(regex)) {
            pattern = REGEX_MAP.get(regex);
        } else {
            pattern = Pattern.compile(regex);
            REGEX_MAP.put(regex, pattern);
        }
        ArrayList<String> result = new ArrayList<>();
        for (String input : value.result) {
            Matcher matcher = pattern.matcher(input);
            while (matcher.find()) {
                result.add(matcher.group(1));
            }
        }
        return result;
    }
}
