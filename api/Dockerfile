FROM gcr.io/google.com/cloudsdktool/google-cloud-cli:alpine

# OpenJDK 17 설치
RUN apk --update-cache add openjdk17-jre

# gcloud 설정 디렉토리 생성
RUN mkdir -p /root/.config/gcloud

# Build argument for Google credentials
ARG GOOGLE_CREDENTIALS

# Set up Google Cloud credentials
RUN echo "${GOOGLE_CREDENTIALS}" > /root/.config/gcloud/key.json

# Activate the service account
RUN gcloud auth activate-service-account --key-file=/root/.config/gcloud/key.json

# gcloud 추가 구성 요소 설치
RUN gcloud components install app-engine-java kubectl

# JAR 파일 설정
ARG JAR_FILE=api/build/libs/*.jar
COPY ${JAR_FILE} app.jar

# 환경변수 설정
ENV GOOGLE_APPLICATION_CREDENTIALS=/root/.config/gcloud/key.json

# 애플리케이션 실행
ENTRYPOINT ["sh", "-c", "gcloud auth activate-service-account --key-file=/root/.config/gcloud/key.json && java -jar app.jar"]
