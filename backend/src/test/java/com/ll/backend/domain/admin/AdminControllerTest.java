package com.ll.backend.domain.admin;

import com.ll.backend.domain.admin.service.AdminService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AdminControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private AdminService adminService;

    @Test
    @DisplayName("로그인")
    void t1() throws Exception {

        String username = "user1";
        String password = "1234";

        ResultActions resultActions = mvc
                .perform(
                        formLogin("/admin/login").user(username).password(password))
                .andDo(print());

        resultActions
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(authenticated().withUsername("user1"))
                .andExpect(redirectedUrl("/"));

    }

//    @Test
//    @WithMockUser(username = "user1", roles = "USER")
//    @DisplayName("로그아웃")
//    void t2() throws Exception {
//
//        ResultActions resultActions = mvc
//                .perform(
//                        post("/logout"))
//                .andDo(print());
//
//        resultActions
//                .andExpect(status().isForbidden())
//                .andExpect(redirectedUrl("/"));
//    }

}
