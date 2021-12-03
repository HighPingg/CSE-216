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

        isUDP = curr[2].replace(" ", "") == "UDP"
        isSuspicious = curr[12].replace(" ", "") == "suspicious" and prev[12].replace(" ", "") == "suspicious"
        isSmallDuration = float(curr[1].replace(" ", "")) < 1

        # We now need to check whether the previous attack is within a second of the current.
        isQuick = True
        prev = prev[0].split(" ")
        curr = curr[0].split(" ")

        # We then split the datetimes into their date and time fields.
        prevDate = prev[0]
        prevTime = prev[1].split(":")

        currDate = curr[0]
        currTime = curr[1].split(":")

        # This stack of if statements will check the dates to be the same. If they are not the same, then
        # prevDate is over a day older than current.
        # Check the years
        if not(currDate == prevDate):
            isQuick = False

        # We can now check the times if the days are equal.
        # Check if the hours and minutes are equal. If not, then they must not be more than a second long.
        if isQuick and not(currTime[0] == prevTime[0] and currTime[1] == prevTime[1]):
            isQuick = False
        
        # We can now check the seconds. We subtract the current seconds from the previous seconds
        # and check that the difference is less than 1.
        elif float(currTime[2]) - float(currTime[2]) > 1:
            isQuick = False

        return isUDP and isSuspicious and isSmallDuration and isQuick
