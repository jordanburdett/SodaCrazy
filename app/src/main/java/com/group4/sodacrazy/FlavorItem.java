package com.group4.sodacrazy;

/**
 * FlavorItem contains a string flavor and a string for a hexidecimal color (formatted like "#ffffff")
 * */
class FlavorItem {
    String name; // e.g. "Salted Caramel"
    String color;  // e.g. "#c68e17"

    /**
     * Non-default constructor
     * Used to create new flavorItems
     */
    FlavorItem(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
