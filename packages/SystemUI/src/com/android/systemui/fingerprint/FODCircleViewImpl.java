/**
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.systemui.fingerprint;

import android.content.pm.PackageManager;
import android.util.Slog;
import android.view.View;

import com.android.systemui.SystemUI;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.CommandQueue.Callbacks;

import com.android.internal.util.cr.CrConstants;

public class FODCircleViewImpl extends SystemUI implements CommandQueue.Callbacks {
    private static final String TAG = "FODCircleViewImpl";

    private FODCircleView mFodCircleView;

    @Override
    public void start() {
        PackageManager packageManager = mContext.getPackageManager();
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT) ||
                !packageManager.hasSystemFeature(CrConstants.Features.FOD)) {
            return;
        }
        getComponent(CommandQueue.class).addCallbacks(this);
        try {
            mFodCircleView = new FODCircleView(mContext);
        } catch (RuntimeException e) {
            Slog.e(TAG, "Failed to initialize FODCircleView", e);
        }
    }

    @Override
    public void showInDisplayFingerprintView() {
        if (mFodCircleView != null) {
            mFodCircleView.show();
        }
    }

    @Override
    public void hideInDisplayFingerprintView() {
        if (mFodCircleView != null) {
            mFodCircleView.hide();
        }
    }
}
