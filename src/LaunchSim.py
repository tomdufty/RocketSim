import math
from src import AtmoSim
from src import VehicleSim as VS
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
    timestep = 1
    endtime =1000

    def __init__(self):
        print("new launch")
        self.fs = FlightState()

    def setTakeoff(self,vx,theta):
        TO = FlightState()
        TO.vx = vx
        TO.aot = theta
        self.fs = TO

    def RunLaunch(self,scheme):
        self.setTakeoff(scheme.takeOffV, scheme.takeoffAng)
        for time in range(0, self.endtime, self.timestep):
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
            print('roc=' + str(self.fs.calc_rate_of_Climb()))
            error = scheme.rateofclimb - self.fs.calc_rate_of_Climb()
            print('error=' + str(error))
            print(time,self.fs.sy)


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
        self.aot = 90
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
        return AtmoSim.gravity(self.sy) - self.calc_centr_acc()

    def calc_rate_of_Climb(self):
        roc = math.degrees(math.atan(self.vy/self.vx))
        return roc

    def update(self,timestep):
        # note thrust and angle of thrust constant for timestep - to be updated when thrust more acurate.
        #acceleration based on avearage mass taken as linear avearge of timestep
        #change
        fuel0 = self.vehicle.fueli
        self.vehicle.useFuel(self.vehicle.thrust, timestep)
        fuel1 = self.vehicle.fueli

        avmass = self.vehicle.mass + (fuel0+fuel1)/2

        #calcualte acceleartion for period
        self.ax = 0  #self.vehicle.thrust*math.cos(math.radians(self.aot))/(avmass)
        self.ay = self.vehicle.thrust * math.sin(math.radians(self.aot))/avmass + GRAVITY

        # calculate velocity - velocity at end based on constant average acceleration to be corrected when thrust varied
        vx0 = self.vx
        vy0 = self.vy
        vx1 = self.vx + self.ax*timestep
        vy1 = self.vy + self.ay*timestep
        self.vx = vx1
        self.vy = vy1

        #calcualte displacement based on average velocity
        self.sx = self.sx + (vx0 +vx1)/2*timestep
        self.sy = self.sy + (vy0 + vy1) / 2 * timestep




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