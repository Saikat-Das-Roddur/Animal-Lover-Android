package org.richit.animal.Youtube;

public interface YoutubeListener {
    void onJsonDataReceived(String updateModel);

    void onError(String error);
}