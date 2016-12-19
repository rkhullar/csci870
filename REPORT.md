<img src=http://www.nyit.edu/files/communications_and_marketing/DIGITAL_LOGO_Engineering_RGB_HORIZ.png width="179" height="63" />

<br>

# Indoor Localization Framework with WiFi Fingerprinting

<p align="center">
  <img src=https://rkhullar.github.io/csci870/images/icon.png width="256" height="256" />
</p>

<br>
<br>

| Project Member  | **Rajan Khullar** |
| --------------- | ----------------- |
| Project Advisor | Dr. Ziqian Dong   |
| Semester        | Fall 2016         |

<div style="page-break-after: always;"></div>

## Introduction
### Abstract
As with most studies, the results of WiFi fingerprinting are much more meaningful if there is a large sample size. For this project I created an Android app that helps researchers in this field build their dataset  efficiently. After collecting two weeks worth of data I studied how many access points are ideal to predict a user's location from a single WiFi scan.

### Background
Global Positioning Systems (GPS) have been the standard technology used to obtain location of electronic devices. GPS devices are essential for the functionality of many of today’s devices, appearing in vehicles, drones, and smart phones. They have a wide range of applications, from helping people navigate the roads to assisting the military in navigating planes and drones. Moreover, the popularity of smartphones with their inbuilt GPS devices has made GPS easily available.

Unfortunately localization using GPS is ineffective indoors and in highly metropolitan environments because of GPS signal fading. Signal fading occurs when signals penetrate building materials causing a decrease in intensity. This causes a decrease in signal-to-noise ratio making it difficult for GPS devices to differentiate between signals and noise. Signal fading is also a result of multipath phenomenon, which is caused by reflection and refraction of signals when they encounter walls [1]. Because of these reasons GPS signal is often lost entirely on smartphones in indoor environments, causing a pressing need for indoor localization.

Indoor Localization may be utilized similarly to outdoor localization. This includes commercial applications such as real-time maps to help people navigate within malls or museums, and more technical applications such as guiding drones through indoor environments or locating cars inside tunnels. An indoor positioning system will open a new market of applications.
Indoor Localization may be utilized similarly to outdoor localization. This includes commercial applications such as real-time maps to help people navigate within malls or museums, and more technical applications such as guiding drones through indoor environments or locating cars inside tunnels. An indoor positioning system will open a new market of applications.

<div style="page-break-after: always;"></div>

| ![REU 2013][reu13] | ![REU 2015][reu15] |
| :----------------: | :----------------: |
|  ***Figure 1.1***  |  ***Figure 2.1***  |

### Related Work
Indoor Localization has been attempted through the use of Wi-Fi signal strength and Sensor fusion. Kothari et al (2012) [2] have successfully used dead reckoning and Wi-Fi signal strength fingerprinting to find the location of a smartphone. Dead reckoning was their method of using the accelerometer, gyroscope, compass and a particle filter in order to track walking and thereby track location. Both of these methods are prone to large errors. Wi-Fi signal strength is affected by obstacles and by the myriad of other Wi-Fi signals in an urban environment, while the accelerometer and gyroscope sensors are likely to generate random noise in the data.

Thanks to the National Science Foundation's Research Experience Undergraduate (REU) program, research fellows have been able to study indoor localization at NYIT. As shown in Figure 1.1, students in the summer of 2013 chose three access points on a single floor and measured signal strengths from twenty six spots evenly distributed in the hallways.

In Summer 2015 a student from Cooper Union and I were two of the REU fellows. We attempted to correlate energy consumption of a Nexus 5 to physical distance from an access point. We setup one router as a fixed access point and designed an Android application that records the current and voltage of the phone while pinging the router. We took ten samples at sixty points in a large classroom, however we were unable to find any significant correlation as shown in Figure 1.2. 

In Summer 2016 two fellows studied multifloor localization with four consecutive floors in NYIT's main building. They were able to distinguish between thirty locations in their dataset with high accuracy. In all three REU studies the process of data collection proved difficult. The third project had a sample size of around 300 WiFi scans. After realizing this I was inspired to create a framework to help automate the process of gathering samples.

<div style="page-break-after: always;"></div>
"Hello"
| ![app-setup][app1] | ![app-push][app2] |
| :----------------: | :---------------: |
|  ***Figure 2.1***  | ***Figure 2.2***  |

## Implementation
The Digital Ocean server has Apache and PostgreSQL installed. A python library called Flask was used to create a REST api. Java was used to create the Android application. As shown in Figure 2.1, once users sign up and login they can choose their classroom and setup a scan for the duration of that class. The scans will occur in the background so the users can close the app and use their phone normally. The scans can be paused or canceled in case the users needs to change their location. Finally once the scans are complete, then each user can upload their local dataset to the server.

### Database
|   ![erd][erd]   |
| :--------------: |
| ***Figure 3.1*** |

One scan record contains the following information:
* mac address to one access point
* signal strength to that access point
* location (building, floor, room)
* time stamp
* owner

In order to reduce redundancy a table of unique access points is maintained as well as one for unique locations. The actor table contains information for all people in the system including normal users, administrators, and new unverified signups.

[reu13]: https://rkhullar.github.io/csci870/images/report/reu-2013.png
[reu15]: https://rkhullar.github.io/csci870/images/report/reu-2015.png
[app1]: https://rkhullar.github.io/csci870/images/report/app-setup.png
[app2]: https://rkhullar.github.io/csci870/images/report/app-push.png
[erd]: https://rkhullar.github.io/csci870/images/report/erd.png
[pre]: https://rkhullar.github.io/csci870/images/report/preprocessing.png