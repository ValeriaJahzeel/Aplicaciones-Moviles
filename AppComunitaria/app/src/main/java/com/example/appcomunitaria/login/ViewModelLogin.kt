package com.example.appcomunitaria.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ViewModelLogin: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    fun singInWithEmailAndPassword(email: String, password: String, home: ()-> Unit)
    = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("AppComunitaria", "Log in correcto")
                        home()
                    } else {
                        Log.d("AppComunitaria", "Log in fallido")
                    }

                }
        }
        catch(ex:Exception){
            Log.d("AppComunitaria", "Log in ${ex.message}")
        }
    }

    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        home: () -> Unit
    ){
        if(_loading.value == false){
            _loading.value = true
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{task ->
                    if(task.isSuccessful){
                        val displayName = task.result.user?.email?.split("@")?.get(0)
                    createUser(displayName)
                        home()
                    }else{
                        Log.d("AppComunitaria", "createUserWithEmailAndPassword: ${task.result.toString()}")
                    }
                    _loading.value = false
                }
        }
    }

    private fun createUser(displayName: String?) {
        val userId = auth.currentUser?.uid
        val user = mutableMapOf<String, Any>()

        user["user_id"] = userId.toString()
        user["display_name"] = displayName.toString()
        FirebaseFirestore.getInstance().collection("usuarios")
            .add(user)
            .addOnSuccessListener {
                Log.d("AppComunitaria", "Creado ${it.id}")
            }.addOnFailureListener{
                Log.d("AppComunitaria", "Error creando ${it}")
            }


    }
}