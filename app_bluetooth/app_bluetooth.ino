#include <SoftwareSerial.h>

void setup() {
  Serial.begin(115200);
  Serial1.begin(9600);
}

void loop() {
  // if there's any serial available, read it:
  while (Serial1.available()) {
    Serial.println(Serial1.parseInt());
    /*

      // look for the next valid integer in the incoming serial stream:
      int red   = Serial1.parseInt();
      int green = Serial1.parseInt();   // do it again:
      int blue  = Serial1.parseInt();   // do it again:

      // look for the newline. That's the end of your statement
      if (Serial1.read() == '\n') {

        // constrain the values to 0 - 255
        red   = constrain(red, 0, 255);
        green = constrain(green, 0, 255);
        blue  = constrain(blue, 0, 255);

        // send some data back
        Serial1.println(
          "BT:"
          + String(red) + ","
          + String(green) + ","
          + String(blue)
        );
        // print some data back
        Serial.println(
          "Device-received:"
          + String(red) + ","
          + String(green) + ","
          + String(blue)
        );

      }
    */
  }
}
