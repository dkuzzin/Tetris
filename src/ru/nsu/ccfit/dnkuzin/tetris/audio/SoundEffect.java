package ru.nsu.ccfit.dnkuzin.tetris.audio;

public enum SoundEffect {
    BUTTON("/sounds/button.wav"),
    ROTATE("/sounds/rotate.wav"),
    DROP("/sounds/drop.wav"),
    CLEAR("/sounds/clear.wav"),
    LOCK("/sounds/select.wav"),
    MOVE("/sounds/rotate.wav");



    private final String path;

    SoundEffect(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}