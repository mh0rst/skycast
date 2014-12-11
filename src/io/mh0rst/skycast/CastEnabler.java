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

    private static class DoNothing extends XC_MethodReplacement {

        public DoNothing() {
            // do nothing
        }

        @Override
        protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
            return null;
        }

    }

    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("de.sky.bw")) {
            return;
        }
        XposedHelpers.findAndHookMethod("android.media.MediaRouter", lpparam.classLoader, "getSelectedRoute", int.class, new DoNothing());
    }
}
