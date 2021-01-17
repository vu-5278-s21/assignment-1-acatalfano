package edu.vanderbilt.cs.live2;

public class GeoHash {

	/**
	 * This live session will focus on basic Java and some concepts important to
	 * functional programming, such as recursion.
	 *
	 * This class uses a main() method where we can write our own simple "experiments" to
	 * test how our code works. You are encouraged to modify the main method to play
	 * around with your code and understand it. When you have working code, you can
	 * extract it into a method. When you have working examples with assertions, you can
	 * extract them into tests.
	 *
	 * I have left some sample experiments in main() to help you understand the geohash
	 * algorithm.
	 *
	 *
	 * This class will provide an implementation of GeoHashes:
	 *
	 * https://www.mapzen.com/blog/geohashes-and-you/
	 * https://en.wikipedia.org/wiki/Geohash
	 *
	 *
	 * GeoHash Spatial Precision:
	 *
	 * https://releases.dataone.org/online/api-documentation-v2.0.1/design/geohash.html
	 */

	public static final double[] LATITUDE_RANGE = { -90, 90 };
	public static final double[] LONGITUDE_RANGE = { -180, 180 };

	public static boolean[] geohash1D(
		double valueToHash,
		double[] valueRange,
		int bitsOfPrecision
	) {

		if (valueRange == null || valueRange.length != 2) {
			throw new IllegalArgumentException(
				"value range must be an array of 2 doubles"
			);
		}

		if (bitsOfPrecision < 0) {
			throw new IllegalArgumentException(
				"precision must be a positive integer"
			);
		}

		if (valueToHash < Math.min(valueRange[0], valueRange[1])
			|| valueToHash > Math.max(valueRange[0], valueRange[1])
		) {
			throw new IllegalArgumentException(
				"value must be within the provided range"
			);
		}

		double normalizedUpperRange = Math.abs(valueRange[1] - valueRange[0]);
		double normalizedValue =
			valueToHash - Math.min(valueRange[0], valueRange[1]);

		return recursiveGeohash1D(normalizedValue, normalizedUpperRange, bitsOfPrecision);
	}

	public static boolean[] geohash2D(
		double v1,
		double[] v1range,
		double v2,
		double[] v2range,
		int bitsOfPrecision
	) {
		int longitudePrecision = bitsOfPrecision / 2;
		boolean[] longitudeGeohash =
			GeoHash.geohash1D(v2, v2range, longitudePrecision);

		int latitudePrecision = (int)Math.ceil(bitsOfPrecision / 2.0);
		boolean[] latitudeGeohash = GeoHash.geohash1D(v1, v1range, latitudePrecision);
		boolean[] geohash = new boolean[bitsOfPrecision];

		for(int srcIndex = 0 , destIndex = 0;
			destIndex < bitsOfPrecision;
			srcIndex++, destIndex += 2
		) {
			geohash[destIndex] = latitudeGeohash[srcIndex];

			if (srcIndex < longitudeGeohash.length) {
				geohash[destIndex + 1] = longitudeGeohash[srcIndex];
			}
		}

		return geohash;
	}

	public static boolean[] geohash(
		double lat,
		double lon,
		int bitsOfPrecision
	) {
		return geohash2D(lat, LATITUDE_RANGE, lon, LONGITUDE_RANGE, bitsOfPrecision);
	}

	// This is a helper method that will make printing out
	// geohashes easier
	public static String toHashString(boolean[] geohash) {
		String hashString = "";

		for(boolean b : geohash) {
			hashString += (b ? "1" : "0");
		}
		return hashString;
	}

	// This is a convenience method to make it easy to get a string of 1s and 0s
	// for
	// a
	// geohash
	public static String geohashString(
		double valueToHash,
		double[] valueRange,
		int bitsOfPrecision
	) {
		return toHashString(
			geohash1D(valueToHash, valueRange, bitsOfPrecision)
		);
	}

	// Faux testing for now
	public static void assertEquals(
		String v1,
		String v2
	) {

		if (!v1.contentEquals(v2)) {
			throw new RuntimeException(v1 + " != " + v2);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Example of hand-coding a 3-bit geohash

		// 1st bit of the geohash
		double longitude = 0.0;
		double[] bounds = { LONGITUDE_RANGE[0], LONGITUDE_RANGE[1] };
		double midpoint = (bounds[0] + bounds[1]) / 2;
		boolean bit = false;

		if (longitude >= midpoint) {
			bit = true;
			bounds[0] = midpoint;
		} else {
			bit = false;
			bounds[1] = midpoint;
		}

		// 2nd bit of the geohash
		boolean bit2 = false;
		midpoint = (bounds[0] + bounds[1]) / 2;

		if (longitude >= midpoint) {
			bit2 = true;
			bounds[0] = midpoint;
		} else {
			bit2 = false;
			bounds[1] = midpoint;
		}

		// 3rd bit of the geohash
		boolean bit3 = false;
		midpoint = (bounds[0] + bounds[1]) / 2;

		if (longitude >= midpoint) {
			bit3 = true;
			bounds[0] = midpoint;
		} else {
			bit3 = false;
			bounds[1] = midpoint;
		}
		// Continue this process for however many bits of precision we need...

		// Faux testing for now
		assertEquals("100", toHashString(new boolean[] { bit, bit2, bit3 }));

		assertEquals(
			"01010101010",
			toHashString(geohash(LATITUDE_RANGE[0], LONGITUDE_RANGE[1], 11))
		);

		String allZeros = "00000";
		String allOnes = "11111";
		// If you can get the 1D geohash to pass all of these faux tests, you
		// should be
		// in
		// good shape to complete the 2D version.
		assertEquals(allZeros, geohashString(LONGITUDE_RANGE[0], LONGITUDE_RANGE, 5));
		assertEquals(allZeros, geohashString(LATITUDE_RANGE[0], LATITUDE_RANGE, 5));
		assertEquals(allOnes, geohashString(LONGITUDE_RANGE[1], LONGITUDE_RANGE, 5));
		assertEquals(allOnes, geohashString(LATITUDE_RANGE[1], LATITUDE_RANGE, 5));
		assertEquals("10000", geohashString(0, LONGITUDE_RANGE, 5));
		assertEquals("11000", geohashString(90.0, LONGITUDE_RANGE, 5));
		assertEquals("11100", geohashString(135.0, LONGITUDE_RANGE, 5));
		assertEquals("11110", geohashString(157.5, LONGITUDE_RANGE, 5));
		assertEquals(allOnes, geohashString(168.75, LONGITUDE_RANGE, 5));
		assertEquals("01111", geohashString(-1, LONGITUDE_RANGE, 5));
		assertEquals("00111", geohashString(-91.0, LONGITUDE_RANGE, 5));
		assertEquals("00011", geohashString(-136.0, LONGITUDE_RANGE, 5));
		assertEquals("00001", geohashString(-158.5, LONGITUDE_RANGE, 5));
		assertEquals(allZeros, geohashString(-169.75, LONGITUDE_RANGE, 5));
	}

	private static boolean[] recursiveGeohash1D(
		double normalizedValue,
		double normalizedUpperRange,
		int precision
	) {
		boolean[] geohash = new boolean[precision];

		if (precision != 0) {
			boolean[] partialGeohash;
			double nextUpperRange = normalizedUpperRange / 2;

			if (normalizedValue >= nextUpperRange) {
				// @ToDo: RECUR CASE 1
				partialGeohash = recursiveGeohash1D(
					normalizedValue - nextUpperRange, nextUpperRange,
					precision - 1
				);
				geohash[0] = true;
			} else {
				// @ToDo: RECUR CASE 2
				partialGeohash = recursiveGeohash1D(
					normalizedValue, nextUpperRange, precision - 1
				);
				geohash[0] = false;
			}
			System.arraycopy(
				partialGeohash, 0, geohash, 1, partialGeohash.length
			);
		} else {
			// BASE CASE -- no additional action
			;
		}
		return geohash;
	}
}
