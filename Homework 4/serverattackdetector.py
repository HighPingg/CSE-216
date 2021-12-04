from datetime import datetime

class ServerAttackDetector:
    def __init__(self, file: str):
        self.__file = file

    def iterate_files(self):
        fileOpen = open(self.__file, mode = 'r')

        previous = fileOpen.readline()  # Discard header
        previous = fileOpen.readline()

        while True:
            current = fileOpen.readline()
            yield previous, current
            previous = current

    def detect(self):
        # We start at 2 
        count = 2
        for pair in self.iterate_files():
            if ServerAttackDetector.isSus(pair[0], pair[1]):
                return (count, pair[1])
            
            count += 1

    @staticmethod
    def isSus(prev: str, curr: str) -> bool:
        prev = prev.split(",")
        curr = curr.split(",")

        isUDP = curr[2].replace(" ", "") == "UDP" and prev[2].replace(" ", "") == "UDP"
        isSuspicious = curr[12].replace(" ", "") == "suspicious" and prev[12].replace(" ", "") == "suspicious"
        isSmallDuration = float(curr[1].replace(" ", "")) < 1

        # Append '000' at the end of the time strings to turn into microseconds
        prev[0] += "000"
        curr[0] += "000"

        # We now need to check whether the previous attack is within a second of the current.
        # Create previous and current datetime instances
        prev = datetime.strptime(prev[0], "%Y-%m-%d %H:%M:%S.%f")
        curr = datetime.strptime(curr[0], "%Y-%m-%d %H:%M:%S.%f")

        # Subtract times, check if difference is less than a second.
        if (curr - prev).total_seconds() < 1:
            isQuick = True
        else:
            isQuick = False

        return isUDP and isSuspicious and isSmallDuration and isQuick
