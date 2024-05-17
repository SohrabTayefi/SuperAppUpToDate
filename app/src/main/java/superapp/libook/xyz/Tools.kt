package superapp.libook.xyz

import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

object Tools: ComponentActivity() {
    @Composable
    fun WebViewComponent(url: String) {
        val context = LocalContext.current
        val webView = remember { WebView(context) }

        AndroidView(modifier = Modifier.fillMaxSize(), factory = {
            webView.apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                loadUrl(url)
                clearCache(true)
                clearHistory()
                settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.loadsImagesAutomatically = true
                settings.allowContentAccess = true
                settings.setSupportMultipleWindows(true)
                settings.setSupportZoom(true)
                settings.builtInZoomControls = true
                settings.displayZoomControls = false
                isHorizontalScrollBarEnabled = true
                isVerticalScrollBarEnabled = true
                scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        if (url.equals("https://pathways.libook.xyz/contents/search")) view?.loadUrl(
                            "javascript:var container = document.getElementById('loginRegisterButtons');" + "if(container) container.parentNode.removeChild(container);"
                        )
                        super.onPageStarted(view, url, favicon)
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        view?.loadUrl(
                            "javascript:var container = document.getElementById('loginRegisterButtons');" + "container.parentNode.removeChild(container);"
                        )
                    }

                    @Deprecated(
                        "Deprecated in Java", ReplaceWith(
                            "Toast.makeText(context, description, Toast.LENGTH_SHORT).show()",
                            "android.widget.Toast",
                            "android.widget.Toast"
                        )
                    )
                    override fun onReceivedError(
                        view: WebView?, errorCode: Int, description: String?, failingUrl: String?
                    ) {
                        Toast.makeText(context, description, Toast.LENGTH_SHORT).show()
                    }

                    override fun shouldOverrideUrlLoading(
                        view: WebView?, request: WebResourceRequest?
                    ): Boolean {
//                    view?.loadUrl("javascript:var container = document.getElementById('loginRegisterButtons');" +
//                            "if(container) container.parentNode.removeChild(container);")
                        // Implement custom logic to determine whether to load from cache or network
                        return super.shouldOverrideUrlLoading(view, request)
                    }

                    override fun shouldInterceptRequest(
                        view: WebView?, request: WebResourceRequest?
                    ): WebResourceResponse? {
                        val headers = HashMap<String, String>()
                        headers["X-Forwarded-For"] = "141.211.4.224"
                        request?.requestHeaders?.forEach {
                            headers[it.key] = it.value
                        }
                        val modifiedRequest = object : WebResourceRequest {
                            override fun getUrl(): Uri? {
                                return request?.url
                            }

                            override fun isForMainFrame(): Boolean {
                                return request?.isForMainFrame ?: false
                            }

                            override fun isRedirect(): Boolean {
                                return request?.isRedirect ?: false
                            }

                            override fun hasGesture(): Boolean {
                                return request?.hasGesture() ?: false
                            }

                            override fun getMethod(): String? {
                                return request?.method
                            }

                            override fun getRequestHeaders(): MutableMap<String, String>? {
                                return headers
                            }
                        }
                        return super.shouldInterceptRequest(view, modifiedRequest)
                    }


                }
                val css = """
                <style>
                    table {
                        width: 100%;
                        border-collapse: collapse;
                    }
                    th, td {
                        padding: 8px;
                        text-align: left;
                        border-bottom: 1px solid #ddd;
                    }
                    th {
                        background-color: #f2f2f2;
                    }
                    @media screen and (max-width: 600px) {
                        table, thead, tbody, th, td, tr {
                            display: block;
                        }
                        th {
                            display: none;
                        }
                        td {
                            border: none;
                            border-bottom: 1px solid #ddd;
                            position: relative;
                            padding-left: 50%;
                        }
                        td:before {
                            position: absolute;
                            top: 6px;
                            left: 6px;
                            width: 45%;
                            padding-right: 10px;
                            white-space: nowrap;
                        }
                    }
                </style>
            """.trimIndent()

                // Inject the CSS into the WebView
                val js =
                    "javascript:var container = document.getElementById('loginRegisterButtons');" + "if(container) container.parentNode.removeChild(container);"
                val javascript =
                    "$js + javascript:document.getElementsByTagName('head')[0].innerHTML += '$css';"
                loadUrl(javascript)
            }
        })

        // Handle back button press
        BackHandler {
            // Check if the WebView can go back
            if (webView.canGoBack()) {
                webView.goBack() // Go back to previous page
            } else {
                // Let the system handle the back button
            }
        }
    }
    @Composable
    fun SiteBoxComponent(drawable: Int, text: String) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = drawable),
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .size(75.dp)
            )
            Text(
                text = text,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
        }
    }
}