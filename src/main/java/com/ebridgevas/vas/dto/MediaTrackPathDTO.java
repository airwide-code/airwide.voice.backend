package com.ebridgevas.vas.dto;

/**
 * @author david@tekeshe.com
 */
public class MediaTrackPathDTO {

    private final String trackPath;
    private final String trackTitlePath;
    private final Long trackDuration;
    private Integer trackID;

    public MediaTrackPathDTO(String trackPath,
                             String trackTitlePath,
                             Long trackDuration) {
        this.trackPath = trackPath;
        this.trackTitlePath = trackTitlePath;
        this.trackDuration = trackDuration;
        this.trackID = 0;
    }

    public MediaTrackPathDTO(String trackPath,
                             String trackTitlePath,
                             Long trackDuration,
                             Integer trackID) {
        this.trackPath = trackPath;
        this.trackTitlePath = trackTitlePath;
        this.trackDuration = trackDuration;
        this.trackID = trackID;
    }

    public String getTrackPath() {
        return trackPath;
    }

    public String getTrackTitlePath() {
        return trackTitlePath;
    }

    public Long getTrackDuration() {
        return trackDuration;
    }

    public Integer getTrackID() {
        return trackID;
    }

    public void setTrackID(Integer trackID) {
        this.trackID = trackID;
    }

    @Override
    public String toString() {
        return "MediaTrackPathDTO{" +
                "trackPath='" + trackPath + '\'' +
                ", trackTitlePath='" + trackTitlePath + '\'' +
                ", trackDuration=" + trackDuration +
                ", trackID=" + trackID +
                '}';
    }
}
