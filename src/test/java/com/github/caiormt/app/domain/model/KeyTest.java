package com.github.caiormt.app.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.StringLength;

import com.github.caiormt.app.domain.error.KeyError;

class KeyTest {

  @Example
  void shouldRejectCreationWhenNullType() {
    final KeyError error = assertThrows(KeyError.class, () -> new Key(null, ""));
    assertEquals("Key Type cannot be null", error.getMessage());
  }

  @Property
  void shouldRejectCreationWhenNullValue(@ForAll final Key.Type type) {
    final KeyError error = assertThrows(KeyError.class, () -> new Key(type, null));
    assertEquals("Key Value cannot be null", error.getMessage());
  }

  @Property
  void shouldRejectCreationWhenValueGreaterThan77Chars(
      @ForAll final Key.Type type,
      @ForAll @StringLength(min = 78) final String value) {

    final KeyError error = assertThrows(KeyError.class, () -> new Key(type, value));
    assertEquals("Key Value length must be less than or equal 77", error.getMessage());
  }

  @Property
  void shouldAcceptCreation(@ForAll final Key.Type type, @ForAll @StringLength(max = 77) final String value) {
    final Key key = assertDoesNotThrow(() -> new Key(type, value));
    assertEquals(type, key.type());
    assertEquals(value, key.value());
  }
}
