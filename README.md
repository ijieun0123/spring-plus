# SPRING PLUS

## EC2 설정
<img width="444" alt="Image" src="https://github.com/user-attachments/assets/060ef240-3fb6-4e31-8727-87efe6bace95" />

## Health Check API

이 프로젝트에는 서버의 상태를 확인할 수 있는 헬스 체크 API가 포함되어 있습니다.

### 엔드포인트

- **URL**: `/actuator/health`
- **Method**: `GET`
- **응답 예시**:
    - 성공 시:
      ```json
      {
        "status": "UP"
        ...
      }
      ```
    - 실패 시:
      ```json
      {
        "status": "DOWN"
        ...
      }
      ```
      
### 예시
<img width="278" alt="Image" src="https://github.com/user-attachments/assets/4cbb3d2e-bece-4456-859a-fbed7f891da3" />

## RDS
<img width="1131" alt="Image" src="https://github.com/user-attachments/assets/f0eabc99-10fd-4b9c-a5a2-bfaa21419f9e" />