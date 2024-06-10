package com.tlu.ktraandroid

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.tlu.ktraandroid.control.DonViViewModel
import com.tlu.ktraandroid.control.NhanVienViewModel
import com.tlu.ktraandroid.helper.FirebaseStorageHelper
import com.tlu.ktraandroid.model.NhanVien
import com.tlu.ktraandroid.model.listChucVus
import com.tlu.ktraandroid.ui.theme.KtraAndroidTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NVScreen(navController: NavHostController, nhanVienViewModel: NhanVienViewModel) {
    KtraAndroidTheme {


        val context = LocalContext.current

        val searchtxt = remember {
            mutableStateOf("")
        }
        var isLoading by remember { mutableStateOf(false) }

        val sheetState = rememberModalBottomSheetState()
        val scope = rememberCoroutineScope()
        var showBottomSheet by remember { mutableStateOf(false) }
        val focuesManager = LocalFocusManager.current

        val dsNV = nhanVienViewModel.dsNhanVien.collectAsState()
        val dv = nhanVienViewModel.donVi.collectAsState()
        var expandFilter by remember{
            mutableStateOf(false)
        }
        LaunchedEffect(Unit) {
            isLoading = true
            delay(1000) // Delay for 2 seconds
            isLoading = false
        }
        ConstraintLayout {
            Column(modifier = Modifier.fillMaxSize()) {

                TextField(value = searchtxt.value,
                    onValueChange = {
                        searchtxt.value = it
                        scope.launch {
                            delay(100)
                            nhanVienViewModel
                                .searchNV(it,"")
                        }

                    },
                    placeholder = {
                        Text(
                            text = "Tìm kiếm tên nhân viên"
                        )
                    }, keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search, keyboardType = KeyboardType.Text
                    ), keyboardActions = KeyboardActions(
                        onSearch = {

                            scope.launch {
                                isLoading = true
                                delay(1000)
                                nhanVienViewModel
                                    .searchNV(searchtxt.value,"")
                                isLoading = false
                            }
                            focuesManager.clearFocus()
                        }
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search, contentDescription = null
                        )
                    }, trailingIcon = {

                        Box(contentAlignment = Alignment.BottomEnd){
                            Icon(painter = painterResource(id = R.drawable.sort), contentDescription =null,modifier=Modifier.clickable { expandFilter=true }.size(24.dp))
                            DropdownMenu(expanded = expandFilter, onDismissRequest = { expandFilter=false }) {
                                DropdownMenuItem(text = { Text(text="All") }, onClick = {
                                    nhanVienViewModel.setData(NhanVien())
                                    searchtxt.value=""
                                    nhanVienViewModel.searchNV(searchtxt.value,"")
                                    expandFilter=false

                                })

                                nhanVienViewModel.lstdonVi.collectAsState().value.forEach {
                                    DropdownMenuItem(text = { Text(text=it.ten) }, onClick = {
                                        nhanVienViewModel.searchNV(searchtxt.value,it.maDonVi)
                                        expandFilter=false
                                    })
                                }
                            }
                        }
                    })


                // hiển thị danh sách
                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {


                        CircularProgressIndicator(
                            0.1f,
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                        )

                    }
                } else {
                    Column(modifier = Modifier.padding(10.dp)) {

                        if (dsNV.value.isNotEmpty()) {

                            LazyColumn {

//                            val grouped = list.groupBy { it.ten[0] }
                                dsNV.value.filter { it.ten.isNotEmpty() } // Lọc các phần tử có trường 'ten' không rỗng
                                    .groupBy { it.ten[0] }
                                    .forEach { (header, u) ->
                                        stickyHeader(key = "header_$header") {
                                            Text(
                                                text = header.toString(),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(10.dp)
                                                    .background(Color.Gray)
                                            )
                                        }
                                        itemsIndexed(
//                                        u
                                            u
                                        )
                                        { index, item ->
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

                                                            .padding(8.dp),
                                                        contentAlignment = Alignment.CenterEnd
                                                    ) {
                                                        Row() {
                                                            Icon(
                                                                Icons.Default.Delete,
                                                                contentDescription = null,
                                                                tint = Color.White,
                                                                modifier = Modifier

                                                                    .clickable {
                                                                        nhanVienViewModel.deleteNV(
                                                                            item
                                                                        ) {
                                                                            Log.d(
                                                                                "Xóa nhan vien: ",
                                                                                item.toString()
                                                                            )
                                                                        }
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
                                                    ItemList(item) {
                                                        navController.navigate("infoNV/" + it.maNhanVien)
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
                    ThemNhanVienScreen(nhanVienViewModel){
                        showBottomSheet=it
                    }
                }
            }
        }


    }
}

@Composable
fun ItemList(n: NhanVien, onClick: (NhanVien) -> Unit) {
    androidx.compose.material3.ListItem(headlineContent = { Text(text = if (n.ten == "") "NoName" else n.ten) },
        overlineContent = { Text(text = "OverLine") },
        leadingContent = {
            Image(
                painter = rememberAsyncImagePainter(
                    model = n.avatar,
                    error = painterResource(id = R.drawable.ic_launcher_background)
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
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

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ThemNhanVienScreen(nhanVienViewModel: NhanVienViewModel,doneAction:(Boolean)->Unit) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val name by nhanVienViewModel.tenNV.collectAsState()
    val email by nhanVienViewModel.email.collectAsState()
    val sdt by nhanVienViewModel.soDienThoai.collectAsState()
    val avatar by nhanVienViewModel.avatar.collectAsState()
    val dv by nhanVienViewModel.donVi.collectAsState()

    val chucvu by nhanVienViewModel.chucvu.collectAsState()
    val expanded = remember {
        mutableStateOf(false)
    }

    var error by remember {
        mutableStateOf("")
    }




    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Column(modifier = Modifier.padding(10.dp)) {
            SelectImage { uri ->
                FirebaseStorageHelper().uploadImageToFirebaseStorage(
                    uri,
                    onSuccess = { downloadUri ->
                        nhanVienViewModel.setAvatar(downloadUri.toString())
                    },
                    onFailure = { exception ->
                        error = exception.message.toString()
                    }
                )
            }
            Log.d("err", avatar.toString())
            Image(
                painter = rememberAsyncImagePainter(
                    model = avatar,
                    error = painterResource(id = R.drawable.ic_launcher_background)
                ),
                contentDescription = avatar,
                modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .height(250.dp)
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(8.dp)
                    ),
                contentScale = ContentScale.Inside,

                )
            TextField(
                value = name,
                onValueChange = { nhanVienViewModel.setTenNV(it) },
                placeholder = {
                    Text(
                        text = "Tên nhân viên"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            Column {
                TextField(
                    value = chucvu,
                    onValueChange = { nhanVienViewModel.setChucVu(it) },
                    placeholder = {
                        Text(
                            text = "Chức vụ"
                        )
                    }, enabled = false,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier.clickable { expanded.value = !expanded.value })
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded.value = !expanded.value }

                )
                DropdownMenu(modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = !expanded.value }) {
                    listChucVus.forEach {
                        DropdownMenuItem(
                            text = { Text(text = it) },
                            onClick = {
                                nhanVienViewModel.setChucVu(it)

                                expanded.value = false
                            })
                    }
                }
            }
            TextField(
                value = email,
                onValueChange = { nhanVienViewModel.setEmail(it) },
                placeholder = {
                    Text(
                        text = "Email"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = sdt,
                onValueChange = { nhanVienViewModel.setPhone(it) },
                placeholder = {
                    Text(
                        text = "Số điện thoại"
                    )
                }, keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()// ẩn bàn phím
                }),
                modifier = Modifier.fillMaxWidth()
            )
            Column {
                val expandedDV = remember { mutableStateOf(false) }

                TextField(
                    value = dv.ten,
                    onValueChange = {},
                    placeholder = {
                        Text(
                            text = "Chọn đơn vị trực thuộc"
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier.clickable { expandedDV.value = !expandedDV.value })
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusable(false)
                )
                DropdownMenu(
                    expanded = expandedDV.value,
                    onDismissRequest = { expandedDV.value = !expandedDV.value }) {
                    nhanVienViewModel.lstdonVi.collectAsState().value.forEach {
                        DropdownMenuItem(text = { Text(text = it.ten) }, onClick = {
                            nhanVienViewModel.setDonVi(it)

                            expandedDV.value = false
                        })
                    }
                }
            }


        }
        Column(modifier = Modifier.padding(10.dp)) {

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = {
                        nhanVienViewModel.addOrUpdateNV(null) {

                            nhanVienViewModel.setData(NhanVien())
                            doneAction(false)

                        }
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show()


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
fun thongTinNVScreen(idNV: String?) {
    val nhanVienViewModel: NhanVienViewModel = viewModel()

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val donViViewModel: DonViViewModel = viewModel()
    val stickyHeaderHeight = 200.dp
    var headerHeight by remember { mutableStateOf(stickyHeaderHeight) }
    val name by nhanVienViewModel.tenNV.collectAsState()
    val email by nhanVienViewModel.email.collectAsState()
    val sdt by nhanVienViewModel.soDienThoai.collectAsState()
    val avatar by nhanVienViewModel.avatar.collectAsState()
    val dv by nhanVienViewModel.donVi.collectAsState()

    val chucvu by nhanVienViewModel.chucvu.collectAsState()
    val expanded = remember {
        mutableStateOf(false)
    }

    var error by remember {
        mutableStateOf("")
    }

//        LaunchedEffect(idNV) {
////            donViViewModel.getDonVi(idDonVi.toString(), context)
//        }
    LaunchedEffect(key1 = nhanVienViewModel) {
        nhanVienViewModel.getNhanVien(idNV.toString())
    }
//        LaunchedEffect(tenDVC) {
//            donViViewModel.getNameDVC()
//        }
    LazyColumn() {
        item {
            Header(headerHeight, avatar)

        }
        item {
            SelectImage { uri ->
                FirebaseStorageHelper().uploadImageToFirebaseStorage(
                    uri,
                    onSuccess = { downloadUri ->
                        nhanVienViewModel.setAvatar(downloadUri.toString())
                    },
                    onFailure = { exception ->
                        error = exception.message.toString()
                    }
                )
            }


            TextField(
                value = name,
                onValueChange = { nhanVienViewModel.setTenNV(it) },
                placeholder = {
                    Text(
                        text = "Tên nhân viên"
                    )
                }, label = {
                    Text(
                        text = "Tên nhân viên"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            Column {
                TextField(
                    value = chucvu,
                    onValueChange = { nhanVienViewModel.setChucVu(it) },
                    placeholder = {
                        Text(
                            text = "Chức vụ"
                        )
                    }, label = {
                        Text(
                            text = "Chức vụ"
                        )
                    }, enabled = false,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier.clickable { expanded.value = !expanded.value })
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded.value = !expanded.value }

                )
                DropdownMenu(modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = !expanded.value }) {
                    listChucVus.forEach {
                        DropdownMenuItem(
                            text = { Text(text = it) },
                            onClick = {
                                nhanVienViewModel.setChucVu(it)

                                expanded.value = false
                            })
                    }
                }
            }
            TextField(
                value = email,
                onValueChange = { nhanVienViewModel.setEmail(it) },
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
                value = sdt,
                onValueChange = { nhanVienViewModel.setPhone(it) },
                placeholder = {
                    Text(
                        text = "Số điện thoại"
                    )
                }, label = {
                    Text(
                        text = "Số điện thoại"
                    )
                }, keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()// ẩn bàn phím
                }),
                modifier = Modifier.fillMaxWidth()
            )
            Column {
                val expandedDV = remember { mutableStateOf(false) }

                TextField(
                    value = dv.ten, enabled = false,
                    onValueChange = {},
                    placeholder = {
                        Text(
                            text = "Chọn đơn vị trực thuộc"
                        )
                    }, label = {
                        Text(
                            text = "Chọn đơn vị trực thuộc"
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier.clickable { expandedDV.value = !expandedDV.value })
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusable(false)
                )
                DropdownMenu(
                    expanded = expandedDV.value,
                    onDismissRequest = { expandedDV.value = !expandedDV.value }) {
                    nhanVienViewModel.lstdonVi.collectAsState().value.forEach {
                        DropdownMenuItem(text = { Text(text = it.ten) }, onClick = {
                            nhanVienViewModel.setDonVi(it)
                            expandedDV.value = false
                        })
                    }
                }
            }



            Button(
                onClick = {
                    nhanVienViewModel.addOrUpdateNV(idNV) {
                        Log.d("Update NV: ",it)
                    }
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show()


                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3F51B5)
                ), shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Sửa")
            }

        }

    }
}

