package org.example.expert.domain.manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.manager.enums.Status;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "manager_logs")
public class ManagerLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime requestTime;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String errorMessage;

    private Long managerUserId;

    public ManagerLog(LocalDateTime requestTime, Status status, String errorMessage, Long managerUserId){
        this.requestTime = requestTime;
        this.status = status;
        this.errorMessage = errorMessage;
        this.managerUserId = managerUserId;
    }
}
