/**
 * Logic for mainpage.
 * 
 * @author jorgenfinsveen
 * @since 26-10-22
 * @version 18-11-22
*/
window.onload = init;

/** Path to php-server. */
const PHP_PATH = "server/getcriticalpatients.php";

/* Importing global code. */
import {setDescription, IMG_PATH} from '/global/logic.js';

/**
 * Initializer for the HTML-page. Gets called when
 * window loading the page.
 */
function init() {
    
    /* Updates details with user-info. */
    setDescription();
    
    /* Adding logo. */
    document.getElementById('logoPic').src = IMG_PATH;

    /* Fill table with patient details. */
    fillTable();
}




/**
 * Fill table with patients which have a status assigned
 * which is not "stable".<br>
 * 
 * Requests all non-stable patients from the server through a
 * XMLHttpRequest.
 */
function fillTable() {

    /** Output element from the page. */
    const output  = document.getElementById('output');
    output.innerHTML = "";

    /* Creates an XML-HTTP request for calling the php-servers. */
    const xhttp   = new XMLHttpRequest();
    xhttp.onload = function() { output.innerHTML = this.responseText; };
    xhttp.open('GET', PHP_PATH);
    xhttp.send();
}