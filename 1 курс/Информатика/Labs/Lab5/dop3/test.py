import numpy as np
import matplotlib.pyplot as plt

x = np.linspace(-10, 10, 1000)
y = -x**2 + x

plt.plot(x, y, label="y = -x^2 + x")
plt.title("Парабола")
plt.xlabel("x")
plt.ylabel("y")
plt.grid()
plt.show()
