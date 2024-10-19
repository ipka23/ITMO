def cron(s):
    if ((s.split()[3][:3] == "feb") + (s.split()[2][:2] == "29") + (s.split()[2][-2:] == "29")+ (s.split()[3][-3:] == "feb") + (s.split()[2][-2:] == "30") + (s.split()[2][:2] == "30")) >= 2:
        return print(s.split()[3][:3])
test = "* * 29 jan,feb *"
cron(test)