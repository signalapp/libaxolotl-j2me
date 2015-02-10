/*
 * Copyright (C) 2011 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.whispersystems.libaxolotl.util.guava;

/**
 * Implementation of an {@link Optional} containing a reference.
 */

final class Present extends Optional {
  private final Object reference;

  Present(Object reference) {
    this.reference = reference;
  }

  public boolean isPresent() {
    return true;
  }

  public Object get() {
    return reference;
  }

  public Object or(Object defaultValue) {
    Preconditions.checkNotNull(defaultValue, "use orNull() instead of or(null)");
    return reference;
  }

  public Optional or(Optional secondChoice) {
    Preconditions.checkNotNull(secondChoice);
    return this;
  }

  public Object or(Supplier supplier) {
    Preconditions.checkNotNull(supplier);
    return reference;
  }

  public Object orNull() {
    return reference;
  }

//  public Set<T> asSet() {
//    return Collections.singleton(reference);
//  }
  
//  public Optional<V> transform(Function<? super T, V> function) {
//    return new Present<V>(checkNotNull(function.apply(reference),
//        "Transformation function cannot return null."));
//  }

  public boolean equals(Object object) {
    if (object instanceof Present) {
      Present other = (Present) object;
      return reference.equals(other.reference);
    }
    return false;
  }

  public int hashCode() {
    return 0x598df91c + reference.hashCode();
  }

  public String toString() {
    return "Optional.of(" + reference + ")";
  }
}
