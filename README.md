# About
This is a Java API for the Novilation Launchpad.
The documentation is still pending but the API is quite straightforward:

``` java

// Create a new instance, fade all pads with an timeout of 50ms for each stepp and 50ms per pad
// and create a new press listener

Launchpad la = new Launchpad(Channel.C1).fadePads(Color.BLANK, 10, 10).pressed("/", { l, m ->
            l.move("/2") //Move to the /2 preset
            l.fadePads(Color.RED, 10)
        }).pressed("/2", { l, m ->
            l.move("/") //Move to the / preset
            l.fadePads(Color.GREEN, 10)

        })
```

