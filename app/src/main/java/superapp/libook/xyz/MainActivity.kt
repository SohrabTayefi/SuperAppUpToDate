package superapp.libook.xyz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import superapp.libook.xyz.Tools.SiteBoxComponent
import superapp.libook.xyz.Tools.WebViewComponent
import superapp.libook.xyz.ui.theme.SuperAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuperAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    Scaffold(topBar = {
        val currentDestination by navController.currentBackStackEntryAsState()
        TopAppBar(modifier = Modifier.fillMaxWidth(), colors = topAppBarColors(
            containerColor = Color(0xD5E0E1EB),
            titleContentColor = Color.Black,
        ), navigationIcon = {
            // Get the current destination
            if (currentDestination?.destination?.route != "home") {
                Button( onClick = {
                    scope.launch {
                        navController.navigateUp()
                    }
                }) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = "Back To Main"
                    )
                }
            }
        }, title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (currentDestination?.destination?.route != "home") {
                    Text(
                        "Super App",
                        modifier = Modifier.align(Alignment.CenterStart).padding(start = 37.dp),
                        textAlign = TextAlign.Center
                    )
                }else
                    Text(
                        "Super App",
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )

            }
        })
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { FeatureGrid(navController) }
            composable("site1") { WebViewComponent(url = "http://Ck.libook.xyz ") }
            composable("site2") { WebViewComponent(url = "http://ck.libook.xyz/Nursing") }
            composable("site3") { WebViewComponent(url = "http://ck.libook.xyz/pharmacology") }
            composable("site4") { WebViewComponent(url = "https://ck.liboox.xyz/student") }
            composable("site5") { WebViewComponent(url = "http://exp.libook.xyz  ") }
            composable("site6") { WebViewComponent(url = "https://mrdx.libook.xyz/login") }
            composable("site7") { WebViewComponent(url = "https://Ovidpack1.libook.xyz/login") }
            composable("site8") { WebViewComponent(url = "https://Ovidpack2.libook.xyz/login") }
            composable("site9") { WebViewComponent(url = "https://Ovidpack3.libook.xyz/login") }
            composable("site10") { WebViewComponent(url = "http://pepid.libook.xyz ") }
            composable("site11") { WebViewComponent(url = "http://proquest.libook.xyz ") }
            composable("site12") { WebViewComponent(url = "https://sanfordguide.libook.xyz") }
            composable("site13") { WebViewComponent(url = "http://sp.libook.xyz ") }
            composable("site14") { WebViewComponent(url = "http://statdx.libook.xyz ") }
            composable("site15") { WebViewComponent(url = "http://upd.libook.xyz") }
            composable("site16") { WebViewComponent(url = "https://Pathways.libook.xyz") }
            composable("site17") { WebViewComponent(url = "https://lexi.libook.xyz") }
            composable("site18") { WebViewComponent(url = "https://wos.libook.xyz") }
        }
    }
}


@Composable
fun FeatureGrid(navController: NavController) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(18) { index ->
            FeatureTile(index, navController)
        }
    }
}

@Composable
fun FeatureTile(index: Int, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .aspectRatio(1f), // To make the tile square
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxSize()
            .clickable {
                navController.navigate("site${index + 1}")
            }) {
            when (index) {
                0 -> {
                    SiteBoxComponent(R.drawable.clinicalkey, "clinicalkey")
                }

                1 -> {
                    SiteBoxComponent(R.drawable.clinicalkey, "clinicalkey Nursing")
                }

                2 -> {
                    SiteBoxComponent(R.drawable.clinicalkey, "clinicalkey pharmacology")
                }

                3 -> {
                    SiteBoxComponent(R.drawable.clinicalkey, "clinicalkey Student")
                }

                4 -> {
                    SiteBoxComponent(R.drawable.clinicalkey, "Expertpath")
                }

                5 -> {
                    SiteBoxComponent(R.drawable.merative_micromedex, "Merative Micromedex")
                }

                6 -> {
                    SiteBoxComponent(R.drawable.ovid_journals, "Ovid Journals 01")
                }

                7 -> {
                    SiteBoxComponent(R.drawable.ovid_journals, "Ovid Journals 02")
                }

                8 -> {
                    SiteBoxComponent(R.drawable.ovid_journals, "Ovid Journals 03")
                }

                9 -> {
                    SiteBoxComponent(R.drawable.pepid, "PEPID")
                }

                10 -> {
                    SiteBoxComponent(R.drawable.proquest, "Proquest Theses and Dissertations")
                }

                11 -> {
                    SiteBoxComponent(R.drawable.sanford_guide, "Sanford Guide")
                }

                12 -> {
                    SiteBoxComponent(R.drawable.springer_link, "SpringerLink")
                }

                13 -> {
                    SiteBoxComponent(R.drawable.statdx, "StatDX")
                }

                14 -> {
                    SiteBoxComponent(R.drawable.uptodate, "Uptodate")
                }

                15 -> {
                    SiteBoxComponent(R.drawable.uptodate, "Uptodate Pathways")
                }

                16 -> {
                    SiteBoxComponent(R.drawable.uptodate_lexidrug, "Uptodate LexiDrug (Lexicomp)")
                }

                17 -> {
                    SiteBoxComponent(R.drawable.web_of_science, "Web Of Science")
                }

            }
        }
    }
}

