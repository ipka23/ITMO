import matplotlib.pyplot as plt
import pandas as pd


data_frame = pd.read_csv("data2.csv")
figure, ax = plt.subplots(1, 4, figsize=(16, 8), sharey=True)
g = data_frame.groupby(['<DATE>']).boxplot(column="<OPEN>,<HIGH>,<LOW>,<CLOSE>".split(","), ax=ax) # группировка данных в data_frame по уникальным значениям в столбце <DATE>
# plt.show()
figure.savefig('boxplot.png')