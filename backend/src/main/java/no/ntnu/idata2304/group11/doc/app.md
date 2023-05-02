# app directory

This directory contains the code which is considered to be the main part of this application.

#### Coordinator.java

    Responsible for handling a new measurement which has been done on a patient. It will analyze the new measurement and compare it to the previous measurements done on the patient, which it will use to determine whether the patients health-status should be catecorized as:

* "stable"
* "unusual"
* "critical"

    The class is also responsible for uploading the new measurement to the database, making the measurement available for the users of the web-application.

    Testing of this class has been done through checking the website and accessing the database directly through phpMyAdmin, as it would be inefficient to run JUnit-tests on this part of the application. If we were to do JUnit-tests, we would have to make more methods in the DataBaseClient class so that we could check that the actions done by the coordinator was executed correctly, which would require tests of those methods as well.


#### Measurement.java

    Works as a wrapper around a measurement received. Makes it easier to access the contents of the measurement through different parts of the application.

    The class is being tested through a JUnit testing class. See the test-directory.
