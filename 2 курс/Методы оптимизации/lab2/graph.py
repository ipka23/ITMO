import numpy as np
import matplotlib.pyplot as plt

def f(x):
    return 2 * np.sin(x) - 1.5 * np.cos(2 * x) + np.sin(3 * x) - 0.5 * np.cos(4 * x)

x = np.linspace(-2, 2, 1000)
y = f(x)


plt.figure(figsize=(10, 6))
plt.plot(x, y, 'b-', linewidth=2, label='f(x) = 2sin(x) - 1.5cos(2x) + sin(3x) - 0.5cos(4x)')
plt.grid(True, alpha=0.3)
plt.axhline(y=0, color='k', linestyle='--', alpha=0.5)
plt.axvline(x=0, color='k', linestyle='--', alpha=0.5)
plt.xlabel('x')
plt.ylabel('f(x)')
plt.title('График функции на промежутке [-2, 2]')
plt.legend()
plt.tight_layout()
plt.show()
