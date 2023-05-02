
/** Directory-path for images. */
const IMG_PATH = '/img/logo.png';
/** HTML-tag <br> for linebreak. */
const BR = document.createElement('br');


/**
 * Update the description segment on the webpage.
 */
function setDescription() {
    /** HTML-element displaying user-info. */ 
    const details = document.getElementById('details');

    if (details == null) return;
    try {
        let DRN = sessionStorage.getItem('DRN');
        let UID = sessionStorage.getItem('UID');

        if (DRN == null || UID == null) return;
        
        /* Updates details with user-info. */
        details.innerHTML = "User: Dr. " + eval(DRN);
        details.appendChild(BR);
        details.innerHTML += "Doctor ID: " + sessionStorage.getItem('UID');
    } catch (e) {
        window.location.replace("../index.html");
        return;
    }
}

export {setDescription, IMG_PATH};