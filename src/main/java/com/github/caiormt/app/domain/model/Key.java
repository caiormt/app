package com.github.caiormt.app.domain.model;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import com.github.caiormt.app.domain.error.KeyError;

@Getter
@ToString
@EqualsAndHashCode
@Accessors(fluent = true)
public final class Key {

  private static final int MAX_LENGTH = 77;

  private final Type type;
  private final String value;

  @RequiredArgsConstructor
  public enum Type {
    CPF(new Predicate<>() {

      private static final Pattern PATTERN = Pattern.compile("^[0-9]{11}$");

      @Override
      public boolean test(final String input) {
        return PATTERN.matcher(input).matches();
      }
    }, "Key Value must be a valid CPF"),
    CNPJ(new Predicate<>() {

      private static final Pattern PATTERN = Pattern.compile("^[0-9]{14}$");

      @Override
      public boolean test(final String input) {
        return PATTERN.matcher(input).matches();
      }
    }, "Key Value must be a valid CNPJ"),
    PHONE(new Predicate<>() {

      private static final Pattern PATTERN = Pattern.compile("^\\+[1-9]\\d{1,14}$");

      @Override
      public boolean test(final String input) {
        return PATTERN.matcher(input).matches();
      }
    }, "Key Value must be a valid phone number"),
    EMAIL(new Predicate<>() {

      private static final Pattern PATTERN = Pattern.compile(
          "^[a-z0-9.!#$&'*+/=?^_`{|}~-]+"
              .concat("@")
              .concat("[a-z0-9](?:[a-z0-9-]{0,61}[a-z0-9])?(?:\\.[a-z0-9](?:[a-z0-9-]{0,61}[a-z0-9])?)*$"));

      @Override
      public boolean test(final String input) {
        return PATTERN.matcher(input).matches();
      }
    }, "Key Value must be a valid e-mail"),
    EVP(new Predicate<>() {

      private static final Pattern PATTERN = Pattern.compile(
          "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");

      @Override
      public boolean test(final String input) {
        return PATTERN.matcher(input).matches();
      }
    }, "Key Value must be a valid UUID");

    private final Predicate<String> predicate;
    private final String rejectionMessage;

    public boolean accepts(final String value) {
      return this.predicate.test(value);
    }

    String rejectionMessage() {
      return this.rejectionMessage;
    }
  }

  public Key(final Type type, final String value) {
    validateType(type);
    validateValue(value);
    validateCoherence(type, value);
    this.type = type;
    this.value = value;
  }

  private static void validateType(final Type type) {
    if (Objects.isNull(type))
      throw new KeyError("Key Type cannot be null");
  }

  private static void validateValue(final String value) {
    if (Objects.isNull(value))
      throw new KeyError("Key Value cannot be null");
    if (StringUtils.length(value) > MAX_LENGTH)
      throw new KeyError("Key Value length must be less than or equal 77");
  }

  private static void validateCoherence(final Type type, final String value) {
    if (!type.accepts(value))
      throw new KeyError(type.rejectionMessage());
  }
}
