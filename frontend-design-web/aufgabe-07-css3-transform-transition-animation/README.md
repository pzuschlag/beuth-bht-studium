# Aufgabe 7 — CSS3 transform, transition, animation

Drei Varianten derselben Seite, je mit einem anderen Animationsansatz:

- **`index.html`** (`styles/styles.css`) — `@keyframes rotate` (Dauerrotation, play/pause) und
  `@keyframes hide` (Ein-/Ausblenden)
- **`index.2.html`** (`styles/styles.2.css`) — `@keyframes scaleUp` mit `transform: scale(...)`
- **`index.3.html`** (`styles/styles.3.css`) — schlichter `transition`-Ansatz ohne Keyframes

`prefixfree.min.js` fängt fehlende Vendor-Prefixes ab. Jede `index*.html` einzeln im Browser
öffnen.
