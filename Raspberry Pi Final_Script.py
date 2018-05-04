import RPi.GPIO as IO #calls header file for GPIO's of RasPi
import time #imports time package
import requests #imports requests package
import urllib3 #imports urllib3 package
IO.setwarnings(False) #sets program to not display warnings regarding the GPIO's
IO.setmode (IO.BCM) #programming the GPIO by BCM pin numbers
IO.setup(18,IO.OUT) #initializes GPIO 18 as an output
IO.setup(12,IO.IN) #initializes GPIO 12 as an input
p = IO.PWM(18,50) #sets GPIO 18 to output at 50Hz frequency
time.sleep(15) #delays the program from running temporarily to ensure Pi is connected to internet after bootup
while True: #runs the program forever
    r = requests.get('https://my-smart-lock.com/?beaconid=0bad1f66ca2237d6f4afe723e2758a3d') #gets content of website
    status = r.text #assigns text of website to variable
    sensor = IO.input(12) #sets GPIO 12 as an input
    print(status)
    print(sensor)
    if status == 'True' and sensor == 1: #for when the door is locked and phone enters proximity with beacon
        p.start(7.5) #starts PWM signal with 7.5% duty cycle
        print('Opened!')
        print(status)
        time.sleep(0.5)
        sensor = IO.input(12)
        while status == 'True' and sensor == 0: #keeps door unlocked until parameters change
            p.stop()
            try:
                r = requests.get('https://my-smart-lock.com/?beaconid=0bad1f66ca2237d6f4afe723e2758a3d')
            except:
                pass
            status = r.text
            sensor = IO.input(12)
        p = IO.PWM(18,50)
    if status != 'True' and sensor == 0: #for when the door is unlocked and phone is not in close proximity with beacon
        p.start(2.75) #starts PWM signal with 2.75% duty cycle
        print('Locked!')
        print(status)
        time.sleep(0.5)
        sensor = IO.input(12)
        while status != 'True' and sensor == 1: #keeps door locked until parameters change
            p.stop()
            try:
                r = requests.get('https://my-smart-lock.com/?beaconid=0bad1f66ca2237d6f4afe723e2758a3d')
            except:
                pass
            status = r.text
            sensor = IO.input(12)
        p = IO.PWM(18,50)
    time.sleep(2)
