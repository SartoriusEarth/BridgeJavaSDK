package org.sagebionetworks.bridge.sdk;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import org.sagebionetworks.bridge.sdk.models.users.ResetPasswordCredentials;
import org.sagebionetworks.bridge.sdk.models.users.SignInCredentials;
import org.sagebionetworks.bridge.sdk.models.users.SignUpCredentials;

public class ClientProvider {

    private static final Config config = Config.valueOf();
    
    private static final ClientInfo info = new ClientInfo(true);
    
    /**
     * Retrieve the Config object for the system.
     *
     * @return Config
     */
    public static synchronized Config getConfig() {
        return config;
    }
    
    public static ClientInfo getClientInfo() {
        return info;
    }

    /**
     * Sign In to Bridge with the given credentials. Returns a session object to manipulate. Method is idempotent.
     *
     * @param signIn
     *            The credentials you wish to sign in with.
     * @return Session
     */
    public static Session signIn(SignInCredentials signIn) {
        checkNotNull(signIn, "SignInCredentials required.");

        UserSession session = new BaseApiCaller(null).post(config.getAuthSignInApi(), signIn, UserSession.class);
        return BridgeSession.valueOf(session);
    }

    /**
     * Sign Up an account with Bridge using the given credentials.
     *
     * @param signUp
     *            The credentials to create an account with.
     */
    public static void signUp(SignUpCredentials signUp) {
        checkNotNull(signUp, "SignUpCredentials required.");
        checkArgument(isNotBlank(signUp.getEmail()), "Email cannot be blank/null");
        checkArgument(isNotBlank(signUp.getUsername()), "Username cannot be blank/null");
        checkArgument(isNotBlank(signUp.getPassword()), "Password cannot be blank/null");

        new BaseApiCaller(null).post(config.getAuthSignUpApi(), signUp);
    }

    /**
     * Request your password be reset. A link to change the password will be sent to the provided email.
     *
     * @param email
     *            Email associated with a Bridge account.
     */
    public static void requestResetPassword(String email) {
        checkArgument(isNotBlank(email), "Email cannot be blank/null");

        new BaseApiCaller(null).post(config.getAuthRequestResetApi(), new ResetPasswordCredentials(email));
    }
}
