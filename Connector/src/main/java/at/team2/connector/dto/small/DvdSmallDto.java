package at.team2.connector.dto.small;

public class DvdSmallDto extends MediaSmallDto {
    private int _id;
    private Integer _playingTime;

    public int getId() {
        return _id;
    }

    public Integer getPlayingTime() {
        return _playingTime;
    }

    public void setId(int id) {
        _id = id;
    }

    public void setPlayingTime(Integer playingTime) {
        _playingTime = playingTime;
    }
}