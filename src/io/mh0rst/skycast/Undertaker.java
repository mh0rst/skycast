package io.mh0rst.skycast;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;

/**
 * Disable skull activity in sky go Germany app, which would otherwise show with a detected Xposed framework.
 * 
 * @author mh0rst
 *
 */
public class Undertaker extends XC_MethodReplacement {

    private final Class<?> layoutClass;

    private static final Map<String, String> ID_MAP = new HashMap<>();
    static {
        ID_MAP.put("de.sky.bw.player.PlayMediaNexPlayerActivity", "activity_player");
    }

    /**
     * Creates new Undertaker instance
     * 
     * @param classLoader
     *            Used to retrieve R.layout instance
     */
    public Undertaker(ClassLoader classLoader) {
        layoutClass = XposedHelpers.findClass("de.sky.bw.R$layout", classLoader);
    }

    @Override
    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
        Activity activity = (Activity) param.args[0];
        String activityName = activity.getClass().getCanonicalName();
        // Do contentView registration, this fixes NPEs, which occur otherwise when Xposed is active.
        if (ID_MAP.containsKey(activityName)) {
            int id = XposedHelpers.getStaticIntField(layoutClass, ID_MAP.get(activityName));
            activity.setContentView(id);
        }
        return Boolean.TRUE;
    }
}
