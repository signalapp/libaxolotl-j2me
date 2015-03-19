package org.whispersystems.libaxolotl;

public class AxolotlAddress {

  private final String name;
  private final int    deviceId;

  public AxolotlAddress(String name, int deviceId) {
    this.name     = name;
    this.deviceId = deviceId;
  }

  public String getName() {
    return name;
  }

  public int getDeviceId() {
    return deviceId;
  }

//  @Override
  public String toString() {
    return name + ":" + deviceId;
  }

//  @Override
  public boolean equals(Object other) {
    if (other == null)                       return false;
    if (!(other instanceof  AxolotlAddress)) return false;

    AxolotlAddress that = (AxolotlAddress)other;
    return this.name.equals(that.name) && this.deviceId == that.deviceId;
  }

//  @Override
  public int hashCode() {
    int hash = 7;
    hash = 31 * hash + deviceId;
    hash = 31 * hash + name.hashCode();
    return hash;
  }
}
