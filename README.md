# 概要
Android Studio 学習用、および GitHub 学習用に作成したアプリです。

本アプリは、Google 公式のサンプルアプリ「[basic-android-kotlin-compose-training-lunch-tray](https://github.com/google-developer-training/basic-android-kotlin-compose-training-lunch-tray/tree/starter)」をベースとして構築しました。

## 機能追加内容

このプロジェクトは、提供されたスターターコードをベースに以下の機能拡張を行いました：

- **Jetpack Navigation を使用した画面遷移の実装**  
  全5画面（Start, EntreeMenu, SideDishMenu, AccompanimentMenu, CheckOut）間の遷移を NavHost を用いて構成しました。  
  各画面での「NEXT」「CANCEL」操作に応じて ViewModel を通じた状態管理と画面遷移を実現しています。

- **Compose UI テストの追加**  
  Navigation 構成に対して、UIテスト (`androidTest`) を実装しました。  
  ボタン押下に伴う遷移確認や、ViewModel の状態リセットなどの検証を行っています。  
  `composeTestRule` を用いた `assertIsDisplayed`, `performClick`, `assertEquals` などを活用し、動作確認済みです。
