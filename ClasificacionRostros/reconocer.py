import cv2
import json

UMBRAL = 70  # en LBPH, menor valor = mas parecido. Si pasa de aqui, es "Desconocido"

face_cascade = cv2.CascadeClassifier(cv2.data.haarcascades + 'haarcascade_frontalface_default.xml')

reconocedor = cv2.face.LBPHFaceRecognizer_create()
reconocedor.read("modelo.yml")

with open("nombres.json", encoding="utf-8") as f:
    nombres = {int(k): v for k, v in json.load(f).items()}

video_capture = cv2.VideoCapture(0)

while True:
    result, frame = video_capture.read()
    if result is False:
        break

    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
    faces = face_cascade.detectMultiScale(gray, scaleFactor=1.1,
                                          minNeighbors=5, minSize=(60, 60))

    for (x, y, w, h) in faces:
        rostro = cv2.resize(gray[y:y + h, x:x + w], (200, 200))
        id_persona, distancia = reconocedor.predict(rostro)

        if distancia < UMBRAL:
            texto = nombres.get(id_persona, "Desconocido")
            color = (0, 255, 0)
        else:
            texto = "Desconocido"
            color = (0, 0, 255)

        cv2.rectangle(frame, (x, y), (x + w, y + h), color, 2)
        cv2.putText(frame, f"{texto} ({distancia:.0f})", (x, y - 10),
                    cv2.FONT_HERSHEY_SIMPLEX, 0.8, color, 2)

    cv2.imshow("Reconocimiento facial", frame)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

video_capture.release()
cv2.destroyAllWindows()
