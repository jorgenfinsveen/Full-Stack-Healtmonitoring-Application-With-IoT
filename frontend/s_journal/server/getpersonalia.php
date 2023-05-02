<?php
/**
 * Retrieve all personalia of a patient stored in the database.
 * 
 * Runs queries on the Patient-table and echoes
 * the results back to the document.
 */


/* Retrives a login-session. */
include $_SERVER['DOCUMENT_ROOT'] . "/php/db.php";

/** Query to run on the database. {@param q} is the PID of a patient. */
$sql = "SELECT DISTINCT PID, name, DateOfBirth, gender, Status, Sensor FROM Patient WHERE PID LIKE '"; 
$sql = $sql . $_GET['q'] . "%'";

/** Runs query on the database. */
$query = mysqli_query($mysqli, $sql)
            or die (mysqli_error($mysqli));

/** Database response as an array. */           
$row = mysqli_fetch_array($query);


/* Iterates over each index in the array received from DB. */
if ($row != false && $row != null) {
    do {
        /** Content of a cell in the Name-column in camelcase. */
        $name = ucwords($row['name']);
        /** Content of a cell in the Sex-column in camelcase. */
        $sex = ucwords($row['gender']);
        /** Content of a cell in the Status-column in uppercase. */
        $status = strtoupper($row['Status']);
        echo 
            "
                <strong>PID</strong>: <i>{$row['PID']}</i><br>
                <strong>Name</strong>: <i>{$name}</i><br>
                <strong>Date of Birth</strong>: <i>{$row['DateOfBirth']}</i><br>
                <strong>Sex</strong>: <i>{$sex}</i><br>
                <br>
                <strong>Status</strong>: <i class='status-{$row['Status']}'>{$status}</i><br>
                <strong>Sensor</strong>: <i>{$row['Sensor']}</i><br>
            ";
        } while ($row = mysqli_fetch_array($query));

        /* Ends div. */
    echo "</div>";
} else {

    /* Returns a message on error */
    echo "<p style='text-align:center'>No records found.</p>";
}

/* Closing connection, leaving the datbase-connection available for later service. */
$mysqli->close();