package com.atakanb.rokul;
import android.app.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.*;
import android.util.Log;
import android.webkit.*;


public class WebView2 extends Activity
{
	WebView wb;
	private class HelloWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
}

public String go; //Gidilecek Seçenek 0 Not : "ort" Ortalama
public ProgressDialog dialog; //Yükleniyor Diyaloğu
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wb);
		Intent intent = getIntent();
		String url = intent.getStringExtra("url"); //Gelen URL
		String dls = intent.getStringExtra("dls"); //Gelen Yüklenme Metni
		dialog = ProgressDialog.show(WebView2.this, "", dls, true);
		go = intent.getStringExtra("go"); //0 Not : 1 Ortalama
        wb=(WebView)findViewById(R.id.webView1); //WebView
        wb.getSettings().setJavaScriptEnabled(true);
        wb.getSettings().setLoadWithOverviewMode(true);
        wb.getSettings().setUseWideViewPort(true);
        wb.getSettings().setBuiltInZoomControls(false);
        wb.getSettings().setPluginState(WebSettings.PluginState.ON);
		wb.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30");
        wb.setWebViewClient(new HelloWebViewClient());
        wb.loadUrl(url);
		Handler handler = new Handler(); 
		handler.postDelayed(new Runnable() {
				@Override 
				public void run() { 
				    WebView2.this.runOnUiThread(new Runnable(){
							@Override
							public void run() {
								yay();
							}});
					}
			}, 2000); //Yüklenme Tamamlanmasına Atanabilirdi Bu, Ancak Oturum açmak için tüm sayfayı yüklemeye gerek yok. 2 Saniyelik Bir Oturum Açma Süresi Veriyoruz
    }
	void yay(){
		if(go.equals("ort")){
			wb.loadUrl("http://mobileokul.meb.gov.tr/OgrenciWM/SubeYaziliOrtalamalari/"); //Ortalama Seciliyse Ortalama Sayfasını Aç
		}else{
			wb.loadUrl("http://mobileokul.meb.gov.tr/OgrenciWM/NotBilgisi"); //Not Bilgisi Seçili Ise Not Bilgisini Aç
		}
		dialog.hide();
	}

}