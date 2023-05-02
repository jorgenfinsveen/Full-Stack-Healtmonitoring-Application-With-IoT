/**
 * Logic for login-page.
 * 
 * @author jorgenfinsveen
 * @since 16-11-22
 * @version 18-11-22
*/
window.onload = init;



/** Pattern for user-id. */
const REGEX_UID = /^\d+$/;
/** Pattern for password. */
const REGEX_PSW = /^[a-z0-9]+$/i;


let DRN;
let UID


/**
 * Initializer for the HTML-page. Gets called when
 * window loading the page.
 */
function init() {
    /* Adding EventListeners. */
    document.getElementById('login').onclick = login;
    document.getElementById('id').onkeydown = function(ev) {eventHandler(ev);}
    document.getElementById('pswrd').onkeydown = function(ev) {eventHandler(ev);}
}



/**
 * EventHandler for keydown event.
 * 
 * Prevents page reload when pressing enter.
 * 
 * @param {KeyboardEvent} ev Event.
 */
function eventHandler(ev) {
    if (ev.key === "Enter") {
        ev.preventDefault();
        document.getElementById('login').click();
    }
}




/**
 * Login to the application.<br>
 * 
 * Fetches the login credentials given by the
 * user and parses it to the login-server for
 * authentication.
 */
function login() {
    
    /* Fetching username and password. */
    const username = document.getElementById('id').value;
    const password = document.getElementById('pswrd').value;

    /* Stores the username in localStorage. */
    sessionStorage.setItem('UID', username);
    

    /* 
     * Validates the login credentials before calling
     * the login-server.
     * 
     * Improves efficiency and ads an extra layer of security
     * to the application, as it makes it harder to perform
     * SQL-injection attacks.
     */
    if (username.length === 0 || password.length === 0) return;
    if (!REGEX_UID.test(username) || !REGEX_PSW.test(password)) {
        document.getElementById('message').innerHTML = "Invalid username or password";
        clear();
        return;
    }

    /* Creates an XML-HTTP request for calling the login-server. */
    const xhttp   = new XMLHttpRequest();

    /* Handles the server response. */
    xhttp.onload = function() { 
        let response = this.responseText;
        if (response != "null" && response != null) {
            DRN = response;
            accessPage(response)
            clear();
        } else {
            document.getElementById('message').innerHTML = "Invalid username or password";
        }
    };

    /* Executing the request. */
    xhttp.open('GET', "s_login/server/login.php?q=" + username + ";" + password);
    xhttp.send();
}




/** 
 * Redirect to the main-page.
 * 
 * @param {string} name Name of the user.
 */ 
function accessPage(name) {
    sessionStorage.setItem('DRN', name);
    window.location.replace("../../s_index/index.html");
}




/**
 * Clear input fields.
 */
function clear() {
    document.getElementById('id').value = '';
    document.getElementById('pswrd').value = '';
}
