package com.tlu.firebaseexample

import android.os.Bundle
import android.widget.Toast

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

//class MainActivity : AppCompatActivity(), View.OnClickListener {
//
//    private var _binding: ActivityMainBinding? = null
//    val binding get() = _binding!!
//    private var firebaseDatabaseHelper: FirebaseDatabaseHelper? = null
//    private lateinit var listStudents: ArrayList<Student>
//    private  var idStudent: String?=""
//    private lateinit var adapter: MyAdapter
//
//    init {
//
//
//        firebaseDatabaseHelper = FirebaseDatabaseHelper()
//        idStudent = ""
//    }
//
//    @SuppressLint("MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        _binding = ActivityMainBinding.inflate(layoutInflater)
//        val view = binding.root
//        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//        setContentView(view)
//
//        listStudents = arrayListOf()
//
//        val btnAdd = binding.buttonAdd
//        val btnUpdate = binding.buttonUpdate
//        val btnDelete = binding.buttonDelete
//        val lstView = binding.listViewStudents
//        adapter = MyAdapter(applicationContext, listStudents)
//        adapter.notifyDataSetChanged()
//
//        firebaseDatabaseHelper?.getAllStudents {
//            listStudents.clear()
//            listStudents.addAll(it)
//            adapter.notifyDataSetChanged()
//
//        }
//
//
//        lstView.setOnItemClickListener { parent, view, position, id ->
//            val st = listStudents[position]
//            setData(st)
//        }
//
//        lstView.adapter = adapter
//        Log.d("size list", listStudents.size.toString())
//        btnAdd.setOnClickListener(this)
//        btnUpdate.setOnClickListener(this)
//        btnDelete.setOnClickListener(this)
//
//
//    }
//
//    private fun setData(st: Student) {
//        binding.editTextName.setText(st.name.toString())
//        binding.editTextEmail.setText(st.email.toString())
//        idStudent = st.id.toString()
//
//    }
//
//
//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }
//
//    override fun onClick(v: View?) {
//        val editTextStudent = binding.editTextName
//        val editTextEmail = binding.editTextEmail
//
//        val student =
//            Student(idStudent, editTextStudent.text.toString(), editTextEmail.text.toString())
//        when (v?.id) {
//            binding.buttonAdd.id -> {
//
//
//                firebaseDatabaseHelper?.addStudent(student) {
//                    if (it) {
//
//                        firebaseDatabaseHelper?.getAllStudents {
//                            listStudents.clear()
//
//                            listStudents.addAll(it)
//                            adapter.notifyDataSetChanged()
//                            Toast.makeText(this, "Add success", Toast.LENGTH_SHORT).show()
//                            setData(Student(null, "", ""))
//                        }
//
//
//                    } else {
//
//                        Toast.makeText(this, "Add failed", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//
//            }
//
//            R.id.buttonUpdate -> {
//
//                firebaseDatabaseHelper?.updateStudent(student) {
//                    if (it) {
//                        firebaseDatabaseHelper?.getAllStudents {
//                            listStudents.clear()
//
//                            listStudents.addAll(it)
//                            adapter.notifyDataSetChanged()
//                            Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show()
//                            setData(Student("", "", ""))
//                        }
//
//
//                    } else {
//
//                        Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//
//            }
//
//            R.id.buttonDelete -> {
//
//                firebaseDatabaseHelper?.deleteStudent(studentId = student.id.toString()) {
//                    if (it) {
//                        firebaseDatabaseHelper?.getAllStudents {
//                            listStudents.clear()
//
//                            listStudents.addAll(it)
//                            adapter.notifyDataSetChanged()
//                            Toast.makeText(this, "Delete success", Toast.LENGTH_SHORT).show()
//                            setData(Student("", "", ""))
//
//                        }
//
//
//                    } else {
//
//                        Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//
//            }
//        }
//    }
//}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModel<MainActivityViewModel>()
            MainScreen(viewModel)
        }
    }
}

@Composable
fun MainScreen(mainViewModel: MainActivityViewModel) {
    val userName by mainViewModel.username.collectAsState()
    val email by mainViewModel.email.collectAsState()
    val list by mainViewModel.listData.observeAsState()
    val listClasses by mainViewModel.classrooms.observeAsState()

    Column {
        TextField(value = userName, onValueChange = { mainViewModel.setName(it) }, placeholder = {
            Text(
                text = "Name"
            )
        }, modifier = Modifier.fillMaxWidth())
        TextField(
            value = email,
            onValueChange = { mainViewModel.setEmail(it) },
            placeholder = {
                Text(
                    text = "Enter Email"
                )
            }, modifier = Modifier.fillMaxWidth()
        )
        val context = LocalContext.current
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Button(onClick = {
                mainViewModel.addData(context)
            }) {
                Text(text = "Add")
            }
            Button(onClick = { mainViewModel.updateData(context) }) {
                Text(text = "Update")
            }
            Button(onClick = { mainViewModel.deleteData(context) }) {
                Text(text = "Delete")
            }
        }
        if (list?.isEmpty() == true || list == null) {
            CircularProgressIndicator()
        } else {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                itemsIndexed(list!!) { index, item ->
                    Item(st = item) {
                        mainViewModel.setName(it.name)
                        mainViewModel.setEmail(it.email)
                        mainViewModel.setId(it.id)

                    }
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun MainScreenPre() {
    val viewModel = MainActivityViewModel()
    MaterialTheme {
        MainScreen(viewModel)
    }
}

@Composable
fun Item(st: Student, onClickItem: (Student) -> Unit) {
    Column(modifier = Modifier
        .padding(vertical = 10.dp)
        .fillMaxWidth()
        .clickable { onClickItem(st) }) {
        Text(
            text = "${st.name} - ${st.email}",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold
        )
    }
}
//(st.students).forEach {
//    Column(modifier=Modifier.clickable { onClickItem(it) }) {
//        Text(
//            text = "${it.name}",
//            color = MaterialTheme.colorScheme.onSurface,
//            style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold
//        )
//    }
//}