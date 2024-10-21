package com.github.caiormt.app.domain.model;

import java.util.Objects;
import java.util.regex.Pattern;

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

  private static final Pattern CPF_PATTERN = Pattern.compile(
      "^[0-9]{11}$");
  private static final Pattern CNPJ_PATTERN = Pattern.compile(
      "^[0-9]{14}$");
  private static final Pattern PHONE_PATTERN = Pattern.compile(
      "^\\+[1-9]\\d{1,14}$");
  private static final Pattern EMAIL_PATTERN = Pattern.compile(
      "^[a-z0-9.!#$&'*+/=?^_`{|}~-]+@[a-z0-9](?:[a-z0-9-]{0,61}[a-z0-9])?(?:\\.[a-z0-9](?:[a-z0-9-]{0,61}[a-z0-9])?)*$");
  private static final Pattern EVP_PATTERN = Pattern.compile(
      "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");

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
    if (Type.CPF == type && !CPF_PATTERN.matcher(value).matches())
      throw new KeyError("Key Value must be a valid CPF");
    if (Type.CNPJ == type && !CNPJ_PATTERN.matcher(value).matches())
      throw new KeyError("Key Value must be a valid CNPJ");
    if (Type.PHONE == type && !PHONE_PATTERN.matcher(value).matches())
      throw new KeyError("Key Value must be a valid phone number");
    if (Type.EMAIL == type && !EMAIL_PATTERN.matcher(value).matches())
      throw new KeyError("Key Value must be a valid e-mail");
    if (Type.EVP == type && !EVP_PATTERN.matcher(value).matches())
      throw new KeyError("Key Value must be a valid UUID");

    this.type = type;
    this.value = value;
  }
}
