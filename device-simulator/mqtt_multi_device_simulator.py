import paho.mqtt.client as mqtt
import json
import time
import random
import argparse
from threading import Thread

# MQTT configuration
MQTT_SERVER = "192.168.1.2"
MQTT_PORT = 1883
SUBSCRIBE_TOPIC_TEMPLATE = "iot/devices/settings/{mac}"
PUBLISH_TOPIC = "iot/devices"

# Default configuration
DEFAULT_PING_TIME = 10000
DEFAULT_LIGHT_STATUS = "OFF"

# MQTT callback functions
def on_connect(client, userdata, flags, rc):
    mac_address = userdata['mac_address']
    print(f"Connected to MQTT broker with result code {rc} for device {mac_address}")
    subscribe_topic = SUBSCRIBE_TOPIC_TEMPLATE.format(mac=mac_address)
    client.subscribe(subscribe_topic)

def on_message(client, userdata, msg):
    global ping_time, light_status
    mac_address = userdata['mac_address']
    print(f"Message arrived on topic: {msg.topic} for device {mac_address}. Message: {msg.payload.decode()}")

    try:
        message_json = json.loads(msg.payload)
        ping_time = int(message_json["pingTime"])
        light_status = message_json["lightStatus"]
        print(f"Device {mac_address} - Ping Time: {ping_time}")
        print(f"Device {mac_address} - Light Status: {light_status}")

    except json.JSONDecodeError as e:
        print(f"JSON decode error for device {mac_address}: {e}")

def generate_sensor_data():
    return {
        "current": str(random.uniform(10, 30)),
        "voltage": str(random.uniform(4.5, 5.5)),
        "latitude": "12.9716",
        "longitude": "77.5946"
    }

def device_simulator(mac_address, ping_time):
    global last_msg_time
    last_msg_time = 0
    light_status = DEFAULT_LIGHT_STATUS

    # Initialize MQTT client
    client = mqtt.Client(userdata={'mac_address': mac_address})
    client.on_connect = on_connect
    client.on_message = on_message

    # Connect to MQTT broker
    client.connect(MQTT_SERVER, MQTT_PORT, 60)

    # Start the MQTT client loop
    client.loop_start()

    try:
        while True:
            now = time.time() * 1000  # Current time in milliseconds
            if now - last_msg_time > ping_time:
                last_msg_time = now

                # Create a JSON object with sensor data
                data = {
                    "macAddress": mac_address,
                    "lightStatus": light_status,
                    "pingTime": ping_time,
                    "lightOnTime": "18:00",
                    "lightOffTime": "07:00"
                }
                data.update(generate_sensor_data())

                # Serialize JSON to string
                json_data = json.dumps(data)

                # Publish JSON string to the MQTT topic
                print(f"Device {mac_address} publishing message: {json_data}")
                client.publish(PUBLISH_TOPIC, json_data)
            
            time.sleep(1)  # Adjust as needed
    except KeyboardInterrupt:
        print(f"Exiting device simulator for {mac_address}...")
    finally:
        client.loop_stop()
        client.disconnect()

def main():
    # Parse command-line arguments
    parser = argparse.ArgumentParser(description='MQTT Device Simulator')
    parser.add_argument('--num_devices', type=int, default=1, help='Number of devices to simulate')
    parser.add_argument('--ping', type=int, default=DEFAULT_PING_TIME, help='Ping interval in milliseconds')
    args = parser.parse_args()

    num_devices = args.num_devices
    ping_time = args.ping

    # Create and start a thread for each simulated device
    threads = []
    for i in range(num_devices):
        mac_address = f"device_{i+1}"
        thread = Thread(target=device_simulator, args=(mac_address, ping_time))
        thread.start()
        threads.append(thread)

    # Wait for all threads to complete
    for thread in threads:
        thread.join()

if __name__ == "__main__":
    main()
