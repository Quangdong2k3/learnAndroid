package com.tlu.ktraandroid

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.database.FirebaseDatabase
import com.tlu.ktraandroid.control.DonViViewModel
import com.tlu.ktraandroid.control.NhanVienViewModel
import com.tlu.ktraandroid.model.NhanVien
import com.tlu.ktraandroid.ui.theme.KtraAndroidTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val splashScreen = installSplashScreen()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        splashScreen.setKeepOnScreenCondition{
            false
        }

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





