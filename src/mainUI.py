# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'mainui.ui'
#
# Created by: PyQt5 UI code generator 5.6
#
# WARNING! All changes made in this file will be lost!

from PyQt5 import QtCore, QtGui, QtWidgets

class Ui_MainWindow(object):
    def setupUi(self, MainWindow):
        MainWindow.setObjectName("MainWindow")
        MainWindow.resize(1665, 1587)
        self.centralwidget = QtWidgets.QWidget(MainWindow)
        sizePolicy = QtWidgets.QSizePolicy(QtWidgets.QSizePolicy.Expanding, QtWidgets.QSizePolicy.Expanding)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.centralwidget.sizePolicy().hasHeightForWidth())
        self.centralwidget.setSizePolicy(sizePolicy)
        self.centralwidget.setObjectName("centralwidget")
        self.gridLayout = QtWidgets.QGridLayout(self.centralwidget)
        self.gridLayout.setObjectName("gridLayout")
        self.TabWidget = QtWidgets.QTabWidget(self.centralwidget)
        self.TabWidget.setObjectName("TabWidget")
        self.LaunchTab = QtWidgets.QWidget()
        self.LaunchTab.setObjectName("LaunchTab")
        self.gridLayout_3 = QtWidgets.QGridLayout(self.LaunchTab)
        self.gridLayout_3.setContentsMargins(0, 0, 0, 0)
        self.gridLayout_3.setObjectName("gridLayout_3")
        self.verticalLayout = QtWidgets.QVBoxLayout()
        self.verticalLayout.setContentsMargins(5, 5, 5, 5)
        self.verticalLayout.setObjectName("verticalLayout")
        self.widget_F = MplWidget(self.LaunchTab)
        sizePolicy = QtWidgets.QSizePolicy(QtWidgets.QSizePolicy.Expanding, QtWidgets.QSizePolicy.Expanding)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.widget_F.sizePolicy().hasHeightForWidth())
        self.widget_F.setSizePolicy(sizePolicy)
        self.widget_F.setObjectName("widget_F")
        self.verticalLayout.addWidget(self.widget_F)
        self.widget_a = MplWidget(self.LaunchTab)
        sizePolicy = QtWidgets.QSizePolicy(QtWidgets.QSizePolicy.Expanding, QtWidgets.QSizePolicy.Expanding)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.widget_a.sizePolicy().hasHeightForWidth())
        self.widget_a.setSizePolicy(sizePolicy)
        self.widget_a.setObjectName("widget_a")
        self.verticalLayout.addWidget(self.widget_a)
        self.widget_v = MplWidget(self.LaunchTab)
        sizePolicy = QtWidgets.QSizePolicy(QtWidgets.QSizePolicy.Expanding, QtWidgets.QSizePolicy.Expanding)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.widget_v.sizePolicy().hasHeightForWidth())
        self.widget_v.setSizePolicy(sizePolicy)
        self.widget_v.setObjectName("widget_v")
        self.verticalLayout.addWidget(self.widget_v)
        self.widget_s = MplWidget(self.LaunchTab)
        sizePolicy = QtWidgets.QSizePolicy(QtWidgets.QSizePolicy.Expanding, QtWidgets.QSizePolicy.Expanding)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.widget_s.sizePolicy().hasHeightForWidth())
        self.widget_s.setSizePolicy(sizePolicy)
        self.widget_s.setObjectName("widget_s")
        self.verticalLayout.addWidget(self.widget_s)
        self.horizontalLayout_3 = QtWidgets.QHBoxLayout()
        self.horizontalLayout_3.setSizeConstraint(QtWidgets.QLayout.SetMinimumSize)
        self.horizontalLayout_3.setContentsMargins(5, 5, 5, 5)
        self.horizontalLayout_3.setObjectName("horizontalLayout_3")
        self.gridLayout_6 = QtWidgets.QGridLayout()
        self.gridLayout_6.setContentsMargins(10, 5, 10, 5)
        self.gridLayout_6.setObjectName("gridLayout_6")
        self.label_10 = QtWidgets.QLabel(self.LaunchTab)
        self.label_10.setObjectName("label_10")
        self.gridLayout_6.addWidget(self.label_10, 3, 0, 1, 1)
        self.label_8 = QtWidgets.QLabel(self.LaunchTab)
        self.label_8.setObjectName("label_8")
        self.gridLayout_6.addWidget(self.label_8, 1, 0, 1, 1)
        self.label_7 = QtWidgets.QLabel(self.LaunchTab)
        self.label_7.setObjectName("label_7")
        self.gridLayout_6.addWidget(self.label_7, 0, 0, 1, 1)
        self.label_9 = QtWidgets.QLabel(self.LaunchTab)
        self.label_9.setObjectName("label_9")
        self.gridLayout_6.addWidget(self.label_9, 2, 0, 1, 1)
        self.lcdNumber = QtWidgets.QLCDNumber(self.LaunchTab)
        self.lcdNumber.setObjectName("lcdNumber")
        self.gridLayout_6.addWidget(self.lcdNumber, 0, 1, 1, 1)
        self.label_11 = QtWidgets.QLabel(self.LaunchTab)
        self.label_11.setObjectName("label_11")
        self.gridLayout_6.addWidget(self.label_11, 4, 0, 1, 1)
        self.lcdNumber_2 = QtWidgets.QLCDNumber(self.LaunchTab)
        self.lcdNumber_2.setObjectName("lcdNumber_2")
        self.gridLayout_6.addWidget(self.lcdNumber_2, 4, 1, 1, 1)
        self.lcdNumber_3 = QtWidgets.QLCDNumber(self.LaunchTab)
        self.lcdNumber_3.setObjectName("lcdNumber_3")
        self.gridLayout_6.addWidget(self.lcdNumber_3, 3, 1, 1, 1)
        self.lcdNumber_4 = QtWidgets.QLCDNumber(self.LaunchTab)
        self.lcdNumber_4.setObjectName("lcdNumber_4")
        self.gridLayout_6.addWidget(self.lcdNumber_4, 2, 1, 1, 1)
        self.lcdNumber_5 = QtWidgets.QLCDNumber(self.LaunchTab)
        self.lcdNumber_5.setObjectName("lcdNumber_5")
        self.gridLayout_6.addWidget(self.lcdNumber_5, 1, 1, 1, 1)
        self.horizontalLayout_3.addLayout(self.gridLayout_6)
        self.Trajectory_widget = MplWidget(self.LaunchTab)
        sizePolicy = QtWidgets.QSizePolicy(QtWidgets.QSizePolicy.Expanding, QtWidgets.QSizePolicy.MinimumExpanding)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.Trajectory_widget.sizePolicy().hasHeightForWidth())
        self.Trajectory_widget.setSizePolicy(sizePolicy)
        self.Trajectory_widget.setMinimumSize(QtCore.QSize(0, 200))
        self.Trajectory_widget.setObjectName("Trajectory_widget")
        self.horizontalLayout_3.addWidget(self.Trajectory_widget)
        self.gridLayout_2 = QtWidgets.QGridLayout()
        self.gridLayout_2.setObjectName("gridLayout_2")
        self.label_6 = QtWidgets.QLabel(self.LaunchTab)
        self.label_6.setObjectName("label_6")
        self.gridLayout_2.addWidget(self.label_6, 5, 0, 1, 1)
        self.lineEdit_3 = QtWidgets.QLineEdit(self.LaunchTab)
        self.lineEdit_3.setObjectName("lineEdit_3")
        self.gridLayout_2.addWidget(self.lineEdit_3, 5, 1, 1, 2)
        self.label = QtWidgets.QLabel(self.LaunchTab)
        self.label.setObjectName("label")
        self.gridLayout_2.addWidget(self.label, 1, 0, 1, 1)
        self.label_3 = QtWidgets.QLabel(self.LaunchTab)
        self.label_3.setObjectName("label_3")
        self.gridLayout_2.addWidget(self.label_3, 3, 0, 1, 1)
        self.label_2 = QtWidgets.QLabel(self.LaunchTab)
        self.label_2.setObjectName("label_2")
        self.gridLayout_2.addWidget(self.label_2, 2, 0, 1, 1)
        self.doubleSpinBox = QtWidgets.QDoubleSpinBox(self.LaunchTab)
        self.doubleSpinBox.setSuffix("")
        self.doubleSpinBox.setDecimals(0)
        self.doubleSpinBox.setMinimum(100.0)
        self.doubleSpinBox.setMaximum(10000.0)
        self.doubleSpinBox.setSingleStep(100.0)
        self.doubleSpinBox.setObjectName("doubleSpinBox")
        self.gridLayout_2.addWidget(self.doubleSpinBox, 4, 1, 1, 1)
        self.label_4 = QtWidgets.QLabel(self.LaunchTab)
        self.label_4.setObjectName("label_4")
        self.gridLayout_2.addWidget(self.label_4, 4, 0, 1, 1)
        self.label_5 = QtWidgets.QLabel(self.LaunchTab)
        self.label_5.setObjectName("label_5")
        self.gridLayout_2.addWidget(self.label_5, 4, 2, 1, 1)
        self.la_lineEdit = QtWidgets.QLineEdit(self.LaunchTab)
        sizePolicy = QtWidgets.QSizePolicy(QtWidgets.QSizePolicy.Minimum, QtWidgets.QSizePolicy.Fixed)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.la_lineEdit.sizePolicy().hasHeightForWidth())
        self.la_lineEdit.setSizePolicy(sizePolicy)
        self.la_lineEdit.setObjectName("la_lineEdit")
        self.gridLayout_2.addWidget(self.la_lineEdit, 3, 1, 1, 2)
        self.lv_lineEdit = QtWidgets.QLineEdit(self.LaunchTab)
        sizePolicy = QtWidgets.QSizePolicy(QtWidgets.QSizePolicy.Fixed, QtWidgets.QSizePolicy.Fixed)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.lv_lineEdit.sizePolicy().hasHeightForWidth())
        self.lv_lineEdit.setSizePolicy(sizePolicy)
        self.lv_lineEdit.setInputMethodHints(QtCore.Qt.ImhDigitsOnly|QtCore.Qt.ImhPreferNumbers)
        self.lv_lineEdit.setObjectName("lv_lineEdit")
        self.gridLayout_2.addWidget(self.lv_lineEdit, 2, 1, 1, 2)
        self.pushButton = QtWidgets.QPushButton(self.LaunchTab)
        sizePolicy = QtWidgets.QSizePolicy(QtWidgets.QSizePolicy.Fixed, QtWidgets.QSizePolicy.Fixed)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.pushButton.sizePolicy().hasHeightForWidth())
        self.pushButton.setSizePolicy(sizePolicy)
        self.pushButton.setObjectName("pushButton")
        self.gridLayout_2.addWidget(self.pushButton, 1, 1, 1, 2)
        self.polarWidget = QtWidgets.QWidget(self.LaunchTab)
        sizePolicy = QtWidgets.QSizePolicy(QtWidgets.QSizePolicy.MinimumExpanding, QtWidgets.QSizePolicy.MinimumExpanding)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.polarWidget.sizePolicy().hasHeightForWidth())
        self.polarWidget.setSizePolicy(sizePolicy)
        self.polarWidget.setMinimumSize(QtCore.QSize(300, 300))
        self.polarWidget.setObjectName("polarWidget")
        self.gridLayout_2.addWidget(self.polarWidget, 0, 0, 1, 3)
        self.horizontalLayout_3.addLayout(self.gridLayout_2)
        self.verticalLayout.addLayout(self.horizontalLayout_3)
        self.gridLayout_3.addLayout(self.verticalLayout, 0, 0, 1, 1)
        self.TabWidget.addTab(self.LaunchTab, "")
        self.MotorTab = QtWidgets.QWidget()
        self.MotorTab.setObjectName("MotorTab")
        self.gridLayout_4 = QtWidgets.QGridLayout(self.MotorTab)
        self.gridLayout_4.setContentsMargins(0, 0, 0, 0)
        self.gridLayout_4.setObjectName("gridLayout_4")
        self.widget_6 = MplWidget(self.MotorTab)
        sizePolicy = QtWidgets.QSizePolicy(QtWidgets.QSizePolicy.Expanding, QtWidgets.QSizePolicy.Expanding)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.widget_6.sizePolicy().hasHeightForWidth())
        self.widget_6.setSizePolicy(sizePolicy)
        self.widget_6.setObjectName("widget_6")
        self.gridLayout_4.addWidget(self.widget_6, 0, 0, 1, 2)
        self.formLayout = QtWidgets.QFormLayout()
        self.formLayout.setObjectName("formLayout")
        self.oxidiserLabel = QtWidgets.QLabel(self.MotorTab)
        self.oxidiserLabel.setObjectName("oxidiserLabel")
        self.formLayout.setWidget(0, QtWidgets.QFormLayout.LabelRole, self.oxidiserLabel)
        self.oxidiserComboBox = QtWidgets.QComboBox(self.MotorTab)
        self.oxidiserComboBox.setObjectName("oxidiserComboBox")
        self.formLayout.setWidget(0, QtWidgets.QFormLayout.FieldRole, self.oxidiserComboBox)
        self.fuelLabel = QtWidgets.QLabel(self.MotorTab)
        self.fuelLabel.setObjectName("fuelLabel")
        self.formLayout.setWidget(1, QtWidgets.QFormLayout.LabelRole, self.fuelLabel)
        self.fuelComboBox = QtWidgets.QComboBox(self.MotorTab)
        self.fuelComboBox.setObjectName("fuelComboBox")
        self.formLayout.setWidget(1, QtWidgets.QFormLayout.FieldRole, self.fuelComboBox)
        self.fullThrottleOxidiserMassFlowRateLabel = QtWidgets.QLabel(self.MotorTab)
        self.fullThrottleOxidiserMassFlowRateLabel.setObjectName("fullThrottleOxidiserMassFlowRateLabel")
        self.formLayout.setWidget(2, QtWidgets.QFormLayout.LabelRole, self.fullThrottleOxidiserMassFlowRateLabel)
        self.fullThrottleOxidiserMassFlowRateLineEdit = QtWidgets.QLineEdit(self.MotorTab)
        self.fullThrottleOxidiserMassFlowRateLineEdit.setObjectName("fullThrottleOxidiserMassFlowRateLineEdit")
        self.formLayout.setWidget(2, QtWidgets.QFormLayout.FieldRole, self.fullThrottleOxidiserMassFlowRateLineEdit)
        self.fullThrottleChamberPressureLabel = QtWidgets.QLabel(self.MotorTab)
        self.fullThrottleChamberPressureLabel.setObjectName("fullThrottleChamberPressureLabel")
        self.formLayout.setWidget(3, QtWidgets.QFormLayout.LabelRole, self.fullThrottleChamberPressureLabel)
        self.fullThrottleChamberPressureLineEdit = QtWidgets.QLineEdit(self.MotorTab)
        self.fullThrottleChamberPressureLineEdit.setObjectName("fullThrottleChamberPressureLineEdit")
        self.formLayout.setWidget(3, QtWidgets.QFormLayout.FieldRole, self.fullThrottleChamberPressureLineEdit)
        self.throatDiameterLabel = QtWidgets.QLabel(self.MotorTab)
        self.throatDiameterLabel.setObjectName("throatDiameterLabel")
        self.formLayout.setWidget(4, QtWidgets.QFormLayout.LabelRole, self.throatDiameterLabel)
        self.throatDiameterLineEdit = QtWidgets.QLineEdit(self.MotorTab)
        self.throatDiameterLineEdit.setObjectName("throatDiameterLineEdit")
        self.formLayout.setWidget(4, QtWidgets.QFormLayout.FieldRole, self.throatDiameterLineEdit)
        self.expansionRationLabel = QtWidgets.QLabel(self.MotorTab)
        self.expansionRationLabel.setObjectName("expansionRationLabel")
        self.formLayout.setWidget(5, QtWidgets.QFormLayout.LabelRole, self.expansionRationLabel)
        self.expansionRationLineEdit = QtWidgets.QLineEdit(self.MotorTab)
        self.expansionRationLineEdit.setObjectName("expansionRationLineEdit")
        self.formLayout.setWidget(5, QtWidgets.QFormLayout.FieldRole, self.expansionRationLineEdit)
        self.nozzleExitDiameterLabel = QtWidgets.QLabel(self.MotorTab)
        self.nozzleExitDiameterLabel.setObjectName("nozzleExitDiameterLabel")
        self.formLayout.setWidget(6, QtWidgets.QFormLayout.LabelRole, self.nozzleExitDiameterLabel)
        self.nozzleExitDiameterLineEdit = QtWidgets.QLineEdit(self.MotorTab)
        self.nozzleExitDiameterLineEdit.setObjectName("nozzleExitDiameterLineEdit")
        self.formLayout.setWidget(6, QtWidgets.QFormLayout.FieldRole, self.nozzleExitDiameterLineEdit)
        self.fuelGrainMassLabel = QtWidgets.QLabel(self.MotorTab)
        self.fuelGrainMassLabel.setObjectName("fuelGrainMassLabel")
        self.formLayout.setWidget(7, QtWidgets.QFormLayout.LabelRole, self.fuelGrainMassLabel)
        self.fuelGrainMassLineEdit = QtWidgets.QLineEdit(self.MotorTab)
        self.fuelGrainMassLineEdit.setObjectName("fuelGrainMassLineEdit")
        self.formLayout.setWidget(7, QtWidgets.QFormLayout.FieldRole, self.fuelGrainMassLineEdit)
        self.motorDryMassLabel = QtWidgets.QLabel(self.MotorTab)
        self.motorDryMassLabel.setObjectName("motorDryMassLabel")
        self.formLayout.setWidget(8, QtWidgets.QFormLayout.LabelRole, self.motorDryMassLabel)
        self.motorDryMassLineEdit = QtWidgets.QLineEdit(self.MotorTab)
        self.motorDryMassLineEdit.setObjectName("motorDryMassLineEdit")
        self.formLayout.setWidget(8, QtWidgets.QFormLayout.FieldRole, self.motorDryMassLineEdit)
        self.motorEfficencyLabel = QtWidgets.QLabel(self.MotorTab)
        self.motorEfficencyLabel.setObjectName("motorEfficencyLabel")
        self.formLayout.setWidget(9, QtWidgets.QFormLayout.LabelRole, self.motorEfficencyLabel)
        self.motorEfficencyLineEdit = QtWidgets.QLineEdit(self.MotorTab)
        self.motorEfficencyLineEdit.setObjectName("motorEfficencyLineEdit")
        self.formLayout.setWidget(9, QtWidgets.QFormLayout.FieldRole, self.motorEfficencyLineEdit)
        self.fullThrottleThrustLabel = QtWidgets.QLabel(self.MotorTab)
        self.fullThrottleThrustLabel.setObjectName("fullThrottleThrustLabel")
        self.formLayout.setWidget(10, QtWidgets.QFormLayout.LabelRole, self.fullThrottleThrustLabel)
        self.fullThrottleThrustLineEdit = QtWidgets.QLineEdit(self.MotorTab)
        self.fullThrottleThrustLineEdit.setObjectName("fullThrottleThrustLineEdit")
        self.formLayout.setWidget(10, QtWidgets.QFormLayout.FieldRole, self.fullThrottleThrustLineEdit)
        self.pushButton_2 = QtWidgets.QPushButton(self.MotorTab)
        self.pushButton_2.setObjectName("pushButton_2")
        self.formLayout.setWidget(11, QtWidgets.QFormLayout.FieldRole, self.pushButton_2)
        self.gridLayout_4.addLayout(self.formLayout, 1, 0, 1, 1)
        self.formLayout_2 = QtWidgets.QFormLayout()
        self.formLayout_2.setObjectName("formLayout_2")
        self.oxidiserLabel_2 = QtWidgets.QLabel(self.MotorTab)
        self.oxidiserLabel_2.setObjectName("oxidiserLabel_2")
        self.formLayout_2.setWidget(0, QtWidgets.QFormLayout.LabelRole, self.oxidiserLabel_2)
        self.fuelLabel_2 = QtWidgets.QLabel(self.MotorTab)
        self.fuelLabel_2.setObjectName("fuelLabel_2")
        self.formLayout_2.setWidget(1, QtWidgets.QFormLayout.LabelRole, self.fuelLabel_2)
        self.fuelComboBox_2 = QtWidgets.QComboBox(self.MotorTab)
        self.fuelComboBox_2.setObjectName("fuelComboBox_2")
        self.formLayout_2.setWidget(1, QtWidgets.QFormLayout.FieldRole, self.fuelComboBox_2)
        self.fullThrottleOxidiserMassFlowRateLabel_2 = QtWidgets.QLabel(self.MotorTab)
        self.fullThrottleOxidiserMassFlowRateLabel_2.setObjectName("fullThrottleOxidiserMassFlowRateLabel_2")
        self.formLayout_2.setWidget(2, QtWidgets.QFormLayout.LabelRole, self.fullThrottleOxidiserMassFlowRateLabel_2)
        self.fullThrottleOxidiserMassFlowRateLineEdit_2 = QtWidgets.QLineEdit(self.MotorTab)
        self.fullThrottleOxidiserMassFlowRateLineEdit_2.setObjectName("fullThrottleOxidiserMassFlowRateLineEdit_2")
        self.formLayout_2.setWidget(2, QtWidgets.QFormLayout.FieldRole, self.fullThrottleOxidiserMassFlowRateLineEdit_2)
        self.fullThrottleChamberPressureLabel_2 = QtWidgets.QLabel(self.MotorTab)
        self.fullThrottleChamberPressureLabel_2.setObjectName("fullThrottleChamberPressureLabel_2")
        self.formLayout_2.setWidget(3, QtWidgets.QFormLayout.LabelRole, self.fullThrottleChamberPressureLabel_2)
        self.fullThrottleChamberPressureLineEdit_2 = QtWidgets.QLineEdit(self.MotorTab)
        self.fullThrottleChamberPressureLineEdit_2.setObjectName("fullThrottleChamberPressureLineEdit_2")
        self.formLayout_2.setWidget(3, QtWidgets.QFormLayout.FieldRole, self.fullThrottleChamberPressureLineEdit_2)
        self.throatDiameterLabel_2 = QtWidgets.QLabel(self.MotorTab)
        self.throatDiameterLabel_2.setObjectName("throatDiameterLabel_2")
        self.formLayout_2.setWidget(4, QtWidgets.QFormLayout.LabelRole, self.throatDiameterLabel_2)
        self.throatDiameterLineEdit_2 = QtWidgets.QLineEdit(self.MotorTab)
        self.throatDiameterLineEdit_2.setObjectName("throatDiameterLineEdit_2")
        self.formLayout_2.setWidget(4, QtWidgets.QFormLayout.FieldRole, self.throatDiameterLineEdit_2)
        self.expansionRationLabel_2 = QtWidgets.QLabel(self.MotorTab)
        self.expansionRationLabel_2.setObjectName("expansionRationLabel_2")
        self.formLayout_2.setWidget(5, QtWidgets.QFormLayout.LabelRole, self.expansionRationLabel_2)
        self.expansionRationLineEdit_2 = QtWidgets.QLineEdit(self.MotorTab)
        self.expansionRationLineEdit_2.setObjectName("expansionRationLineEdit_2")
        self.formLayout_2.setWidget(5, QtWidgets.QFormLayout.FieldRole, self.expansionRationLineEdit_2)
        self.nozzleExitDiameterLabel_2 = QtWidgets.QLabel(self.MotorTab)
        self.nozzleExitDiameterLabel_2.setObjectName("nozzleExitDiameterLabel_2")
        self.formLayout_2.setWidget(6, QtWidgets.QFormLayout.LabelRole, self.nozzleExitDiameterLabel_2)
        self.nozzleExitDiameterLineEdit_2 = QtWidgets.QLineEdit(self.MotorTab)
        self.nozzleExitDiameterLineEdit_2.setObjectName("nozzleExitDiameterLineEdit_2")
        self.formLayout_2.setWidget(6, QtWidgets.QFormLayout.FieldRole, self.nozzleExitDiameterLineEdit_2)
        self.fuelGrainMassLabel_2 = QtWidgets.QLabel(self.MotorTab)
        self.fuelGrainMassLabel_2.setObjectName("fuelGrainMassLabel_2")
        self.formLayout_2.setWidget(7, QtWidgets.QFormLayout.LabelRole, self.fuelGrainMassLabel_2)
        self.fuelGrainMassLineEdit_2 = QtWidgets.QLineEdit(self.MotorTab)
        self.fuelGrainMassLineEdit_2.setObjectName("fuelGrainMassLineEdit_2")
        self.formLayout_2.setWidget(7, QtWidgets.QFormLayout.FieldRole, self.fuelGrainMassLineEdit_2)
        self.motorDryMassLabel_2 = QtWidgets.QLabel(self.MotorTab)
        self.motorDryMassLabel_2.setObjectName("motorDryMassLabel_2")
        self.formLayout_2.setWidget(8, QtWidgets.QFormLayout.LabelRole, self.motorDryMassLabel_2)
        self.motorDryMassLineEdit_2 = QtWidgets.QLineEdit(self.MotorTab)
        self.motorDryMassLineEdit_2.setObjectName("motorDryMassLineEdit_2")
        self.formLayout_2.setWidget(8, QtWidgets.QFormLayout.FieldRole, self.motorDryMassLineEdit_2)
        self.oxidiserComboBox_2 = QtWidgets.QComboBox(self.MotorTab)
        self.oxidiserComboBox_2.setObjectName("oxidiserComboBox_2")
        self.formLayout_2.setWidget(0, QtWidgets.QFormLayout.FieldRole, self.oxidiserComboBox_2)
        self.gridLayout_4.addLayout(self.formLayout_2, 1, 1, 1, 1)
        self.TabWidget.addTab(self.MotorTab, "")
        self.VehicleTab = QtWidgets.QWidget()
        self.VehicleTab.setObjectName("VehicleTab")
        self.gridLayout_5 = QtWidgets.QGridLayout(self.VehicleTab)
        self.gridLayout_5.setContentsMargins(0, 0, 0, 0)
        self.gridLayout_5.setObjectName("gridLayout_5")
        self.tableWidget_2 = QtWidgets.QTableWidget(self.VehicleTab)
        self.tableWidget_2.setObjectName("tableWidget_2")
        self.tableWidget_2.setColumnCount(3)
        self.tableWidget_2.setRowCount(0)
        item = QtWidgets.QTableWidgetItem()
        self.tableWidget_2.setHorizontalHeaderItem(0, item)
        item = QtWidgets.QTableWidgetItem()
        self.tableWidget_2.setHorizontalHeaderItem(1, item)
        item = QtWidgets.QTableWidgetItem()
        self.tableWidget_2.setHorizontalHeaderItem(2, item)
        self.gridLayout_5.addWidget(self.tableWidget_2, 1, 0, 1, 1)
        self.tableWidget = QtWidgets.QTableWidget(self.VehicleTab)
        sizePolicy = QtWidgets.QSizePolicy(QtWidgets.QSizePolicy.Expanding, QtWidgets.QSizePolicy.Preferred)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.tableWidget.sizePolicy().hasHeightForWidth())
        self.tableWidget.setSizePolicy(sizePolicy)
        self.tableWidget.setMinimumSize(QtCore.QSize(600, 0))
        self.tableWidget.setObjectName("tableWidget")
        self.tableWidget.setColumnCount(3)
        self.tableWidget.setRowCount(0)
        item = QtWidgets.QTableWidgetItem()
        self.tableWidget.setHorizontalHeaderItem(0, item)
        item = QtWidgets.QTableWidgetItem()
        self.tableWidget.setHorizontalHeaderItem(1, item)
        item = QtWidgets.QTableWidgetItem()
        self.tableWidget.setHorizontalHeaderItem(2, item)
        self.gridLayout_5.addWidget(self.tableWidget, 1, 1, 1, 1)
        self.horizontalLayout = QtWidgets.QHBoxLayout()
        self.horizontalLayout.setObjectName("horizontalLayout")
        self.widget = MplWidget(self.VehicleTab)
        sizePolicy = QtWidgets.QSizePolicy(QtWidgets.QSizePolicy.Expanding, QtWidgets.QSizePolicy.Preferred)
        sizePolicy.setHorizontalStretch(0)
        sizePolicy.setVerticalStretch(0)
        sizePolicy.setHeightForWidth(self.widget.sizePolicy().hasHeightForWidth())
        self.widget.setSizePolicy(sizePolicy)
        self.widget.setMinimumSize(QtCore.QSize(700, 0))
        self.widget.setObjectName("widget")
        self.horizontalLayout.addWidget(self.widget)
        self.formLayout_3 = QtWidgets.QFormLayout()
        self.formLayout_3.setObjectName("formLayout_3")
        self.vehicleMassLabel = QtWidgets.QLabel(self.VehicleTab)
        self.vehicleMassLabel.setObjectName("vehicleMassLabel")
        self.formLayout_3.setWidget(0, QtWidgets.QFormLayout.LabelRole, self.vehicleMassLabel)
        self.vehicleMassLineEdit = QtWidgets.QLineEdit(self.VehicleTab)
        self.vehicleMassLineEdit.setObjectName("vehicleMassLineEdit")
        self.formLayout_3.setWidget(0, QtWidgets.QFormLayout.FieldRole, self.vehicleMassLineEdit)
        self.vehicleLengthLabel = QtWidgets.QLabel(self.VehicleTab)
        self.vehicleLengthLabel.setObjectName("vehicleLengthLabel")
        self.formLayout_3.setWidget(1, QtWidgets.QFormLayout.LabelRole, self.vehicleLengthLabel)
        self.vehicleLengthLineEdit = QtWidgets.QLineEdit(self.VehicleTab)
        self.vehicleLengthLineEdit.setObjectName("vehicleLengthLineEdit")
        self.formLayout_3.setWidget(1, QtWidgets.QFormLayout.FieldRole, self.vehicleLengthLineEdit)
        self.wingAreaLabel = QtWidgets.QLabel(self.VehicleTab)
        self.wingAreaLabel.setObjectName("wingAreaLabel")
        self.formLayout_3.setWidget(2, QtWidgets.QFormLayout.LabelRole, self.wingAreaLabel)
        self.wingAreaLineEdit = QtWidgets.QLineEdit(self.VehicleTab)
        self.wingAreaLineEdit.setObjectName("wingAreaLineEdit")
        self.formLayout_3.setWidget(2, QtWidgets.QFormLayout.FieldRole, self.wingAreaLineEdit)
        self.frontalCrossSectionLabel = QtWidgets.QLabel(self.VehicleTab)
        self.frontalCrossSectionLabel.setObjectName("frontalCrossSectionLabel")
        self.formLayout_3.setWidget(3, QtWidgets.QFormLayout.LabelRole, self.frontalCrossSectionLabel)
        self.frontalCrossSectionLineEdit = QtWidgets.QLineEdit(self.VehicleTab)
        self.frontalCrossSectionLineEdit.setObjectName("frontalCrossSectionLineEdit")
        self.formLayout_3.setWidget(3, QtWidgets.QFormLayout.FieldRole, self.frontalCrossSectionLineEdit)
        self.alphaAngleLabel = QtWidgets.QLabel(self.VehicleTab)
        self.alphaAngleLabel.setObjectName("alphaAngleLabel")
        self.formLayout_3.setWidget(4, QtWidgets.QFormLayout.LabelRole, self.alphaAngleLabel)
        self.alphaAngleLineEdit = QtWidgets.QLineEdit(self.VehicleTab)
        self.alphaAngleLineEdit.setObjectName("alphaAngleLineEdit")
        self.formLayout_3.setWidget(4, QtWidgets.QFormLayout.FieldRole, self.alphaAngleLineEdit)
        self.horizontalLayout.addLayout(self.formLayout_3)
        self.gridLayout_5.addLayout(self.horizontalLayout, 0, 0, 1, 2)
        self.TabWidget.addTab(self.VehicleTab, "")
        self.ControlTab = QtWidgets.QWidget()
        self.ControlTab.setObjectName("ControlTab")
        self.TabWidget.addTab(self.ControlTab, "")
        self.gridLayout.addWidget(self.TabWidget, 0, 0, 1, 1)
        MainWindow.setCentralWidget(self.centralwidget)
        self.menubar = QtWidgets.QMenuBar(MainWindow)
        self.menubar.setGeometry(QtCore.QRect(0, 0, 1665, 38))
        self.menubar.setObjectName("menubar")
        MainWindow.setMenuBar(self.menubar)
        self.statusbar = QtWidgets.QStatusBar(MainWindow)
        self.statusbar.setObjectName("statusbar")
        MainWindow.setStatusBar(self.statusbar)

        self.retranslateUi(MainWindow)
        self.TabWidget.setCurrentIndex(0)
        self.pushButton.clicked.connect(MainWindow.launchClicked)
        QtCore.QMetaObject.connectSlotsByName(MainWindow)

    def retranslateUi(self, MainWindow):
        _translate = QtCore.QCoreApplication.translate
        MainWindow.setWindowTitle(_translate("MainWindow", "MainWindow"))
        self.label_10.setText(_translate("MainWindow", "Delta V Budget"))
        self.label_8.setText(_translate("MainWindow", "Total Thrust"))
        self.label_7.setText(_translate("MainWindow", "Total Mass"))
        self.label_9.setText(_translate("MainWindow", "Total Drag"))
        self.label_11.setText(_translate("MainWindow", "Remaining Delta V"))
        self.label_6.setText(_translate("MainWindow", "Run Time (s)"))
        self.label.setText(_translate("MainWindow", "TextLabel"))
        self.label_3.setText(_translate("MainWindow", "Launch Angle"))
        self.label_2.setText(_translate("MainWindow", "Initital Launch Velocity"))
        self.label_4.setText(_translate("MainWindow", "Time Step"))
        self.label_5.setText(_translate("MainWindow", "%"))
        self.la_lineEdit.setPlaceholderText(_translate("MainWindow", "0"))
        self.lv_lineEdit.setPlaceholderText(_translate("MainWindow", "0"))
        self.pushButton.setText(_translate("MainWindow", "Launch"))
        self.TabWidget.setTabText(self.TabWidget.indexOf(self.LaunchTab), _translate("MainWindow", "Launch"))
        self.oxidiserLabel.setText(_translate("MainWindow", "Oxidiser"))
        self.fuelLabel.setText(_translate("MainWindow", "Fuel"))
        self.fullThrottleOxidiserMassFlowRateLabel.setText(_translate("MainWindow", "Full Throttle Oxidiser Mass Flow Rate"))
        self.fullThrottleChamberPressureLabel.setText(_translate("MainWindow", "Full Throttle Chamber Pressure"))
        self.throatDiameterLabel.setText(_translate("MainWindow", "Throat Diameter"))
        self.expansionRationLabel.setText(_translate("MainWindow", "Expansion Ratio"))
        self.nozzleExitDiameterLabel.setText(_translate("MainWindow", "Nozzle Exit Diameter"))
        self.fuelGrainMassLabel.setText(_translate("MainWindow", "Fuel Grain Mass"))
        self.motorDryMassLabel.setText(_translate("MainWindow", "Motor Dry Mass"))
        self.motorEfficencyLabel.setText(_translate("MainWindow", "Motor Efficency"))
        self.fullThrottleThrustLabel.setText(_translate("MainWindow", "Full Throttle Thrust"))
        self.pushButton_2.setText(_translate("MainWindow", "PushButton"))
        self.oxidiserLabel_2.setText(_translate("MainWindow", "Oxidiser"))
        self.fuelLabel_2.setText(_translate("MainWindow", "Fuel"))
        self.fullThrottleOxidiserMassFlowRateLabel_2.setText(_translate("MainWindow", "Full Throttle Oxidiser Mass Flow Rate"))
        self.fullThrottleChamberPressureLabel_2.setText(_translate("MainWindow", "Full Throttle Chamber Pressure"))
        self.throatDiameterLabel_2.setText(_translate("MainWindow", "Throat Diameter"))
        self.expansionRationLabel_2.setText(_translate("MainWindow", "Expansion Ratio"))
        self.nozzleExitDiameterLabel_2.setText(_translate("MainWindow", "Nozzle Exit Diameter"))
        self.fuelGrainMassLabel_2.setText(_translate("MainWindow", "Fuel Grain Mass"))
        self.motorDryMassLabel_2.setText(_translate("MainWindow", "Motor Dry Mass"))
        self.TabWidget.setTabText(self.TabWidget.indexOf(self.MotorTab), _translate("MainWindow", "Motor"))
        item = self.tableWidget_2.horizontalHeaderItem(0)
        item.setText(_translate("MainWindow", "New Column"))
        item = self.tableWidget_2.horizontalHeaderItem(1)
        item.setText(_translate("MainWindow", "CL"))
        item = self.tableWidget_2.horizontalHeaderItem(2)
        item.setText(_translate("MainWindow", "CD"))
        item = self.tableWidget.horizontalHeaderItem(0)
        item.setText(_translate("MainWindow", "M"))
        item = self.tableWidget.horizontalHeaderItem(1)
        item.setText(_translate("MainWindow", "CL"))
        item = self.tableWidget.horizontalHeaderItem(2)
        item.setText(_translate("MainWindow", "CD"))
        self.vehicleMassLabel.setText(_translate("MainWindow", "Vehicle Mass"))
        self.vehicleLengthLabel.setText(_translate("MainWindow", "Vehicle Length"))
        self.wingAreaLabel.setText(_translate("MainWindow", "Wing Area"))
        self.frontalCrossSectionLabel.setText(_translate("MainWindow", "Frontal Cross Section"))
        self.alphaAngleLabel.setText(_translate("MainWindow", "Alpha Angle"))
        self.TabWidget.setTabText(self.TabWidget.indexOf(self.VehicleTab), _translate("MainWindow", "Vehicle"))
        self.TabWidget.setTabText(self.TabWidget.indexOf(self.ControlTab), _translate("MainWindow", "Control"))

from mplwidget import MplWidget

if __name__ == "__main__":
    import sys
    app = QtWidgets.QApplication(sys.argv)
    MainWindow = QtWidgets.QMainWindow()
    ui = Ui_MainWindow()
    ui.setupUi(MainWindow)
    MainWindow.show()
    sys.exit(app.exec_())

