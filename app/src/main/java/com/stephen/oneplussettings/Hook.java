package com.stephen.oneplussettings;

import android.content.Context;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * @Name: 一加设置 hook
 * @Author: StephenChen
 * @Date: 2021/05/25
 * @LastEditors: StephenChen
 * @LastEditTime: 2021/05/25
 */

public class Hook implements IXposedHookLoadPackage {

    private final static String settingPkName = "com.android.settings";
    private final static String applicationLoaderClazzName = "com.oneplus.settings.apploader.OPApplicationLoader";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals(settingPkName)) return;

        // 拦截双开限制
        findAndHookMethod(applicationLoaderClazzName,
                lpparam.classLoader,
                "multiAppPackageExcludeFilter",
                Context.class,
                String.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        param.setResult(true);
                    }
                });
    }
}
