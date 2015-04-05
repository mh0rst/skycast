package io.mh0rst.skycast;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.XposedHelpers.ClassNotFoundError;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

/**
 * Render Androids MediaRouter checks inert for sky go Germany app.
 * 
 * @author mh0rst
 *
 */
public class CastEnabler implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("de.sky.bw")) {
            return;
        }
        XposedHelpers.findAndHookMethod("android.media.MediaRouter", lpparam.classLoader, "getSelectedRoute",
                int.class, XC_MethodReplacement.DO_NOTHING);
        XposedBridge.log("[SkyCast] MediaRouter hooked");
        try {
            XposedHelpers.findAndHookMethod("de.sky.bw.tools.NativeTools", lpparam.classLoader, "doChecksAndLoad",
                    android.app.Activity.class, new Undertaker(lpparam.classLoader));
            XposedBridge.log("[SkyCast] Skull screen activation class hooked");
        } catch (ClassNotFoundError | NoSuchMethodError e) {
            XposedBridge.log("[SkyCast] Could not find skull screen class, probably older/newer version of go");
        }
    }
}
