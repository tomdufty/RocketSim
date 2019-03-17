# this module has several functions which interpolate atmospehric values based on altitude. Vales are based on
#the lookup table atmotable. Atmotable is based on the International standard atmosphere from 0-30000m and the US
#standard atmosphere from 30000-80000m

import csv


def load_atmosphere():
    atmo = []

    with open('atmotable') as csvDataFile:
        csvReader = list(csv.reader(csvDataFile, delimiter ='\t'))
        # for row in csvReader:
        #     line = []
        #     for value in row:
        #         line.append(value)
        #     atmo.append(line)
        atmo = csvReader
        print("atmosphere loaded")
        return atmo


def get_atmo_value(atmo, altitude, variable):
    if variable == 'p':
        print("interpolating pressure")
        col = 2
    elif variable == 'rho':
        print("interpolating density")
        col = 3
    else:
        print("varibale not found")
    a0 = None
    i = 1
    while float(atmo[i][0]) <= altitude:
        a0 = float(atmo[i][0])
        i = i+1
    p0 = float(atmo[i - 1][col])
    p1 = float(atmo[i][col])
    a1 = float(atmo[i][0])
    print(a0)
    print(a1)
    ratio = (altitude - a0)/(a1 - a0)
    p = ratio*(p1-p0)+p0
    return p

def gravity(altitude):
    g = -3E-06*altitude + 9.8064
    return g

#testing Main
atmo = load_atmosphere()
print(get_atmo_value(atmo, 30000,'rho'))