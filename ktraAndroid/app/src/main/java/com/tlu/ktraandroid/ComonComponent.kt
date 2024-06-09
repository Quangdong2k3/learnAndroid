package com.tlu.ktraandroid

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.tlu.ktraandroid.control.DVCViewModel
import com.tlu.ktraandroid.model.DonViCha

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CommonScaffold(
    navController: NavController,
    title: String,
    showBackButton: Boolean,
    fab: ((NavController) -> Unit?)? = null,
    content: @Composable (PaddingValues) -> Unit, showBottomBar: Boolean
) {
    Scaffold(
        modifier = Modifier
            .statusBarsPadding(),
        topBar = {
            TopAppBar(
                title = { Text(title) },

                navigationIcon = {
                    if (showBackButton) IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    } else {
                        null
                    }
                }

            )
        },
//        },floatingActionButton = {
//            if(!showBackButton){
//                FloatingActionButton(onClick = { fab?.invoke(navController) }) {
//                    Icon(imageVector = Icons.Default.Add, contentDescription =null )}
//            }else{
//                null
//            }
//        }, floatingActionButtonPosition = FabPosition.Center
        bottomBar = {
            if (showBottomBar)
                Bottombar(navController = navController) else null
        },
    ) {
        content(it)
    }
}

val NavItems = listOf(
    NavItem("Đơn Vị", "DVScreen", R.drawable.structure),
    NavItem("Nhân viên", "NVScreen", R.drawable.baseline_perm_contact_calendar_24)

)

@Composable
fun Bottombar(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    BottomAppBar( modifier = Modifier.systemBarsPadding()) {
        NavItems.fastForEachIndexed { i, navItem ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }

                },
                label = { Text(text = navItem.name) },
                icon = {
                    Icon(
                        painter = painterResource(id = navItem.icon),
                        contentDescription = null,modifier=Modifier.size(32.dp)
                    )
                })
        }
    }
}

@Composable
fun ComboBoxListDV(list: List<DonViCha>, callBack: (String) -> Unit, title: String) {
    val dvcViewModel: DVCViewModel = viewModel()
    var expanded by remember { mutableStateOf(false) }
    var txt by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        TextField(
            value = txt,
            placeholder = { Text(text = title) },
            onValueChange = { txt = it },
            trailingIcon = {

                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            },
            modifier = Modifier.fillMaxWidth()
        )


        Box(contentAlignment = Alignment.BottomEnd, modifier = Modifier.fillMaxWidth()) {
            DropdownMenu(
                expanded = expanded, modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 200.dp), content =
                {
                    list.forEach {
                        DropdownMenuItem(
                            text = { Text(it.tenDV) },
                            onClick = {
                                txt = it.tenDV
                                callBack(it.maDVCha)
                                expanded = false
                            }
                        )
                        Divider()
                    }
                },
                onDismissRequest = { expanded = false }
            )
        }
    }
}

data class NavItem(
    val name: String,
    val route: String,
    val icon: Int
)
