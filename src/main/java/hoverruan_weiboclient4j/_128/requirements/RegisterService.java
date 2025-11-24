package weiboclient4j;

import weiboclient4j.model.VerifyNicknameResult;
import hoverruan_weiboclient4j._128.params.Nickname;

/**
 * @author Hover Ruan
 */
public class RegisterService extends AbstractService {
    public RegisterService(WeiboClient client) {
        super(client);
    }

    public VerifyNicknameResult verifyNickname(Nickname nickname) throws WeiboClientException {
        return doGet("register/verify_nickname",
                withParams(nickname), VerifyNicknameResult.class);
    }
}
