from serverattackdetector import ServerAttackDetector

d = ServerAttackDetector("./Homework 4/hw4testfile.csv")
a,b = d.detect()

print(a)
print(b)