package de.jonasborn.launchpad.api

class ColoredPressable {

    Pressable pressable
    Color color

    ColoredPressable(Pad pad, Color color) {
        this.pressable = pad
        this.color = color
    }
}
