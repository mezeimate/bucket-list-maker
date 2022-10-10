package com.mezeim.bucketlistmaker.common;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.mezeim.bucketlistmaker.exception.NoSessionException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthorizedUtil {

    public String getUserId(String idToken) {
        if (idToken.isEmpty()) {
            throw new NoSessionException();
        }

        String uid;
        try {
            uid = FirebaseAuth.getInstance().verifyIdToken(idToken).getUid();
        } catch (FirebaseAuthException e) {
            throw new NoSessionException();
        }
        return uid;
    }
}
