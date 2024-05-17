package superapp.libook.xyz

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
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
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import kotlinx.coroutines.launch
import superapp.libook.xyz.ui.theme.SuperAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuperAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            AppBar(
                onMenuClick = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            )
        },
        drawerContent = {
            DrawerContent(navController)
        },
        drawerState = drawerState
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") { HomeScreen() }
            composable("feature1") { FeatureScreen(featureName = "Feature 1") }
            composable("feature2") { FeatureScreen(featureName = "Feature 2") }
            composable("feature3") { FeatureScreen(featureName = "Feature 3") }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    WebViewComponent(url = "https://pathways.libook.xyz")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SuperAppTheme {
        FeatureGrid()
    }
}

@Composable
fun FeatureGrid() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(18) { index ->
            FeatureTile(index)
        }
    }
}

@Composable
fun FeatureTile(index: Int) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .aspectRatio(1f), // To make the tile square
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = "Feature ${index + 1}")
        }
    }
}

@Composable
fun WebViewComponent(url: String) {
    val context = LocalContext.current
    val webView = remember { WebView(context) }

    AndroidView(modifier = Modifier.fillMaxSize(), factory = {
        webView.apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
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
                    if (url.equals("https://pathways.libook.xyz/contents/search"))
                        view?.loadUrl(
                            "javascript:var container = document.getElementById('loginRegisterButtons');" +
                                    "if(container) container.parentNode.removeChild(container);"
                        )
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    view?.loadUrl(
                        "javascript:var container = document.getElementById('loginRegisterButtons');" +
                                "container.parentNode.removeChild(container);"
                    )
                }

                @Deprecated("Deprecated in Java")
                override fun onReceivedError(
                    view: WebView?, errorCode: Int, description: String?, failingUrl: String?
                ) {
                    Toast.makeText(context, description, Toast.LENGTH_SHORT).show()
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
//                    view?.loadUrl("javascript:var container = document.getElementById('loginRegisterButtons');" +
//                            "if(container) container.parentNode.removeChild(container);")
                    // Implement custom logic to determine whether to load from cache or network
                    return super.shouldOverrideUrlLoading(view, request)
                }

                override fun shouldInterceptRequest(
                    view: WebView?,
                    request: WebResourceRequest?
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

                        @RequiresApi(Build.VERSION_CODES.N)
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
            val js = "javascript:var container = document.getElementById('loginRegisterButtons');" +
                    "if(container) container.parentNode.removeChild(container);"
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
