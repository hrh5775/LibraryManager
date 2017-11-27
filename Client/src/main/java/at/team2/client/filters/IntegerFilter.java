package at.team2.client.filters;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class IntegerFilter implements UnaryOperator<String> {
    private final static Pattern integerPattern = Pattern.compile("[-]?\\d*");

    @Override
    public String apply(String value) {
        return integerPattern.matcher(value).matches() ? value : null;
    }
}