package at.team2.client.pages.searchMedium.showDetail;

import at.team2.common.dto.detailed.BookDetailedDto;
import at.team2.common.dto.detailed.DvdDetailedDto;
import at.team2.common.dto.detailed.MediaDetailedDto;
import at.team2.common.dto.small.CreatorPersonSmallDto;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class ShowDetail {
    @FXML
    private Label _mediaType;
    @FXML
    private Label _index;
    @FXML
    private Label _available;
    @FXML
    private Label _title;
    @FXML
    private Label _genre;
    @FXML
    private Label _published;
    @FXML
    private TextArea _description;
    @FXML
    private TextArea _publishersPersons;
    @FXML
    private TextArea _comment;

    private MediaDetailedDto _media;

    public ShowDetail(MediaDetailedDto media) {
        _media = media;
    }

    @FXML
    public void initialize() {
        if(_media instanceof BookDetailedDto) {
            // cast the object to the specified type
        } else if(_media instanceof DvdDetailedDto) {
            // cast the object to the specified type
        }

        _mediaType.setText(_media.getMediaType().getName());
        _index.setText(_media.getBaseIndex());
        _available.setText(_media.getAvailable() ? "true" : "false");
        _title.setText(_media.getTitle());
        _genre.setText(_media.getGenre().getName());
        _published.setText(_media.getPublishedDate().toLocalDate().toString());
        _description.setText(_media.getDescription());
        _comment.setText(_media.getComment());

        if(_media.getCreatorPersons() != null) {
            StringBuilder builder = new StringBuilder();

            for(CreatorPersonSmallDto item : _media.getCreatorPersons()) {
                builder.append(item.getFirstName() + " " + item.getLastName() + "\n");
            }

            _publishersPersons.setText(builder.toString());
        }
    }
}