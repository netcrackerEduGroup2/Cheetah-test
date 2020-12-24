package com.ncedu.cheetahtest.config.beans;

import com.ncedu.cheetahtest.dao.genericdao.Consts;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class BeanFactory {

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    public Consts consts(String[] rows, String tableName) {
        return new Consts(rows, tableName);
    }
}
