/*
 * Copyright 2020 White Magic Software, Ltd.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.whitemagicsoftware.kmcaster;

import static org.jnativehook.NativeInputEvent.*;

/**
 * Used for compile-time binding between change listeners input events.
 */
public enum HardwareSwitch {
  KEY_ALT( "alt", ALT_MASK ),
  KEY_CTRL( "ctrl", CTRL_MASK ),
  KEY_SHIFT( "shift", SHIFT_MASK ),
  KEY_REGULAR( "regular" ),
  MOUSE_LEFT( "button 1" ),
  MOUSE_WHEEL( "button 2" ),
  MOUSE_RIGHT( "button 3" ),
  MOUSE_LR( "button 1-3" );

  /**
   * Indicates the switch is not a modifier.
   */
  private final static int NO_MASK = -1;

  private final String mName;
  private final int mMask;

  /**
   * Constructs a new switch with no mask value.
   *
   * @param name The switch name.
   */
  HardwareSwitch( final String name ) {
    this( name, NO_MASK );
  }

  /**
   * Constructs a new switch associated with a mask value that can be used
   * to determine whether a modifier key is pressed.
   *
   * @param name The switch name.
   * @param mask Modifier key bitmask.
   */
  HardwareSwitch( final String name, final int mask ) {
    mName = name;
    mMask = mask;
  }

  /**
   * Answers whether this enumerated item represents a keyboard modifier.
   *
   * @return {@code true} when the switch is a modifier key.
   */
  public boolean isModifier() {
    return this == KEY_ALT || this == KEY_CTRL || this == KEY_SHIFT;
  }

  /**
   * Answers whether the given name and the switch's name are the same,
   * ignoring case.
   *
   * @param name The switch name to compare against this name.
   * @return {@code true} when the names match, regardless of case.
   */
  public boolean isName( final String name ) {
    return mName.equalsIgnoreCase( name );
  }

  /**
   * Answers whether this hardware switch is pressed, but only applies to
   * keyboard modifier keys, not mouse buttons.
   *
   * @param modifiers A set of bits that indicate what modifier keys are
   *                  pressed.
   * @return {@code true} if this switch's modifier bit is set in the
   * given {@code modifiers} value.
   */
  public boolean isPressed( final int modifiers ) {
    assert isModifier();

    return (modifiers & mMask) != 0;
  }

  /**
   * Looks up the key that matches the given name, case-insensitively.
   *
   * @param name The name of the key to find in this enum.
   * @return The {@link HardwareSwitch} object that matches the name.
   */
  public static HardwareSwitch valueFrom( final String name ) {
    for( final var b : HardwareSwitch.values() ) {
      if( b.isName( name ) ) {
        return b;
      }
    }

    return KEY_REGULAR;
  }

  /**
   * Returns the switch name.
   *
   * @return The switch name, nothing more.
   */
  @Override
  public String toString() {
    return mName;
  }

}