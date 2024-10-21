package com.github.caiormt.app.suppliers;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ArbitrarySupplier;
import net.jqwik.api.Combinators;
import net.jqwik.web.api.Web;

public class Emails implements ArbitrarySupplier<String> {

  @Override
  public Arbitrary<String> get() {
    return Combinators.combine(localPart(), webDomain())
                      .as((localPart, domain) -> localPart + "@" + domain);
  }

  private Arbitrary<String> localPart() {
    return Arbitraries.strings()
                      .withChars("abcdefghijklmnopqrstuvwxyz")
                      .withChars("0123456789.!#$&'*+/=?^_`{|}~-")
                      .ofMinLength(1).ofMaxLength(64);
  }

  private Arbitrary<String> webDomain() {
    return Web.webDomains().map(String::toLowerCase);
  }
}
