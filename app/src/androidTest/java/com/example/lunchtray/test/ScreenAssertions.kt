package com.example.lunchtray.test

import androidx.navigation.NavController
import org.junit.Assert.assertEquals

// 引数にrouteを渡し、それが現在居るrouteと一致しているかを検証
fun NavController.assertCurrentRouteName(expectedRouteName: String) {
    assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
}