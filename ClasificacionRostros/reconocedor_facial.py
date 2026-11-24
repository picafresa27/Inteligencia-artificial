import cv2

# Usar el clasificador de rostros integrado en OpenCV (no requiere dlib ni archivos externos)
face_cascade = cv2.CascadeClassifier(
    cv2.data.haarcascades + 'haarcascade_frontalface_default.xml'
)

video_capture = cv2.VideoCapture(0)  # activar la camara


def detect_bounding_box(frame):
  gray_image = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

  # Detectar rostros con OpenCV
  faces = face_cascade.detectMultiScale(
      gray_image, scaleFactor=1.1, minNeighbors=5, minSize=(30, 30)
  )

  for x, y, w, h in faces:  # dibujar rectangulo en cada rostro detectado
    cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 255, 0), 2)

  return faces  # retorna los rostros detectados


while True:
  result, video_frame = video_capture.read()  # leer el video
  if result is False:  # si no hay video, salir del ciclo
    break

  faces = detect_bounding_box(video_frame)  # detectar rostros
  cv2.imshow('Rostros detectados en la camara', video_frame)

  if cv2.waitKey(1) & 0xFF == ord('q'):  # salir al presionar la tecla q
    break

video_capture.release()  # liberar la camara
cv2.destroyAllWindows()  # cerrar todas las ventanas