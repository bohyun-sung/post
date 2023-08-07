# post

swagger3.0 port: {host}/swagger-ui/index.html

# docker with wsl

reference : https://goddaehee.tistory.com/313

Ubuntu-20.04

Docker version 24.0.2

```aidl
// wsl 접속
wsl

// docker mysql images
docker pull mysql

// docker rum
docker run -p [호스트 포트]:[컨테이너 포트] [이미지 이름]
// docker run -d -p 23306:3306 -e MYSQL_ROOT_PASSWORD=123 --name mysql-container mysql

// docker 컨테이너 접속
docker exec -it [이미지 이름] bash

// mysql 
// root 로 접속
mysql -u root -p
// mysql 데이터베이스 목록 조회
show databases;
// post 데이터베이스 생성
create database post;
// mysql 데이터베이스 선택 [mysql 사용자 계정, 권한을 저장하는 시스템 데이터 베이스]
use mysql;
// 호스트 '%' 에서 접속하는 bohyun 이라는 새로운 사용자 생성 비밀번호는 123 으로 설정
create user 'bohyun'@'%' identified by '123';
// 모든 사용자 정보 조회 [bohyun 생성되었는지 확인]
select host, user from user;
// mysql 권하을 다시 로드하여 사용자 계정 변경 사항을 적용
flush privileges;
// '%' 호스트에 접속하는 사용자 bohyun 에게 post 데이터베이스에 대한 모든권한 부여 
grant all privileges on post.* to bohyun@'%';
// geant 명령어 실행 후 업데이트된 사용자 bohyun에게 부여된 권한을 표시 
show grants for 'bohyun'@'%';
```

## DBeaver 접속
Dirver properties 옵션 설정
```
// 연결이 끊어지면 자동으로 재연결 시도
autoReconnect=true
// ssl 연결을 사용하지 않도록 설정
useSSL=false
// mysql 8.0 이후에 공개 키를 검색하는 것을 허용하도록 설정
// 8.0 이전버전을 true 가 default
allowPublicKeyRetrieval=true
```

