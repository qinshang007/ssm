package com.ssm.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 将经纬度转成字符串
 * @author Administrator
 *
 */
public class GeoHashKit {

	private static char[] base32 = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	private final static Map<Character, Integer> decodemap = new HashMap<Character, Integer>();
	static {
		int sz = base32.length;
		for (int i = 0; i < sz; i++) {
			decodemap.put(base32[i], i);
		}
	}
	private static int precision = 10;
	private static int[] bits = { 16, 8, 4, 2, 1 };

	/**
	 * 设置精度
	 * @param int precision 设置精确位数，此参数决定了该算法的经度
	 *
	 * */
	public static void setPrecision(int precision) {
		GeoHashKit.precision = precision;
	}

	public static double getPrecision(double x, double precision) {
		double base = Math.pow(10, -precision);
		double diff = x % base;
		return x - diff;
	}
	

	public static String encode(double latitude, double longitude) {
		double[] lat_interval = { -90.0, 90.0 };
		double[] lon_interval = { -180.0, 180.0 };
		StringBuilder geohash = new StringBuilder();
		boolean is_even = true;
		int bit = 0, ch = 0;
		while (geohash.length() < precision) {
			double mid = 0.0;
			if (is_even) {
				mid = (lon_interval[0] + lon_interval[1]) / 2;
				if (longitude > mid) {
					ch |= bits[bit];
					lon_interval[0] = mid;
				} else {
					lon_interval[1] = mid;
				}
			} else {
				mid = (lat_interval[0] + lat_interval[1]) / 2;
				if (latitude > mid) {
					ch |= bits[bit];
					lat_interval[0] = mid;
				} else {
					lat_interval[1] = mid;
				}
			}
			is_even = is_even ? false : true;

			if (bit < 4) {
				bit++;
			} else {
				geohash.append(base32[ch]);
				bit = 0;
				ch = 0;
			}
		}
		return geohash.toString();
	}

	public static double[] decode(String geohash) {
		double[] ge = decode_exactly(geohash);
		double lat, lon, lat_err, lon_err;
		lat = ge[0];
		lon = ge[1];
		lat_err = ge[2];
		lon_err = ge[3];
		double lat_precision = Math.max(1, Math.round(-Math.log10(lat_err))) - 1;
		double lon_precision = Math.max(1, Math.round(-Math.log10(lon_err))) - 1;
		lat = getPrecision(lat, lat_precision);
		lon = getPrecision(lon, lon_precision);
		return new double[] { lat, lon };
	}

	public static double[] decode_exactly(String geohash) {
		double[] lat_interval = { -90.0, 90.0 };
		double[] lon_interval = { -180.0, 180.0 };
		double lat_err = 90.0;
		double lon_err = 180.0;
		boolean is_even = true;
		int sz = geohash.length();
		int bsz = bits.length;
		double latitude, longitude;
		for (int i = 0; i < sz; i++) {
			int cd = decodemap.get(geohash.charAt(i));
			for (int z = 0; z < bsz; z++) {
				int mask = bits[z];
				if (is_even) {
					lon_err /= 2;
					if ((cd & mask) != 0) {
						lon_interval[0] = (lon_interval[0] + lon_interval[1]) / 2;
					} else {
						lon_interval[1] = (lon_interval[0] + lon_interval[1]) / 2;
					}
				} else {
					lat_err /= 2;

					if ((cd & mask) != 0) {
						lat_interval[0] = (lat_interval[0] + lat_interval[1]) / 2;
					} else {
						lat_interval[1] = (lat_interval[0] + lat_interval[1]) / 2;
					}
				}
				is_even = is_even ? false : true;
			}
		}
		latitude = (lat_interval[0] + lat_interval[1]) / 2;
		longitude = (lon_interval[0] + lon_interval[1]) / 2;
		return new double[] { latitude, longitude, lat_err, lon_err };
	}


	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		GeoHashKit ghf = new GeoHashKit();
		String gc1 = GeoHashKit.encode(30.270067,120.129649);
		String gc2 = GeoHashKit.encode(39.897916, 116.426008);
		String gc3 = GeoHashKit.encode(39.947938, 116.361617);

		System.out.println(gc1);
		System.out.println(gc2);
		System.out.println(gc3);

		double[] gd1 = GeoHashKit.decode(gc1);
		double[] gd2 = GeoHashKit.decode(gc2);
		System.out.println(gd1[0] + ", " + gd1[1]);
		System.out.println(gd2[0] + ", " + gd2[1]);
	}
}

