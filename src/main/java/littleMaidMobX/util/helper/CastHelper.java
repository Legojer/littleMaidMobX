package littleMaidMobX.util.helper;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CastHelper {

	public static String String(Object o) {
		return o != null 
				&& o instanceof String ? (String) o : o != null ? o.toString() : null;
	}

	public static int Int(Object o) {
		return Int(o, 0);
	}

	public static int Int(Object o, int i) {
		return Int(o, i, 2, 1);
	}

	public static int Int(Object o, int i, int i1, int i2) {
		if (o != null); else return i;
		if (o instanceof Boolean) {
			boolean b = (Boolean) o;
			return b ? i1 : i2;
		}
		if (o instanceof Integer) return (Integer) o;
		if (integerCheck(""+o)) return Integer.valueOf(""+o);
		return i;
	}

	public static short Short(Object o) {
		return Short(o, (short) 0);
	}

	public static short Short(Object o, short sh) {
		if (o != null); else return sh;
		if (o instanceof Boolean) {
			boolean b = (Boolean) o;
			return (short) (b ? 2 : 1);
		}
		if (o instanceof Short) return (Short) o;
		if (shortCheck(""+o)) return Short.valueOf(""+o);
		return sh;
	}

	public static long Long(Object o) {
		return Long(o, 0);
	}

	public static long Long(Object o, long l) {
		if (o != null); else return l;
		try {
			return Long.valueOf(""+o);
		} catch (Exception e) {
		}
		return l;
	}

	public static int[] IntArray(Object o) {
		return o != null 
				&& o instanceof int[] ? (int[]) o : null;
	}

	public static long[] LongArray(Object o) {
		return o != null 
				&& o instanceof long[] ? (long[]) o : null;
	}

	public static short[] ShortArray(Object o) {
		return o != null 
				&& o instanceof short[] ? (short[]) o : null;
	}

	public static float[] FloatArray(Object o) {
		return o != null 
				&& o instanceof float[] ? (float[]) o : null;
	}

	public static double[] DoubleArray(Object o) {
		return o != null 
				&& o instanceof double[] ? (double[]) o : null;
	}

	public static byte[] ByteArray(Object o) {
		return o != null 
				&& o instanceof byte[] ? (byte[]) o : null;
	}

	public static String[] StringArray(Object o) {
		return o != null 
				&& o instanceof String[] ? (String[]) o : null;
	}

	public static boolean[] BooleanArray(Object o) {
		return o != null 
				&& o instanceof boolean[] ? (boolean[]) o : null;
	}

	public static Enum[] EnumArray(Object o) {
		return o != null 
				&& o instanceof Enum[] ? (Enum[]) o : null;
	}

	public static List[] ListArray(Object o) {
		return o != null 
				&& o instanceof List[] ? (List[]) o : null;
	}

	public static ArrayList[] ArrayListArray(Object o) {
		return o != null 
				&& o instanceof ArrayList[] ? (ArrayList[]) o : null;
	}

	public static LinkedList[] LinkedListArray(Object o) {
		return o != null 
				&& o instanceof LinkedList[] ? (LinkedList[]) o : null;
	}

	public static File[] FileArray(Object o) {
		return o != null 
				&& o instanceof File[] ? (File[]) o : null;
	}

	public static ConcurrentHashMap[] ConcurrentHashMapArray(Object o) {
		return o != null 
				&& o instanceof ConcurrentHashMap[] ? (ConcurrentHashMap[]) o : null;
	}

	public static HashMap[] HashMapArray(Object o) {
		return o != null 
				&& o instanceof HashMap[] ? (HashMap[]) o : null;
	}

	public static Map[] MapArray(Object o) {
		return o != null 
				&& o instanceof Map[] ? (Map[]) o : null;
	}

//	public static Multimap[] MultimapArray(Object o) {
//		return o != null 
//				&& o instanceof Multimap[] ? (Multimap[]) o : null;
//	}

	public static IntBuffer[] IntBufferArray(Object o) {
		return o != null 
				&& o instanceof IntBuffer[] ? (IntBuffer[]) o : null;
	}

	public static InputStream[] InputStreamArray(Object o) {
		return o != null 
				&& o instanceof InputStream[] ? (InputStream[]) o : null;
	}

	public static UUID[] UUIDArray(Object o) {
		return o != null 
				&& o instanceof UUID[] ? (UUID[]) o : null;
	}

	public static Object[] ObjectArray(Object o) {
		return o != null 
				&& o.getClass().isArray() ? (Object[]) o : null;
	}

	public static Object[] ObjectArray(Object o, Class[] c) {
		LinkedList list = new LinkedList();
		Object[] o1 = null;
		if (o != null
				&& o instanceof Object[]) {
			o1 = (Object[]) o;
		} else if (o.getClass().isArray()) {
			int size = Array.getLength(o);
			o1 = new Object[size];
			for (int i = 0; i < size; i++) {
				o1[i] = Array.get(o, i);
			}
		} else {
			o1 = new Object[]{ o };
		}
		if (o != null
				&& c != null) {
			for (int i = 0; i < o1.length; i++) {
				if (i >= c.length) break;
				Class c1 = c[i];
				list.add(Object(o1[i], c1));
			}
			if (!list.isEmpty()) {
				Object[] o2 = new Object[list.size()];
				int i2 = 0;
				for (Object o3 : list) {
					o2[i2] = o3;
					i2++;
				}
				return o2;
			}
		}
		return o1;
	}

	public static Object Object(Object o, Class c1) {
		if (c1 == int.class) return Int(o);
		if (c1 == float.class) return Float(o);
		if (c1 == double.class) return Double(o);
		if (c1 == byte.class) return Byte(o);
		if (c1 == boolean.class) return Boolean(o);
		if (c1 == long.class) return Long(o);
		if (c1 == Short.class) return Short(o);
		if (c1 == String.class) return String(o);
		if (c1 == Enum.class) return Enum(o);
		if (c1 == List.class) return List(o);
		if (c1 == ArrayList.class) return ArrayList(o);
		if (c1 == LinkedList.class) return LinkedList(o);
		if (c1 == File.class) return File(o);
		if (c1 == ConcurrentHashMap.class) return ConcurrentHashMap(o);
		if (c1 == HashMap.class) return HashMap(o);
		if (c1 == Map.class) return Map(o);
//		if (c1 == Multimap.class) return Multimap(o);
		if (c1 == IntBuffer.class) return IntBuffer(o);
		if (c1 == InputStream.class) return InputStream(o);
		if (c1 == UUID.class) return UUID(o);
		if (c1 == Object[].class) return ObjectArray(o);
		if (c1 == int[].class) return IntArray(o);
		if (c1 == float[].class) return FloatArray(o);
		if (c1 == double[].class) return DoubleArray(o);
		if (c1 == byte[].class) return ByteArray(o);
		if (c1 == boolean[].class) return BooleanArray(o);
		if (c1 == long[].class) return LongArray(o);
		if (c1 == Short[].class) return ShortArray(o);
		if (c1 == String[].class) return StringArray(o);
		if (c1 == Enum[].class) return EnumArray(o);
		if (c1 == List[].class) return ListArray(o);
		if (c1 == ArrayList[].class) return ArrayListArray(o);
		if (c1 == LinkedList[].class) return LinkedListArray(o);
		if (c1 == File[].class) return FileArray(o);
		if (c1 == ConcurrentHashMap[].class) return ConcurrentHashMapArray(o);
		if (c1 == HashMap[].class) return HashMapArray(o);
		if (c1 == Map[].class) return MapArray(o);
//		if (c1 == Multimap[].class) return MultimapArray(o);
		if (c1 == IntBuffer[].class) return IntBufferArray(o);
		if (c1 == InputStream[].class) return InputStreamArray(o);
		if (c1 == UUID[].class) return UUIDArray(o);
		return null;
	}

	public static byte Byte(Object o) {
		return Byte(o, (byte)0);
	}

	public static byte Byte(Object o, byte by) {
		return Byte(o, by, (byte) 2, (byte) 1);
	}

	public static byte Byte(Object o, byte by, byte by1, byte by2) {
		if (o != null); else return by;
		if (o instanceof Boolean) {
			boolean b = (Boolean) o;
			return (byte) (b ? by1 : by2);
		}
		if (o instanceof Byte) return (Byte) o;
		if (byteCheck(""+o)) return Byte.valueOf(""+o);
		return by;
	}

	public static float Float(Object o) {
		return Float(o, 0.0F);
	}

	public static float Float(Object o, float f) {
		if (o != null); else return f;
		if (o instanceof Boolean) {
			boolean b = (Boolean) o;
			return b ? 2 : 1;
		}
		if (o instanceof Float) return (Float) o;
		if (floatCheck(""+o)) return Float.valueOf(""+o);
		return f;
	}

	public static double Double(Object o) {
		return Double(o, 0.0D);
	}

	public static double Double(Object o, double d) {
		if (o != null); else return d;
		if (o instanceof Boolean) {
			boolean b = (Boolean) o;
			return b ? 2 : 1;
		}
		if (o instanceof Double) return (Double) o;
		return Double.valueOf(""+o);
	}

	public static boolean Boolean(Object o) {
		if (o != null); else return false;
		if (boolean.class.isInstance(o)
				| o instanceof String) {
			return Boolean.valueOf(""+o);
		}
		if (o instanceof Boolean) return (Boolean) o;
		return false;
	}

	public static Enum Enum(Object o) {
		return o != null 
				&& o instanceof Enum ? (Enum) o : null;
	}

	public static List List(Object o) {
		return o != null 
				&& o instanceof List ? (List) o : null;
	}

	public static ArrayList ArrayList(Object o) {
		return o != null 
				&& o instanceof ArrayList ? (ArrayList) o : null;
	}

	public static LinkedList LinkedList(Object o) {
		return o != null 
				&& o instanceof LinkedList ? (LinkedList) o : null;
	}

	public static File File(Object o) {
		return o != null 
				&& o instanceof File ? (File) o : null;
	}

	public static ConcurrentHashMap ConcurrentHashMap(Object o) {
		return o != null 
				&& o instanceof ConcurrentHashMap ? (ConcurrentHashMap) o : null;
	}

	public static HashMap HashMap(Object o) {
		return o != null 
				&& o instanceof HashMap ? (HashMap) o : null;
	}

	public static Map Map(Object o) {
		return o != null 
				&& o instanceof Map ? (Map) o : null;
	}

//	public static Multimap Multimap(Object o) {
//		return o != null 
//				&& o instanceof Multimap ? (Multimap) o : null;
//	}

	public static IntBuffer IntBuffer(Object o) {
		return o != null 
				&& o instanceof IntBuffer ? (IntBuffer) o : null;
	}

	public static InputStream InputStream(Object o) {
		return o != null 
				&& o instanceof InputStream ? (InputStream) o : null;
	}

	public static UUID UUID(Object o) {
		return o != null 
				&& o instanceof UUID ? (UUID) o : null;
	}

	public static boolean integerCheck(String s) {
		try {
			Integer.valueOf(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean floatCheck(String s) {
		try {
			Float.valueOf(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean doubleCheck(String s) {
		try {
			Double.valueOf(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean byteCheck(String s) {
		try {
			Byte.valueOf(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean shortCheck(String s) {
		try {
			Short.valueOf(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
