import matplotlib.pyplot as plt

fig, ax = plt.subplots()

# твой пример данных
ax.plot([1, 2, 3, 4], [1, 4, 2, 3])

# подписи по центру над/под делениями
ax.set_xticks([1, 2, 3, 4])
ax.set_xticklabels([1, 2, 3, 4])
ax.tick_params(axis='x', which='both', labelbottom=True)

# выравнивание подписей по центру относительно деления
ax.set_xticklabels([1, 2, 3, 4], ha='center', fontsize=10)

plt.show()