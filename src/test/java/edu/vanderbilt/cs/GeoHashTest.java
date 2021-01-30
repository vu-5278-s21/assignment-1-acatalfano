package edu.vanderbilt.cs;

import org.junit.jupiter.api.Test;

import static edu.vanderbilt.cs.live2.GeoHash.*;

public class GeoHashTest {

	@Test
	public void test1DNeg180LNG() {
		assertEquals("00000", geohashString(LONGITUDE_RANGE[0], LONGITUDE_RANGE, 5));
	}

	@Test
	public void test1DNeg90LAT() {
		assertEquals("00000", geohashString(LATITUDE_RANGE[0], LATITUDE_RANGE, 5));
	}

	@Test
	public void test1D180LNG() {
		assertEquals("11111", geohashString(LONGITUDE_RANGE[1], LONGITUDE_RANGE, 5));
	}

	@Test
	public void test1D90LAT() {
		assertEquals("11111", geohashString(LATITUDE_RANGE[1], LATITUDE_RANGE, 5));
	}

	@Test
	public void test1D0LNG() {
		assertEquals("10000", geohashString(0, LONGITUDE_RANGE, 5));
	}

	@Test
	public void test1D90LNG() {
		assertEquals("11000", geohashString(90.0, LONGITUDE_RANGE, 5));
	}

	@Test
	public void test1D135pt0LNG() {
		assertEquals("11100", geohashString(135.0, LONGITUDE_RANGE, 5));
	}

	@Test
	public void test1DNeg157pt5LNG() {
		assertEquals("11110", geohashString(157.5, LONGITUDE_RANGE, 5));
	}

	@Test
	public void test1D168pt75LNG() {
		assertEquals("11111", geohashString(168.75, LONGITUDE_RANGE, 5));
	}

	@Test
	public void test1DNeg1LNG() {
		assertEquals("01111", geohashString(-1, LONGITUDE_RANGE, 5));
	}

	@Test
	public void test1DNeg91LNG() {
		assertEquals("00111", geohashString(-91.0, LONGITUDE_RANGE, 5));
	}

	@Test
	public void test1DNeg136LNG() {
		assertEquals("00011", geohashString(-136.0, LONGITUDE_RANGE, 5));
	}

	@Test
	public void test1DNeg158pt5LNG() {
		assertEquals("00001", geohashString(-158.5, LONGITUDE_RANGE, 5));
	}

	@Test
	public void test1DNeg169pt75LNG() {
		assertEquals("00000", geohashString(-169.75, LONGITUDE_RANGE, 5));
	}

	@Test
	public void testAssorted1DHashes() {
		// If you can get the 1D geohash to pass all of these tests, you should
		// be in
		// good shape to complete the 2D version.
		assertEquals("00000", geohashString(LONGITUDE_RANGE[0], LONGITUDE_RANGE, 5));
		assertEquals("00000", geohashString(LATITUDE_RANGE[0], LATITUDE_RANGE, 5));
		assertEquals("11111", geohashString(LONGITUDE_RANGE[1], LONGITUDE_RANGE, 5));
		assertEquals("11111", geohashString(LATITUDE_RANGE[1], LATITUDE_RANGE, 5));
		assertEquals("10000", geohashString(0, LONGITUDE_RANGE, 5));
		assertEquals("11000", geohashString(90.0, LONGITUDE_RANGE, 5));
		assertEquals("11100", geohashString(135.0, LONGITUDE_RANGE, 5));
		assertEquals("11110", geohashString(157.5, LONGITUDE_RANGE, 5));
		assertEquals("11111", geohashString(168.75, LONGITUDE_RANGE, 5));
		assertEquals("01111", geohashString(-1, LONGITUDE_RANGE, 5));
		assertEquals("00111", geohashString(-91.0, LONGITUDE_RANGE, 5));
		assertEquals("00011", geohashString(-136.0, LONGITUDE_RANGE, 5));
		assertEquals("00001", geohashString(-158.5, LONGITUDE_RANGE, 5));
		assertEquals("00000", geohashString(-169.75, LONGITUDE_RANGE, 5));
	}

	@Test
	public void testAssorted2DHashes() {
		assertEquals(
			"0000000000", toHashString(geohash(LATITUDE_RANGE[0], LONGITUDE_RANGE[0], 10))
		);
		assertEquals(
			"0101010101", toHashString(geohash(LATITUDE_RANGE[0], LONGITUDE_RANGE[1], 10))
		);
		assertEquals(
			"01010101010",
			toHashString(geohash(LATITUDE_RANGE[0], LONGITUDE_RANGE[1], 11))
		);
		assertEquals(
			"01010101010",
			toHashString(geohash(LATITUDE_RANGE[0], LONGITUDE_RANGE[1], 11))
		);
		assertEquals("1010101011", toHashString(geohash(LATITUDE_RANGE[1], -158.5, 10)));
		assertEquals("10101010111", toHashString(geohash(LATITUDE_RANGE[1], -158.5, 11)));
		assertEquals(
			"10101010111111", toHashString(geohash(LATITUDE_RANGE[1], -158.5, 14))
		);
		assertEquals(
			"11111111111111",
			toHashString(geohash(LATITUDE_RANGE[1], LONGITUDE_RANGE[1], 14))
		);
	}

	@Test
	public void test2DHashAllZeroPrecision10() {
		assertEquals(
			"0000000000", toHashString(geohash(LATITUDE_RANGE[0], LONGITUDE_RANGE[0], 10))
		);
	}

	@Test
	public void test2DHashAlternate01Precision10() {
		assertEquals(
			"0101010101", toHashString(geohash(LATITUDE_RANGE[0], LONGITUDE_RANGE[1], 10))
		);
	}

	@Test
	public void test2DHashAlternate01Precision11() {
		assertEquals(
			"01010101010",
			toHashString(geohash(LATITUDE_RANGE[0], LONGITUDE_RANGE[1], 11))
		);
	}

	@Test
	public void testConsistency() {
		assertEquals(
			"01010101010",
			toHashString(geohash(LATITUDE_RANGE[0], LONGITUDE_RANGE[1], 11))
		);
	}

	@Test
	public void testAltTrailing1sPrecision10() {
		assertEquals("1010101011", toHashString(geohash(LATITUDE_RANGE[1], -158.5, 10)));
	}

	@Test
	public void testAltTrailing1sPrecision11() {
		assertEquals("10101010111", toHashString(geohash(LATITUDE_RANGE[1], -158.5, 11)));
	}

	@Test
	public void testAltTrailing1sPrecision14() {
		assertEquals(
			"10101010111111", toHashString(geohash(LATITUDE_RANGE[1], -158.5, 14))
		);
	}

	@Test
	public void testAll1sPrecision14() {
		assertEquals(
			"11111111111111",
			toHashString(geohash(LATITUDE_RANGE[1], LONGITUDE_RANGE[1], 14))
		);
	}
}
