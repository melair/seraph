# Seraph Pixel Protocol

A protocol to replace PixelPusher for the specific use of updating LED matrix
screens.

All modifications take place on an off screen buffer until a SYNC command is
received.

Any packets with a lower sequence number then has already been received must be
ignored. 

## Operation

While the intention of Seraph's protocol to allow small updates to a screen, rather 
entire frame redraws or even whole lines. UDP is a lossy protocol and as such the client
should simulate a key frame on a semi regular occasion.

To do this, send a sequence of packets with a FILL for black, SET all packets, then SYNC.

It's recommended that the initial packet sent from a client to the display is that
of a FILL for black, or a key frame.

## Packet

* uint32: Sequence number, used for receiving server to detect packet loss
* [Command]: Unbound list of commands

### Command

An individual instruction to the display.

* uint8: Opcode for command, rest of data is opcode specific

#### [0] Sync

Sync the offscreen buffer to onscreen at next vsync.

#### [1] Fill

Fill the entire screen with the colour provided.

* uint8: red pixel
* uint8: green pixel
* uint8: blue pixel

#### [2] Set Position

Set the position of the cursor, should default to 0,0 on receipt of a packet.

* uint16: x
* uint16: y

#### [3] Advance Position

Move the cursor forward by a number of pixels, wrapping as needed at ends of rows.

* uint8: delta

#### [4] Set Pixels

Sets an array of pixels to colours, if the array is longer then the width then
it should wrap to the next row.

* uint16: pixel count
* Pixels (repeated until pixel count)
  * uint8: red pixel
  * uint8: green pixel
  * uint8: blue pixel

## Efficiency

The PixelPusher protocol (which this is intended to replace for Seraph) is very 
efficient when large updates to screens are needed, but does not allow control 
of syncing frames (which results in screen tearing, unless with some implementations
you updates the whole screen in one packet).

The Seraph protocol has more over head as it has op codes and a cursor to move around,
as such it requires intelligence in the client to get near the same levels of efficiency.

### Example

Assuming we have a 128x64 RGB pixel display, assuming MTU of 1460. We can see for updates which
involve less then 35 pixels then Seraph can update anywhere on the entire display for the same
byte count as pixel pusher can update one line. 

In a display however it's likely we'll need to update few pixels in many lines, as such Seraph
will be clearly more efficient. For example drawing a vertical line requires a complete frame
to be send with PixelPusher, with Seraph it would just require two packets of pixel updates.

Updating one line is more expensive with Sereph, however for information displays is an unlikely
operation. Doing a whole frame is more expensive by 63 bytes, however there are 4 fewer packets.

#### Updating One Pixel

* PixelPusher
  * 4 byte sequence
  * 1 byte for strip, 3 (colours) * 128 (width) 
  * **Total is 389**

* Sereph
  * 4 byte sequence
  * Set Position: 1 byte op code, 2 bytes X, 2 bytes Y
  * Set Pixel: 1 byte op code, 2 bytes count, 3 bytes colours
  * Sync: 1 byte
  * **Total is 16**

#### Updating One Strip

* PixelPusher
  * 4 byte sequence
  * 1 byte for strip, 3 (colours) * 128 (width) 
  * **Total is 389**
  
* Sereph
  * 4 byte sequence
  * Set Position: 1 byte op code, 2 bytes X, 2 bytes Y
  * Set Pixel: 1 byte op code, 2 bytes count, 3 (colours) * 128 (width)
  * Sync: 1 byte
  * **Total is 397**

#### Updating Whole Display

* Pixel Pusher
  * Three Strips Sends (21 packets)
    * 4 byte sequence
    * 1 byte for strip, 3 (colours) * 128 (width) 
    * 1 byte for strip, 3 (colours) * 128 (width) 
    * 1 byte for strip, 3 (colours) * 128 (width) 
    * **Total is 1159**
  * One Strip Send (1 packet)
    * 4 byte sequence
    * 1 byte for strip, 3 (colours) * 128 (width) 
    * **Total is 389**
  * **Total is 21 packets, 24728 bytes**

* Sereph
 * Full packet sends (16 packets)
   * 4 byte sequence
   * Set Position: 1 byte op code, 2 bytes X, 2 bytes Y
   * Set Pixel: 1 byte op code, 2 bytes count, 3 bytes colours (482 pixels)
   * **Total is 1458**
 * Remaining send (1 packet)
   * 4 byte sequence
   * Set Position: 1 byte op code, 2 bytes X, 2 bytes Y
   * Set Pixel: 1 byte op code, 2 bytes count, 3 bytes colours (480 pixels)
   * Sync: 1 byte
   * **Total is 1463**
 * **Total is 17 packets, 24791 bytes**
 

 
   