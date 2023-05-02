<?php
/**
 * Retrieve ICD-10 code and name attached to a patient
 * stored in the database.
 * 
 * Runs queries on the PatientDiagnostic-table and echoes
 * the results back to the document as table-rows.
 */


/* Retrives a login-session. */
include $_SERVER['DOCUMENT_ROOT'] . "/php/db.php";

/** Query to run on the database. {@param q} is the PID of a patient. */
$sql = "SELECT ICD, Name FROM PatientDiagnostic WHERE PID LIKE '"; 
$sql = $sql . $_GET['q'] . "%'";

/** Runs query on the database. */
$query = mysqli_query($mysqli, $sql)
            or die (mysqli_error($mysqli));

/** Database response as an array. */
$row = mysqli_fetch_array($query);


/* Iterates over each index in the array received from DB. */
if ($row != false && $row != null) {
    do {
        /** Content of a cell in the Name-column. */
        $name = "<td class='dd'>" . ucwords($row['Name']) . "</td>";
        echo
            "<tr>
                <td class='dh'>{$row['ICD']}</td>
                $name
            </tr>";
        } while ($row = mysqli_fetch_array($query));

        /* Ends table. */
    echo "</table>";
} else {

    /* Returns a message on error */
    echo "<p style='text-align:center'>No records found.</p>";
}

/* Closing connection, leaving the datbase-connection available for later service. */
$mysqli->close();