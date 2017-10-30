package at.team2.domain.interfaces;

import javafx.util.Pair;
import java.util.List;

public interface Validate<E> {
    /**
     * Validates all attributes. The entire object will be validated
     * @return a list of key-value pairs:
     *         key: specifies the property
     *         value: a detailed error message for the user
     *         is never null
     */
    public List<Pair<E, String>> validate();
}
