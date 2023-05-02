<?php
/**
 * Retrieve all measurements of a patient stored in the database.
 * 
 * Runs queries on the PatientData-table and returns the result.
 */


/* Setting header to json */
header('Content-Type: application/json');

/* Retrives a login-session. */
include $_SERVER['DOCUMENT_ROOT'] . "/php/db.php";

/** Query to run on the database. {@param q} is the PID of a patient. */
$req = "SELECT Time, Sys, Dia, HR 
        FROM PatientData 
        WHERE PID =";         
$req = $req . $_GET['q'] . " ORDER BY UNIX_TIMESTAMP(Time) ASC";

/** Formated query. */
$query = sprintf($req);

/** Result from the executed query as a tuple. */
$result = $mysqli->query($query);

/** Array containing each element from the tuple. */
$data = array();

/* Append each tuple-element to the data array. */
foreach ($result as $row) $data[] = $row;


/** Free memory associated with the result. */
$result->close();

/* Closing connection, leaving the datbase-connection available for later service. */
$mysqli->close();

/* Return the response. */
print json_encode($data);