<?php
/**
 * Validate login credentials against the usernames and their
 * corresponding passwords which are stored in the database.
 * 
 * Runs queries on the Users-table and informs the website
 * wether the credentials are valid or not.
 */

/* Retrives a login-session. */
include $_SERVER['DOCUMENT_ROOT'] . "/php/db.php";

/** 
 * Splits {@param q} at ; leaving an array consisting of 2 elements, where:
 * * cred[0] User ID
 * * cred[1] Password
 */
$cred = explode(";", $_GET['q']);

/** Query to run on the database. */
$sql = "SELECT name FROM Users WHERE UID = " . "'" . $cred[0] . "'" .
      " AND password = " . "'" . $cred[1] . "'";

/** Runs query on the database. */
$query = mysqli_query($mysqli, $sql)
        or die (mysqli_error($mysqli));

/** Database response as an array. */
$row = mysqli_fetch_array($query);

if ($row != false && $row != null) {
    $response = $row['name'];
} else {
    $response = null;
}

/* Closing connection, leaving the datbase-connection available for later service. */
$mysqli->close();

/* Return the response. */
print json_encode($response);