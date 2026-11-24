import json
import os
import cv2
import numpy as np

# Ruta de la carpeta donde se guardan las fotos de los rostros capturados.
DATASET = "ClasificacionRostros/dataset"

imagenes = (
    []
)  # Matriz de imágenes que se utilizarán para entrenar el modelo.
etiquetas = []  # Numero identificador de cada persona.
nombres = {}  # id -> nombre

# cada carpeta dentro de dataset/ es una persona
for id_persona, nombre in enumerate(sorted(os.listdir(DATASET))):
  carpeta = os.path.join(DATASET, nombre)
  if not os.path.isdir(carpeta):
    continue
  nombres[id_persona] = nombre
  # Entra en cada carpeta y recorre todas las fotos de la persona.
  for archivo in os.listdir(carpeta):
    img = cv2.imread(os.path.join(carpeta, archivo), cv2.IMREAD_GRAYSCALE)
    if img is not None:
      # Agrega la imagen y su etiqueta correspondiente a las listas de imágenes y etiquetas.
      imagenes.append(img)
      etiquetas.append(id_persona)

# Revisa si hay fotos en la carpeta (FUERA DEL CICLO Y VALIDANDO SI ESTÁ VACÍO)
if len(imagenes) == 0:
  print("No hay fotos en la carpeta dataset. Ejecuta primero captura_rostros.py")
  raise SystemExit

# Entrena el modelo de reconocimiento facial utilizando las imágenes y etiquetas recopiladas.
print(f"Entrenando con {len(imagenes)} fotos de {len(nombres)} persona(s)...")
reconocedor = (
    cv2.face.LBPHFaceRecognizer_create()
)  # Algoritmo de reconocimiento facial a utilizar.
reconocedor.train(imagenes, np.array(etiquetas))

reconocedor.write("modelo.yml")
with open("nombres.json", "w", encoding="utf-8") as f:
  json.dump(nombres, f, ensure_ascii=False)

print("Listo: modelo.yml y nombres.json guardados")