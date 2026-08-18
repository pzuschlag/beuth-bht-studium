/**
 * Initialisiert die Funktion für die Buttons Play, Pause und Stop. Sowie die Funktion das
 * dass Video mittels Klick auf den Content gestartet werden kann.
 *
 * @author: Wookies
 */
window.addEventListener("load", function(event){
	var videos = document.getElementsByTagName("video");
	var buttons = document.getElementsByTagName("button");

	for(var i = 0; i < videos.length; i++){
    (function(){
		    var video = videos[i];
        var buttonBreak = buttons[i+i];
        var buttonStop = buttons[i*2+1];

        // Play/Pause Button
        buttonBreak.addEventListener("click", function() {
          if (video.paused == false) {
            video.pause();
            buttonBreak.textContent = "Play";
          } else {
            video.play();
            buttonBreak.textContent = "Pause";
          }
        });

        //Stop Button
        buttonStop.addEventListener("click", function () {
        	video.pause();
          video.currentTime = 0;
          buttonBreak.textContent = "Play";
        });

        //Ermöglicht das Starten und Pausiern des Videos durch Klicken auf den Videobereich
        video.addEventListener("click", function () {
			    if (video.paused == false) {
			        video.pause();
			        buttonBreak.textContent = "Play";
			    } else {
			        video.play();
			        buttonBreak.textContent = "Pause";
			    }
        });

				//Ändert den Button wieder auf "Play" nach beenden des Videos
				video.addEventListener("ended", function () {
					buttonBreak.textContent = "Play";
				});
    })();
	}
});
