/**
 * JavaScript-file for patients.html.
 * 
 * @author jorgenfinsveen
 * @since 26-10-22
 * @version 18-11-22
*/
window.onload = init;


/** Personalia-div from document. */
const personalia = document.getElementById('personalia');
/** ICD-10-table from document. */
const diagnostic = document.getElementById('icd-output');


import {setDescription, IMG_PATH} from '/global/logic.js';


/**
 * Initializer for the HTML-page. Gets called when
 * window loading the page.
 */
function init() {

    /** Patient-id for patient chosen. */
    let pid = localStorage.getItem('PID');
    
    /* Updates details with user-info. */
    setDescription();

    /* Adding logo. */
    document.getElementById('logoPic').src = IMG_PATH;

    getPersonalia();
    getDiagnostics();
}



/**
 * Inserts patient personalia fetched from the database.
 */
function getPersonalia() {
    const xhttp   = new XMLHttpRequest();
    const request = "server/getpersonalia.php?q=" + localStorage.getItem('PID');

    /* Setting server response to the output element. */
    xhttp.onload = function() { personalia.innerHTML = this.responseText; };

    /* Executing the request. */
    xhttp.open('GET', request);
    xhttp.send();
}



/**
 * Populates the ICD-10-table with the diagnoses which
 * the patient is diagnosed with.
 */
function getDiagnostics() {

    const xhttp   = new XMLHttpRequest();
    const request = "server/getdiagnostics.php?q=" + localStorage.getItem('PID');

    /* Setting server response to the output element. */
    xhttp.onload = function() { diagnostic.innerHTML = this.responseText; };

    /* Executing the request. */
    xhttp.open('GET', request);
    xhttp.send();
}