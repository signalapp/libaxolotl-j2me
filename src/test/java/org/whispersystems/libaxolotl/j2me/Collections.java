package org.whispersystems.libaxolotl.j2me;

import java.util.Random;
import java.util.Vector;

public class Collections {
  public static void shuffle(Vector list, Random random) {
    for (int i=list.size()-1;i>0;i--) {
      int    index          = random.nextInt(i + 1);
      Object randomElement  = list.elementAt(index);
      Object currentElement = list.elementAt(i    );

      list.setElementAt(currentElement, index);
      list.setElementAt(randomElement, i);
    }
  }
}
