package com.github.caiormt.app.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.StringLength;

class KeyTest {

  @Example
  void shouldRejectCreationWhenNullType() {
    assertThrows(RuntimeException.class, () -> new Key(null, ""));
  }

  @Property
  void shouldRejectCreationWhenNullValue(@ForAll final Key.Type type) {
    assertThrows(RuntimeException.class, () -> new Key(type, null));
  }

  @Property
  void shouldRejectCreationWhenValueGreaterThan77Chars(
      @ForAll final Key.Type type,
      @ForAll @StringLength(min = 78) final String value) {

    assertThrows(RuntimeException.class, () -> new Key(type, value));
  }

  @Property
  void shouldAcceptCreation(@ForAll final Key.Type type, @ForAll @StringLength(max = 77) final String value) {
    final Key key = assertDoesNotThrow(() -> new Key(type, value));
    assertEquals(key.type(), type);
    assertEquals(key.value(), value);
  }
}
