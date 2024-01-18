package org.kt.parttime.user.service;

import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;

class BcryptPasswordEncoderTest {
    @Test
    void salt(){
        System.out.println(BCrypt.gensalt());
    }
}