package at.team2.server.helper;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import java.text.ParseException;
import java.time.LocalDateTime;

public class DateTimeHelper {
    private static final String _iCalDateTimeFormat = "yyyy-dd-mm'T'HH:mm";
    private static final TimeZoneRegistry _timeZoneRegistry = TimeZoneRegistryFactory.getInstance().createRegistry();

    public static DateTime convertToICalDateTime(LocalDateTime dateTime) {
        DateTime result = null;

        try {
            result = new DateTime(dateTime.toString(), _iCalDateTimeFormat, _timeZoneRegistry.getTimeZone("UTC"));
        } catch (ParseException e) {
        }

        return result;
    }
}