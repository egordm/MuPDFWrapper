package net.egordmitriev.libmupdf;

import com.artifex.mupdf.fitz.Point;
import com.artifex.mupdf.fitz.Rect;

/**
 * Created by egordm on 15-8-2017.
 */

public class Utils {
	public static Point getCentre(Rect r) {
		return new Point((r.x0 - r.x1)/ 2 + r.x0, (r.y0 - r.y1)/ 2 + r.y0);
	}
}
