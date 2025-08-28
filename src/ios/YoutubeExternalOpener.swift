import Foundation
import WebKit

@objc(YoutubeExternalOpener) class YoutubeExternalOpener: CDVPlugin, WKNavigationDelegate {

  func isYoutube(_ url: URL?) -> Bool {
    guard let u = url, let host = u.host?.lowercased() else { return false }
    if host.hasSuffix("youtube.com") { return true }
    if host.hasSuffix("youtu.be") { return true }
    if host.hasSuffix("youtube-nocookie.com") { return true }
    return false
  }

  override func pluginInitialize() {
    super.pluginInitialize()
    if let wk = self.webView as? WKWebView {
      wk.navigationDelegate = self
    }
  }

  public func webView(_ webView: WKWebView,
                      decidePolicyFor navigationAction: WKNavigationAction,
                      decisionHandler: @escaping (WKNavigationActionPolicy) -> Void) {

    let url = navigationAction.request.url
    if isYoutube(url) {
      if let u = url {
        UIApplication.shared.open(u, options: [:], completionHandler: nil)
      }
      decisionHandler(.cancel)
      return
    }
    decisionHandler(.allow)
  }
}
