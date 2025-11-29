package onosendai._1.requirements.provider;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SendOutboxService extends Service {

    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }
}
