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
 * Implementation of an {@link Optional} not containing a reference.
 */

final class Absent extends Optional {
  static final Absent INSTANCE = new Absent();

  public boolean isPresent() {
    return false;
  }

  public Object get() {
    throw new IllegalStateException("value is absent");
  }

  public Object or(Object defaultValue) {
    return Preconditions.checkNotNull(defaultValue, "use orNull() instead of or(null)");
  }

  public Optional or(Optional secondChoice) {
    return (Optional) Preconditions.checkNotNull(secondChoice);
  }

  public Object or(Supplier supplier) {
    return Preconditions.checkNotNull(supplier.get(),
        "use orNull() instead of a Supplier that returns null");
  }

  public Object orNull() {
    return null;
  }

//  @Override public Set<Object> asSet() {
//    return Collections.emptySet();
//  }

//  @Override
//  public <V> Optional<V> transform(Function<? super Object, V> function) {
//    checkNotNull(function);
//    return Optional.absent();
//  }

  public boolean equals(Object object) {
    return object == this;
  }

  public int hashCode() {
    return 0x598df91c;
  }

  public String toString() {
    return "Optional.absent()";
  }

  private Object readResolve() {
    return INSTANCE;
  }

  private static final long serialVersionUID = 0;
}
