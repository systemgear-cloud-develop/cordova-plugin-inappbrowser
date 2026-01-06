/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/
package org.apache.cordova.inappbrowser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.Window;

/**
 * Created by Oliver on 22/11/2013.
 */
public class InAppBrowserDialog extends Dialog {
    Context context;
    InAppBrowser inAppBrowser = null;

    public InAppBrowserDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    public void setInAppBroswer(InAppBrowser browser) {
        this.inAppBrowser = browser;
    }

    public void onBackPressed() {
        // Disable Android back button in InAppBrowser
        // if (this.inAppBrowser == null) {
        // this.dismiss();
        // } else {
        // // better to go through the in inAppBrowser
        // // because it does a clean up
        // if (this.inAppBrowser.hardwareBack() && this.inAppBrowser.canGoBack()) {
        // this.inAppBrowser.goBack();
        // } else {
        // this.inAppBrowser.closeDialog();
        // }
        // }
    }

    /**
     * 表示され始める瞬間に呼ばれるライフサイクル
     * 継承元Dialogの開始時処理とフルスクリーンモードにする処理を行う
     */
    @Override
    public void onStart() {
        super.onStart();
        enableImmersiveMode();
    }

    /**
     * フォーカスが切り替わるときに呼ばれるライフサイクル
     * ダイアログにフォーカスが当たったときにフルスクリーンモード処理を行う
     * 
     * @param hasFocus フォーカスフラグ(true:フォーカス状態/false:非フォーカス状態)
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            enableImmersiveMode();
        }
    }

    /**
     * フルスクリーン処理
     */
    private void enableImmersiveMode() {
        Window window = getWindow();
        if (window == null)
            return;
        // ステータスバーとナビゲーションバーの領域まで描画
        window.setDecorFitsSystemWindows(false);
        WindowInsetsController controller = window.getInsetsController();
        if (controller == null)
            return;
        // ステータスバーとナビゲーションバーを隠す
        controller.hide(
                WindowInsets.Type.statusBars()
                        | WindowInsets.Type.navigationBars());
        // ユーザーがバーを一時的に表示しても数秒後に自動で隠れる
        controller.setSystemBarsBehavior(
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
    }
}
