package at.team2.connector.dto.small;

public class BookSmallDto extends MediaSmallDto {
    private int _id;
    private Integer _edition;

    public int getId() {
        return _id;
    }

    public Integer getEdition() {
        return _edition;
    }

    public void setId(int id) {
        _id = id;
    }

    public void setEdition(Integer edition) {
        _edition = edition;
    }
}