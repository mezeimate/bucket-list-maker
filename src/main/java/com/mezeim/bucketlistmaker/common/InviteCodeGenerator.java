package com.mezeim.bucketlistmaker.common;

import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.Random;

@Slf4j
public class InviteCodeGenerator {

    public static String generate(){
        char[] chars = AppConstants.INVITE_CODE_CHARSET.toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new SecureRandom();
        char c;
        for (int i = 0; i < AppConstants.INVITE_CODE_LEN; i++) {
            c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        log.info("Invite code generated: " + output);
        return output;
    }

}
