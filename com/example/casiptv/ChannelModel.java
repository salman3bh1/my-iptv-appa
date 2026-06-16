package com.example.casiptv;

public class ChannelModel {
    private String name;
    private String streamIcon;
    private int streamId;

    public ChannelModel() {}

    public ChannelModel(String name, String streamIcon, int streamId) {
        this.name = name;
        this.streamIcon = streamIcon;
        this.streamId = streamId;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStreamIcon() { return streamIcon; }
    public void setStreamIcon(String streamIcon) { this.streamIcon = streamIcon; }

    // Alias for getStreamIcon used in some adapters
    public String getIcon() { return streamIcon; }
    public void setIcon(String icon) { this.streamIcon = icon; }

    public int getStreamId() { return streamId; }
    public void setStreamId(int streamId) { this.streamId = streamId; }
}
