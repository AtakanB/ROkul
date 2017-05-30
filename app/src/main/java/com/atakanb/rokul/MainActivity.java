package com.atakanb.rokul;

import android.app.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.*;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import android.widget.*;
import android.view.*;
import android.graphics.*;
import java.util.*;
import java.text.*;
import android.content.*;
import android.telephony.*;
import android.provider.*;
import android.content.pm.*;
import android.net.*;
import org.json.*;
import okhttp3.*;
import org.jsoup.nodes.*;
import org.jsoup.*;
import org.jsoup.select.*;
import com.atakanb.rokul.CookieStore;

public class MainActivity extends Activity 
{
	public static String del;
	public static ProgressDialog dialog;
	public static String user[];
	public static String deviceId;
	public static String loading[] = { //that's pretty funny eh?
		"Robot İstilası Engelleniyor",
		"La Li Lu Le Lo",
		"Atom Kurmak Yok",
		"Assault Kurdum Gel Abi",
		"Sometimes, I Dream About Cheese",
		"Tek 7, Hack Dedi",
		"Rankım Tahta 1 Ama Bir Globali Yenebilirim",
		"Yüklenmiyor... :)",
		"(ﾉಠ_ಠ)ﾉ",
		"Kara Jawaylan Manavgat-Alanya Arası 6 Kişi 15 Dakikada Geliverdik",
	};
	PackageManager pm; //Paket Yöneticisi
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

		/////////////////////////////////////E-Okul Uygulamasının Telefon ID Alma Sistemin Kopyası
		SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		TelephonyManager telephonyManager = (TelephonyManager)this.getBaseContext().getSystemService("phone");
		String string2 = telephonyManager.getDeviceId();
		String string3 = telephonyManager.getSimSerialNumber();
		UUID uUID = new UUID((long)Settings.Secure.getString((ContentResolver)this.getContentResolver(), (String)"android_id").hashCode(), (long)string2.hashCode() << 32 | (long)string3.hashCode());
		this.deviceId = uUID.toString();
		////////////////////////////////////////////////////

		pm = this.getPackageManager();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
    }
	
	public void onClick(View v) //Birinci Buton
    {
		int value = new Random().nextInt(loading.length);
		del = loading[value];
		dialog = ProgressDialog.show(MainActivity.this, "", del, true);
		Thread thread = new Thread(new Runnable() {
		@Override
		public void run() {
			try  {
				if(!isPackageInstalled("com.meb.vbsmobil", pm)){
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.meb.vbsmobil")));
					Toast.makeText(MainActivity.this, "E Okul Uygulaması Yüklü Değil", Toast.LENGTH_SHORT).show();
	    		}else{
					try
						{
						user = get_user();
						MainActivity.this.runOnUiThread(new Runnable(){
						@Override
							public void run() {
								check(0);
							}});
						}
					catch (IOException e)
					{}
					catch (JSONException e)
					{}
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		});
		thread.start();
		
    } 
	
	public void onClickOrta(View v) //Ikinci Buton
    {
		int value = new Random().nextInt(loading.length);
		del = loading[value];
		dialog = ProgressDialog.show(MainActivity.this, "", del, true);
		Thread thread = new Thread(new Runnable() {


				@Override
				public void run() {
					try  {
						if(!isPackageInstalled("com.meb.vbsmobil", pm)){
							dialog.hide();
							startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.meb.vbsmobil")));
							Toast.makeText(MainActivity.this, "E Okul Uygulaması Yüklü Değil", Toast.LENGTH_SHORT).show();
						}else{
							try
							{
								user = get_user();
								MainActivity.this.runOnUiThread(new Runnable(){
										@Override
										public void run() {
											check(1);
										}});
							}
							catch (IOException e)
							{}
							catch (JSONException e)
							{}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

		thread.start(); 

    } 
	
	public void onClickKopya(View v) //Ikinci Buton
    {
		int value = new Random().nextInt(loading.length);
		del = loading[value];
		dialog = ProgressDialog.show(MainActivity.this, "", del, true);
		Thread thread = new Thread(new Runnable() {


				@Override
				public void run() {
					try  {
						if(!isPackageInstalled("com.meb.vbsmobil", pm)){
							dialog.hide();
							startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.meb.vbsmobil")));
							Toast.makeText(MainActivity.this, "E Okul Uygulaması Yüklü Değil", Toast.LENGTH_SHORT).show();
						}else{
							try
							{
								user = get_user();
								MainActivity.this.runOnUiThread(new Runnable(){
										@Override
										public void run() {
											check(2);
										}});
							}
							catch (IOException e)
							{}
							catch (JSONException e)
							{}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

		thread.start(); 

    }

	private boolean isPackageInstalled(String packagename, PackageManager packageManager) {
		try {
			packageManager.getPackageInfo(packagename, 0);
			return true;
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
	}
	
	public static OkHttpClient client = new OkHttpClient.Builder()
    .cookieJar(new CookieStore())
    .build();

	public static String run(String url) throws IOException { 
		Request request = new Request.Builder() .url(url)
			.header("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.1.1; en-gb; Build/KLP) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Safari/534.30")
			.build();
		Response response = client.newCall(request).execute(); 
		if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
		return response.body().string();
    }
	

	public static String[] get_user() throws IOException, JSONException{
      
		String[] user  = new String[2];//return
		JSONArray GID = new JSONArray(run("http://mservice.eokul.meb.gov.tr/Service1.svc/OgrenciWebSayfasinaBaglanma?sifre=fEwQ37.pa4$BT&uid="+ deviceId +"&ip=1.1.1.1")); //GID Json Verisi
		Document doc = Jsoup.parse(run("http://mobileokul.meb.gov.tr/OgrenciWM/GirisApp/?uid="+ deviceId +"&gid="+ GID.getJSONObject(0).getString("GID"))); //Öğrenci Seçme Menüsü
		Elements content = doc.select("div[class=ui-block-a id=]");   //Birinci Baloncuk
		user[0] = content.first().text(); //Öğrencinin Adı
		String fnLink = content.first().select("a").attr("onclick");//Öğrencinin Adresi
		fnLink = fnLink.replace("fnLinkGiris('Main','", "");
		fnLink = fnLink.replace("',0)",""); //TODO: DAHA IYI BIR YOL BUL
		user[1] = fnLink; //Link
		if(content.first().select("a").size() > 0) {
			return user; //Kullanıcı Varsa Onu Geri Gönder
		}else {
			user[0] = "Öğrenci Ekle-Sil"; //Kullanıcı Yoksa Öğrenci Ekle-Sil'i Geri Gönder
			return user;
		}
	}
	
	public void check(int sw){
		if (!user[0].equalsIgnoreCase("Öğrenci Ekle-Sil")) //Gelen Veri Öğrenci Ekle Sil Değilse Öğrenci Vardır Demektir. Ilk Baloncuktaki Öğrenciye Giriş Yap
		{
			Intent i=new Intent(MainActivity.this,WebView2.class); //WebView Intent'i
			i.putExtra("url", "http://mobileokul.meb.gov.tr/OgrenciWM/Main?idt="+user[1]); //Giriş Anahtarı Ile Ana Sayfayı Birleştir
			i.putExtra("dls", del); //Yükleniyor Ekranı Metni
			switch(sw){ //3 Buton Için Ayrı Yönlendirmeler
				case 0: 
					i.putExtra("go", "0"); //Açılan WebView'i 2 Saniye Sonra /NotBilgisi Adresine Yönlendir
					MainActivity.this.startActivity(i); //WebView Aktivitesini Başlat
					break;
			    case 1: 
					i.putExtra("go", "ort"); //Açılan WebView'i 2 Saniye Sonra /SubeOrtalamasi Adresine Yönlendir
					MainActivity.this.startActivity(i); //WebView Aktivitesini Başlat
					break;
				case 2: 
					Toast.makeText(MainActivity.this, "E Okul Linki Kopyalandı", Toast.LENGTH_SHORT).show(); //Linki Kopyala
					ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
					ClipData clip = ClipData.newPlainText(null," http://mobileokul.meb.gov.tr/OgrenciWM/Main?idt="+user[1]); clipboard.setPrimaryClip(clip); //Kopyala
					break;

			}
		    dialog.hide();
		}
		else
		{
			dialog.hide();
			Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.meb.vbsmobil"); //Eğer Kullanıcı Yoksa
			startActivity( LaunchIntent );															//Asıl E Okul Uygulamasını Başlatıp
			Toast.makeText(MainActivity.this, "E Okul Uygulamasında Giriş Yapmış Öğrenci Yok", Toast.LENGTH_SHORT).show(); //Giriş Yapılmasını Istiyoruz
		}
	}
}

	