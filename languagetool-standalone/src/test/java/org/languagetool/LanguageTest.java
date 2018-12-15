/* LanguageTool, a natural language style checker
 * Copyright (C) 2012 Daniel Naber (http://www.danielnaber.de)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 * USA
 */
package org.languagetool;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.languagetool.language.AmericanEnglish;
import org.languagetool.language.AustrianGerman;
import org.languagetool.language.BritishEnglish;
import org.languagetool.language.English;
import org.languagetool.language.German;
import org.languagetool.language.GermanyGerman;
import org.languagetool.language.SwissGerman;

public class LanguageTest {

  @Test
  public void testRuleFileName() {
    assertEquals("[/org/languagetool/rules/en/grammar.xml, /org/languagetool/rules/en/en-GB/grammar.xml]", new BritishEnglish().getRuleFileNames().toString());
    assertEquals("[/org/languagetool/rules/en/grammar.xml, /org/languagetool/rules/en/en-US/grammar.xml]", new AmericanEnglish().getRuleFileNames().toString());
    assertEquals("[/org/languagetool/rules/en/grammar.xml]", new English().getRuleFileNames().toString());
    assertEquals("[/org/languagetool/rules/de/grammar.xml]", new GermanyGerman().getRuleFileNames().toString());
  }

  @Test
  public void testGetTranslatedName() {
    assertEquals("English", new English().getTranslatedName(TestTools.getMessages("en")));
    assertEquals("English (British)", new BritishEnglish().getTranslatedName(TestTools.getMessages("en")));

    assertEquals("Englisch", new English().getTranslatedName(TestTools.getMessages("de")));
    assertEquals("Englisch (Großbritannien)", new BritishEnglish().getTranslatedName(TestTools.getMessages("de")));
    assertEquals("Deutsch", new GermanyGerman().getTranslatedName(TestTools.getMessages("de")));
    assertEquals("Deutsch (Schweiz)", new SwissGerman().getTranslatedName(TestTools.getMessages("de")));
  }

  @Test
  public void testGetShortNameWithVariant() {
    assertEquals("en-US", new AmericanEnglish().getShortCodeWithCountryAndVariant());
    assertEquals("de", new GermanyGerman().getShortCodeWithCountryAndVariant());
  }

  @Test
  public void testEquals() {
    assertEquals(new GermanyGerman(), new GermanyGerman());
    assertNotEquals(new AustrianGerman(), new GermanyGerman());
    assertNotEquals(new AustrianGerman(), new German());
  }

  @Test
  public void testEqualsConsiderVariantIfSpecified() {
    // every language equals itself:
    assertTrue(new German().equalsConsiderVariantsIfSpecified(new German()));
    assertTrue(new GermanyGerman().equalsConsiderVariantsIfSpecified(new GermanyGerman()));
    assertTrue(new English().equalsConsiderVariantsIfSpecified(new English()));
    assertTrue(new AmericanEnglish().equalsConsiderVariantsIfSpecified(new AmericanEnglish()));
    // equal if variant is the same, but only if specified:
    assertTrue(new AmericanEnglish().equalsConsiderVariantsIfSpecified(new English()));
    assertTrue(new English().equalsConsiderVariantsIfSpecified(new AmericanEnglish()));

    assertFalse(new AmericanEnglish().equalsConsiderVariantsIfSpecified(new BritishEnglish()));
    assertFalse(new English().equalsConsiderVariantsIfSpecified(new German()));
  }

}
