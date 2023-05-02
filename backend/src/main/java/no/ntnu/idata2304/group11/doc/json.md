# json directory

Contains the expected health-values for patients of different ages and sexes, as well as a JsonHandler-class which is responsible for reading the contents of the file.


#### JsonHandler.java

    Responsible for reading the JSON-file. It contains a single method. This method is supposed to retrieve the expected health-values for a given patient, when the JsonHandler is presented with the age and sex of a patient.

    This class has been tested through JUnit tests. See the test-directory.


#### pressure_chart.json

    A chart represented by a JSON-file containing the health-values expected for patients based on their age and sex. For each age, and for both male and female, the chart contains the values for:

* Expected systolic blood pressure.
* Expected diastolic blood pressure.
* Lower values for expected resting heartrate.
* Upper values for expected resting heartrate.

###### The values are specified by the use of the following references:

* Elderly Blood Pressure Chart: See What’s Normal and What Isn’t!- Organization: CareClinic
  * Author: unknown
  * Date: unknown
  * Location: Toronto, Canada
  * `<link href="https://careclinic.io/elderly-blood-pressure-chart/">source</link>`
  * Download: 18-10-2022
* Puls, Store Norske Lekikon
  * Author: Arnesen, Harald
  * Date: 31-01-2020
  * Location: Oslo, Norway
  * `<link href="https://sml.snl.no/puls">source</link>`
  * Download: 25-11-2022
