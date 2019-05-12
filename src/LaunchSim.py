import math
from src import AtmoSim
from src import VehicleSim as VS

GRAVITY = -9.81


class ControlScheme:

    def __init__(self):
        print('new control scheme')
        self.targetvariable = 'total acceleration' #vertical acceleartion, total acceleration,drag,lift,dyamic pressure
        self.controlfactor = 'angle of attack'  #''throttle' or 'rate of climb'
        self.varyingfactor = 'time' #altitude, total velcocity, horizontal velocity
        self.controltable = []
        self.rateofclimb = 10 # angle of climb set and to be swept or used as rate of climb
        self.targetaltitude = 100000
        self.targetxvelocity = 1000
        self.takeOffV = 300
        self.takeoffAng = 0

    def addtarget(self, x, target):
        targetentry = [x, target]
        self.controltable.append(targetentry)


class Launch:
    timestep = 0.1
    endtime = 1000

    def __init__(self):
        print("new launch")
        self.fs = FlightState()
        self.log = []


    def setTakeoff(self, v, theta):

        print("setting take off parameters")
        TO = self.fs
        TO.vy = math.sin(math.radians(theta)) * v
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
        self.atmo = AtmoSim.load_atmosphere()

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

        #calculate fuel reduction
        fuel0 = self.vehicle.fueli
        self.vehicle.useFuel(self.vehicle.thrust, timestep)
        fuel1 = self.vehicle.fueli
        avmass = self.vehicle.mass + (fuel0+fuel1)/2

        #calculate lift
        sos = AtmoSim.get_atmo_value(self.atmo,self.sy,'sos')
        print ("sos", sos)
        vtotal = math.sqrt(self.vx**2 + self.vy**2)
        if self.vx == 0:
            Omega = 90
        else:
            Omega =  math.degrees(math.atan(self.vy/self.vx))
        print("angle of flight", Omega)
        #note angle of attack and angle of thrust should be referenced to vtotal
        M = vtotal/sos
        cl = self.vehicle.lookup_cl(M)
        lift = self.vehicle.calc_lift(self.atmo,cl,vtotal,self.sy)
        liftx = lift*math.cos(math.radians(Omega))
        lifty = lift*math.sin(math.radians(Omega))
        self.vehicle.lift = lift
        cd = self.vehicle.lookup_cd(M)
        drag = self.vehicle.calc_drag(self.atmo, cd, vtotal, self.sy)
        dragx = drag * math.cos(math.radians(Omega))
        dragy = drag * math.sin(math.radians(Omega))
        self.vehicle.drag = drag


        #calcualte acceleartion for period
        thrustx =self.vehicle.thrust*math.cos(math.radians(self.aot))
        thrusty = self.vehicle.thrust * math.sin(math.radians(self.aot))
        self.ax = (thrustx + liftx - dragx )/avmass
        self.ay = GRAVITY + (thrusty + lifty - dragy)/avmass

        # calculate velocity - velocity at end based on constant average acceleration to be corrected when thrust varied
        vx0 = self.vx
        vy0 = self.vy
        vx1 = self.vx + self.ax*timestep
        vy1 = self.vy + self.ay*timestep
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