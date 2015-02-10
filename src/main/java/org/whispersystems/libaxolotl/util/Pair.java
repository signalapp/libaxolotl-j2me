/**
 * Copyright (C) 2014 Open WhisperSystems
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.whispersystems.libaxolotl.util;

public class Pair {
  private final Object v1;
  private final Object v2;

  public Pair(Object v1, Object v2) {
    this.v1 = v1;
    this.v2 = v2;
  }

  public Object first() {
    return v1;
  }

  public Object second() {
    return v2;
  }

  public boolean equals(Object o) {
    return o instanceof Pair &&
        equal(((Pair) o).first(), first()) &&
        equal(((Pair) o).second(), second());
  }

  public int hashCode() {
    return first().hashCode() ^ second().hashCode();
  }

  private boolean equal(Object first, Object second) {
    if (first == null && second == null) return true;
    if (first == null || second == null) return false;
    return first.equals(second);
  }
}
