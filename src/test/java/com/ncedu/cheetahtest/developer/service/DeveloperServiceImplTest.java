package com.ncedu.cheetahtest.developer.service;

import com.ncedu.cheetahtest.developer.dao.DeveloperDao;
import com.ncedu.cheetahtest.developer.entity.Developer;
import com.ncedu.cheetahtest.developer.entity.ResetToken;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class DeveloperServiceImplTest {
  @Autowired
  private DeveloperService developerService;
  @MockBean
  private DeveloperDao developerDao;

  @Test
  void shouldFindDeveloperByEmail() {
    Developer developer = new Developer(1, "netCracker2@gmail.com", "1234", "NetCracker", "ADMIN", "ACTIVE", 43);
    doReturn(developer).when(developerDao).findDeveloperByEmail("netCracker2@gmail.com");
    developerService.findDeveloperByEmail("netCracker2@gmail.com");
  }

  @Test
  void shouldCreatePasswordResetTokenForUser() {
    Developer developer = new Developer(1, "netCracker2@gmail.com", "1234", "NetCracker", "ADMIN", "ACTIVE", 43);
    doNothing().when(developerDao).createToken(new ResetToken("Token-1", developer.getId(), new Date()));
    doNothing().when(developerDao).saveToken(new ResetToken("Token-1", developer.getId(), new Date()));
    developerService.createPasswordResetTokenForUser(developer, "Token-1");
  }
}