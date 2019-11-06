package de.jonasborn.launchpad.api

enum Color {


    R0G0(0x00), R1G0(0x01), R2G0(0x02), R3G0(0x03), //
    R0G1(0x10), R1G1(0x11), R2G1(0x12), R3G1(0x13), //
    R0G2(0x20), R1G2(0x21), R2G2(0x22), R3G2(0x23), //
    R0G3(0x30), R1G3(0x31), R2G3(0x32), R3G3(0x33);


    static List<List<Color>> mappings = [

            [R0G0, R0G1, R0G2, R0G3],

            [ R1G0, R1G1, R1G2, R1G3],

            [ R2G0, R2G1, R2G2, R2G3],

            [ R3G0, R3G1, R3G2, R3G3],


            [R0G0, R1G0, R2G0, R3G0],
            [R0G1, R1G1, R2G1, R3G1],
            [R0G2, R1G2, R2G2, R3G2],
            [R0G3, R1G3, R2G3, R3G3],

            [R0G0, R0G2, R1G3],

            [R0G0, R0G1, R1G2, R2G3],

            [R0G0, R0G0, R1G1, R2G2, R3G3],

            [R0G0, R1G0, R2G1, R3G2],

            [R0G0, R2G0, R3G1],


            [R1G0, R0G1],

            [R2G0, R1G1, R0G2],

            [R3G0, R2G1, R1G2, R0G3],

            [R3G1, R2G2, R1G3],

            [R3G2, R2G3]


    ]

    public List<Color> fade(Color to) {

        if (this == to) return [this]

        def route = mappings.find {it.contains(this) && it.contains(to)}

        if (route == null)  route = [to]

        def first = route.indexOf(this)

        def second = route.indexOf(to)

        if (first > second) {
            return route.reverse().subList(second+1, first+1)
        } else {
            return route.subList(first +1, second+1)
        }

    }


    /**
     * Alias for {@link #R0G0}
     */
    public static final Color BLANK = R0G0
    /**
     * Alias for {@link #R0G3}
     */
    public static final Color GREEN = R0G3
    /**
     * Alias for {@link #R3G0}
     */
    public static final Color RED = R3G0
    /**
     * Alias for {@link #R3G3}
     */
    public static final Color AMBER = R3G3

    private int code

    private Color(int code) {
        this.code = code
    }

    /**
     * Provide the code to be sent to the launchpad device.
     *
     * @return the code to be sent to the launchpad device
     */
    int getCode() {
        return code
    }
}
