
# this function returns the RK4 for a given function and guess timestep

def RK4(function,inital,timestep):
    h= timestep
    yn = inital

    k1 = h*function(0,kn)
    k2 = h*function(0 + h/2,kn + k1/2)
    k3 = h*function(h + h/2,kn +k2/2)
    k4 = h*function(h , kn + k3)

    yn1 = yn + 1 / 6 * (k1 + 2 * k2 + 2 * k3 + k4)
    return yn1