package com.lahiru.integrator.service.impl;

import com.lahiru.integrator.dao.RequestLogRepository;
import com.lahiru.integrator.domain.RequestLog;
import com.lahiru.integrator.service.RequestLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestLogServiceImpl implements RequestLogService {

    private static final Logger logger= LoggerFactory.getLogger(RequestLogServiceImpl.class);

    @Autowired
    private RequestLogRepository requestLogRepository;

    @Override
    public void logRequest(RequestLog requestLog){
        try{
            requestLogRepository.save(requestLog);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
        }
    }
}
