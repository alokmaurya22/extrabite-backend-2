# Use official Java 17 image
FROM eclipse-temurin:17-jdk

# App directory bana do
WORKDIR /app

# Sab file copy kar lo
COPY . .

# Build project (skip tests)
RUN ./mvnw clean package -DskipTests

# Expose port 8080 (Render expects this)
EXPOSE 8080

# Run the jar
CMD ["java", "-jar", "target/extrabite-backend-0.0.1-SNAPSHOT.jar"]