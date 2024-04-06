package com.attendanceapp2.authentication

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.attendanceapp2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun signUpScreen() {
    val context = LocalContext.current

    // State variables to store the text field values
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }

    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    //user dropdown menu
    val userType = arrayOf("Student", "Faculty")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(userType[0]) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Image(
                modifier = Modifier
                    .size(200.dp)
                    .padding(24.dp),
                painter = painterResource(id = R.drawable.nbslogo),
                contentDescription = "NBS LOGO"
            )

            Text(
                text = "Welcome to NBSC Attendance App!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Please sign up using your school email",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(20.dp)
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(20.dp)
            )

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(20.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "Toggle password visibility"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(20.dp)
            )


            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                OutlinedTextField(
                    value = selectedText,
                    onValueChange = {  },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    textStyle = TextStyle(textAlign = TextAlign.Center)
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    userType.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item) },
                            onClick = {
                                selectedText = item
                                expanded = false
                                Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {  },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp ,vertical = 4.dp)
                    .size(width = 80.dp,height = 50.dp)
            ) {
                Text(
                    text = "Sign Up",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            Button(
                onClick = {  },
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp ,vertical = 4.dp)
                    .size(width = 80.dp,height = 50.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Sign Up with Google",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Icon(
                        imageVector = Icons.Default.Mail,
                        contentDescription = "Email",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(
                    text = "Already have an account? ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )

                ClickableText(
                    text = AnnotatedString("Sign In"),
                    onClick = {  },
                    style = TextStyle(
                        color = Color.Red,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}