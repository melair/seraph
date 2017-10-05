# Seraph

Seraph is a toolkit for organising, laying out and manipulating LED matrix screens.

If you wish to make use of the HUB75 server ensure that you check out with submodules.

```git clone --recursive git@github.com:melair/seraph.git```

## Java Modules

Build with:

```bash
mvn test install
```

### Display Library

Building blocks for generating a layout on an LED matrix screen.

### Bitmap Font Library

A simple library for loading and using bitmap fonts.

### Display Demo

A rapidly mutating demonstration application to test Seraph on a larger scale.

## Raspberry Pi HUB75 Server (C++)

A server implementing Seraph's LED matrix display protocol, using Henner Zeller's library to drive HUB75 style LED
matrix displays using a Raspberry Pi's GPIO port. Using a Raspberry Pi Zero W a refresh rate of 170Hz can be reached
with a 128x64 display.

Build with:

```bash
make
```

## License

[GPL v3](https://github.com/melair/seraph/blob/master/LICENSE.md)