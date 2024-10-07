# Use debian-based image instead of alpine
FROM gcr.io/google.com/cloudsdktool/google-cloud-cli:debian_component_based

# Install OpenJDK 17
RUN apt-get update && \
    apt-get install -y openjdk-17-jre && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Create gcloud config directory
RUN mkdir -p /root/.config/gcloud

# Install additional gcloud components
RUN gcloud components install app-engine-java kubectl

# Set up JAR file
ARG JAR_FILE=api/build/libs/*.jar
COPY ${JAR_FILE} app.jar

# Set environment variable
ENV GOOGLE_APPLICATION_CREDENTIALS=/root/.config/gcloud/key.json

# Activate service account before running the application
ENTRYPOINT ["sh", "-c", "gcloud auth activate-service-account --key-file=/root/.config/gcloud/key.json && java -jar app.jar"]