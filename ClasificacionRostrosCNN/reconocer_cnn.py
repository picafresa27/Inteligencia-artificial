import cv2
import numpy as np
import tensorflow as tf

# Cargar el modelo.
model = tf.keras.models.load_model('modelo_cnn_rostros.h5')
class_names = ["ale", "ana", "Jorge", "lesly", "paola"]

# Cargar el detector de rostros de OpenCV
face_cascade = cv2.CascadeClassifier('haarcascade_frontalface_default.xml')

cap = cv2.VideoCapture(0) # abrir camara web.

while True:
    ret, frame = cap.read() # Lee un frame de la cámara web.
    if not ret:
        break
        
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray, 1.3, 5)

    for (x, y, w, h) in faces:
        # Recortar, redimensionar y normalizar el rostro para la CNN
        roi_gray = gray[y:y+h, x:x+w]
        roi_gray = cv2.resize(roi_gray, (100, 100))
        roi_gray = np.expand_dims(roi_gray, axis=-1) # Canal de grises
        roi_gray = np.expand_dims(roi_gray, axis=0)  

        # Predicción
        predictions = model.predict(roi_gray, verbose=0)
        class_idx = np.argmax(predictions[0])
        confidence = np.max(predictions[0]) * 100
        
        # Umbral de confianza (si es menor al 60%, se marca como desconocido)
        nombre = class_names[class_idx] if confidence > 60 else "Desconocido"

        # Dibujar cuadro y texto en pantalla
        cv2.putText(frame, f"{nombre} ({confidence:.1f}%)", (x, y-10), 
                    cv2.FONT_HERSHEY_SIMPLEX, 0.8, (0, 255, 0), 2)
        cv2.rectangle(frame, (x, y), (x+w, y+h), (0, 255, 0), 2)

    cv2.imshow('Reconocimiento Facial con CNN', frame)
    
    # Presiona la tecla 'q' para salir
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()