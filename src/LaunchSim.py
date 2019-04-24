import math
from src import AtmoSim
from src import VehicleSim as VS
import numpy as np
import csv
import matplotlib
import PyQt5

GRAVITY = -9.81

class ControlScheme:

    def __init__(self):
        print('new control scheme')
        self.targetvariable = 'total acceleration' #vertical acceleartion, total acceleration,drag,lift,dyamic pressure
        self.controlfactor = 'angle of attacl'  #''throttle' or 'rate of climb'
        self.varyingfactor = 'time' #altitude, total velcocity, horizontal velocity
        self.controltable = []
        self.rateofclimb = 10 # angle of climb set and to be swept or used as rate of climb
        self.targetaltitude =100000
        self.targetxvelocity = 1000
        self.takeOffV = 300
        self.takeoffAng = 0

    def addtarget(self,x,target):
        targetentry = [x,target]
        self.controltable.append(targetentry)



class Launch:
    timestep = 0.1
    endtime =1000

    def __init__(self):
        print("new launch")
        self.fs = FlightState()
        self.log = []

    def setTakeoff(self,v,theta):
        print("setting take off parameters")
        TO = FlightState()
        TO.vy = math.sin(math.radians(theta))*v
        TO.vx = math.cos(math.radians(theta)) * v
        TO.aot = theta
        self.fs = TO

class FlightState:

    def __init__(self):
        self.vehicle = VS.Vehicle()
        self.ax = 0
        self.ay = 0
        self.vx = 0
        self.vy = 0
        self.sx = 0
        self.sy = 0
        self.aoa = 0
        self.aot = 0
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

    def calc_centr_acc(self):
        print("calculating centrifugal accelaration")
        return self.vx**2/(self.sx + 6671000)

    def calc_total_grav(self):
        #calcalates total acceleration due to gravity - centripetal acceleration
        return AtmoSim.gravity(self.sy) #- self.calc_centr_acc()

    def calc_rate_of_Climb(self):
        roc = math.degrees(math.atan(self.vy/self.vx))
        return roc

    def update(self, timestep):
        # note thrust and angle of thrust constant for timestep - to be updated when thrust more acurate.
        #acceleration based on avearage mass taken as linear avearge of timestep

        fuel0 = 1000 #self.vehicle.fueli
        self.vehicle.useFuel(self.vehicle.thrust, timestep)
        fuel1 = self.vehicle.fueli

        avmass = self.vehicle.mass + (fuel0+fuel1)/2

        #calcualte acceleartion for period
        ax0 = self.ax
        ay0 = self.ay
        self.ax = self.vehicle.thrust*math.cos(math.radians(self.aot))/(avmass)
        print("ax =",self.vehicle.thrust*math.cos(math.radians(self.aot))/(avmass))
        self.ay = GRAVITY + self.vehicle.thrust * math.sin(math.radians(self.aot))/avmass

        # calculate velocity - velocity at end based on constant average acceleration to be corrected when thrust varied
        vx0 = self.vx
        vy0 = self.vy
        vx1 = self.vx + (self.ax+ax0)/2*timestep
        vy1 = self.vy + (self.ay+ay0)/2*timestep
        self.vx = vx1
        self.vy = vy1

        #calcualte displacement based on average velocity
        self.sx = self.sx + (vx0 + vx1)/2*timestep
        self.sy = self.sy + (vy0 + vy1)/2*timestep




# ## MAIN
# def Launch():
    # v = VS.Vehicle()
    # atmo = AtmoSim.load_atmosphere()
    # v.load_ldcurve()
    # cl = v.lookup_cl(3)
    #
    # print("lift =")
    # print(v.calc_lift(atmo, cl, 1000, 0))
    #
    # scheme = ControlScheme()
    # launch = Launch()
    # launch.RunLaunch(scheme)