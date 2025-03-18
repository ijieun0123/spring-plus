package org.example.expert.domain.manager.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.manager.entity.Manager;
import org.example.expert.domain.manager.entity.ManagerLog;
import org.example.expert.domain.manager.enums.Status;
import org.example.expert.domain.manager.repository.ManagerLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ManagerLogService {

    private final ManagerLogRepository managerLogRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(Status status, String errorMesssage, Long managerUserId){
        try{
            ManagerLog newManagerLog = new ManagerLog(LocalDateTime.now(), status, errorMesssage, managerUserId);

            managerLogRepository.save(newManagerLog);
        } catch (Exception e) {
            System.err.println("Log 저장 실패" + e.getMessage());
        }
    }
}
