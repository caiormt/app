package com.github.caiormt.app.suppliers;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ArbitrarySupplier;
import net.jqwik.api.Combinators;

public class Phones implements ArbitrarySupplier<String> {

  @Override
  public Arbitrary<String> get() {
    return Combinators.combine(firstDigit(), remainingDigits())
                      .as((firstDigit, remainingDigits) -> "+" + firstDigit + remainingDigits);
  }

  private Arbitrary<String> firstDigit() {
    return Arbitraries.strings()
                      .withChars("123456789")
                      .ofLength(1);
  }

  private Arbitrary<String> remainingDigits() {
    return Arbitraries.strings()
                      .withChars("0123456789")
                      .ofMinLength(1).ofMaxLength(14);
  }
}
