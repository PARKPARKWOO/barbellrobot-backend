# Google Cloud CLI 설치를 위한 베이스 이미지 사용
FROM gcr.io/google.com/cloudsdktool/google-cloud-cli:alpine

# OpenJDK 17 설치
RUN apk --update-cache add openjdk17-jre

# Java 애플리케이션을 위한 JAR 파일 설정
ARG JAR_FILE=api/build/libs/*.jar

# JAR 파일을 이미지에 복사
COPY ${JAR_FILE} app.jar

# Google Cloud CLI의 추가 구성요소 설치 (필요한 경우 설치할 구성요소 명시)
# 예시: app-engine-java 및 kubectl 설치
RUN gcloud components install app-engine-java kubectl

# 애플리케이션을 실행하는 명령어 설정
ENTRYPOINT ["java", "-jar", "app.jar"]
