# Safestash Python Raspberry Pi Client
# Created by cartermccardwell on 3/25/2017
import time
import picamera
import requests

from neopixel import *


# LED strip configuration:
LED_COUNT      = 22      # Number of LED pixels.
LED_PIN        = 18      # GPIO pin connected to the pixels (must support PWM!).
LED_FREQ_HZ    = 800000  # LED signal frequency in hertz (usually 800khz)
LED_DMA        = 5       # DMA channel to use for generating signal (try 5)
LED_BRIGHTNESS = 180     # Set to 0 for darkest and 255 for brightest
LED_INVERT     = False   # True to invert the signal (when using NPN transistor level shift)

CMP1 = [7, 8, 9, 10]
CMP2 = [3, 4, 5, 6]
CMP3 = [0, 1, 2]
CMP4 = [11, 12, 13, 14]
CMP5 = [15, 16, 17, 18]
CMP6 = [19, 20, 21]

def setStripGroupingToGreen(strip, ledList):
	for i in range(strip.numPixels()):
		strip.setPixelColor(i, Color(0, 255, 0))
	for i in ledList:
		ct = []
		if i is 1:
			ct = CMP1
		elif i is 2:
			ct = CMP2
		elif i is 3:
			ct = CMP3
		elif i is 4:
			ct = CMP4
		elif i is 5:
			ct = CMP5
		elif i is 6:
			ct = CMP6
		for j in ct:
			strip.setPixelColor(j, Color(255, 0, 0))			
	strip.show()

def clearLights(strip):
	for i in range(strip.numPixels()):
		strip.setPixelColor(i, Color(0, 0, 0))

	strip.show()

def allBlue(strip):
	for i in range(strip.numPixels()):
		strip.setPixelColor(i, Color(0, 0, 255))
	strip.show()

def allRed(strip):
	for i in range(strip.numPixels()):
		strip.setPixelColor(i, Color(255, 0, 0))
	strip.show()


def showProcessing(strip):
	for i in range(strip.numPixels()):
		if i % 2 is 0:
			strip.setPixelColor(i, Color(255, 0, 255))
		else:
			strip.setPixelColor(i, Color(0, 0, 255))
	strip.show()

def getResponseFromServer(strip):
	with open('img.jpg', 'rb') as f:
		r = requests.post('http://safestash.athoura.com:8080/checkFace', files={'file': f})
	 	resp = r.text
		print("Response:: " + resp)

		if resp is 'X':
			allRed(strip)

		lightList = []
		buffer = ""
		for i in range(0, len(resp)):
			if resp[i] == '/':
				lightList.append(int(buffer))
				buffer = ""
				continue
			buffer = buffer + resp[i]
		print(lightList)
		setStripGroupingToGreen(strip, lightList)	


# Main program logic follows:
if __name__ == '__main__':
	# Create NeoPixel object with appropriate configuration.
	strip = Adafruit_NeoPixel(LED_COUNT, LED_PIN, LED_FREQ_HZ, LED_DMA, LED_INVERT, LED_BRIGHTNESS)
	# Intialize the library (must be called once before other functions).
	strip.begin()
	camera = picamera.PiCamera()	
	camera.resolution = (1024, 768)

	print("Starting Safestash Client...\nConnecting to system >>>")
	print("Taking shots every 5 seconds >>>")
	
	while True:
		#allBlue(strip)
		#time.sleep(1)
		camera.capture('img.jpg')
		#showProcessing(strip)
		getResponseFromServer(strip)
		#time.sleep(5)


