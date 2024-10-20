package com.github.caiormt.app.domain.model;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
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

  public enum Type {
    CPF, CNPJ, PHONE, EMAIL, EVP
  }

  public Key(final Type type, final String value) {
    if (Objects.isNull(type))
      throw new KeyError("Key Type cannot be null");
    if (Objects.isNull(value))
      throw new KeyError("Key Value cannot be null");
    if (StringUtils.length(value) > MAX_LENGTH)
      throw new KeyError("Key Value length must be less than or equal 77");

    this.type = type;
    this.value = value;
  }
}
