import math


GRAVITY = -9.81

class Launch:
    timestep = 1
    endtime =1000

    def __init__(self):
        print("new launch")
        self.fs = FlightState()

    def RunLaunch(self):
        for time in range(0,self.endtime,self.timestep):
            self.fs.update(self.timestep)
            print(time,self.fs.sy)


class FlightState:


    def __init__(self):
        self.vehicle = Vehicle()
        self.ax = 0;
        self.ay = 0;
        self.vx = 0;
        self.vy = 0;
        self.sx = 0;
        self.sy = 0;
        self.aoa = 0;
        self.aot = 90;
        self.wz = 0
        print("new state")

    def setax(self,ax):
        self.ax = ax

    def setay(self,ay):
        self.ay = ay

    def setvx(self,vx):
        self.vx = vx

    def setvy(self,vy):
        self.vy = vy

    def setsx(self,sx):
        self.sx = sx

    def setsy(self,sy):
        self.sy = sy

    def update(self,timestep):
        # note thrust and angle of thrust constant for timestep - to be updated when thurst more acurate.
        #acceleration base don avearage mass taken as linear avearge of timestep
        #change
        fuel0 = self.vehicle.fueli
        self.vehicle.useFuel(self.vehicle.thrust,timestep)
        fuel1= self.vehicle.fueli

        avmass = self.vehicle.mass + (fuel0+fuel1)/2

        #calcualte acceleartion for period
        self.ax = 0  #self.vehicle.thrust*math.cos(math.radians(self.aot))/(avmass)
        self.ay = self.vehicle.thrust * math.sin(math.radians(self.aot))/avmass + GRAVITY

        # calculate velocity - velocivty at end based on constant average accelation to be corrected when thrust varied
        vx0 = self.vx
        vy0 = self.vy
        vx1 = self.vx + self.ax*timestep
        vy1 = self.vy + self.ay*timestep
        self.vx = vx1
        self.vy = vy1

        #calcualte displacement based on average velocity
        self.sx = self.sx + (vx0 +vx1)/2*timestep
        self.sy = self.sy + (vy0 + vy1) / 2 * timestep


class Vehicle:
    def __init__(self):
        print("vehicel created")
        self.mass = 0
        self.fuel0 = 10000
        self.fueli = self.fuel0
        self.thrust = 100000
        self.tsfc = 0.0001

    def useFuel(self,thrust,timestep):
        self.fueli = self.fueli -self.tsfc*thrust*timestep
## MAIN

launch = Launch()
launch.RunLaunch()