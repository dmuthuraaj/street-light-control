# Use an official Eclipse Mosquitto image from the Docker Hub
FROM eclipse-mosquitto:2.0.15

# Copy configuration files
COPY mosquitto.conf /mosquitto/config/mosquitto.conf

# Expose the default MQTT port
EXPOSE 1883

# Expose the default MQTT over WebSockets port
EXPOSE 9001

# Run Mosquitto broker
CMD ["mosquitto", "-c", "/mosquitto/config/mosquitto.conf"]

