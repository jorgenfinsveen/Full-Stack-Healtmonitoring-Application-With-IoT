import random
import numpy as np
from datetime import datetime

# The average fluctuation percentage in BP in adult patients
BP_FLUCTUATION_PERCENTAGE = 0.10

# The average fluctuation percentage in heartrate in adult patients
HEARTRATE_FLUCTUATION = 0.10

"""
This class represents a Patient with random but realistic gender,age, heartrate and blood pressure fluctuation

Template based on Data from "National Health and Nutrition Examination Survey 2007-2010. JAMA. 2011;305(19):1971-1979"
@version 21/11/22
"""
class Patient:

    def __init__(self, gender, age, Systolicfluctuation, Diastolicfluctuation, heartrateFluctuation):
        self.gender = gender
        self.age = age
        self.systolicFluctuation = Systolicfluctuation
        self.diastolicFluctuation = Diastolicfluctuation
        self.heartrateFluctuation = heartrateFluctuation
    
    gender = int
    age = int
    systolicFluctuation = int
    diastolicFluctuation = int
    heartrateFluctuation = int

  
"""
Generates a psuedorandom elderly patient with its respective attributes
"""
def generatePatient():
    gender = random.randrange(0, 2)
    age = random.randrange(60, 90)
    heartrateFluctuationRange = round(random.randrange(60,100) * HEARTRATE_FLUCTUATION)
    systolicfluctuationRange = round((BP_FLUCTUATION_PERCENTAGE * fetchMeanValue(age, gender)[0]))
    diastolicfluctuationRange = round((BP_FLUCTUATION_PERCENTAGE * fetchMeanValue(age, gender)[1]))
    
    return Patient(gender, age, systolicfluctuationRange, diastolicfluctuationRange, heartrateFluctuationRange)


"""
Fetches the mean blood pressure values of a given age and gender of a patient
"""
def fetchMeanValue(age, gender):
    if gender == 0:
        return fetchMeanValuesMan(age)
    else:
        return fetchMeanValuesFemale(age)


"""
Returns the male mean value in systolic and diastolic blood pressure of a given age
Values are extracted from "National Health and Nutrition Examination Survey 2007-2010. JAMA. 2011;305(19):1971-1979"
"""
def fetchMeanValuesFemale(age):
    if age < 64:
        return 130,80 
    elif age < 69:
        return 135,85
    elif age < 74:
        return 140,90
    else:
        return 145,95


"""
Returns the female mean value in systolic and diastolic blood pressure of a given age
Values are extracted from "National Health and Nutrition Examination Survey 2007-2010. JAMA. 2011;305(19):1971-1979"
"""
def fetchMeanValuesMan(age):
    if age < 64:
        return 130,80
    elif age < 69:
        return 140,90
    elif age < 74:
        return 155,95
    elif age < 79:
        return 170,105
    else:
        return 180,115


"""
Creates a sine wave that can be modified according to given parameters

@version 21/11/22
"""
def customizableSineWave(variable, amplitude, frequency, equilibrium):
    return amplitude * np.sin(frequency * variable) + equilibrium


"""
Returns a integer representing a coefficient sign value where the amplitude is directed towards the middle of the amplitude range provided

@version 26/10/22
"""
def stabilizeAmplitude(amplitude, amplitudeRange):
    coefficientSign = 1

    if amplitude > amplitudeRange / 2:  # Amplitude is in upper quadrant of fluctuation range
        coefficientSignValue = random.randrange(0, 3)

        if coefficientSignValue > 0:
            coefficientSign = -1
        else:
            coefficientSign = 1;

    if amplitude < -1 * (amplitudeRange / 2):  # Amplitude is in lower quadrant of fluctuation range
        coefficientSignValue = random.randrange(0, 3)

        if coefficientSignValue > 0:
            coefficientSign = 1
        else:
            coefficientSign = -1

    return coefficientSign

""" 
This method generates an array of Heartrate data

@version 21/11/22 
"""

def generateHeartrateData(seconds, patient):
    
    equilibrium_line = random.randrange(60,100)
    fluctuationRange = patient.heartrateFluctuation
    time_interval = np.arange(1, seconds + 1, 1)
    amplitude = fluctuationRange / 2

    HR_data = []
   
    for t in time_interval:
        frequency = random.uniform(6, 8)

        coefficient = stabilizeAmplitude(amplitude, fluctuationRange)

        amplitude = coefficient * random.randrange(1, fluctuationRange)

        dataValue = customizableSineWave(t, amplitude, frequency, equilibrium_line)

        roundedDataValue = round(dataValue)

        HR_data.append(roundedDataValue)

    return HR_data
    

"""
This method generates an array of Systolic blood pressure data

@version 21/11/22
"""
def generateSystolicData(seconds, patient):

    equilibrium_line = fetchMeanValue(patient.age, patient.gender)[0]
    fluctuationRange = patient.systolicFluctuation
    time_interval = np.arange(1, seconds + 1, 1)
    amplitude = fluctuationRange / 2

    Sysdata = []
   
    for t in time_interval:
        frequency = random.uniform(6, 8)

        coefficient = stabilizeAmplitude(amplitude, fluctuationRange)

        amplitude = coefficient * random.randrange(1, fluctuationRange)

        dataValue = customizableSineWave(t, amplitude, frequency, equilibrium_line)

        roundedDataValue = round(dataValue)

        Sysdata.append(roundedDataValue)

    return Sysdata

"""
This method generates an array of Diastolic blood pressure data

@version 21/11/22
"""
def generateDiastolicData(seconds, patient):

    equilibrium_line = fetchMeanValue(patient.age, patient.gender)[1]
    fluctuationRange = patient.diastolicFluctuation
    time_interval = np.arange(1, seconds + 1, 1)
    amplitude = fluctuationRange / 2

    Diadata = []
   
    for t in time_interval:
        frequency = random.uniform(6, 8)

        coefficient = stabilizeAmplitude(amplitude, fluctuationRange)

        amplitude = coefficient * random.randrange(1, fluctuationRange)

        dataValue = customizableSineWave(t, amplitude, frequency, equilibrium_line)

        roundedDataValue = round(dataValue)

        Diadata.append(roundedDataValue)

    return Diadata


""" 
The method returs an array of time values from the time of the method being called, till the desired duration

@version 22/11/22
"""
def generateTimeframeData(seconds):
    
    SECONDS_IN_A_MINUTE = 60
    
    currentTime = datetime.now()
    
    currentSecond = currentTime.now().second
    
    currentMinute = currentTime.minute
    
    counter = 1
    
    Timedata = [str(currentTime)[0:19]]
    
    while counter < seconds:
                
        newSecond = (currentSecond + counter) % SECONDS_IN_A_MINUTE
        
        newTime = str(currentTime.replace(second= newSecond, minute= currentMinute))
        
        Timedata.append(newTime[0:17])
       
        counter += 1
        
        if newSecond == (SECONDS_IN_A_MINUTE - 1):
            currentMinute += 1
        
    return Timedata    
    
"""
Generates sensor data for pre-defined registered sensor users in a given timeframe

"""
def generateRegisteredSensorData(seconds):
    
    # A list which consists of all the registered sensor serial numbers 
    REGISTERED_SENSOR_SERIALNUMBERS = ["ABCD-EFGH-AC78","ABCD-EFGH-AC79","ABCD-EFGH-AC80","ABCD-EFGH-AC81","ABCD-EFGH-AC82"]

    # A dictonary of all current patients with arbituary fluctuation range 
    REGISTERED_PATIENTS =   {"ABCD-EFGH-AC78": Patient(1,72,3,8,3)
                            ,"ABCD-EFGH-AC79": Patient(0,83,25,22,15) 
                            ,"ABCD-EFGH-AC80": Patient(1,66,3,5,2)
                            ,"ABCD-EFGH-AC81": Patient(0,88,6,2,7)
                            ,"ABCD-EFGH-AC82": Patient(0,71,4,6,5)}
    
    PatientData = []
    
    currentDataEntry = ""
        
    for sensor in REGISTERED_SENSOR_SERIALNUMBERS:
        
        timeframe = generateTimeframeData(seconds)
        
        systolicData = generateSystolicData(seconds, REGISTERED_PATIENTS[sensor])
        
        diastolicData = generateDiastolicData(seconds, REGISTERED_PATIENTS[sensor])
        
        heartrateData = generateHeartrateData(seconds, REGISTERED_PATIENTS[sensor])
        
        dataIndex = 0
        
        while dataIndex < seconds:
            
            currentDataEntry += timeframe[dataIndex] + ";" 
            currentDataEntry += str(systolicData[dataIndex]) + ";"
            currentDataEntry += str(diastolicData[dataIndex]) + ";"
            currentDataEntry += str(heartrateData[dataIndex]) + ";"
            currentDataEntry += sensor
            
            PatientData.append(currentDataEntry)
            
            currentDataEntry = ""
            
            dataIndex += 1
        
        
    
    return PatientData
        

print(generateRegisteredSensorData(2))        
        


