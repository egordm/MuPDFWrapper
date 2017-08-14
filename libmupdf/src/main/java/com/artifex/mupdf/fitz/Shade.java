package com.artifex.mupdf.fitz;

@SuppressWarnings("JniMissingFunction")
public class Shade
{
	private long pointer;

	protected native void finalize();

	public void destroy() {
		finalize();
		pointer = 0;
	}

	private Shade(long p) {
		pointer = p;
	}
}
