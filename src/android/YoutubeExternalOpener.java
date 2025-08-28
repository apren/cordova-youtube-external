package cordova.plugin.youtubeexternal;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import org.apache.cordova.*;
import org.apache.cordova.engine.SystemWebViewEngine;
import org.apache.cordova.engine.SystemWebViewClient;

public class YoutubeExternalOpener extends CordovaPlugin {

  private static boolean isYoutube(String url) {
    if (url == null) return false;
    // Coincide youtube.com, youtu.be, youtube-nocookie.com
    return url.matches("(?i)^https?://([a-z0-9-]+\\.)*(youtube\\.com|youtu\\.be|youtube-nocookie\\.com)/.*");
  }

  @Override
  public void initialize(CordovaInterface cordova, CordovaWebView webView) {
    super.initialize(cordova, webView);

    SystemWebViewEngine engine = (SystemWebViewEngine) webView.getEngine();
    WebView view = (WebView) engine.getView();

    view.setWebViewClient(new SystemWebViewClient(engine) {

      @Override
      public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        if (isYoutube(url)) {
          Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
          cordova.getActivity().startActivity(i);
          return true; // cancelar en WebView
        }
        return super.shouldOverrideUrlLoading(view, request);
      }

      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (isYoutube(url)) {
          Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
          cordova.getActivity().startActivity(i);
          return true;
        }
        return super.shouldOverrideUrlLoading(view, url);
      }
    });
  }
}
