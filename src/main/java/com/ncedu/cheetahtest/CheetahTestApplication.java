package com.ncedu.cheetahtest;

import com.ncedu.cheetahtest.dao.resettoken.ResetTokenDaoImpl;
import com.ncedu.cheetahtest.dao.user.UserDaoImpl;
import com.ncedu.cheetahtest.service.user.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class CheetahTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheetahTestApplication.class, args);
	}
}
