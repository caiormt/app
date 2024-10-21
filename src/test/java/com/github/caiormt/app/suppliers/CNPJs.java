package com.github.caiormt.app.suppliers;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ArbitrarySupplier;
import net.jqwik.api.Combinators;

public class CNPJs implements ArbitrarySupplier<String> {

  @Override
  public Arbitrary<String> get() {
    return Arbitraries.strings()
                      .numeric()
                      .ofLength(14);
  }
}
