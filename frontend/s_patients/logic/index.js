/**
 * JavaScript for patients.html.
 * 
 * @author jorgenfinsveen
 * @since 26-10-22
 * @version 18-11-22
*/
window.onload = init;



/** Pattern not allowing numbers. */
const REGEX_NAME = /^([^0-9]*)$/;
/** Path to PHP-server. */
const PHP_PATH = "server/patient/getpatientby";

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

    /* Adding event-listener for the button. */
    document.getElementById('searchBtn').onclick = search;
    document.getElementById('searchBtn').click();
    document.getElementById('searchWord').addEventListener('input', function(e) {search();});

    /* Prevents page reloading on pressing enter. */
    document.getElementById('searchWord').onkeydown = function(ev) {
        if (ev.key === "Enter") {
            ev.preventDefault();
            document.getElementById('searchBtn').click();
        }
    }
}




/**
 * Searching the database for the specified searchword from
 * HTML-input.<br>
 *
 * Parses searchword as parameter to the php-server running queries
 * on the database.
 */
function search() {

    /* Fetching HTML elements. */
    const input     = document.getElementById('searchWord').value.trim().toLowerCase();
    const output    = document.getElementById('output');

    /* Clears previous output. */
    output.innerHTML = "";

    /* Creates an XML-HTTP request for calling the php-servers. */
    const xhttp   = new XMLHttpRequest();
    const request = PHP_PATH + ( (REGEX_NAME.test(input)) ? "name.php?q=" : "pid.php?q=" ) + input;

    /* Setting server response to the output element. */
    xhttp.onload = function() { output.innerHTML = this.responseText; };

    /* Executing the request. */
    xhttp.open('GET', request);
    xhttp.send();
}