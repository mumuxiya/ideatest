package com.lg.jabcmysqldemo.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.jabcmysqldemo.dao.TypeDao;
import com.lg.jabcmysqldemo.model.Type;
import org.springframework.stereotype.Service;

/**
 * Name: lg
 * Date: 2019/10/11 16:39
 * Content:
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeDao, Type> implements TypeService {

}
