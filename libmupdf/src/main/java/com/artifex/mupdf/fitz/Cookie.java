package com.artifex.mupdf.fitz;

@SuppressWarnings("JniMissingFunction")
public class Cookie
{
	private long pointer;

	protected native void finalize();

	public void destroy() {
		finalize();
		pointer = 0;
	}

	private native long newNative();

	public Cookie() {
		pointer = newNative();
	}

	public native void abort();
}
