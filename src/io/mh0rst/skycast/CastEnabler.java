package io.mh0rst.skycast;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

/**
 * Render Androids MediaRouter checks inert for sky go germany app.
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
        XposedHelpers.findAndHookMethod("android.media.MediaRouter", lpparam.classLoader, "getSelectedRoute", int.class,
                XC_MethodReplacement.DO_NOTHING);
    }
}
