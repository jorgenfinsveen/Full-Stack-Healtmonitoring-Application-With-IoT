<?php
/** 
 * Establishes connection to the datbase server and the
 * requested database. Other server-files will make use
 * of the variable $mysqli to run queries.
 *
 * @author  jorgenfinsveen
 * @since   14-11-2022
 * @version 2o-11-2022
*/

/* Retrives a server-credentials. */ 
include $_SERVER['DOCUMENT_ROOT'] . "/php/session.php";

/* Setting mysqli error reporting mode. */
mysqli_report(MYSQLI_REPORT_ERROR | MYSQLI_REPORT_STRICT);
/** MySQL connection-session to the datbase server. */
$mysqli = mysqli_connect($hostname, $username, $password, $database);
/* Sets the charset of the DB-conenction to UTF-8. */
mysqli_set_charset($mysqli, 'utf8');