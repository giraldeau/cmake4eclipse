/*******************************************************************************
 * Copyright (c) 2015 Martin Weber.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Martin Weber - Initial implementation
 *******************************************************************************/
package de.marw.cmake.cdt.language.settings.providers;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.settings.model.ICLanguageSettingEntry;
import org.eclipse.cdt.core.settings.model.ICSettingEntry;
import org.junit.Before;
import org.junit.Test;

import de.marw.cmake.cdt.language.settings.providers.ToolArgumentParsers.IncludePath_C_POSIX;

/**
 * @author Martin Weber
 */
public class IncludePath_C_POSIXTest {

  private IncludePath_C_POSIX testee;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    testee = new IncludePath_C_POSIX();
  }

  /**
   * Test method for
   * {@link de.marw.cmake.cdt.language.settings.providers.ToolArgumentParsers.IncludePath_C_POSIX#processArgument(java.util.List, java.lang.String)}
   * .
   */
  @Test
  public final void testProcessArgument() {
    final String more = " -g -MMD -MT CMakeFiles/execut1.dir/util1.c.o -MF \"CMakeFiles/execut1.dir/util1.c.o.d\""
        + " -o CMakeFiles/execut1.dir/util1.c.o -c /testprojects/C-subsrc/src/src-sub/main1.c";
    List<ICLanguageSettingEntry> entries = new ArrayList<ICLanguageSettingEntry>();
    ICLanguageSettingEntry parsed;

    String name = "/an/Include/Path";
    // -I/an/Include/Path
    entries.clear();
    assertEquals(2 + name.length(),
        testee.processArgument(entries, "-I" + name + more));
    assertEquals("#entries", 1, entries.size());
    parsed = entries.get(0);
    assertEquals("kind", ICSettingEntry.INCLUDE_PATH, parsed.getKind());
    assertEquals("name", name, parsed.getName());
    // -I'/an/Include/Path'
    entries.clear();
    assertEquals(2 + name.length() + 2,
        testee.processArgument(entries, "-I" + "'" + name + "'" + more));
    assertEquals("#entries", 1, entries.size());
    parsed = entries.get(0);
    assertEquals("kind", ICSettingEntry.INCLUDE_PATH, parsed.getKind());
    assertEquals("name", name, parsed.getName());
    // -I"/an/Include/Path"
    entries.clear();
    assertEquals(2 + name.length() + 2,
        testee.processArgument(entries, "-I" + "\"" + name + "\"" + more));
    assertEquals("#entries", 1, entries.size());
    parsed = entries.get(0);
    assertEquals("kind", ICSettingEntry.INCLUDE_PATH, parsed.getKind());
    assertEquals("name", name, parsed.getName());

    // -I   /an/Include/Path
    entries.clear();
    assertEquals(2 + name.length() + 3,
        testee.processArgument(entries, "-I   " + name + more));
    assertEquals("#entries", 1, entries.size());
    parsed = entries.get(0);
    assertEquals("kind", ICSettingEntry.INCLUDE_PATH, parsed.getKind());
    assertEquals("name", name, parsed.getName());
    // -I   '/an/Include/Path'
    entries.clear();
    assertEquals(2 + name.length() + 3 + 2,
        testee.processArgument(entries, "-I   " + "'" + name + "'" + more));
    assertEquals("#entries", 1, entries.size());
    parsed = entries.get(0);
    assertEquals("kind", ICSettingEntry.INCLUDE_PATH, parsed.getKind());
    assertEquals("name", name, parsed.getName());
    // -I   "/an/Include/Path"
    entries.clear();
    assertEquals(2 + name.length() + 3 + 2,
        testee.processArgument(entries, "-I   " + "\"" + name + "\"" + more));
    assertEquals("#entries", 1, entries.size());
    parsed = entries.get(0);
    assertEquals("kind", ICSettingEntry.INCLUDE_PATH, parsed.getKind());
    assertEquals("name", name, parsed.getName());

    name = "A:an\\Include/Path";
    // -IA:an\Include/Path
    entries.clear();
    assertEquals(2 + name.length(),
        testee.processArgument(entries, "-I" + name + more));
    assertEquals("#entries", 1, entries.size());
    parsed = entries.get(0);
    assertEquals("kind", ICSettingEntry.INCLUDE_PATH, parsed.getKind());
    assertEquals("name", name, parsed.getName());
  }

  /**
   * Test method for
   * {@link de.marw.cmake.cdt.language.settings.providers.ToolArgumentParsers.IncludePath_C_POSIX#processArgument(java.util.List, java.lang.String)}
   */
  @Test
  public final void testProcessArgument_WS() {
    final String more = " -g -MMD -MT CMakeFiles/execut1.dir/util1.c.o -MF \"CMakeFiles/execut1.dir/util1.c.o.d\""
        + " -o CMakeFiles/execut1.dir/util1.c.o -c /testprojects/C-subsrc/src/src-sub/main1.c";
    List<ICLanguageSettingEntry> entries = new ArrayList<ICLanguageSettingEntry>();
    ICLanguageSettingEntry parsed;

    String name = "/ye olde/In clu de/Pa the";
    // -I'/ye olde/In clu de/Pa the'
    entries.clear();
    assertEquals(2 + name.length() + 2,
        testee.processArgument(entries, "-I" + "'" + name + "'" + more));
    assertEquals("#entries", 1, entries.size());
    parsed = entries.get(0);
    assertEquals("kind", ICSettingEntry.INCLUDE_PATH, parsed.getKind());
    assertEquals("name", name, parsed.getName());
    // -I"/ye olde/In clu de/Pa the"
    entries.clear();
    assertEquals(2 + name.length() + 2,
        testee.processArgument(entries, "-I" + "\"" + name + "\"" + more));
    assertEquals("#entries", 1, entries.size());
    parsed = entries.get(0);
    assertEquals("kind", ICSettingEntry.INCLUDE_PATH, parsed.getKind());
    assertEquals("name", name, parsed.getName());

    // -I   '/ye olde/In clu de/Pa the'
    entries.clear();
    assertEquals(2 + name.length() + 3 + 2,
        testee.processArgument(entries, "-I   " + "'" + name + "'" + more));
    assertEquals("#entries", 1, entries.size());
    parsed = entries.get(0);
    assertEquals("kind", ICSettingEntry.INCLUDE_PATH, parsed.getKind());
    assertEquals("name", name, parsed.getName());
    // -I   "/ye olde/In clu de/Pa the"
    entries.clear();
    assertEquals(2 + name.length() + 3 + 2,
        testee.processArgument(entries, "-I   " + "\"" + name + "\"" + more));
    assertEquals("#entries", 1, entries.size());
    parsed = entries.get(0);
    assertEquals("kind", ICSettingEntry.INCLUDE_PATH, parsed.getKind());
    assertEquals("name", name, parsed.getName());

    name = "A:an\\In CLU  de/Pat h";
    // -I'A:an\In CLU  de/Pat h'
    entries.clear();
    assertEquals(2 + name.length() + 2,
        testee.processArgument(entries, "-I" + "\"" + name + "\"" + more));
    assertEquals("#entries", 1, entries.size());
    parsed = entries.get(0);
    assertEquals("kind", ICSettingEntry.INCLUDE_PATH, parsed.getKind());
    assertEquals("name", name, parsed.getName());
  }
}
