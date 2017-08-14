package net.egordmitriev.mypdf;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;

import com.artifex.mupdf.fitz.Document;

import net.egordmitriev.libmupdf.PDFView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
	
	private PDFView mDocView;
	private Document mDoc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Uri pdf = openPDF();
		final String path = Uri.decode(pdf.getEncodedPath());
		
		mDocView = (PDFView) findViewById(R.id.doc_view_inner);
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.doc_wrapper);
		mDocView.setupHandles(layout);
		
		mDoc = Document.openDocument(path);
		mDocView.setDocument(mDoc);
		
	}
	
	private void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}
	
	public Uri openPDF() {
		AssetManager assetManager = getAssets();
		InputStream in = null;
		OutputStream out = null;
		
		File file = new File(getFilesDir(), "git-cs.pdf");
		try {
			in = assetManager.open("git-cs.pdf");
			out = openFileOutput(file.getName(), Context.MODE_PRIVATE);
			
			copyFile(in, out);
			in.close();
			in = null;
			out.flush();
			out.close();
			out = null;
		} catch (Exception e) {
			Log.e("tag", e.getMessage());
		}
		return Uri.parse("file://" + getFilesDir() + "/git-cs.pdf");
	}
	
	@Override
	public void finish()
	{
		//  stop the view
		mDocView.finish();
		super.finish();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
