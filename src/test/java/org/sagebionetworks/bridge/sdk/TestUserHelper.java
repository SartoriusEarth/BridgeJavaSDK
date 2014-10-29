package org.sagebionetworks.bridge.sdk;

import static org.sagebionetworks.bridge.sdk.Preconditions.checkNotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.sagebionetworks.bridge.sdk.models.SignInCredentials;
import org.sagebionetworks.bridge.sdk.models.SignUpCredentials;

public class TestUserHelper {

    public static class TestUser {
        private final AdminClient client;
        private final Session userSession;
        private final String username;
        private final String email;
        private final String password;
        private final List<String> roles;

        public TestUser(AdminClient client, Session userSession, String username, String email, String password,
                List<String> roleList) {
            this.client = client;
            this.userSession = userSession;
            this.username = username;
            this.email = email;
            this.password = password;
            this.roles = roleList;
        }
        public Session getSession() {
            return userSession;
        }
        public String getUsername() {
            return username;
        }
        public String getEmail() {
            return email;
        }
        public String getPassword() {
            return password;
        }
        public List<String> getRoles() {
            return roles;
        }
        public boolean signOutAndDeleteUser() {
            userSession.signOut();
            return client.deleteUser(email);
        }
        public boolean isSignedIn() {
            return userSession.isSignedIn();
        }
    }

    public static TestUser createAndSignInUser(Class<?> cls, boolean consent, String... roles) {
        checkNotNull(cls);

        Config config = ClientProvider.getConfig();
        Session session = ClientProvider.signIn(config.getAdminCredentials());
        AdminClient client = session.getAdminClient();

        List<String> rolesList = (roles == null) ? Collections.<String>emptyList() : Arrays.asList(roles);
        String name = makeUserName(cls);
        SignUpCredentials signUp = SignUpCredentials.valueOf()
                .setUsername(name)
                .setEmail(name + "@sagebridge.org")
                .setPassword("P4ssword");
        client.createUser(signUp, rolesList, consent);

        SignInCredentials signIn = SignInCredentials.valueOf().setUsername(name).setPassword("P4ssword");
        Session userSession = ClientProvider.signIn(signIn);

        return new TestUserHelper.TestUser(client, userSession, signUp.getUsername(), signUp.getEmail(),
                signUp.getPassword(), rolesList);
    }

    public static String makeUserName(Class<?> cls) {
        Config config = ClientProvider.getConfig();
        String devName = config.getDevName();
        String clsPart = cls.getSimpleName();
        String rndPart = RandomStringUtils.randomAlphabetic(4);
        return String.format("%s-%s-%s", devName, clsPart, rndPart);
    }
}