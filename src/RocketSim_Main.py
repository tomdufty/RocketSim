# main code for GUI
from PyQt5 import QtWidgets
from src import mainUI
from src import LaunchSim
from src import AtmoSim
from src import VehicleSim as VS
import matplotlib
import sys
import numpy as np
import time as timing

class mainWindow(QtWidgets.QMainWindow):

    def __init__(self):
        super(mainWindow, self).__init__()
        self.ui = mainUI.Ui_MainWindow()
        self.ui.setupUi(self)
        self.data = []

    def launchClicked(self):
        v = VS.Vehicle()
        v.load_ldcurve()


        scheme = LaunchSim.ControlScheme()
        launch = LaunchSim.Launch()
        print(self.ui.lv_lineEdit.text(), float(self.ui.la_lineEdit.text()))
        launch.setTakeoff(float(self.ui.lv_lineEdit.text()), float(self.ui.la_lineEdit.text()))

        time = 0
        while time < launch.endtime and launch.fs.sy >=0:
        #for time in range(0, launch.endtime, launch.timestep):
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


            #print('roc=' + str(self.fs.calc_rate_of_Climb()))
            #error = scheme.rateofclimb - self.fs.calc_rate_of_Climb()
            #print('error=' + str(error))
            print(time, launch.fs.sy)

            self.data.append([time, launch.fs.ax, launch.fs.ay, launch.fs.vx, launch.fs.vy, launch.fs.sx, launch.fs.sy,launch.fs.vehicle.thrust])
            self.npdata = np.array(self.data)
            launch.fs.update(launch.timestep)
            time += launch.timestep



        print("launch succesfull")

        self.update_graph()

    def update_graph(self):
        # # update forces graph
        self.ui.widget_F.canvas.ax.clear()
        self.ui.widget_F.canvas.ax.plot(self.npdata[:, 0], self.npdata[:, 7])
        #self.ui.widget_F.canvas.ax.plot(self.npdata[:, 0], self.npdata[:, 2])
        self.ui.widget_F.canvas.ax.legend(('Thrust', 'ay'), loc='upper right')
        self.ui.widget_F.canvas.ax.set_title('Forces')
        self.ui.widget_F.canvas.ax.relim()
        self.ui.widget_F.canvas.ax.autoscale_view()
        self.ui.widget_F.canvas.draw()

        # #update acceleration graph
        self.ui.widget_a.canvas.ax.clear()
        self.ui.widget_a.canvas.ax.plot(self.npdata[:, 0], self.npdata[:, 1])
        self.ui.widget_a.canvas.ax.plot(self.npdata[:, 0], self.npdata[:, 2])
        self.ui.widget_a.canvas.ax.legend(('ax', 'ay'), loc='upper right')
        self.ui.widget_a.canvas.ax.set_title('Acceleration')
        self.ui.widget_a.canvas.ax.relim()
        self.ui.widget_a.canvas.ax.autoscale_view()
        self.ui.widget_a.canvas.draw()

        # # update velocity graph
        self.ui.widget_v.canvas.ax.clear()
        self.ui.widget_v.canvas.ax.plot(self.npdata[:, 0], self.npdata[:, 3])
        self.ui.widget_v.canvas.ax.plot(self.npdata[:, 0], self.npdata[:, 4])
        self.ui.widget_v.canvas.ax.legend(('ax', 'ay'), loc='upper right')
        self.ui.widget_v.canvas.ax.set_title('Velocity')
        self.ui.widget_v.canvas.ax.relim()
        self.ui.widget_v.canvas.ax.autoscale_view()
        self.ui.widget_v.canvas.draw()
        # # update displacement graph
        self.ui.widget_s.canvas.ax.clear()
        self.ui.widget_s.canvas.ax.plot(self.npdata[:, 0], self.npdata[:, 5])
        self.ui.widget_s.canvas.ax.plot(self.npdata[:, 0], self.npdata[:, 6])
        self.ui.widget_s.canvas.ax.legend(('ax', 'ay'), loc='upper right')
        self.ui.widget_s.canvas.ax.set_title('Displacement')
        self.ui.widget_s.canvas.ax.relim()
        self.ui.widget_s.canvas.ax.autoscale_view()
        self.ui.widget_s.canvas.draw()
        # update trajectory
        self.ui.Trajectory_widget.canvas.ax.clear()
        self.ui.Trajectory_widget.canvas.ax.plot(self.npdata[:, 5], self.npdata[:, 6])
        self.ui.Trajectory_widget.canvas.ax.set_title('Trajectory')
        self.ui.Trajectory_widget.canvas.ax.relim()
        self.ui.Trajectory_widget.canvas.ax.autoscale_view()
        self.ui.Trajectory_widget.canvas.draw()



app = QtWidgets.QApplication([])

application = mainWindow()

application.show()

sys.exit(app.exec())