#!/bin/bash

REPOSITORY=/home/ex2-user/app/step2
PROJECT_NAME=NFT_Backend

echo "> Build 파일 복사"
cp $REPOSITORY/zip/*.war $REPOSITORY/

echo "> 현재 구동 중인 애플리케이션의 pid 확인"
CURRENT_PID=$(pgrep -fl myn | grep war | awk '{print $1}')

echo "현재 구동 중인 애플리케이션의 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않았습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> 새 애플리케이션 배포"
WAR_NAME=$(ls -tr $REPOSITORY/*.war | tail -n 1)

echo "> WAR Name: $WAR_NAME"

echo "> $WAR_NAME에 실행권한을 추가"
chmod +x $WAR_NAME

echo "> $WAR_NAME 실행"
nohup java -jar \
  -Dspring.config.location=/home/ec2-user/app/application.properties,/home/ec2-user/app/application-oauth.properties \
  $WAR_NAME > $REPOSITORY/nohup.out 2>&1 &
