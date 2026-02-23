package com.earth2me.essentials;

import com.earth2me.essentials.utils.DateUtil;
import com.earth2me.essentials.utils.LocationUtil;
import com.earth2me.essentials.utils.VersionUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilTest {

    private Essentials ess;

    @BeforeEach
    public void setUp() {
        MockBukkit.mock();
        Essentials.TESTING = true;
        ess = MockBukkit.load(Essentials.class);
        ess.getI18n().updateLocale(Locale.ENGLISH.toLanguageTag());
    }

    @AfterEach
    public void afterEach() {
        MockBukkit.unmock();
    }

    @Test
    public void testSafeLocation() {
        final Set<String> testSet = new HashSet<>();
        int count = 0;
        int x;
        int y;
        int z;
        final int origX;
        final int origY;
        final int origZ;
        x = y = z = origX = origY = origZ = 0;
        int i = 0;
        while (true) {
            testSet.add(x + ":" + y + ":" + z);
            count++;
            i++;
            if (i >= LocationUtil.VOLUME.length) {
                break;
            }
            x = origX + LocationUtil.VOLUME[i].x;
            y = origY + LocationUtil.VOLUME[i].y;
            z = origZ + LocationUtil.VOLUME[i].z;
        }
        assertTrue(testSet.contains("0:0:0"));
        assertTrue(testSet.contains("3:3:3"));
        assertEquals(testSet.size(), count);
        final int diameter = LocationUtil.RADIUS * 2 + 1;
        assertEquals(diameter * diameter * diameter, count);
    }

    @Test
    public void testFDDnow() {
        final Calendar c = new GregorianCalendar();
        final String resp = DateUtil.formatDateDiff(c, c);
        assertEquals(resp, "now");
    }

    @Test
    public void testFDDfuture() {
        Calendar a, b;
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 1);
        assertEquals("1second", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 2);
        assertEquals("2seconds", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 3);
        assertEquals("3seconds", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 1, 0);
        assertEquals("1minute", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 2, 0);
        assertEquals("2minutes", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 3, 0);
        assertEquals("3minutes", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 11, 0, 0);
        assertEquals("1hour", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 12, 0, 0);
        assertEquals("2hours", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 13, 0, 0);
        assertEquals("3hours", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 2, 10, 0, 0);
        assertEquals("1day", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 3, 10, 0, 0);
        assertEquals("2days", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 4, 10, 0, 0);
        assertEquals("3days", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.MARCH, 1, 10, 0, 0);
        assertEquals("1month", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.APRIL, 1, 10, 0, 0);
        assertEquals("2months", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.MAY, 1, 10, 0, 0);
        assertEquals("3months", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2011, Calendar.FEBRUARY, 1, 10, 0, 0);
        assertEquals("1year", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2012, Calendar.FEBRUARY, 1, 10, 0, 0);
        assertEquals("2years", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2013, Calendar.FEBRUARY, 1, 10, 0, 0);
        assertEquals("3years", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2011, Calendar.MAY, 5, 23, 38, 12);
        assertEquals("1year 3months 4days", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.OCTOBER, 17, 23, 45, 45);
        b = new GregorianCalendar(2015, Calendar.APRIL, 7, 10, 0, 0);
        assertEquals("4years 5months 20days", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2011, Calendar.MAY, 31, 10, 0, 0);
        b = new GregorianCalendar(2011, Calendar.MAY, 31, 10, 5, 0);
        assertEquals("5minutes", DateUtil.formatDateDiff(a, b));
    }

    @Test
    public void testFDDpast() {
        Calendar a, b;
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 9, 59, 59);
        assertEquals("1second", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 9, 59, 58);
        assertEquals("2seconds", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 9, 59, 57);
        assertEquals("3seconds", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 9, 59, 0);
        assertEquals("1minute", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 9, 58, 0);
        assertEquals("2minutes", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 9, 57, 0);
        assertEquals("3minutes", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 9, 0, 0);
        assertEquals("1hour", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 8, 0, 0);
        assertEquals("2hours", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 7, 0, 0);
        assertEquals("3hours", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 5, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 4, 10, 0, 0);
        assertEquals("1day", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 5, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 3, 10, 0, 0);
        assertEquals("2days", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 5, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.FEBRUARY, 2, 10, 0, 0);
        assertEquals("3days", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.JUNE, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.MAY, 1, 10, 0, 0);
        assertEquals("1month", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.JUNE, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.APRIL, 1, 10, 0, 0);
        assertEquals("2months", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.JUNE, 1, 10, 0, 0);
        b = new GregorianCalendar(2010, Calendar.MARCH, 1, 10, 0, 0);
        assertEquals("3months", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2009, Calendar.FEBRUARY, 1, 10, 0, 0);
        assertEquals("1year", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2008, Calendar.FEBRUARY, 1, 10, 0, 0);
        assertEquals("2years", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2007, Calendar.FEBRUARY, 1, 10, 0, 0);
        assertEquals("3years", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.FEBRUARY, 1, 10, 0, 0);
        b = new GregorianCalendar(2009, Calendar.MAY, 5, 23, 38, 12);
        assertEquals("8months 26days 10hours", DateUtil.formatDateDiff(a, b));
        a = new GregorianCalendar(2010, Calendar.OCTOBER, 17, 23, 45, 45);
        b = new GregorianCalendar(2000, Calendar.APRIL, 7, 10, 0, 0);
        assertEquals("10years 6months 10days", DateUtil.formatDateDiff(a, b));
    }

    @Test
    public void testVer() {
        VersionUtil.BukkitVersion v;
        v = VersionUtil.BukkitVersion.fromString("1.13.2-R0.1");
        assertEquals(v.getMajor(), 1);
        assertEquals(v.getMinor(), 13);
        assertEquals(v.getPatch(), 2);
        assertEquals(v.getRevision(), 0.1);
        assertEquals(v.getPrerelease(), -1);
        assertEquals(v.getReleaseCandidate(), -1);
        v = VersionUtil.BukkitVersion.fromString("1.9-R1.4"); // not real
        assertEquals(v.getMajor(), 1);
        assertEquals(v.getMinor(), 9);
        assertEquals(v.getPatch(), 0);
        assertEquals(v.getRevision(), 1.4);
        assertEquals(v.getPrerelease(), -1);
        assertEquals(v.getReleaseCandidate(), -1);
        v = VersionUtil.BukkitVersion.fromString("1.14-pre5");
        assertEquals(v.getMajor(), 1);
        assertEquals(v.getMinor(), 14);
        assertEquals(v.getPatch(), 0);
        assertEquals(v.getRevision(), 0.0);
        assertEquals(v.getPrerelease(), 5);
        assertEquals(v.getReleaseCandidate(), -1);
        v = VersionUtil.BukkitVersion.fromString("1.13.2-pre1-R0.1"); // not real
        assertEquals(v.getMajor(), 1);
        assertEquals(v.getMinor(), 13);
        assertEquals(v.getPatch(), 2);
        assertEquals(v.getRevision(), 0.1);
        assertEquals(v.getPrerelease(), 1);
        assertEquals(v.getReleaseCandidate(), -1);
        v = VersionUtil.BukkitVersion.fromString("1.14.3-SNAPSHOT");
        assertEquals(v.getMajor(), 1);
        assertEquals(v.getMinor(), 14);
        assertEquals(v.getPatch(), 3);
        assertEquals(v.getRevision(), 0.0);
        assertEquals(v.getPrerelease(), -1);
        assertEquals(v.getReleaseCandidate(), -1);
        v = VersionUtil.BukkitVersion.fromString("1.18-rc3-R0.1-SNAPSHOT");
        assertEquals(v.getMajor(), 1);
        assertEquals(v.getMinor(), 18);
        assertEquals(v.getPatch(), 0);
        assertEquals(v.getRevision(), 0.1);
        assertEquals(v.getPrerelease(), -1);
        assertEquals(v.getReleaseCandidate(), 3);
    }
}
