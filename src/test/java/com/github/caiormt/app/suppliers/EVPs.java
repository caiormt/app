package com.github.caiormt.app.suppliers;

import java.util.UUID;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ArbitrarySupplier;

public class EVPs implements ArbitrarySupplier<String> {

  @Override
  public Arbitrary<String> get() {
    return Arbitraries.ofSuppliers(UUID::randomUUID).map(UUID::toString);
  }
}
