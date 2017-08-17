package net.egordmitriev.mypdf;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.artifex.mupdf.fitz.Document;

import net.egordmitriev.libmupdf.PDFView;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import static net.egordmitriev.mypdf.Utils.copyFile;

public class MainActivity extends AppCompatActivity {
	
	private PDFView mDocView;
	private Document mDoc;
	
	private EditText mSearch;
	private String mPath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Uri pdf = extractAsset("git-cs.pdf");
		mPath = Uri.decode(pdf.getEncodedPath());
		
		mDocView = (PDFView) findViewById(R.id.doc_view_inner);
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.doc_wrapper);
		mDocView.setupHandles(layout);
		
		
		mDocView.setSearchScrollPos(0.35f);
		
		Button previous = (Button) findViewById(R.id.search_previous);
		Button next = (Button) findViewById(R.id.search_next);
		mSearch = (EditText) findViewById(R.id.search_input);
		
		next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mDocView.onSearchNext(mSearch.getText().toString());
				//mDocView.setScale(2.0f);
			}
		});
		
		previous.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mDocView.onSearchPrevious(mSearch.getText().toString());
			}
		});
		
		Button load = (Button) findViewById(R.id.load);
		load.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mDoc = Document.openDocument(mPath);
				mDocView.setDocument(mDoc);
			}
		});
		
	}
	
	public Uri extractAsset(String name) {
		AssetManager assetManager = getAssets();
		File file = new File(getFilesDir(), name);
		try {
			InputStream in = assetManager.open(name);
			OutputStream out = openFileOutput(file.getName(), Context.MODE_PRIVATE);
			copyFile(in, out);
			in.close();
			out.flush();
			out.close();
		} catch (Exception e) {
			Log.e("tag", e.getMessage());
		}
		return Uri.parse("file://" + getFilesDir() + "/" + name);
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
