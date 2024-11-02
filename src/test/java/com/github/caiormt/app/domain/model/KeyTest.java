package com.github.caiormt.app.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.StringLength;

import com.github.caiormt.app.domain.error.KeyError;
import com.github.caiormt.app.suppliers.CNPJs;
import com.github.caiormt.app.suppliers.CPFs;
import com.github.caiormt.app.suppliers.EVPs;
import com.github.caiormt.app.suppliers.Emails;
import com.github.caiormt.app.suppliers.Phones;

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
  void shouldRejectNonCpfValueWhenCpfType(@ForAll @StringLength(max = 77) final String value) {
    final KeyError error = assertThrows(KeyError.class, () -> new Key(Key.Type.CPF, value));
    assertEquals("Key Value must be a valid CPF", error.getMessage());
  }

  @Property
  void shouldRejectNonCnpjValueWhenCnpjType(@ForAll @StringLength(max = 77) final String value) {
    final KeyError error = assertThrows(KeyError.class, () -> new Key(Key.Type.CNPJ, value));
    assertEquals("Key Value must be a valid CNPJ", error.getMessage());
  }

  @Property
  void shouldRejectNonPhoneValueWhenPhoneType(@ForAll @StringLength(max = 77) final String value) {
    final KeyError error = assertThrows(KeyError.class, () -> new Key(Key.Type.PHONE, value));
    assertEquals("Key Value must be a valid phone number", error.getMessage());
  }

  @Property
  void shouldRejectNonEmailValueWhenEmailType(@ForAll @StringLength(max = 77) final String value) {
    final KeyError error = assertThrows(KeyError.class, () -> new Key(Key.Type.EMAIL, value));
    assertEquals("Key Value must be a valid e-mail", error.getMessage());
  }

  @Property
  void shouldRejectNonUuidValueWhenEvpType(@ForAll @StringLength(max = 77) final String value) {
    final KeyError error = assertThrows(KeyError.class, () -> new Key(Key.Type.EVP, value));
    assertEquals("Key Value must be a valid UUID", error.getMessage());
  }

  @Property
  void shouldAcceptCreationWhenCpfType(@ForAll(supplier = CPFs.class) final String value) {
    final Key key = assertDoesNotThrow(() -> new Key(Key.Type.CPF, value));
    assertEquals(Key.Type.CPF, key.type());
    assertEquals(value, key.value());
  }

  @Property
  void shouldAcceptCreationWhenCnpjType(@ForAll(supplier = CNPJs.class) final String value) {
    final Key key = assertDoesNotThrow(() -> new Key(Key.Type.CNPJ, value));
    assertEquals(Key.Type.CNPJ, key.type());
    assertEquals(value, key.value());
  }

  @Property
  void shouldAcceptCreationWhenPhoneType(@ForAll(supplier = Phones.class) final String value) {
    final Key key = assertDoesNotThrow(() -> new Key(Key.Type.PHONE, value));
    assertEquals(Key.Type.PHONE, key.type());
    assertEquals(value, key.value());
  }

  @Property
  void shouldAcceptCreationWhenEmailType(@ForAll(supplier = Emails.class) @StringLength(max = 77) final String value) {
    final Key key = assertDoesNotThrow(() -> new Key(Key.Type.EMAIL, value));
    assertEquals(Key.Type.EMAIL, key.type());
    assertEquals(value, key.value());
  }

  @Property
  void shouldAcceptCreationWhenEvpType(@ForAll(supplier = EVPs.class) final String value) {
    final Key key = assertDoesNotThrow(() -> new Key(Key.Type.EVP, value));
    assertEquals(Key.Type.EVP, key.type());
    assertEquals(value, key.value());
  }
}
