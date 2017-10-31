package at.team2.application.helper;

import org.modelmapper.ModelMapper;

public class MapperHelper {
    private static ModelMapper modelMapper;

    public static ModelMapper getMapper() {
        if(modelMapper == null) {
            modelMapper = new ModelMapper();
            // http://modelmapper.org/user-manual/property-mapping/
            // https://www.programcreek.com/java-api-examples/index.php?api=org.modelmapper.ModelMapper
        }

        return modelMapper;
    }
}
