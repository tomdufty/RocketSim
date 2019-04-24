# main code for GUI
from PyQt5 import QtWidgets
from src import mainUI
from src import LaunchSim
from src import AtmoSim
from src import VehicleSim as VS
import matplotlib
import sys
import numpy as np
import random



class mainWindow(QtWidgets.QMainWindow):

    def __init__(self):
        super(mainWindow, self).__init__()
        self.ui = mainUI.Ui_MainWindow()
        self.ui.setupUi(self)



    def launchClicked(self):
        v = VS.Vehicle()
        atmo = AtmoSim.load_atmosphere()
        v.load_ldcurve()
        cl = v.lookup_cl(3)
        print("lift =")
        print(v.calc_lift(atmo, cl, 1000, 0))
        h1, = self.ui.widget.canvas.ax.plot([],[])



        scheme = LaunchSim.ControlScheme()
        launch = LaunchSim.Launch()
        launch.setTakeoff(scheme.takeOffV, scheme.takeoffAng)

        for time in range(0, launch.endtime, launch.timestep):
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

            launch.fs.update(launch.timestep)
            #print('roc=' + str(self.fs.calc_rate_of_Climb()))
            #error = scheme.rateofclimb - self.fs.calc_rate_of_Climb()
            #print('error=' + str(error))
            print(time, launch.fs.sy)
            data = [time, launch.fs.sy]
            print(data)
            self.update_line(h1,data)



        #launch.RunLaunch(scheme)

        print("launch succesfull")
        # self.update_graph()

    def update_line(self, hl, new_data):
        print("updating line")
        print(hl)
        hl.set_xdata(np.append(hl.get_xdata(), new_data))
        hl.set_ydata(np.append(hl.get_ydata(), new_data))
        print("h1 data set")

        self.ui.widget.canvas.ax.draw()



    def update_graph(self):
        fs = 500
        f = random.randint(1, 100)
        ts = 1 / fs
        length_of_signal = 100
        t = np.linspace(0, 1, length_of_signal)

        cosinus_signal = np.cos(2 * np.pi * f * t)
        sinus_signal = np.sin(2 * np.pi * f * t)

        self.ui.widget.canvas.ax.clear()
        self.ui.widget.canvas.ax.plot(t, cosinus_signal)
        self.ui.widget.canvas.ax.plot(t, sinus_signal)
        self.ui.widget.canvas.ax.legend(('cosinus', 'sinus'), loc='upper right')
        self.ui.widget.canvas.ax.set_title(' Cosinus - Sinus Signal')
        self.ui.widget.canvas.draw()


app = QtWidgets.QApplication([])

application = mainWindow()

application.show()

sys.exit(app.exec())