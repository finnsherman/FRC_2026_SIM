import math
import numpy as np

def generate_table():
    C = .5588
    dZ = 2
    v_Range = 6.7056
    g = 9.81
    result = []
    for v_X in range(0, v_Range / 0.02, 1):
        for v_Y in range(0, v_Range / 0.02, 1):
            for v_Z in range(0, v_Range / 0.02, 1):
                if (v_X + v_Y + v_Z) <= v_Range:
                    continue
                for dXY in range(1, 18.288, 0.1):
                    theta = math.atan2(dZ - C + (g((dXY^2)/((v_X^2) + (v_Y^2))))/2, dXY)
                    phi = math.atan2(v_Y, v_X)
                    result.append((v_X, v_Y, v_Z, dXY, theta, phi))
    print(result)

generate_table()