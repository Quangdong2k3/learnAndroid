package com.tlu.ktraandroid

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.database.FirebaseDatabase
import com.tlu.ktraandroid.control.DonViViewModel
import com.tlu.ktraandroid.control.NhanVienViewModel
import com.tlu.ktraandroid.model.DonVi
import com.tlu.ktraandroid.model.NhanVien
import com.tlu.ktraandroid.ui.theme.KtraAndroidTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        setContent {
            val navController = rememberNavController()
            val currentBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = currentBackStackEntry?.destination?.route
            KtraAndroidTheme {
                CommonScaffold(
                    navController = navController,
                    title = when(currentRoute){
                        "infoNV/{itemId}"->"Thông tin nhân viên"
                        "inFoDonVi/{itemId}"->"Thông tin đơn vị"
                        else -> {"App Danh bạ"}
                    },
                    showBackButton = if (currentRoute != "themNhanVien" && currentRoute != "themDonVi" && currentRoute != "infoNV/{itemId}"&& currentRoute != "inFoDonVi/{itemId}") false else true,
//                    fab = { navController: NavController ->
//                        if (currentRoute == "DVScreen") navController.navigate(
//                            "themDonVi"
//                        ) else if (currentRoute == "NVScreen") navController.navigate("themNhanVien")
//                    },
                    content = {
                        NavHost(
                            navController = navController,
                            startDestination = "DVScreen",
                            modifier = Modifier.padding(
                                top = it.calculateTopPadding(),
                                bottom = it.calculateBottomPadding()
                            )
                        ) {
                            composable("DVScreen") {
                                DVScreen(DonViViewModel(), navController)
                            }
                            composable("NVScreen") { NVScreen( navController,NhanVienViewModel()) }
                            composable("inFoDonVi/{itemId}") {backStackEntry->
                                val itemId = backStackEntry.arguments?.getString("itemId")
                                thongTinDVScreen(navController,itemId)
                            }
                            composable("infoNV/{itemId}") {
                                val id = it.arguments?.getString("itemId")
                                thongTinNVScreen(id)
                            }

                        }
                    },
                    showBottomBar = if (currentRoute != "themNhanVien" && currentRoute != "themDonVi" && currentRoute != "inFoEmpl" && currentRoute != "inFoDonVi/{itemId}") true else false
                )
            }
        }


    }





//    @Composable
//    fun AppComp(navController: NavHostController) {
//
//        val selectedTabIndex = remember {
//            mutableStateOf(0)
//        }
//        val donViViewModel = DonViViewModel()
//        val nhanVienViewModel = NhanVienViewModel()
//        Column {
//            TabRow(selectedTabIndex = selectedTabIndex.value) {
//                tabItems.forEachIndexed { index, s ->
//                    Tab(selected = index == selectedTabIndex.value,
//                        onClick = { selectedTabIndex.value = index },
//                        text = {
//                            Text(
//                                text = s
//                            )
//                        })
//                }
//            }
//            Column {
//                when (selectedTabIndex.value) {
//                    0 -> QLDVScreen(donViViewModel, navController)
//                    1 -> QLNVSCreen(nhanVienViewModel, navController)
//                }
//            }
//        }
//    }

//@Composable
//@Preview(showSystemUi = true)
//fun AppCompPre() {
//    QLNVSCreen(nhanVienViewModel, navController)
//}


}

val list = mutableStateListOf(
    NhanVien()

)


data class TabItem(
    val name: String,

    )

val tabItems = listOf(
    "Quản lý Đơn vị", "Quản lý Nhân viên"
)





