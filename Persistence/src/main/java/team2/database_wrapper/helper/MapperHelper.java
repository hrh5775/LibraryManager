package team2.database_wrapper.helper;

import org.modelmapper.ModelMapper;

public class MapperHelper {
    private static ModelMapper _modelMapper;

    public static ModelMapper getMapper() {
        if(_modelMapper == null) {
            _modelMapper = new ModelMapper();
            // http://modelmapper.org/user-manual/property-mapping/
        }

        return _modelMapper;
    }
}
