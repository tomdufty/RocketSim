import math


GRAVITY = -9.81

class ControlScheme:

    def __init__(self):
        print('new control scheme')
        self.targetvariable = 'total acceleration' #vertical acceleartion, total acceleration,drag,lift,dyamic pressure
        self.controlfactor = 'throttle' #angle of attack or rate of climb
        self.varyingfactor = 'time' #altitude, total velcocity, horizontal velocity
        self.controltable = []
        self.rateofclimb = 10 # angle of climb set and to be swept or used as rate of climb
        self.targetaltitude =10000
        self.targetxvelocity = 1000

    def addtarget(self,x,target):
        targetentry = [x,target]
        self.controltable.append(targetentry)


class Launch:
    timestep = 1
    endtime =1000

    def __init__(self):
        print("new launch")
        self.fs = FlightState()

    def RunLaunch(self,scheme):
        for time in range(0,self.endtime,self.timestep):
            #if scheme is acceeration:
            # calculate rquired lift
            # calcaulte maximum possible lift and aot
            # calcualte drag
            # calculate required thurst
            # calcualte possible thurst
            # convegre on acutal thrust
            #
            # if scheme stress or pressure based
            # calcualte maximum target velocity
            # calcualte error to velocity
            # calacuate required acceleration
            # repeat steps above
            # converge on target velocity
            #
            # calcalate flight state
            # update flight state
            # update fuel
            # next timestep
            

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
        self.cd =0.5
        self.cl =0.5
        self.frontA = 1
        self.wingA = 3

    def useFuel(self,thrust,timestep):
        self.fueli = self.fueli -self.tsfc*thrust*timestep
## MAIN

scheme = ControlScheme()


launch = Launch()
launch.RunLaunch(scheme)