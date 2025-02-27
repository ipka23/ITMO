month30 = ["apr", "jun", "sen", "nov", "4", "6", "9", "1"]
after28 = ["29", "30", "31"]
feb = ["feb", "2"]
def cron(s):
    if ((s.split()[3][:3] in feb) + (s.split()[3][-3:] in feb) + (s.split()[3][0] in feb) + (s.split()[3][-1] in feb)) >= 1 \
            and ((s.split()[2][:2] in after28) + (s.split()[2][-2:] in after28)) >= 1 \
            or ((s.split()[3][:3] in month30) + (s.split()[3][-3:] in month30) + (s.split()[3][0] in month30) + (s.split()[3][-1] in month30) >= 1 \
                and ((s.split()[2][:2] == "31") + (s.split()[2][-2:] == "31")) >= 1):
        return print("There is no such date in this month!")
test = "* * 31 1,apr *"
cron(test)