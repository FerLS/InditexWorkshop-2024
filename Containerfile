# Base image
FROM adoptopenjdk:21-jdk-hotspot as builder

# Workdir
WORKDIR /app

# Copy project
COPY . .

# Give permissions to Maven Wrapper
RUN chmod +x mvnw

# Pack & Execute test or Pack without test
RUN ./mvnw clean package
#RUN ./mvnw package -DskipTests

# Image with the Jar
FROM adoptopenjdk:21-jre-hotspot

# Workdir
WORKDIR /app

# Copy Jar from previous step
COPY --from=builder /app/target/sample-app.jar .

# Expose port
EXPOSE 8080

# Execute app - Complete this one!
 



