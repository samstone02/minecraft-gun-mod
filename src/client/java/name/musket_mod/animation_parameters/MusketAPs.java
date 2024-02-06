package name.musket_mod.animation_parameters;

public class MusketAPs {
    public static final float RECOIL_THRESHOLD = 0.1f;
    public static float RECOIL_TRANSLATION_CONVERSION = 6f;
    public static float RECOIL_ROTATION_CONVERSION = 150f;
    public static float RECOVERY_TRANSLATION_CONVERSION = (RECOIL_THRESHOLD * RECOIL_TRANSLATION_CONVERSION) / (1 - RECOIL_THRESHOLD);
    public static float RECOVERY_ROTATION_CONVERSION = (RECOIL_THRESHOLD * RECOIL_ROTATION_CONVERSION) / (1 - RECOIL_THRESHOLD);
    public static float RELOAD_THRESHOLD = 0.05f;
    public static float RELOAD_WINDUP_TRANSLATION_CONVERSION_X = 0.0f;
    public static float RELOAD_WINDUP_TRANSLATION_CONVERSION_Y = -8.0f;
    public static float READY_RELOAD_ROTATION_CONVERSION_X = 650f; // 900f?
    public static float READY_RELOAD_ROTATION_CONVERSION_Y = 1200f; // 900f?
    public static float SHAKE_INTENSITY = 0.1f;
    public static float SHAKE_SPEED = 25.0f;
}
