package com.lahiru.integrator.dao;

import com.lahiru.integrator.domain.RequestLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLogRepository extends CrudRepository<RequestLog,Integer> {
}
