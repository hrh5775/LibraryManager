package team2.client.filters;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class DoubleFilter implements UnaryOperator<String> {
    private final static Pattern doublePattern = Pattern.compile("[-]?\\d*.\\d*");

    @Override
    public String apply(String value) {
        return doublePattern.matcher(value).matches() ? value : null;
    }
}
