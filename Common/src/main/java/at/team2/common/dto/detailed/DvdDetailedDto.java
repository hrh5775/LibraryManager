package at.team2.common.dto.detailed;

public class DvdDetailedDto extends MediaDetailedDto {
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