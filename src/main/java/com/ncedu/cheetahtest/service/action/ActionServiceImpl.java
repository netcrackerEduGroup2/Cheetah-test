package com.ncedu.cheetahtest.service.action;

import com.ncedu.cheetahtest.config.security.jwt.JwtTokenProvider;
import com.ncedu.cheetahtest.dao.action.ActionDao;
import com.ncedu.cheetahtest.entity.action.Action;
import com.ncedu.cheetahtest.exception.security.JwtAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionServiceImpl implements ActionService {
    private final ActionDao actionDao;
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    public ActionServiceImpl(ActionDao actionDao, JwtTokenProvider jwtTokenProvider){
        this.actionDao =actionDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void createAction(String accessToken, Action actionDTO) {

        if(!jwtTokenProvider.isTokenValid(accessToken)){
            throw new JwtAuthenticationException();
        }
        actionDao.createAction(actionDTO);
    }
}
