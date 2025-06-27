# Use official Java 17 image
FROM eclipse-temurin:17-jdk

# Create app directory
WORKDIR /app

# Copy everything to image
COPY . .

#  Make mvnw executable
RUN chmod +x mvnw

# Build the project, skipping tests
RUN ./mvnw clean package -DskipTests

# Expose port (Render expects 8080)
EXPOSE 8080

# Run the jar
CMD ["java", "-jar", "target/extrabyte-backend-0.0.1-SNAPSHOT.jar"]