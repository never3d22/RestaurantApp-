package com.example.restaurantapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Dish(val name: String, val price: Int)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantApp()
        }
    }
}

@Composable
fun RestaurantApp() {
    var cart by remember { mutableStateOf(listOf<Dish>()) }
    var showCart by remember { mutableStateOf(false) }

    if (showCart) {
        CartScreen(cart, onBack = { showCart = false }, onClear = { cart = listOf() })
    } else {
        DishList(onAdd = { cart = cart + it }, onCart = { showCart = true })
    }
}

@Composable
fun DishList(onAdd: (Dish) -> Unit, onCart: () -> Unit) {
    val dishes = listOf(
        Dish("Пицца Маргарита", 500),
        Dish("Бургер Классический", 450),
        Dish("Суп Том Ям", 600),
        Dish("Тирамису", 300)
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Меню", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn {
            items(dishes.size) {
                val dish = dishes[it]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${dish.name} - ${dish.price}₽")
                    Button(onClick = { onAdd(dish) }) {
                        Text("Добавить")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onCart, modifier = Modifier.fillMaxWidth()) {
            Text("Перейти в корзину")
        }
    }
}

@Composable
fun CartScreen(cart: List<Dish>, onBack: () -> Unit, onClear: () -> Unit) {
    val total = cart.sumOf { it.price }
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Корзина", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(8.dp))
        if (cart.isEmpty()) {
            Text("Корзина пуста.")
        } else {
            cart.forEach {
                Text("${it.name} - ${it.price}₽")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Итого: $total₽")
            Button(onClick = onClear) {
                Text("Очистить")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onBack) {
            Text("Назад")
        }
    }
}