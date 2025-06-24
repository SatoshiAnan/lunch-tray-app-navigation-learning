package com.example.lunchtray.test

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import org.junit.Rule
import androidx.navigation.testing.TestNavHostController
import com.example.lunchtray.LunchTrayApp
import com.example.lunchtray.LunchTrayScreen
import com.example.lunchtray.ui.OrderViewModel
import org.junit.Test
import com.example.lunchtray.model.OrderUiState
import org.junit.Assert.assertEquals
import org.junit.Before

/*
TO DO

hasTextにテキストではなくStringIDを渡したい
各画面、それぞれの選択肢を選んだ時の挙動を検証するテスト

*/

class LunchTrayScreenNavigationTest {
    @get:Rule

    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    // 各テストの実行前に呼び出し、テスト用の初期化処理を実行
    @Before
    fun setupCupCakeNavHost() {
        composeTestRule.setContent {
            // navController 初期化
            navController = TestNavHostController(LocalContext.current).apply {
                // rememberNavController に委譲しないため、ComposeNavigator を手動で追加
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            LunchTrayApp(
                navController = navController,
            )
        }
    }

    private val testViewModel = OrderViewModel()

    // EntreeMenu 画面に遷移するヘルパーメソッド
    private fun navigateToEntreeMenuScreen() {
        composeTestRule
            .onNode(
                hasText("Start Order") and hasClickAction(),
            )
            .performClick()
    }
    // SideDishMenu 画面に遷移するヘルパーメソッド
    private fun navigateToSideDishMenuScreen() {
        navigateToEntreeMenuScreen()
        composeTestRule
            .onNode(
                hasText("Cauliflower")
            )
            .performClick()
        composeTestRule
            .onNode(
                hasText("NEXT")
            )
            .performClick()
    }
    // AccompanimentMenu 画面に遷移するヘルパーメソッド
    private fun navigateToAccompanimentMenu() {
        navigateToSideDishMenuScreen()
        composeTestRule
            .onNode(
                hasText("Summer Salad")
            )
            .performClick()
        composeTestRule
            .onNode(
                hasText("NEXT")
            )
            .performClick()
    }
    // Checkout 画面に遷移するヘルパーメソッド
    private fun navigateToCheckoutMenu() {
        navigateToAccompanimentMenu()
        composeTestRule
            .onNode(
                hasText("Lunch Roll")
            )
            .performClick()
        composeTestRule
            .onNode(
                hasText("NEXT")
            )
            .performClick()
    }
    /*
    各テストは、assertIsDisplayed で画面上に表示されたテキストから正しい画面に居るか検証した後、
    assertCurrentRouteName でルート名から正しい画面に居るかを検証している
    どちらか片方でいいかも
    */
    // Start 画面が正しく表示されるかを検証するテスト
    @Test
    fun lunchTrayNavHost_verifyStartDestination() {
        composeTestRule
            .onNode(
                // 「Start Order」のテキストを持ち、かつクリック可能なボタンがあることを検証
                hasText("Start Order") and hasClickAction(),
            )
            .assertIsDisplayed()
        navController.assertCurrentRouteName(LunchTrayScreen.Start.name)
    }
    // Start 画面から EntreeMenu 画面に正しく遷移されるかを検証するテスト
    @Test
    fun lunchTrayNavHost_clickNextOnEntreeMenuScreen_navigatesToEntreeMenuScreen() {
        composeTestRule
            .onNode(
                hasText("Start Order") and hasClickAction(),
            )
            .performClick()
        composeTestRule
            .onNode(
                hasText("Choose Entree")
            )
            .assertIsDisplayed()
        navController.assertCurrentRouteName(LunchTrayScreen.EntreeMenu.name)
    }
    // EntreeMenu 画面から　Cancel ボタンを押した際に正しく Start 画面に戻るかを検証するテスト
    @Test
    fun lunchTrayNavHost_clickCancelOnEntreeMenuScreen_navigatesToStartScreen() {
        navigateToEntreeMenuScreen()
        composeTestRule
            .onNode(
                hasText("CANCEL")
            )
            .performClick()
        composeTestRule
            .onNode(
                hasText("Start Order") and hasClickAction(),
            )
            .assertIsDisplayed()
        navController.assertCurrentRouteName(LunchTrayScreen.Start.name)
        // ViewModel の状態を確認(初期化されているか)
        val startState = testViewModel.uiState.value
        assertEquals(OrderUiState(), startState)
    }
    // EntreeMenu 画面から SideDishMenu 画面に正しく遷移されるかを検証するテスト
    @Test
    fun lunchTrayNavHost_clickNextOnSideDishMenuScreen_navigatesToSideDishMenuScreen() {
        navigateToEntreeMenuScreen()
        composeTestRule
            .onNode(
                hasText("Cauliflower")
            )
            .performClick()
        composeTestRule
            .onNode(
                hasText("NEXT")
            )
            .performClick()
        composeTestRule
            .onNode(
                hasText("Choose Side Dish")
            )
            .assertIsDisplayed()
        navController.assertCurrentRouteName(LunchTrayScreen.SideDishMenu.name)
    }
    // SideDishMenu 画面から　Cancel ボタンを押した際に正しく Start 画面に戻るかを検証するテスト
    @Test
    fun lunchTrayNavHost_clickCancelOnSideDishMenuScreen_navigatesToStartScreen() {
        navigateToSideDishMenuScreen()
        composeTestRule
            .onNode(
                hasText("CANCEL")
            )
            .performClick()
        composeTestRule
            .onNode(
                hasText("Start Order") and hasClickAction(),
            )
            .assertIsDisplayed()
        navController.assertCurrentRouteName(LunchTrayScreen.Start.name)
        val startState = testViewModel.uiState.value
        assertEquals(OrderUiState(), startState)
    }
    // SideDishMenu 画面から AccompanimentMenu 画面に正しく遷移されるかを検証するテスト
    @Test
    fun lunchTrayNavHost_clickNextOnAccompanimentMenuScreen_navigatesToAccompanimentMenuScreen() {
        navigateToSideDishMenuScreen()
        composeTestRule
            .onNode(
                hasText("Summer Salad")
            )
            .performClick()
        composeTestRule
            .onNode(
                hasText("NEXT")
            )
            .performClick()
        composeTestRule
            .onNode(
                hasText("Choose Accompaniment")
            )
            .assertIsDisplayed()
        navController.assertCurrentRouteName(LunchTrayScreen.AccompanimentMenu.name)
    }
    // AccompanimentMenu 画面から　Cancel ボタンを押した際に正しく Start 画面に戻るかを検証するテスト
    @Test
    fun lunchTrayNavHost_clickCancelOnAccompanimentMenuScreen_navigatesToStartScreen() {
        navigateToAccompanimentMenu()
        composeTestRule
            .onNode(
                hasText("CANCEL")
            )
            .performClick()
        composeTestRule
            .onNode(
                hasText("Start Order") and hasClickAction(),
            )
            .assertIsDisplayed()
        navController.assertCurrentRouteName(LunchTrayScreen.Start.name)
        val startState = testViewModel.uiState.value
        assertEquals(OrderUiState(), startState)
    }
    // AccompanimentMenu 画面から Checkout 画面に正しく遷移されるかを検証するテスト
    @Test
    fun lunchTrayNavHost_clickNextOnAccompanimentMenuScreen_navigatesToStartScreen() {
        navigateToAccompanimentMenu()
        composeTestRule
            .onNode(
                hasText("Lunch Roll")
            )
            .performClick()
        composeTestRule
            .onNode(
                hasText("NEXT")
            )
            .performClick()
        composeTestRule
            .onNode(
                hasText("Order Checkout")
            )
            .assertIsDisplayed()
        navController.assertCurrentRouteName(LunchTrayScreen.CheckOut.name)
    }
    // Checkout 画面から SUBMIT ボタンを押した際に正しく Start 画面に戻るかを検証するテスト
    @Test
    fun lunchTrayNavHost_clickSubmitOnCheckoutMenuScreen_navigatesToStartScreen() {
        navigateToCheckoutMenu()
        composeTestRule
            .onNode(
                hasText("SUBMIT")
            )
            .performClick()
        composeTestRule
            .onNode(
                hasText("Start Order") and hasClickAction(),
            )
            .assertIsDisplayed()
        navController.assertCurrentRouteName(LunchTrayScreen.Start.name)
        val startState = testViewModel.uiState.value
        assertEquals(OrderUiState(), startState)
    }
    // Checkout 画面から　Cancel ボタンを押した際に正しく Start 画面に戻るかを検証するテスト
    @Test
    fun lunchTrayNavHost_clickNextOnCheckoutMenuScreen_navigatesToCheckoutMenuScreen() {
        navigateToCheckoutMenu()
        composeTestRule
            .onNode(
                hasText("CANCEL")
            )
            .performClick()
        composeTestRule
            .onNode(
                hasText("Start Order") and hasClickAction(),
            )
            .assertIsDisplayed()
        navController.assertCurrentRouteName(LunchTrayScreen.Start.name)
        val startState = testViewModel.uiState.value
        assertEquals(OrderUiState(), startState)
    }
}