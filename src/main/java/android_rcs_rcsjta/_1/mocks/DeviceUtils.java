package android_rcs_rcsjta._1.mocks;

import java.util.UUID;

public class DeviceUtils {
    // AndroidFactoryからの戻り値を受け取り、ランダムなUUIDを返します
    public static UUID getDeviceUUID(Object context) {
        return UUID.randomUUID();
    }
}