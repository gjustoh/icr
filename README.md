# Conversion de OCR a ICR

instalar librerias:
Las priemeras librerias a instalar son sobre el opencv para el tratamiento de imagenes, por lo cual, en el proyecto extraido nos apaarecera la siguiente estructura:

![estructura](https://github.com/gjustoh/icr/blob/master/estructura.JPG)

Ahi encontrara un folder llamado lib_opencv, lo cual debera agregarlo al buildPath del proyecto:

![añadir JAR](https://github.com/gjustoh/icr/blob/master/a%C3%B1adirJar.JPG)

primero escoges el opencv-412.jar:

![escoger JAR](https://github.com/gjustoh/icr/blob/master/escogerJAR.JPG)

Abrimos el jar que añadimos:

![abrir JAR](https://github.com/gjustoh/icr/blob/master/abrir%20JAR.JPG)

y donde nos indica `Native library location.(None)` le damos a editar:

![editar JAR](https://github.com/gjustoh/icr/blob/master/editar%20JAR.JPG)

Luego le damos a workspace y escogemos el folder lib_opencv, hi nos saldra 2 carpetas, una es para las versiones de 64 bit y la otra es para las versiones de 86, en mi caso tengo sistema operativo de 64 bits:

![escoger version ](https://github.com/gjustoh/icr/blob/master/escogiendo%20version.JPG)

![confirmar](https://github.com/gjustoh/icr/blob/master/confirmar.JPG)

Terminado este preceso, ya tendra el proyecto con las librerias necesarias para poder ejecutar el programa:

![corriendo](https://github.com/gjustoh/icr/blob/master/corriendo%C3%A7.JPG)
