# =====================
# 🔧 Stage 1: Build app
# =====================
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app
COPY . .

# Make mvnw executable
RUN chmod +x mvnw

# Build the .jar file
RUN ./mvnw clean package -DskipTests

# =========================
# Stage 2: Run the app
# =========================
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy only the built jar file from earlier stage
COPY --from=build /app/target/extrabite-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]