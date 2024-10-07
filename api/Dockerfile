FROM gcr.io/google.com/cloudsdktool/google-cloud-cli:alpine

# OpenJDK 17 설치
RUN apk --update-cache add openjdk17-jre

# gcloud 설정 디렉토리 생성
RUN mkdir -p /root/.config/gcloud

# Build argument for Google credentials
ARG GOOGLE_CREDENTIALS

# Set up Google Cloud credentials
RUN echo "${GOOGLE_CREDENTIALS}" > /root/.config/gcloud/key.json && \
    gcloud auth activate-service-account --key-file=/root/.config/gcloud/key.json

RUN gcloud components install app-engine-java kubectl

ARG JAR_FILE=api/build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENV GOOGLE_APPLICATION_CREDENTIALS=/root/.config/gcloud/key.json

ENTRYPOINT ["java", "-jar", "app.jar"]