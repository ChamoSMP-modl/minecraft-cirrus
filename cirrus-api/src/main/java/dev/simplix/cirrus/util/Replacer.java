package dev.simplix.cirrus.util;

import org.jspecify.annotations.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Replacer {

    public String replace(@NonNull String input, @NonNull String... replacements) {
        String result = input;
        for (int i = 0; i < replacements.length; i++) {
            result = result.replace("{" + i + "}", replacements[i]);
        }
        return result;
    }
}
