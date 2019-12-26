package org.firstinspires.ftc.teamcode;

public class Util {
	private Util() {
	}
	
	public static <T> T[] varargPlus(T[] arr, T... more) {
		if (more.length == 0) return arr;
		var result = new Object[arr.length + more.length];
		System.arraycopy(arr, 0, result, 0, arr.length);
		System.arraycopy(more, 0, result, arr.length, more.length);
		//noinspection unchecked
		return (T[]) result;
	}
}
