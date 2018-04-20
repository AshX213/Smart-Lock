import RPi.GPIO as IO
import time
import requests
import urllib3
IO.setwarnings(False)
IO.setmode (IO.BCM)
IO.setup(18,IO.OUT)
IO.setup(12,IO.IN)
p = IO.PWM(18,50)
time.sleep(15)
while True:
    r = requests.get('https://my-smart-lock.com/?beaconid=0bad1f66ca2237d6f4afe723e2758a3d')
    status = r.text
    sensor = IO.input(12)
    print(status)
    print(sensor)
    if status == 'True' and sensor == 1:
        p.start(7.5)
        print('Opened!')
        print(status)
        time.sleep(0.5)
        sensor = IO.input(12)
        while status == 'True' and sensor == 0:
            p.stop()
            try:
                r = requests.get('https://my-smart-lock.com/?beaconid=0bad1f66ca2237d6f4afe723e2758a3d')
            except:
                pass
            status = r.text
            sensor = IO.input(12)
        p = IO.PWM(18,50)
    if status != 'True' and sensor == 0:
        p.start(2.75)
        print('Locked!')
        print(status)
        time.sleep(0.5)
        sensor = IO.input(12)
        while status != 'True' and sensor == 1:
            p.stop()
            try:
                r = requests.get('https://my-smart-lock.com/?beaconid=0bad1f66ca2237d6f4afe723e2758a3d')
            except:
                pass
            status = r.text
            sensor = IO.input(12)
        p = IO.PWM(18,50)
    time.sleep(2)
