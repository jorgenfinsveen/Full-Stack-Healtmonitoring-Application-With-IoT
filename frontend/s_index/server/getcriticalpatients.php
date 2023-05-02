<?php
/**
 * Retrieve all patients which have status "unusual" or "critical"
 * from the database.
 * 
 * Run queries on the Patient-table and echoes the results
 * back to the document.
 */


/* Retrives a login-session. */ 
include $_SERVER['DOCUMENT_ROOT'] . "/php/db.php";

/** Query to run on the database. */
$sql = "SELECT DISTINCT PID, name, DateOfBirth, Status
        FROM Patient 
        WHERE Status = 'critical'
        OR Status = 'unusual'
        ORDER BY Status ASC, PID"; 

/** Runs query on the database. */
$query = mysqli_query($mysqli, $sql)
            or die (mysqli_error($mysqli));

/** Database response as an array. */ 
$row = mysqli_fetch_array($query);

/** First part of the content of a cell in the Journal-column. */
$sta = "<i class='journalBtn' onClick='openJournal(this.id)' id='";
/** Last part of the content of a cell in the Journal-column. */
$end = "'>See Journal</i>";


/* Iterates over each index in the array received from DB. */
if ($row != false && $row != null) {
    do {
        /** Journal-button which are to be placed in the Journal-column. */
        $btn = $sta . $row['PID'] . $end;
        /** Content of a cell in the Status-column. */
        $status = "<td><i class=status-" . $row['Status'] . ">" . strtoupper($row['Status']) . "</i></td>";
        /** Content of a cell in the Name-column */
        $name = "<td>" . ucwords($row['name']) . "</td>";

        /* Echoes each result as a new row in the table. */
        echo
            "<tr>
                <td>{$row['PID']}</td>
                $name
                <td>{$row['DateOfBirth']}</td>
                $status
                <td>$btn</td>
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