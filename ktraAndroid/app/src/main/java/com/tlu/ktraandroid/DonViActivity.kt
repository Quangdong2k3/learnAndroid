package com.tlu.ktraandroid

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDismissState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.tlu.ktraandroid.control.DonViViewModel
import com.tlu.ktraandroid.model.DonVi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//@SuppressLint("RestrictedApi")
//class DonViActivity : ComponentActivity(){
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//
//        }
//    }
//}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun thongTinDVScreen(navController: NavHostController? = null, idDonVi: String? = "") {
    val context = LocalContext.current

    val donViViewModel: DonViViewModel = viewModel()
    val stickyHeaderHeight = 200.dp
    var headerHeight by remember { mutableStateOf(stickyHeaderHeight) }
    val ten by donViViewModel.ten.collectAsState()
    val diaChi by donViViewModel.diaChi.collectAsState()
    val email by donViViewModel.email.collectAsState()
    val logo by donViViewModel.logo.collectAsState()
    val soDT by donViViewModel.soDienThoai.collectAsState()
    val webSite by donViViewModel.website.collectAsState()
    val tenDVC by donViViewModel.ten.collectAsState()

    LaunchedEffect(idDonVi) {
        donViViewModel.getDonVi(idDonVi.toString(), context)
    }
    LaunchedEffect(tenDVC) {
        donViViewModel.getNameDVC()
    }
    LazyColumn() {
        item {
            Header(headerHeight, logo)

        }
        item {
            Log.e("Diachi", diaChi)
            TextField(
                value = ten,
                onValueChange = { donViViewModel.setName(it) },
                placeholder = {
                    Text(
                        text = "Tên đơn vị"
                    )
                }, label = { Text(text = "Tên đơn vị") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = diaChi,
                onValueChange = { donViViewModel.setAddress(it) },
                placeholder = {
                    Text(
                        text = "Địa chỉ"
                    )
                }, label = {
                    Text(
                        text = "Địa chỉ"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = email,
                onValueChange = { donViViewModel.setEmail(it) },
                placeholder = {
                    Text(
                        text = "Email"
                    )
                }, label = {
                    Text(
                        text = "Email"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = soDT,
                onValueChange = { donViViewModel.setPhone(it) },
                placeholder = {
                    Text(
                        text = "Số điện thoại"
                    )
                }, label = {
                    Text(
                        text = "Số điện thoại"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = logo,
                onValueChange = { donViViewModel.setLogo(it) },
                placeholder = {
                    Text(
                        text = "Logo"
                    )
                }, label = {
                    Text(
                        text = "Logo"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = webSite,
                onValueChange = { donViViewModel.setWebsite(it) },
                placeholder = {
                    Text(
                        text = "link: //"
                    )
                }, label = {
                    Text(
                        text = "website"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (tenDVC != null && tenDVC != "") {
                TextField(
                    value = donViViewModel.tenDVC.value, onValueChange = {},
                    enabled = false,
                    placeholder = {
                        Text(
                            text = ""
                        )
                    }, label = {
                        Text(
                            text = "Tên đơn vị cha"
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Button(
                onClick = {
                    Log.d("Thong tin don vi cha: ", donViViewModel.madvc.value)
                    donViViewModel.updateDV(context)

                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3F51B5)
                ), shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Sửa")
            }

        }

    }
}


@Composable
fun Header(height: Dp, image: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = image,
                error = painterResource(id = R.drawable.ic_launcher_background)
            ),
            contentScale = ContentScale.Inside,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .height(height)
                .fillMaxWidth()
        )
    }
}

@Composable
@Preview(showBackground = true)

fun DefaultPreview() {
    thongTinDVScreen()
}


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DVScreen(donViViewModel: DonViViewModel, navController: NavHostController) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val searchtxt = remember {
        mutableStateOf("")
    }


    val listDonVi = donViViewModel.listDonVi.collectAsState()

    ConstraintLayout() {


        Column(modifier = Modifier.fillMaxSize()) {

            TextField(value = searchtxt.value,
                onValueChange = {
                    searchtxt.value = it

                    donViViewModel.searchDV(it)
                },
                placeholder = {
                    Text(
                        text = "Tìm kiếm tên đon vị"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        Icons.Default.Search, contentDescription = null
                    )
                }
            )




            Column(modifier = Modifier.padding(10.dp)) {
//                val listDV01= listDonVi.value?.toMutableList()?: mutableListOf()
                if (listDonVi.value.isNotEmpty()) {
                    LazyColumn {
                        itemsIndexed(
                            listDonVi.value
                        ) { index, item ->
                            val coroutineScope = rememberCoroutineScope()
                            val state = rememberDismissState(

                                initialValue = DismissValue.Default,
                                positionalThreshold = { totalDistance: Float -> totalDistance / 3 })

                            SwipeToDismiss(
                                state = state,
                                modifier = Modifier.animateItemPlacement(tween(200)),
                                background = {
                                    val color = when (state.dismissDirection) {
                                        DismissDirection.EndToStart -> Color.Red
                                        DismissDirection.StartToEnd -> Color.Transparent
                                        null -> Color.Transparent
                                    }
                                    Box(
                                        modifier = Modifier
                                            .background(color)
                                            .fillMaxSize()

                                            .padding(8.dp), contentAlignment = Alignment.CenterEnd
                                    ) {
                                        Row() {
                                            Icon(
                                                Icons.Default.Delete,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier

                                                    .clickable {
                                                        donViViewModel.deleteDV(item, context)
                                                    })
//                                    if (state.targetValue == DismissValue.DismissedToEnd) {
//                                        coroutineScope.launch { state.reset() }
//                                    }
                                            Icon(
                                                Icons.Default.Refresh,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier


                                                    .clickable { coroutineScope.launch { state.reset() } })
                                        }
                                    }
                                },
                                dismissContent = {
                                    ItemListDV(item ?: DonVi()) {
                                        navController.navigate("inFoDonVi/${item.maDonVi}")
                                    }

                                },
                                directions = setOf(DismissDirection.EndToStart)
                            )
                            Divider()
                        }
                    }
                }
            }
        }
        val t = createRef()
        Column(modifier = Modifier.constrainAs(t) {
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
        }) {
            FloatingActionButton(onClick = { showBottomSheet = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
        if (showBottomSheet == true) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight(0.9f),
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState
            ) {
                ThemDonViScreen(donViViewModel){
                    showBottomSheet=it
                }
            }
        }
    }


}

@Composable
//@Preview(showSystemUi = true)
fun ThemDonViScreen(donViViewModel: DonViViewModel = viewModel(),doneAction:(Boolean)->Unit) {
    val name by donViViewModel.ten.collectAsState()
    val context = LocalContext.current
    val diaChi by donViViewModel.diaChi.collectAsState()
    val email by donViViewModel.email.collectAsState()
    val logo by donViViewModel.logo.collectAsState()
    val soDT by donViViewModel.soDienThoai.collectAsState()
    val webSite by donViViewModel.website.collectAsState()
    val donvi = remember {
        mutableStateOf("")
    }
    val listDVC = donViViewModel.listDonviCha.collectAsState()
    val maDonViCha by donViViewModel.madvc.collectAsState()

    Column {
        Column(modifier = Modifier.padding(10.dp)) {
            TextField(
                value = name,
                onValueChange = { donViViewModel.setName(it) },
                placeholder = {
                    Text(
                        text = "Tên đơn vị"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = diaChi,
                onValueChange = { donViViewModel.setAddress(it) },
                placeholder = {
                    Text(
                        text = "Địa chỉ"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = email,
                onValueChange = { donViViewModel.setEmail(it) },
                placeholder = {
                    Text(
                        text = "Email"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = soDT,
                onValueChange = { donViViewModel.setPhone(it) },
                placeholder = {
                    Text(
                        text = "Số điện thoại"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = logo,
                onValueChange = { donViViewModel.setLogo(it) },
                placeholder = {
                    Text(
                        text = "Logo"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = webSite,
                onValueChange = { donViViewModel.setWebsite(it) },
                placeholder = {
                    Text(
                        text = "link: //"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            ComboBoxListDV(listDVC.value, title = "Chọn đơn vị", callBack = {
                donViViewModel.setIdDVC(it)
                Log.d("Ma Cha 1", it)

            })
        }
        Column(modifier = Modifier.padding(10.dp)) {

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = {
                        donViViewModel.addDonVi(
                            DonVi(
                                "", name, email, webSite, logo, diaChi, soDT,
                                mutableListOf(), maDonViCha
                            ), context
                        )
                        donvi.value = ""
                        doneAction(false)

                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0E0ECF)
                    ), shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = "Thêm")
                }

            }
        }
    }

}


@Composable
fun ItemListDV(n: DonVi, onClick: (DonVi) -> Unit) {
    androidx.compose.material3.ListItem(headlineContent = { Text(text = "${if (n.ten.equals("")) "OverLine" else n.ten} ") },
        overlineContent = { Text("OverLine") },
        leadingContent = {
            Image(
                painter = rememberAsyncImagePainter(
                    model = n.logo,
                    error = painterResource(id = R.drawable.ic_launcher_background)
                ),
                contentDescription = null, contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(60.dp)

            )
        },
        trailingContent = {
            Icon(
                Icons.Outlined.Info, contentDescription = null, tint = Color(
                    0xFF03A9F4
                ), modifier = Modifier.clickable { onClick(n) }
            )
        })

}

suspend fun loadProgress(updateProgress: (Float) -> Unit) {
    for (i in 1..100) {
        updateProgress(i.toFloat() / 100)
        delay(100)
    }
}