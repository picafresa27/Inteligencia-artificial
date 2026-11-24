import cv2
import os

# Agrega el nombre de la persona y crea una carpeta para guardar sus fotos si este no existe.
nombre = input("Nombre de la persona: ").strip()
carpeta = os.path.join("ClasificacionRostros/dataset", nombre)
os.makedirs(carpeta, exist_ok=True)

# Se carga el modelo desde OpenCv basador en Haar Cascades.
face_cascade = cv2.CascadeClassifier("ClasificacionRostros/haarcascade_frontalface_default.xml")
video_capture = cv2.VideoCapture(0) # Se inicia la captura de video desde la cámara web.

TOTAL_FOTOS = 100 # Número total de fotos a capturar.
contador = 0 # Contador de fotos capturadas.
frames = 0

print("Mueve la cabeza: izquierda, derecha, arriba, abajo. Presiona q para salir.")

while contador < TOTAL_FOTOS: # Avanzara hasta que se capturen todas las fotos.
    result, frame = video_capture.read()
    if result is False: 
        break
    frames += 1

    # Convierte las imagenes a escala de grises para mejorar la detección de rostros.
    # Analiza las imagenes buscando patrones utilizando el archivo XML de Haar Cascade para detectar rostros.
    # Controla cuanto se reduce la imagen cada pasada, define cuantas secciones iguales 
    # necesita para considerarse un rostro, y el tamaño minimo de pixeles para considerar 
    # un rostro.
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY) 
    faces = face_cascade.detectMultiScale(gray, scaleFactor=1.1, 
                                          minNeighbors=5, minSize=(60, 60)) 
    for (x, y, w, h) in faces:
        cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 255, 0), 2)
        # Guardar una foto cada 3 frames para que no sean todas iguales
        if frames % 3 == 0 and contador < TOTAL_FOTOS:
            rostro = cv2.resize(gray[y:y + h, x:x + w], (200, 200))
            cv2.imwrite(os.path.join(carpeta, f"{contador}.jpg"), rostro)
            contador += 1

    # Mostrar el contador de fotos capturadas en la ventana de video.
    cv2.putText(frame, f"Fotos: {contador}/{TOTAL_FOTOS}", (10, 30),
                cv2.FONT_HERSHEY_SIMPLEX, 1, (0, 255, 0), 2)
    cv2.imshow("Captura de rostros", frame)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

video_capture.release()
cv2.destroyAllWindows()
print(f"Listo: {contador} fotos guardadas en {carpeta}")
