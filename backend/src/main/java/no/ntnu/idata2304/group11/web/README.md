# Web Directory

Represents the web-based user interface of the HealthMonitor application.

## Contents:

### index.html:

- Main page of the application.
- Cannot be located in any of the sub-directories.
- Must be named index.html for the server to recognize it as the initializing file.


### img:

* Sub-directory.
* Contains images used by the web-pages.


### page:

* Sub-directory.
* Contains all HTML-files except index.html.


### style:

* Sub-directory.
* Contains all CSS-files for the different HTML-files.


### logic:

* Sub-directory.
* Contains all js-files for the web-pages.


## Referencing files in HTML/CSS/JS

### CSS to HTML:

- '`<head>'`
  - '<link rel="stylesheet"href="../style/FILENAME.css">'
- '`</head>'`


### JavaScript to HTML:

* '`<head>'`
  * `'<script type="text/javascript" src="../logic/FILENAME.js"></script>'`
* '`</head>`'


### Image to HTML:

* '`<head>'`
  * '`<img src="../img/FILENAME.png">'`
* '`</head>`'
