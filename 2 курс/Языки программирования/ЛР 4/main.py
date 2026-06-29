import ctypes
import numpy as np
import matplotlib.pyplot as plt

lib_name = "CollatzLib.dll"

try:
    lib = ctypes.CDLL(f"./{lib_name}")
except OSError as e:
    exit(1)

lib.GenerateCollatzData.argtypes = [
    ctypes.POINTER(ctypes.c_int32),
    ctypes.c_int32,
    ctypes.c_int32,
    ctypes.c_double,
    ctypes.c_double,
    ctypes.c_double,
    ctypes.c_double,
    ctypes.c_int32
]


def generate_fractal(width, height, x_range, y_range, max_iter):
    data = np.zeros((height, width), dtype=np.int32)

    data_ptr = data.ctypes.data_as(ctypes.POINTER(ctypes.c_int32))

    lib.GenerateCollatzData(
        data_ptr,
        width,
        height,
        x_range[0], x_range[1],
        y_range[0], y_range[1],
        max_iter
    )

    return data

WIDTH = 800
HEIGHT = 600
MAX_ITER = 64

X_MIN, X_MAX = -2.5, 1.5
Y_MIN, Y_MAX = -1.5, 1.5

fractal_data = generate_fractal(WIDTH, HEIGHT, (X_MIN, X_MAX), (Y_MIN, Y_MAX), MAX_ITER)
plt.figure(figsize=(10, 8), dpi=100)

plt.imshow(fractal_data, extent=[X_MIN, X_MAX, Y_MIN, Y_MAX], cmap='twilight_shifted', origin='lower')

plt.colorbar(label='Iterations')
plt.title('Collatz fractal')
plt.xlabel('Re(z)')
plt.ylabel('Im(z)')

plt.tight_layout()
plt.show()